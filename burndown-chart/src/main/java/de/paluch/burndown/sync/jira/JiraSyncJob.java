package de.paluch.burndown.sync.jira;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 *Quartz Sync Job for latest Sprints of all configured Teams.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSyncJob extends AbstractJiraSyncJob implements Job
{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@SuppressWarnings("unused")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		try
		{
			setup();

			logger.debug("execute: start sync");

			for (JiraTeamSync teamSync : getSyncSettings().getTeamSync())
			{
				Team team = getTeam(getTeams(), teamSync.getTeamId());
				if (team == null)
				{
					continue;
				}

				Sprint latestSprint = new DataAccess().getSprint(team.getId(), "latest");
				if (latestSprint == null)
				{
					continue;
				}

				syncSprint(teamSync, team, latestSprint);
			}

		}
		catch (Exception e)
		{
			logger.warn("Sync Sprint: " + e.getMessage(), e);
		}
		finally
		{

			logger.debug("execute: end sync");
		}

	}

}
