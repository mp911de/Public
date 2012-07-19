package de.paluch.burndown.sync.jira.client;

import java.util.Date;

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
public class JiraRestWorklogValue {

    private Date started;
    private int timeSpentSeconds;

    /**
     * @return the minutesSpent
     */
    public int getTimeSpentSeconds() {

        return timeSpentSeconds;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + timeSpentSeconds;
        result = prime * result + ((started == null) ? 0 : started.hashCode());
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
        JiraRestWorklogValue other = (JiraRestWorklogValue) obj;
        if (timeSpentSeconds != other.timeSpentSeconds) {
            return false;
        }
        if (started == null) {
            if (other.started != null) {
                return false;
            }
        } else if (!started.equals(other.started)) {
            return false;
        }
        return true;
    }

    /**
     * @return the started
     */
    public Date getStarted() {

        return started;
    }

    /**
     * @param minutesSpent
     *            the minutesSpent to set
     */
    public void setTimeSpentSeconds(int minutesSpent) {

        this.timeSpentSeconds = minutesSpent;
    }

    /**
     * @param started
     *            the started to set
     */
    public void setStarted(Date started) {

        this.started = started;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [started=" + started + ", minutesSpent=" + timeSpentSeconds + "]";
    }

}
