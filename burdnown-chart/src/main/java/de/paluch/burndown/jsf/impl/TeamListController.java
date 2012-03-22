package de.paluch.burndown.jsf.impl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIDataTable;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.jsf.base.AbstractJSFController;
import de.paluch.burndown.model.Team;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 20.03.2012
 *<br>
 *<br>
 */
@ManagedBean
@RequestScoped
public class TeamListController extends AbstractJSFController
{

	@ManagedProperty("teamListModel")
	private TeamListModel teams;
	@ManagedProperty("sprintsListModel")
	private SprintsListModel sprints;

	public String selectTeam()
	{

		UIDataTable table = (UIDataTable) findComponentInRoot(teams.getTableId());

		sprints.setTeam((Team) table.getRowData());

		return Navigation.SPRINTS_OVERVIEW;
	}


	/**
	 * @return the teams
	 */
	public TeamListModel getTeams()
	{

		return teams;
	}


	/**
	 * @param teams the teams to set
	 */
	public void setTeams(TeamListModel teams)
	{

		this.teams = teams;
	}


	/**
	 * @return the sprints
	 */
	public SprintsListModel getSprints()
	{

		return sprints;
	}


	/**
	 * @param sprints the sprints to set
	 */
	public void setSprints(SprintsListModel sprints)
	{

		this.sprints = sprints;
	}



}
