package de.paluch.burndown.sync.jira.client;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraRestIssue
{

	private JiraRestFields fields;
	private String key;

	/**
	 * @return the fields
	 */
	public JiraRestFields getFields()
	{

		return fields;
	}

	/**
	 * @return the key
	 */
	public String getKey()
	{

		return key;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(JiraRestFields fields)
	{

		this.fields = fields;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key)
	{

		this.key = key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return getClass().getSimpleName() + " [key=" + key + ", fields=" + fields + "]";
	}

}
