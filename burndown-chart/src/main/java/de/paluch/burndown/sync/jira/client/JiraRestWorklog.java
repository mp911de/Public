package de.paluch.burndown.sync.jira.client;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class JiraRestWorklog extends AbstractJiraRestField {

    private List<JiraRestWorklogValue> worklogs = new ArrayList<JiraRestWorklogValue>();

    /**
     * @return the value
     */
    public List<JiraRestWorklogValue> getWorklogs() {

        return worklogs;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setWorklogs(List<JiraRestWorklogValue> value) {

        this.worklogs = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [value=" + worklogs + "]";
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((worklogs == null) ? 0 : worklogs.hashCode());
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
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JiraRestWorklog other = (JiraRestWorklog) obj;
        if (worklogs == null) {
            if (other.worklogs != null) {
                return false;
            }
        } else if (!worklogs.equals(other.worklogs)) {
            return false;
        }
        return true;
    }

}
