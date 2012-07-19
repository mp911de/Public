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
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        JiraRestTimetracking other = (JiraRestTimetracking) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [value=" + value + "]";
    }

}
