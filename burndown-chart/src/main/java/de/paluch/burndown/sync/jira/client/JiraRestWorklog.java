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

    private List<JiraRestWorklogValue> value = new ArrayList<JiraRestWorklogValue>();

    /**
     * @return the value
     */
    public List<JiraRestWorklogValue> getValue() {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(List<JiraRestWorklogValue> value) {

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
