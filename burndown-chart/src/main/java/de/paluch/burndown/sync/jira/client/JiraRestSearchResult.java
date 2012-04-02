package de.paluch.burndown.sync.jira.client;

import java.util.ArrayList;
import java.util.List;

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
public class JiraRestSearchResult {

    private int startAt;
    private int maxResults;
    private int total;
    private List<JiraRestSearchResultIssue> issues = new ArrayList<JiraRestSearchResultIssue>();

    /**
     * @return the issues
     */
    public List<JiraRestSearchResultIssue> getIssues() {

        return issues;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {

        return maxResults;
    }

    /**
     * @return the startAt
     */
    public int getStartAt() {

        return startAt;
    }

    /**
     * @return the total
     */
    public int getTotal() {

        return total;
    }

    /**
     * @param issues
     *            the issues to set
     */
    public void setIssues(List<JiraRestSearchResultIssue> issues) {

        this.issues = issues;
    }

    /**
     * @param maxResults
     *            the maxResults to set
     */
    public void setMaxResults(int maxResults) {

        this.maxResults = maxResults;
    }

    /**
     * @param startAt
     *            the startAt to set
     */
    public void setStartAt(int startAt) {

        this.startAt = startAt;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(int total) {

        this.total = total;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [startAt=" + startAt + ", maxResults=" + maxResults + ", total=" + total
                + ", issues=" + issues + "]";
    }

}
