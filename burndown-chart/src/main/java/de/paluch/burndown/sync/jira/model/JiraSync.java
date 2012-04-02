package de.paluch.burndown.sync.jira.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
public class JiraSync {

    @XmlElement(name = "teamSync")
    private List<JiraTeamSync> teamSync = new ArrayList<JiraTeamSync>();
    private String baseUrl;
    private String username;
    private String password;
    private UpdateMode updateMode;

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
     * @return the teamSync
     */
    public List<JiraTeamSync> getTeamSync() {

        return teamSync;
    }

    /**
     * @return the updateMode
     */
    public UpdateMode getUpdateMode() {

        return updateMode;
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
     * @param teamSync
     *            the teamSync to set
     */
    public void setTeamSync(List<JiraTeamSync> teamSync) {

        this.teamSync = teamSync;
    }

    /**
     * @param updateMode
     *            the updateMode to set
     */
    public void setUpdateMode(UpdateMode updateMode) {

        this.updateMode = updateMode;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {

        this.username = username;
    }

}
