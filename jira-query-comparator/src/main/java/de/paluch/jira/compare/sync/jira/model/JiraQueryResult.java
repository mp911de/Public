package de.paluch.jira.compare.sync.jira.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <br>
 * <br>
 * Project: jira-query-comparator <br>
 * Autor: mark <br>
 * Created: 19.07.2012 <br>
 * <br>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JiraQueryResult {

    @XmlAttribute
    private String jql = "";

    private Long hashCodeDetail = null;
    @XmlElement(name = "issueKey")
    @XmlElementWrapper(name = "issueKeys")
    private List<String> issueKeys = new ArrayList<String>();

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

    /**
     * @return the hashCodeDetail
     */
    public Long getHashCodeDetail() {
        return hashCodeDetail;
    }

    /**
     * @param hashCodeDetail
     *            the hashCodeDetail to set
     */
    public void setHashCodeDetail(Long hashCodeDetail) {
        this.hashCodeDetail = hashCodeDetail;
    }

    /**
     * @return the issueKeys
     */
    public List<String> getIssueKeys() {
        return issueKeys;
    }

    /**
     * @param issueKeys
     *            the issueKeys to set
     */
    public void setIssueKeys(List<String> issueKeys) {
        this.issueKeys = issueKeys;
    }

}
