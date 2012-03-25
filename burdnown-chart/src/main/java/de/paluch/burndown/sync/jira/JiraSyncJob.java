package de.paluch.burndown.sync.jira;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.model.Teams;
import de.paluch.burndown.sync.jira.model.JiraSync;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSyncJob implements Job
{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		JiraSprintSync sprintSync = null;
		try
		{

			logger.debug("execute: start sync");
			JiraSync syncSettings = new JiraSyncDataAccess().getJiraSync();
			Teams teams = new DataAccess().getTeams();

			sprintSync = new JiraSprintSync(syncSettings.getBaseUrl(), syncSettings.getUsername(),
					syncSettings.getPassword());

			for (JiraTeamSync teamSync : syncSettings.getTeamSync())
			{
				Team team = getTeam(teams, teamSync.getTeamId());
				if (team == null)
				{
					continue;
				}

				Sprint latestSprint = new DataAccess().getSprint(team.getId(), "latest");
				if (latestSprint == null)
				{
					continue;
				}

				syncSprint(sprintSync, team, latestSprint);
			}

		}
		catch (Exception e)
		{
			logger.warn("Sync Sprint: " + e.getMessage(), e);
		}
		finally
		{
			if (sprintSync != null)
			{
				try
				{
					sprintSync.logout();
				}
				catch (JiraSyncException e)
				{
					logger.warn("Sync Sprint: " + e.getMessage(), e);
				}
			}

			logger.debug("execute: end sync");
		}

	}

	/**
	 * @param teams
	 * @param teamId
	 * @return
	 */
	private Team getTeam(Teams teams, String teamId)
	{

		for (Team team : teams.getTeams())
		{
			if (team.getId() != null && team.getId().equals(teamId))
			{
				return team;
			}
		}
		return null;
	}

	/**
	 * @param sprintSync
	 * @param team
	 * @param latestSprint
	 */
	private void syncSprint(JiraSprintSync sprintSync, Team team, Sprint latestSprint)
	{

		try
		{
			logger.info("Sync Sprint " + latestSprint.getId() + "/" + team.getId());
			sprintSync.syncSprint(latestSprint);
			logger.info("Sync Sprint " + latestSprint.getId() + "/" + team.getId() + " done");
		}
		catch (Exception e)
		{
			logger.warn("Sync Sprint " + latestSprint.getId() + "/" + team.getId() + ": " + e.getMessage(), e);
		}
	}

}
