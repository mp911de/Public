package de.paluch.burndown.sync.jira.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraRestTimetrackingValue {

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + originalEstimate;
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
        JiraRestTimetrackingValue other = (JiraRestTimetrackingValue) obj;
        if (originalEstimate != other.originalEstimate) {
            return false;
        }
        return true;
    }

    @JsonProperty("timeoriginalestimate")
    private int originalEstimate;

    /**
     * @return the originalEstimate
     */
    public int getOriginalEstimate() {

        return originalEstimate;
    }

    /**
     * @param originalEstimate
     *            the originalEstimate to set
     */
    public void setOriginalEstimate(int originalEstimate) {

        this.originalEstimate = originalEstimate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "" + getOriginalEstimate();
    }

}
