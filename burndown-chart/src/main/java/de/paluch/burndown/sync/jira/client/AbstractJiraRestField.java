package de.paluch.burndown.sync.jira.client;

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
public abstract class AbstractJiraRestField {

    private String name;
    private String type;

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @return the type
     */
    public String getType() {

        return type;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {

        this.type = type;
    }

}
