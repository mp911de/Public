package de.paluch.burndown.sync.jira.client;

import java.util.Date;
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
    private Date resolutiondate;
    private JiraRestWorklog worklog;
    private final Map<String, Object> properties = new HashMap<String, Object>();
    private long timeoriginalestimate;

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
    public Date getResolutiondate() {

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
    public void setResolutiondate(Date resolutiondate) {

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
     * @return the timeoriginalestimate
     */
    public long getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    /**
     * @param timeoriginalestimate
     *            the timeoriginalestimate to set
     */
    public void setTimeoriginalestimate(long timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((resolution == null) ? 0 : resolution.hashCode());
        result = prime * result + ((resolutiondate == null) ? 0 : resolutiondate.hashCode());
        result = prime * result + ((timetracking == null) ? 0 : timetracking.hashCode());
        result = prime * result + ((worklog == null) ? 0 : worklog.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JiraRestFields other = (JiraRestFields) obj;
        if (properties == null) {
            if (other.properties != null) {
                return false;
            }
        } else if (!properties.equals(other.properties)) {
            return false;
        }
        if (resolution == null) {
            if (other.resolution != null) {
                return false;
            }
        } else if (!resolution.equals(other.resolution)) {
            return false;
        }
        if (resolutiondate == null) {
            if (other.resolutiondate != null) {
                return false;
            }
        } else if (!resolutiondate.equals(other.resolutiondate)) {
            return false;
        }
        if (timetracking == null) {
            if (other.timetracking != null) {
                return false;
            }
        } else if (!timetracking.equals(other.timetracking)) {
            return false;
        }
        if (worklog == null) {
            if (other.worklog != null) {
                return false;
            }
        } else if (!worklog.equals(other.worklog)) {
            return false;
        }
        return true;
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
