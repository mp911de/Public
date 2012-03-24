package de.paluch.burndown.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class Team
{

	@XmlAttribute
	private String id;
	private int regularSprintLength;
	private int regularSprintStart;
	private int teamSize = 0;
	private String name;

	/**
	 *
	 */
	public Team()
	{

		super();
	}

	/**
	 * @param id
	 */
	public Team(String id)
	{

		super();
		this.id = id;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{

		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Team other = (Team) obj;
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (regularSprintLength != other.regularSprintLength)
		{
			return false;
		}
		if (regularSprintStart != other.regularSprintStart)
		{
			return false;
		}
		if (teamSize != other.teamSize)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{

		return id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{

		return name;
	}

	/**
	 * @return the regularSprintLength
	 */
	public int getRegularSprintLength()
	{

		return regularSprintLength;
	}

	/**
	 * @return the regularSprintStart
	 */
	public int getRegularSprintStart()
	{

		return regularSprintStart;
	}

	/**
	 * @return the teamSize
	 */
	public int getTeamSize()
	{

		return teamSize;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + regularSprintLength;
		result = prime * result + regularSprintStart;
		result = prime * result + teamSize;
		return result;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{

		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{

		this.name = name;
	}

	/**
	 * @param regularSprintLength the regularSprintLength to set
	 */
	public void setRegularSprintLength(int regularSprintLength)
	{

		this.regularSprintLength = regularSprintLength;
	}

	/**
	 * @param regularSprintStart the regularSprintStart to set
	 */
	public void setRegularSprintStart(int regularSprintStart)
	{

		this.regularSprintStart = regularSprintStart;
	}

	/**
	 * @param teamSize the teamSize to set
	 */
	public void setTeamSize(int teamSize)
	{

		this.teamSize = teamSize;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return getClass().getSimpleName() + " [id=" + id + ", regularSprintLength=" + regularSprintLength
				+ ", regularSprintStart="
				+ regularSprintStart + ", teamSize=" + teamSize + ", name=" + name + "]";
	}

}
