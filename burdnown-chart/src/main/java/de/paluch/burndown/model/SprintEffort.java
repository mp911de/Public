package de.paluch.burndown.model;

import java.util.Date;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
public class SprintEffort
{

	private Date date;
	private double burned;
	private double unplanned;

	/**
	 *
	 */
	public SprintEffort()
	{

		super();
	}

	/**
	 * @param burned
	 * @param unplanned
	 */
	public SprintEffort(double burned, double unplanned)
	{

		super();
		this.burned = burned;
		this.unplanned = unplanned;
	}



	/**
	 * @param date
	 * @param burned
	 * @param unplanned
	 */
	public SprintEffort(Date date, double burned, double unplanned)
	{

		super();
		this.date = date;
		this.burned = burned;
		this.unplanned = unplanned;
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{

		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{

		this.date = date;
	}

	/**
	 * @return the burned
	 */
	public double getBurned()
	{

		return burned;
	}

	/**
	 * @param burned the burned to set
	 */
	public void setBurned(double burned)
	{

		this.burned = burned;
	}

	/**
	 * @return the unplanned
	 */
	public double getUnplanned()
	{

		return unplanned;
	}

	/**
	 * @param unplanned the unplanned to set
	 */
	public void setUnplanned(double unplanned)
	{

		this.unplanned = unplanned;
	}

}
