package de.paluch.burndown.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
public class Sprint
{

	@XmlAttribute
	private String id = "";

	private int days;
	private Date startDate;
	private double planned = 0;

	private List<SprintEffort> effort = new ArrayList<SprintEffort>();

	/**
	 * @return the id
	 */
	public String getId()
	{

		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{

		this.id = id;
	}

	/**
	 * @return the days
	 */
	public int getDays()
	{

		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(int days)
	{

		this.days = days;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{

		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate)
	{

		this.startDate = startDate;
	}

	/**
	 * @return the effort
	 */
	public List<SprintEffort> getEffort()
	{

		return effort;
	}

	/**
	 * @param effort the effort to set
	 */
	public void setEffort(List<SprintEffort> effort)
	{

		this.effort = effort;
	}

	/**
	 * @return the planned
	 */
	public double getPlanned()
	{

		return planned;
	}

	/**
	 * @param planned the planned to set
	 */
	public void setPlanned(double planned)
	{

		this.planned = planned;
	}

}
