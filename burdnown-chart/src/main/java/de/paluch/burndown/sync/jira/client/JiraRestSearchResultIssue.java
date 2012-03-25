package de.paluch.burndown.sync.jira.client;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraRestSearchResultIssue
{

	private String key;

	/**
	 * @return the key
	 */
	public String getKey()
	{

		return key;
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

		return getKey();
	}

}
