package de.paluch.burndown.jsf.impl;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.paluch.burndown.jsf.base.AbstractJSFListModel;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;
import de.paluch.burndown.model.Team;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 20.03.2012 <br>
 * <br>
 */
@ManagedBean
@RequestScoped
public class SprintListModel extends AbstractJSFListModel<SprintEffort> {

    /**
	 *
	 */
    private static final long serialVersionUID = 5438182710349436352L;
    private boolean newSprint = false;
    private Date oldStartDate;
    private Sprint sprint;
    private Team team;

    /**
     * @return the oldStartDate
     */
    public Date getOldStartDate() {

        return oldStartDate;
    }

    /**
     * @return the sprint
     */
    public Sprint getSprint() {

        return sprint;
    }

    /**
     * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#getTableId()
     */
    @Override
    public String getTableId() {

        return "days";
    }

    /**
     * @return the team
     */
    public Team getTeam() {

        return team;
    }

    /**
     * @return the newSprint
     */
    public boolean isNewSprint() {

        return newSprint;
    }

    /**
     * @see de.paluch.burndown.jsf.base.AbstractJSFListModel#refreshList()
     */
    @Override
    public void refreshList() {

        getList().clear();
        getList().addAll(sprint.getEffort());

    }

    /**
     * @param newSprint
     *            the newSprint to set
     */
    public void setNewSprint(boolean newSprint) {

        this.newSprint = newSprint;
    }

    /**
     * @param oldStartDate
     *            the oldStartDate to set
     */
    public void setOldStartDate(Date oldStartDate) {

        this.oldStartDate = oldStartDate;
    }

    /**
     * @param sprint
     *            the sprint to set
     */
    public void setSprint(Sprint sprint) {

        this.sprint = sprint;
    }

    /**
     * @param team
     *            the team to set
     */
    public void setTeam(Team team) {

        this.team = team;
    }

    public double getSumBurned() {
        double result = 0;
        for (SprintEffort effort : sprint.getEffort()) {
            result += effort.getBurned();
        }

        return result;
    }

    public double getSumUnplanned() {
        double result = 0;
        for (SprintEffort effort : sprint.getEffort()) {
            result += effort.getUnplanned();
        }

        return result;
    }

}
