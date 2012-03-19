package de.paluch.burndown.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
@XmlRootElement
public class Teams
{

	@XmlElement(name = "team")
	private List<Team> teams = new ArrayList<Team>();


	/**
	 * @return the teams
	 */
	public List<Team> getTeams()
	{

		return teams;
	}


	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams)
	{

		this.teams = teams;
	}


}
