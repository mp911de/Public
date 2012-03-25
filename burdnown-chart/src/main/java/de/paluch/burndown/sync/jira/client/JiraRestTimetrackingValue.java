package de.paluch.burndown.sync.jira.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
public class JiraRestTimetrackingValue
{

	@JsonProperty("timeoriginalestimate")
	private int originalEstimate;

	/**
	 * @return the originalEstimate
	 */
	public int getOriginalEstimate()
	{

		return originalEstimate;
	}

	/**
	 * @param originalEstimate the originalEstimate to set
	 */
	public void setOriginalEstimate(int originalEstimate)
	{

		this.originalEstimate = originalEstimate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "" + getOriginalEstimate();
	}

}
