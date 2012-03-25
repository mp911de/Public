package de.paluch.burndown.jsf.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.jsf.base.AbstractJSFListModel;
import de.paluch.burndown.model.Sprint;
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
@SessionScoped
public class SprintsListModel extends AbstractJSFListModel<Sprint>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5066713971117471465L;
	private Team team;

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#getList()
	 */
	@Override
	public List<Sprint> getList()
	{

		if (team != null && super.getList().isEmpty())
		{
			refreshList();
		}

		return super.getList();
	}

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#getTableId()
	 */
	@Override
	public String getTableId()
	{

		return "sprints";
	}

	/**
	 * @return the team
	 */
	public Team getTeam()
	{

		return team;
	}

	/**
	 * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#refreshList()
	 */
	@Override
	public void refreshList()
	{

		super.getList().clear();

		DataAccess access = new DataAccess();
		List<String> sprintIds = access.listSprints(team.getId());
		for (String id : sprintIds)
		{
			super.getList().add(access.getSprint(team.getId(), id));
		}

	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team)
	{

		this.team = team;
	}

}
