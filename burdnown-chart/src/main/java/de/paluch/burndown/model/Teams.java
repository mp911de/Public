package de.paluch.burndown.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Collection of Teams.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Teams
{

	@XmlElement(name = "team")
	private List<Team> teams = new ArrayList<Team>();

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
		Teams other = (Teams) obj;
		if (teams == null)
		{
			if (other.teams != null)
			{
				return false;
			}
		}
		else if (!teams.equals(other.teams))
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the teams
	 */
	public List<Team> getTeams()
	{

		return teams;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{

		final int prime = 31;
		int result = 1;
		result = prime * result + ((teams == null) ? 0 : teams.hashCode());
		return result;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams)
	{

		this.teams = teams;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return getClass().getSimpleName() + " [teams=" + teams + "]";
	}

}
