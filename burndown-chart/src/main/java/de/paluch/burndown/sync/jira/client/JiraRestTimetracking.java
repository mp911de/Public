package de.paluch.burndown.sync.jira.client;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */

public class JiraRestTimetracking extends AbstractJiraRestField {

    private JiraRestTimetrackingValue value;

    /**
     * @return the value
     */
    public JiraRestTimetrackingValue getValue() {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(JiraRestTimetrackingValue value) {

        this.value = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [value=" + value + "]";
    }

}
