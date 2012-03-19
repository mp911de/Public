package de.paluch.burndown.model;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
public class Team
{

	/**
	 * @return the regularSprintLength
	 */
	public int getRegularSprintLength()
	{

		return regularSprintLength;
	}

	/**
	 * @param regularSprintLength the regularSprintLength to set
	 */
	public void setRegularSprintLength(int regularSprintLength)
	{

		this.regularSprintLength = regularSprintLength;
	}

	/**
	 * @return the regularSprintStart
	 */
	public int getRegularSprintStart()
	{

		return regularSprintStart;
	}

	/**
	 * @param regularSprintStart the regularSprintStart to set
	 */
	public void setRegularSprintStart(int regularSprintStart)
	{

		this.regularSprintStart = regularSprintStart;
	}
	private int regularSprintLength;
	private int regularSprintStart;
}
