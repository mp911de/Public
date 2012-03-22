package de.paluch.burndown.jsf.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.richfaces.component.UIDataTable;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.SprintDaysGenerator;
import de.paluch.burndown.jsf.base.AbstractJSFController;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;

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
public class SprintsListController extends AbstractJSFController
{

	@ManagedProperty("sprintsListModel")
	private SprintsListModel sprintsListModel;
	@ManagedProperty("sprintListModel")
	private SprintListModel sprintListModel;

	public String prepareCreateNewSprint()
	{

		Date maxDate = new Date(0);
		int sprintNumber = 0;
		for (Sprint sprint : sprintsListModel.getList())
		{
			if (sprint.getStartDate().after(maxDate))
			{
				maxDate = sprint.getStartDate();
				try
				{
					sprintNumber = Integer.parseInt(sprint.getId().trim());
				}
				catch (NumberFormatException e)
				{
				}
			}
		}

		if (sprintsListModel.getList().isEmpty())
		{
			maxDate = new Date(System.currentTimeMillis());
		}

		sprintNumber++;
		Calendar cal = Calendar.getInstance();
		cal.setTime(maxDate);
		while (cal.get(Calendar.DAY_OF_WEEK) != sprintsListModel.getTeam().getRegularSprintStart())
		{
			cal.add(Calendar.DATE, 1);
		}

		Sprint sprint = new Sprint();
		sprint.setDays(sprintsListModel.getTeam().getRegularSprintLength());
		sprint.setId("" + sprintNumber);
		sprint.setStartDate(cal.getTime());

		SprintDaysGenerator gen = new SprintDaysGenerator();
		List<Date> days = gen.generateSprintDays(sprint.getDays(), cal.getTime());
		for (Date date : days)
		{
			SprintEffort effort = new SprintEffort();
			effort.setDate(date);
			sprint.getEffort().add(effort);
		}

		sprintListModel.setTeam(sprintsListModel.getTeam());
		sprintListModel.setNewSprint(true);
		sprintListModel.setOldStartDate(sprint.getStartDate());
		sprintListModel.setSprint(sprint);
		sprintListModel.refreshList();

		return Navigation.SPRINT;
	}

	public String gotoSprint()
	{

		UIDataTable table = (UIDataTable) findComponentInRoot(sprintsListModel.getTableId());

		Sprint selection = (Sprint) table.getRowData();
		Sprint sprint = new DataAccess().getSprint(sprintsListModel.getTeam().getId(), selection.getId());

		sprintListModel.setTeam(sprintsListModel.getTeam());
		sprintListModel.setOldStartDate(sprint.getStartDate());
		sprintListModel.setSprint(sprint);
		sprintListModel.refreshList();

		return Navigation.SPRINT;
	}

	/**
	 * @return the sprintsListModel
	 */
	public SprintsListModel getSprintsListModel()
	{

		return sprintsListModel;
	}

	/**
	 * @param sprintsListModel the sprintsListModel to set
	 */
	public void setSprintsListModel(SprintsListModel sprintsListModel)
	{

		this.sprintsListModel = sprintsListModel;
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
