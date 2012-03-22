package de.paluch.burndown.jsf.impl;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 21.03.2012
 *<br>
 *<br>
 */
@ManagedBean
@RequestScoped
public class SprintController
{

	public static  DataAccess DATA_ACCESS = new DataAccess();

	@ManagedProperty("sprintListModel")
	private SprintListModel sprintListModel;

	public void save()
	{

		if (!sprintListModel.getOldStartDate().equals(sprintListModel.getSprint().getStartDate()))
		{
			shiftSprintDays(sprintListModel.getSprint());
		}

		DATA_ACCESS.storeSprint(sprintListModel.getTeam().getId(), sprintListModel.getSprint());
		sprintListModel.setNewSprint(false);
		sprintListModel.setOldStartDate(sprintListModel.getSprint().getStartDate());
	}

	/**
	 * @param sprint
	 */
	private void shiftSprintDays(Sprint sprint)
	{

		Date startDate = sprint.getStartDate();
		Date firstDate = sprint.getEffort().get(0).getDate();
		long difference = startDate.getTime() - firstDate.getTime();

		for (SprintEffort effort : sprint.getEffort())
		{
			effort.setDate(new Date(effort.getDate().getTime() + difference));
		}

	}

	/**
	 * @return the sprintListModel
	 */
	public SprintListModel getSprintListModel()
	{

		return sprintListModel;
	}

	/**
	 * @param sprintListModel the sprintListModel to set
	 */
	public void setSprintListModel(SprintListModel sprintListModel)
	{

		this.sprintListModel = sprintListModel;
	}

}
