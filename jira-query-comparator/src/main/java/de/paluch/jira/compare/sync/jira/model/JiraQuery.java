package de.paluch.jira.compare.sync.jira.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * <br>
 * <br>
 * Project: jira-query-comparator <br>
 * Autor: mark <br>
 * Created: 20.07.2012 <br>
 * <br>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JiraQuery {

    @XmlAttribute
    private String id;
    @XmlValue
    private String jql;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the jql
     */
    public String getJql() {
        return jql;
    }

    /**
     * @param jql
     *            the jql to set
     */
    public void setJql(String jql) {
        this.jql = jql;
    }

}
