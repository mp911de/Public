package de.paluch.burndown.sync.jira;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.paluch.burndown.model.SprintEffort;
import de.paluch.burndown.sync.jira.client.JiraRestIssue;
import de.paluch.burndown.sync.jira.client.JiraRestTimetracking;
import de.paluch.burndown.sync.jira.model.EffortMode;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 * Helper-Methods. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 26.03.2012 <br>
 * <br>
 */
public class JiraIssueHelper {

    /**
     * @param teamSync
     * @param issue
     * @return original Estimate from Issue.
     */
    public static double getOriginalEstimate(JiraTeamSync teamSync, JiraRestIssue issue) {

        JiraRestTimetracking timetracking = issue.getFields().getTimetracking();
        if (teamSync.getEffortMode() == EffortMode.HOURS && timetracking != null && timetracking.getValue() != null) {
            return timetracking.getValue().getOriginalEstimate() / 60d;
        }
        return 0;
    }

    /**
     * @param effort
     * @return
     */
    public static Date getSprintEnd(List<SprintEffort> effort) {

        Date max = new Date(0);
        for (SprintEffort sprintEffort : effort) {
            if (sprintEffort.getDate().after(max)) {
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
    public static Date getSprintStart(List<SprintEffort> effort) {

        Date min = new Date(Long.MAX_VALUE);
        for (SprintEffort sprintEffort : effort) {
            if (sprintEffort.getDate().before(min)) {
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
    public static double getStoryPoints(String storyPointsFieldId, JiraRestIssue issue) {

        Map<String, Object> storyPointsField = (Map<String, Object>) issue.getFields().properties()
                .get(storyPointsFieldId);

        if (storyPointsField != null) {
            Object value = storyPointsField.get("value");
            if (value != null) {
                return Double.parseDouble(value.toString().replace(',', '.').trim());
            }
        }

        return 0;
    }

    /**
     * @param resolutionDate
     * @param effort
     * @return
     */
    public static boolean isInSprint(Date resolutionDate, List<SprintEffort> effort) {

        Date min = getSprintStart(effort);
        Date max = new Date(Long.MAX_VALUE);

        if (resolutionDate.after(min) && resolutionDate.before(max)) {
            return true;
        }
        return false;
    }

    /**
     * @param unplannedFlagFieldId
     * @param unplannedFlagValue
     * @param issue
     * @return true if item is unplanned
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean isUnplanned(String unplannedFlagFieldId, String unplannedFlagValue, JiraRestIssue issue) {

        Map<String, Object> flagField = (Map<String, Object>) issue.getFields().properties().get(unplannedFlagFieldId);

        if (flagField == null) {
            return false;
        }

        List<String> value = (List) flagField.get("value");

        if (value == null) {
            return false;
        }

        for (String fieldValueName : value) {
            if (fieldValueName != null && fieldValueName.equalsIgnoreCase(unplannedFlagValue)) {
                return true;
            }
        }

        return false;
    }

}
