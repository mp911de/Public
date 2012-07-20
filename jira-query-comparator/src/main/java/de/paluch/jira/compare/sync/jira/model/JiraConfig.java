package de.paluch.jira.compare.sync.jira.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Jira Sync config. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JiraConfig {

    private String baseUrl;
    private String username;
    private String password;

    @XmlElementWrapper(name = "queries")
    @XmlElement(name = "jqlQuery")
    private final List<JiraQuery> queries = new ArrayList<JiraQuery>();

    /**
     * @return the soapEndpoint
     */
    public String getBaseUrl() {

        return baseUrl;
    }

    /**
     * @return the password
     */
    public String getPassword() {

        return password;
    }

    /**
     * @return the username
     */
    public String getUsername() {

        return username;
    }

    /**
     * @param soapEndpoint
     *            the soapEndpoint to set
     */
    public void setBaseUrl(String soapEndpoint) {

        baseUrl = soapEndpoint;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * @return the queries
     */
    public List<JiraQuery> getQueries() {
        return queries;
    }

}
