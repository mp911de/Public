package de.paluch.burndown.sync.jira;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rpc.soap.client.RemoteAuthenticationException;
import com.atlassian.jira.rpc.soap.client.RemoteCustomFieldValue;
import com.atlassian.jira.rpc.soap.client.RemoteException;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemotePermissionException;
import com.atlassian.jira.rpc.soap.client.RemoteValidationException;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;
import de.paluch.burndown.sync.jira.model.EffortMode;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSprintSync
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private JiraTeamSync teamSync;
	private JiraClient client;

	/**
	 * Create Sprint Sync with JIRA Client.
	 * @param baseUrl
	 * @param username
	 * @param password
	 * @throws JiraSyncException
	 */
	public JiraSprintSync(String baseUrl, String username, String password) throws JiraSyncException
	{

		super();

		try
		{
			client = new JiraClient(baseUrl);
			client.login(username, password);
		}
		catch (Exception e)
		{
			throw new JiraSyncException(e);
		}
	}

	/**
	 * JIRA Session Logout.
	 * @throws JiraSyncException
	 */
	public void logout() throws JiraSyncException
	{

		try
		{
			client.logout();
		}
		catch (Exception e)
		{
			throw new JiraSyncException(e);
		}
	}

	/**
	 * Sync Jira Sprint to Sprint Effort.
	 * @param sprint
	 * @throws JiraSyncException
	 */
	public void syncSprint(Sprint sprint) throws JiraSyncException
	{

		try
		{
			String versionId = MessageFormat.format(teamSync.getSprintVersionNameScheme(), sprint.getId());
			RemoteIssue issues[] = client.findSprintIssues(teamSync.getProjectKey(), versionId);
			if (issues.length == 0)
			{
				logger.info("No issues found for sync " + sprint.getId());
			}
			calculateSprintGoal(issues, sprint);

			resetSprintEfforts(sprint.getEffort(), teamSync.isUnplanned());
			calculateBurnedOnDayBasis(issues, sprint.getId(), sprint.getEffort());

		}
		catch (Exception e)
		{
			throw new JiraSyncException(e);
		}

	}

	/**
	 * @param issues
	 * @param effort
	 * @throws java.rmi.RemoteException
	 * @throws RemoteException
	 * @throws RemoteAuthenticationException
	 * @throws RemotePermissionException
	 */
	private void calculateBurnedOnDayBasis(RemoteIssue[] issues, String sprintId, List<SprintEffort> effort)
			throws RemotePermissionException, RemoteAuthenticationException, RemoteException, java.rmi.RemoteException
	{

		for (RemoteIssue remoteIssue : issues)
		{

			if (remoteIssue.getResolution() == null)
			{
				continue;
			}

			Date resolutionDate = client.getResolutionDate(remoteIssue.getKey());
			if (resolutionDate == null)
			{
				continue;
			}

			calculateEffort(sprintId, effort, remoteIssue, resolutionDate);

		}

	}

	/**
	 * @param sprintId
	 * @param effort
	 * @param remoteIssue
	 * @param resolutionDate
	 * @throws RemotePermissionException
	 * @throws RemoteValidationException
	 * @throws RemoteException
	 * @throws RemoteException
	 */
	private void calculateEffort(String sprintId, List<SprintEffort> effort, RemoteIssue remoteIssue,
			Date resolutionDate) throws RemotePermissionException, RemoteValidationException, RemoteException,
			java.rmi.RemoteException
	{

		if (teamSync.isUnplanned() && isUnplanned(remoteIssue))
		{
			Map<Date, Integer> unplannedEffort = client.getWorklog(remoteIssue.getKey());
			Set<Entry<Date, Integer>> set = unplannedEffort.entrySet();
			for (Entry<Date, Integer> entry : set)
			{
				updateSprintEffort(effort, entry.getKey(), 0, entry.getValue(), sprintId, remoteIssue.getKey());
			}
		}
		else
		{
			double planned = 0;
			if (teamSync.getEffortMode() == EffortMode.HOURS)
			{
				planned = client.getOriginalEstimate(remoteIssue.getKey()) / 60d;
			}

			if (teamSync.getEffortMode() == EffortMode.STORY_POINTS)
			{
				planned = getStoryPoints(remoteIssue);
			}
			updateSprintEffort(effort, resolutionDate, planned, 0, sprintId, remoteIssue.getKey());
		}
	}
	/**
	 * @param issues
	 * @param sprint
	 */
	private void calculateSprintGoal(RemoteIssue[] issues, Sprint sprint)
	{

		double goal = 0;

		for (RemoteIssue remoteIssue : issues)
		{

			if (teamSync.isUnplanned() && isUnplanned(remoteIssue))
			{
				continue;
			}

			double itemValue = 0;

			if (teamSync.getEffortMode() == EffortMode.HOURS)
			{
				itemValue = client.getOriginalEstimate(remoteIssue.getKey()) / 60d;
			}

			if (teamSync.getEffortMode() == EffortMode.STORY_POINTS)
			{
				itemValue = getStoryPoints(remoteIssue);
			}

			goal += itemValue;
		}

		sprint.setPlanned(goal);

	}

	/**
	 * @param remoteIssue
	 * @return Storypoints
	 */
	private double getStoryPoints(RemoteIssue remoteIssue)
	{

		RemoteCustomFieldValue values[] = remoteIssue.getCustomFieldValues();
		for (RemoteCustomFieldValue remoteCustomFieldValue : values)
		{
			if (!remoteCustomFieldValue.getCustomfieldId().equals(teamSync.getStoryPointsFieldId()))
			{
				continue;
			}

			String fieldValues[] = remoteCustomFieldValue.getValues();
			for (String fieldValue : fieldValues)
			{
				if (fieldValue != null)
				{
					return Double.parseDouble(fieldValue.replace(',', '.').trim());
				}
			}
		}

		return 0;
	}

	/**
	 * @param remoteIssue
	 * @return true if item is unplanned.
	 */
	private boolean isUnplanned(RemoteIssue remoteIssue)
	{

		RemoteCustomFieldValue values[] = remoteIssue.getCustomFieldValues();
		for (RemoteCustomFieldValue remoteCustomFieldValue : values)
		{
			if (!remoteCustomFieldValue.getCustomfieldId().equals(teamSync.getUnplannedFlagFieldId()))
			{
				continue;
			}

			String fieldValues[] = remoteCustomFieldValue.getValues();
			for (String fieldValue : fieldValues)
			{
				if (fieldValue.equalsIgnoreCase(teamSync.getUnplannedFlagFieldId()))
				{
					return true;
				}
			}

		}

		return false;
	}

	/**
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
	 *
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
		boolean updated = false;

		for (SprintEffort sprintEffort : effort)
		{
			if (comparator.compare(resolutionDate, sprintEffort.getDate()) != 0)
			{
				continue;
			}

			sprintEffort.setBurned(sprintEffort.getBurned() + planned);
			sprintEffort.setUnplanned(sprintEffort.getUnplanned() + unplanned);
			updated = true;
			break;
		}

		if (!updated)
		{
			logger.info("Cannot add efforts for Issue " + issueKey + ", Sprint " + sprintId + " for Date "
						+ resolutionDate + " because date is out of range.");
		}

	}
}
