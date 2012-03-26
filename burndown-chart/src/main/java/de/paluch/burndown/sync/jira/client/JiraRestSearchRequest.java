package de.paluch.burndown.sync.jira.client;

import javax.xml.bind.annotation.XmlRootElement;

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
public class JiraRestSearchRequest
{

	private String jql;
	private int startAt = 0;
	private int maxResults = 150;

	/**
	 * @return the jql
	 */
	public String getJql()
	{

		return jql;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults()
	{

		return maxResults;
	}

	/**
	 * @return the startAt
	 */
	public int getStartAt()
	{

		return startAt;
	}

	/**
	 * @param jql the jql to set
	 */
	public void setJql(String jql)
	{

		this.jql = jql;
	}

	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults)
	{

		this.maxResults = maxResults;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt)
	{

		this.startAt = startAt;
	}

}
