package de.paluch.burndown.jsf.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.jsf.base.AbstractJSFListModel;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.model.Teams;

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
@SessionScoped
public class TeamListModel extends AbstractJSFListModel<Team>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8676697153331901104L;
	private String selectedTeamId;

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#getList()
	 */
	@Override
	public List<Team> getList()
	{

		if (super.getList().isEmpty())
		{
			refreshList();
		}
		return super.getList();
	}

	/**
	 * @return the selectedTeamId
	 */
	public String getSelectedTeamId()
	{

		return selectedTeamId;
	}

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#getTableId()
	 */
	@Override
	public String getTableId()
	{

		return "teams";
	}

	public Team getTeamById(String teamId)
	{

		for (Team team : getList())
		{
			if (team.getId().equals(selectedTeamId))
			{
				return team;
			}
		}

		return null;
	}

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#refreshList()
	 */
	@Override
	public void refreshList()
	{

		Teams teams = new DataAccess().getTeams();
		super.getList().clear();
		super.getList().addAll(teams.getTeams());
	}

	/**
	 * @param selectedTeamId the selectedTeamId to set
	 */
	public void setSelectedTeamId(String selectedTeamId)
	{

		this.selectedTeamId = selectedTeamId;
	}

}
