package de.paluch.burndown.sync.jira.client;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraRestFields {

    private JiraRestTimetracking timetracking;
    private JiraRestResolution resolution;
    private JiraRestDateField resolutiondate;
    private JiraRestWorklog worklog;
    private final Map<String, Object> properties = new HashMap<String, Object>();

    @JsonAnySetter
    public void add(String key, Object value) { // name does not matter

        properties.put(key, value);
    }

    /**
     * @return the resolution
     */
    public JiraRestResolution getResolution() {

        return resolution;
    }

    /**
     * @return the resolutiondate
     */
    public JiraRestDateField getResolutiondate() {

        return resolutiondate;
    }

    /**
     * @return the timetracking
     */
    public JiraRestTimetracking getTimetracking() {

        return timetracking;
    }

    /**
     * @return the worklog
     */
    public JiraRestWorklog getWorklog() {

        return worklog;
    }

    @JsonAnyGetter
    public Map<String, Object> properties() { // note: for 1.6.0 MUST use non-getter name; otherwise doesn't matter

        return properties;
    }

    /**
     * @param resolution
     *            the resolution to set
     */
    public void setResolution(JiraRestResolution resolution) {

        this.resolution = resolution;
    }

    /**
     * @param resolutiondate
     *            the resolutiondate to set
     */
    public void setResolutiondate(JiraRestDateField resolutiondate) {

        this.resolutiondate = resolutiondate;
    }

    /**
     * @param timetracking
     *            the timetracking to set
     */
    public void setTimetracking(JiraRestTimetracking timetracking) {

        this.timetracking = timetracking;
    }

    /**
     * @param worklog
     *            the worklog to set
     */
    public void setWorklog(JiraRestWorklog worklog) {

        this.worklog = worklog;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [timetracking=" + timetracking + ", resolutiondate=" + resolutiondate
                + ", worklog=" + worklog + "]";
    }

}
