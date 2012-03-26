package de.paluch.burndown.sync.jira;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;
import de.paluch.burndown.sync.jira.client.JiraClient;
import de.paluch.burndown.sync.jira.client.JiraRestIssue;
import de.paluch.burndown.sync.jira.client.JiraRestWorklogValue;
import de.paluch.burndown.sync.jira.model.EffortMode;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 * Jira to Sprint-Model Synchronizer.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSprintSyncWorker
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private JiraClient client;

	/**
	 * Create Sprint Sync with JIRA Client.
	 * @param baseUrl
	 * @param username
	 * @param password
	 * @throws JiraSyncException
	 */
	public JiraSprintSyncWorker(String baseUrl, String username, String password) throws JiraSyncException
	{

		super();

		try
		{
			client = new JiraClient(baseUrl);
			if (username != null && !username.trim().equals(""))
			{
				client.login(username, password);
			}
		}
		catch (Exception e)
		{
			throw new JiraSyncException(e);
		}
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(JiraClient client)
	{

		this.client = client;
	}

	/**
	 * Sync Jira Sprint to Sprint Effort.
	 * @param teamSync
	 * @param sprint
	 * @throws JiraSyncException
	 */
	public void syncSprint(JiraTeamSync teamSync, Sprint sprint) throws JiraSyncException
	{

		try
		{
			String versionId = MessageFormat.format(teamSync.getSprintVersionNameScheme(), sprint.getId());

			List<String> issueKeys = client.findSprintIssues(teamSync.getProjectKey(), versionId);

			if (issueKeys.isEmpty())
			{
				logger.info("No issues found for sync " + sprint.getId());
			}

			List<JiraRestIssue> issues = fetchIssues(issueKeys);

			calculateSprintGoal(issues, teamSync, sprint);

			resetSprintEfforts(sprint.getEffort(), teamSync.isUnplanned());
			calculateBurnedOnDayBasis(issues, teamSync, sprint.getId(), sprint.getEffort());

		}
		catch (Exception e)
		{
			throw new JiraSyncException(e);
		}

	}

	/**
	 * Calculate Burndown from Jira Issues.
	 * @param issues
	 * @param teamSync
	 * @param effort
	 */
	private void calculateBurnedOnDayBasis(List<JiraRestIssue> issues, JiraTeamSync teamSync, String sprintId,
			List<SprintEffort> effort)
	{

		for (JiraRestIssue issue : issues)
		{

			if (issue.getFields().getResolution() == null)
			{
				continue;
			}

			calculateEffort(teamSync, sprintId, effort, issue);

		}

	}

	/**
	 * Calculate Effort on planned/unplanned items.
	 * @param teamSync
	 * @param sprintId
	 * @param effort
	 * @param issue
	 */
	private void calculateEffort(JiraTeamSync teamSync, String sprintId, List<SprintEffort> effort,
			JiraRestIssue issue)
	{

		if (teamSync.isUnplanned()
			&& isUnplanned(teamSync.getUnplannedFlagFieldId(), teamSync.getUnplannedFlagName(), issue))
		{
			Map<Date, Integer> unplannedEffort = getWorklog(issue);
			Set<Entry<Date, Integer>> set = unplannedEffort.entrySet();
			for (Entry<Date, Integer> entry : set)
			{
				updateSprintEffort(effort, entry.getKey(), 0, entry.getValue(), sprintId, issue.getKey());
			}
		}
		else
		{

			Date resolutionDate = issue.getFields().getResolutiondate().getValue();
			if (resolutionDate == null)
			{
				return;
			}

			if (isInSprint(resolutionDate, effort))
			{

				double planned = getOriginalEstimate(teamSync, issue);

				if (teamSync.getEffortMode() == EffortMode.STORY_POINTS)
				{
					planned = getStoryPoints(teamSync.getStoryPointsFieldId(), issue);
				}
				updateSprintEffort(effort, resolutionDate, planned, 0, sprintId, issue.getKey());
			}
		}
	}

	/**
	 * Calculate Goal.
	 * @param issues
	 * @param teamSync
	 * @param sprint
	 */
	private void calculateSprintGoal(List<JiraRestIssue> issues, JiraTeamSync teamSync, Sprint sprint)
	{

		double goal = 0;

		for (JiraRestIssue issue : issues)
		{

			if (teamSync.isUnplanned()
				&& isUnplanned(teamSync.getUnplannedFlagFieldId(), teamSync.getUnplannedFlagName(), issue))
			{
				continue;
			}

			Date resolutionDate = issue.getFields().getResolutiondate().getValue();
			if (resolutionDate != null)
			{
				if (!isInSprint(resolutionDate, sprint.getEffort()))
				{
					continue;
				}
			}

			double itemValue = getOriginalEstimate(teamSync, issue);

			if (teamSync.getEffortMode() == EffortMode.STORY_POINTS)
			{
				itemValue = getStoryPoints(teamSync.getStoryPointsFieldId(), issue);
			}

			goal += itemValue;
		}

		sprint.setPlanned(goal);

	}

	/**
	 * Load Issues from Jira.
	 * @param issueKeys
	 * @return List<JiraRestIssue>
	 */
	private List<JiraRestIssue> fetchIssues(List<String> issueKeys)
	{

		List<JiraRestIssue> result = new ArrayList<JiraRestIssue>();
		for (String issueKey : issueKeys)
		{
			logger.info("Fetching issue " + issueKey);
			JiraRestIssue issue = client.getIssue(issueKey);
			if (issue != null)
			{
				result.add(issue);
			}
		}
		return result;
	}

	/**
	 * @param teamSync
	 * @param issue
	 * @return original Estimate from Issue.
	 */
	private double getOriginalEstimate(JiraTeamSync teamSync, JiraRestIssue issue)
	{

		if (teamSync.getEffortMode() == EffortMode.HOURS && issue.getFields().getTimetracking() != null)
		{
			return issue.getFields().getTimetracking().getValue().getOriginalEstimate() / 60d;
		}
		return 0;
	}

	/**
	 * @param effort
	 * @return
	 */
	private Date getSprintEnd(List<SprintEffort> effort)
	{

		Date max = new Date(0);
		for (SprintEffort sprintEffort : effort)
		{
			if (sprintEffort.getDate().after(max))
			{
				max = sprintEffort.getDate();
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(max);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, 0);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * @param effort
	 * @return
	 */
	private Date getSprintStart(List<SprintEffort> effort)
	{

		Date min = new Date(Long.MAX_VALUE);
		for (SprintEffort sprintEffort : effort)
		{
			if (sprintEffort.getDate().before(min))
			{
				min = sprintEffort.getDate();
			}
		}
		return min;
	}

	/**
	 * @param storyPointsFieldId
	 * @param issue
	 * @return Storypoints
	 */
	@SuppressWarnings("unchecked")
	private double getStoryPoints(String storyPointsFieldId, JiraRestIssue issue)
	{

		Map<String, Object> storyPointsField = (Map<String, Object>) issue.getFields().properties()
				.get(storyPointsFieldId);

		if (storyPointsField != null)
		{
			Object value = storyPointsField.get("value");
			if (value != null)
			{
				return Double.parseDouble(value.toString().replace(',', '.').trim());
			}
		}

		return 0;
	}

	/**
	 * Retrieve Worklog to Date.
	 * @param issue
	 * @return
	 */
	private Map<Date, Integer> getWorklog(JiraRestIssue issue)
	{

		Map<Date, Integer> result = new HashMap<Date, Integer>();

		if (issue.getFields().getWorklog() == null)
		{
			return result;
		}

		for (JiraRestWorklogValue worklog : issue.getFields().getWorklog().getValue())
		{
			Integer time = Math.round(worklog.getMinutesSpent() / 60f);
			if (result.containsKey(worklog.getStarted()))
			{
				time += result.get(worklog.getStarted());
			}

			result.put(worklog.getStarted(), time);

		}
		return result;
	}
	/**
	 * @param resolutionDate
	 * @param effort
	 * @return
	 */
	private boolean isInSprint(Date resolutionDate, List<SprintEffort> effort)
	{

		Date min = getSprintStart(effort);
		Date max = new Date(Long.MAX_VALUE);

		if (resolutionDate.after(min) && resolutionDate.before(max))
		{
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param unplannedFlagFieldId
	 * @param unplannedFlagValue
	 * @param issue
	 * @return true if item is unplanned
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private boolean isUnplanned(String unplannedFlagFieldId, String unplannedFlagValue, JiraRestIssue issue)
	{

		Map<String, Object> flagField = (Map<String, Object>) issue.getFields().properties()
				.get(unplannedFlagFieldId);

		if (flagField == null)
		{
			return false;
		}

		List<String> value = (List) flagField.get("value");

		if (value == null)
		{
			return false;
		}

		for (String fieldValueName : value)
		{
			if (fieldValueName != null && fieldValueName.equalsIgnoreCase(unplannedFlagValue))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Reset Effort Model.
	 * @param effort
	 * @param unplanned
	 */
	private void resetSprintEfforts(List<SprintEffort> effort, boolean unplanned)
	{

		for (SprintEffort sprintEffort : effort)
		{
			sprintEffort.setBurned(0d);
			if (unplanned)
			{
				sprintEffort.setUnplanned(0d);
			}
		}
	}

	/**
	 * Push Effort to Sprint-Model.
	 * @param effort
	 * @param resolutionDate
	 * @param planned
	 * @param unplanned
	 * @param sprintId
	 * @param issueKey
	 */
	private void updateSprintEffort(List<SprintEffort> effort, Date resolutionDate, double planned, double unplanned,
			String sprintId, String issueKey)
	{

		DateDayComparator comparator = new DateDayComparator();
		SprintEffort lastFound = null;

		int lastComparison = 1;
		for (SprintEffort sprintEffort : effort)
		{
			int comparison = comparator.compare(resolutionDate, sprintEffort.getDate());

			if (lastComparison > 0 && comparison <= 0)
			{
				lastFound = sprintEffort;
			}

			lastComparison = comparison;
		}
		if (lastFound == null)
		{
			lastFound = effort.get(effort.size() - 1);
		}

		if (lastFound == null)
		{
			logger.info("Cannot add efforts for Issue " + issueKey + ", Sprint " + sprintId + " for Date "
						+ resolutionDate + " because date is out of range.");
		}
		else
		{
			lastFound.setBurned(lastFound.getBurned() + planned);
			lastFound.setUnplanned(lastFound.getUnplanned() + unplanned);
		}

	}
}
