package de.paluch.jira.compare.sync.jira.client;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraRestIssue {

    private JiraRestFields fields;
    private String key;

    /**
     * @return the fields
     */
    public JiraRestFields getFields() {

        return fields;
    }

    /**
     * @return the key
     */
    public String getKey() {

        return key;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(JiraRestFields fields) {

        this.fields = fields;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {

        this.key = key;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        JiraRestIssue other = (JiraRestIssue) obj;
        if (fields == null) {
            if (other.fields != null) {
                return false;
            }
        } else if (!fields.equals(other.fields)) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [key=" + key + ", fields=" + fields + "]";
    }

}
