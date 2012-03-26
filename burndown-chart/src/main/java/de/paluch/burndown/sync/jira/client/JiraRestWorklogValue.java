package de.paluch.burndown.sync.jira.client;

import java.util.Date;

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
public class JiraRestWorklogValue
{

	private Date started;
	private int minutesSpent;

	/**
	 * @return the minutesSpent
	 */
	public int getMinutesSpent()
	{

		return minutesSpent;
	}

	/**
	 * @return the started
	 */
	public Date getStarted()
	{

		return started;
	}

	/**
	 * @param minutesSpent the minutesSpent to set
	 */
	public void setMinutesSpent(int minutesSpent)
	{

		this.minutesSpent = minutesSpent;
	}

	/**
	 * @param started the started to set
	 */
	public void setStarted(Date started)
	{

		this.started = started;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return getClass().getSimpleName() + " [started=" + started + ", minutesSpent=" + minutesSpent + "]";
	}

}
