package de.paluch.burndown.sync.jira;

import java.text.MessageFormat;
import java.util.ArrayList;
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
 * Jira to Sprint-Model Synchronizer. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class JiraSprintSyncWorker {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JiraClient client;

    /**
     * Create Sprint Sync with JIRA Client.
     * 
     * @param baseUrl
     * @param username
     * @param password
     * @throws JiraSyncException
     */
    public JiraSprintSyncWorker(String baseUrl, String username, String password) throws JiraSyncException {

        super();

        try {
            client = new JiraClient(baseUrl);
            if (username != null && !username.trim().equals("")) {
                client.login(username, password);
            }
        } catch (Exception e) {
            throw new JiraSyncException(e);
        }
    }

    /**
     * @param client
     *            the client to set
     */
    public void setClient(JiraClient client) {

        this.client = client;
    }

    /**
     * Sync Jira Sprint to Sprint Effort.
     * 
     * @param teamSync
     * @param sprint
     * @throws JiraSyncException
     */
    public void syncSprint(JiraTeamSync teamSync, Sprint sprint) throws JiraSyncException {

        try {
            String versionId = MessageFormat.format(teamSync.getSprintVersionNameScheme(), sprint.getId());

            List<String> issueKeys = client.findSprintIssues(teamSync.getProjectKey(), versionId);

            if (issueKeys.isEmpty()) {
                logger.info("No issues found for sync " + sprint.getId());
            }

            List<JiraRestIssue> issues = fetchIssues(issueKeys);

            calculateSprintGoal(issues, teamSync, sprint);

            resetSprintEfforts(sprint.getEffort(), teamSync.isUnplanned());
            calculateBurnedOnDayBasis(issues, teamSync, sprint.getId(), sprint.getEffort());

        } catch (Exception e) {
            throw new JiraSyncException(e);
        }

    }

    /**
     * Calculate Burndown from Jira Issues.
     * 
     * @param issues
     * @param teamSync
     * @param effort
     */
    private void calculateBurnedOnDayBasis(List<JiraRestIssue> issues, JiraTeamSync teamSync, String sprintId,
            List<SprintEffort> effort) {

        for (JiraRestIssue issue : issues) {

            calculateEffort(teamSync, sprintId, effort, issue);

        }

    }

    /**
     * Calculate Effort on planned/unplanned items.
     * 
     * @param teamSync
     * @param sprintId
     * @param effort
     * @param issue
     */
    private void calculateEffort(JiraTeamSync teamSync, String sprintId, List<SprintEffort> effort, JiraRestIssue issue) {

        if (teamSync.isUnplanned()
                && JiraIssueHelper.isUnplanned(teamSync.getUnplannedFlagFieldId(), teamSync.getUnplannedFlagName(),
                        issue)) {
            addWorklogToSprint(sprintId, effort, issue, true);
        } else {

            double planned = JiraIssueHelper.getOriginalEstimate(teamSync, issue);

            if (teamSync.getEffortMode() == EffortMode.STORY_POINTS) {
                planned = JiraIssueHelper.getStoryPoints(teamSync.getStoryPointsFieldId(), issue);
            }

            Date resolutionDate = issue.getFields().getResolutiondate();
            if (resolutionDate == null) {
                logger.info("Issue " + issue.getKey() + " unresolved (Value: " + planned + ")");
                addWorklogToSprint(sprintId, effort, issue, false);
                return;
            } else

            if (JiraIssueHelper.isInSprint(resolutionDate, effort)) {

                updateSprintEffort(effort, resolutionDate, planned, 0, sprintId, issue.getKey());
            } else {
                logger.info("Issue " + issue.getKey() + " resolved out of sprint (" + resolutionDate + ", Value: "
                        + planned + ")");
            }
        }
    }

    private void addWorklogToSprint(String sprintId, List<SprintEffort> effort, JiraRestIssue issue,
            boolean addToUnplanned) {
        Map<Date, Double> unplannedEffort = getWorklog(issue);
        Set<Entry<Date, Double>> set = unplannedEffort.entrySet();
        for (Entry<Date, Double> entry : set) {

            if (addToUnplanned) {
                updateSprintEffort(effort, entry.getKey(), 0, entry.getValue(), sprintId, issue.getKey());
            } else {
                updateSprintEffort(effort, entry.getKey(), entry.getValue(), 0, sprintId, issue.getKey());
            }

        }
    }

    /**
     * Calculate Goal.
     * 
     * @param issues
     * @param teamSync
     * @param sprint
     */
    private void calculateSprintGoal(List<JiraRestIssue> issues, JiraTeamSync teamSync, Sprint sprint) {

        double goal = 0;

        for (JiraRestIssue issue : issues) {

            if (teamSync.isUnplanned()
                    && JiraIssueHelper.isUnplanned(teamSync.getUnplannedFlagFieldId(), teamSync.getUnplannedFlagName(),
                            issue)) {
                logger.info("Unplaned " + issue.getKey());
                continue;
            }

            if (issue.getFields().getResolutiondate() != null) {
                Date resolutionDate = issue.getFields().getResolutiondate();

                if (resolutionDate != null) {
                    if (!JiraIssueHelper.isInSprint(resolutionDate, sprint.getEffort())) {
                        logger.info("Resolved, but not in that Sprint " + issue.getKey());
                        continue;
                    }
                }
            }

            double itemValue = JiraIssueHelper.getOriginalEstimate(teamSync, issue);
            logger.info("Value for " + issue.getKey() + " is: " + itemValue);
            if (teamSync.getEffortMode() == EffortMode.STORY_POINTS) {
                itemValue = JiraIssueHelper.getStoryPoints(teamSync.getStoryPointsFieldId(), issue);
            }

            goal += itemValue;
        }

        sprint.setPlanned(goal);

    }

    /**
     * Load Issues from Jira.
     * 
     * @param issueKeys
     * @return List<JiraRestIssue>
     */
    private List<JiraRestIssue> fetchIssues(List<String> issueKeys) {

        List<JiraRestIssue> result = new ArrayList<JiraRestIssue>();
        for (String issueKey : issueKeys) {
            logger.info("Fetching issue " + issueKey);
            JiraRestIssue issue = client.getIssue(issueKey);
            if (issue != null) {
                result.add(issue);
            } else {
                logger.info("Cannot retrieve issue " + issueKey);
            }
        }
        return result;
    }

    /**
     * Retrieve Worklog to Date.
     * 
     * @param issue
     * @return
     */
    private Map<Date, Double> getWorklog(JiraRestIssue issue) {

        Map<Date, Double> result = new HashMap<Date, Double>();

        if (issue.getFields().getWorklog() == null) {
            return result;
        }

        for (JiraRestWorklogValue worklog : issue.getFields().getWorklog().getWorklogs()) {
            Double time = Math.round(worklog.getTimeSpentSeconds() / 360d) / 10d;
            if (result.containsKey(worklog.getStarted())) {
                time += result.get(worklog.getStarted());
            }

            result.put(worklog.getStarted(), time);

        }
        return result;
    }

    /**
     * Reset Effort Model.
     * 
     * @param effort
     * @param unplanned
     */
    private void resetSprintEfforts(List<SprintEffort> effort, boolean unplanned) {

        for (SprintEffort sprintEffort : effort) {
            sprintEffort.setBurned(0d);
            if (unplanned) {
                sprintEffort.setUnplanned(0d);
            }
        }
    }

    /**
     * Push Effort to Sprint-Model.
     * 
     * @param effort
     * @param resolutionDate
     * @param planned
     * @param unplanned
     * @param sprintId
     * @param issueKey
     */
    private void updateSprintEffort(List<SprintEffort> effort, Date resolutionDate, double planned, double unplanned,
            String sprintId, String issueKey) {

        DateDayComparator comparator = new DateDayComparator();
        SprintEffort lastFound = null;

        int lastComparison = 1;
        for (SprintEffort sprintEffort : effort) {
            int comparison = comparator.compare(resolutionDate, sprintEffort.getDate());

            if (lastComparison > 0 && comparison <= 0) {
                lastFound = sprintEffort;
            }

            lastComparison = comparison;
        }
        if (lastFound == null) {
            lastFound = effort.get(effort.size() - 1);
        }

        if (lastFound == null) {
            logger.info("Cannot add efforts for Issue " + issueKey + ", Sprint " + sprintId + " for Date "
                    + resolutionDate + " because date is out of range.");
        } else {
            lastFound.setBurned(lastFound.getBurned() + planned);
            lastFound.setUnplanned(lastFound.getUnplanned() + unplanned);
        }

    }
}
