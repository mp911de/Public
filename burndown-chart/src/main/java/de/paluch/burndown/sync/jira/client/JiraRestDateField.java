package de.paluch.burndown.sync.jira.client;

import java.util.Date;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */

public class JiraRestDateField extends AbstractJiraRestField {

    private Date value;

    /**
     * @return the value
     */
    public Date getValue() {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Date value) {

        this.value = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return value == null ? null : value.toString();
    }

}
