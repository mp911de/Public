package de.paluch.jira.compare.sync.jira.client;

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
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((issues == null) ? 0 : issues.hashCode());
        result = prime * result + maxResults;
        result = prime * result + startAt;
        result = prime * result + total;
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
        JiraRestSearchResult other = (JiraRestSearchResult) obj;
        if (issues == null) {
            if (other.issues != null) {
                return false;
            }
        } else if (!issues.equals(other.issues)) {
            return false;
        }
        if (maxResults != other.maxResults) {
            return false;
        }
        if (startAt != other.startAt) {
            return false;
        }
        if (total != other.total) {
            return false;
        }
        return true;
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
