package de.paluch.burndown.sync.jira;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.model.Teams;
import de.paluch.burndown.sync.jira.model.JiraSync;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 *Abstract Jira Syncer.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class AbstractJiraSyncJob
{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private JiraSync syncSettings;
	private Teams teams;
	private JiraSprintSync sprintSync;

	/**
	 * @return the sprintSync
	 */
	public JiraSprintSync getSprintSync()
	{

		return sprintSync;
	}

	/**
	 * @return the syncSettings
	 */
	public JiraSync getSyncSettings()
	{

		return syncSettings;
	}

	/**
	 * @return the teams
	 */
	public Teams getTeams()
	{

		return teams;
	}

	/**
	 * Setup Sync-Job.
	 * @throws JiraSyncException
	 */
	public void setup() throws JiraSyncException
	{

		logger.debug("setup");
		syncSettings = new JiraSyncDataAccess().getJiraSync();
		teams = new DataAccess().getTeams();

		sprintSync = new JiraSprintSync(syncSettings.getBaseUrl(), syncSettings.getUsername(),
				syncSettings.getPassword());
		logger.debug("setup ok");
	}

	/**
	 * @param teams
	 * @param teamId
	 * @return Team
	 */
	protected Team getTeam(Teams teams, String teamId)
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
	 * @param teamId
	 * @return Team
	 */
	protected JiraTeamSync getTeamSync(String teamId)
	{

		for (JiraTeamSync team : getSyncSettings().getTeamSync())
		{
			if (team.getTeamId() != null && team.getTeamId().equals(teamId))
			{
				return team;
			}
		}
		return null;
	}

	/**
	 * Start Sprint synchronisation.
	 * @param teamSync
	 * @param team
	 * @param latestSprint
	 */
	protected void syncSprint(JiraTeamSync teamSync, Team team, Sprint latestSprint)
	{

		try
		{
			logger.info("Sync Sprint " + latestSprint.getId() + "/" + team.getId());
			sprintSync.syncSprint(teamSync, latestSprint);
			logger.info("Sync Sprint " + latestSprint.getId() + "/" + team.getId() + " done");
		}
		catch (Exception e)
		{
			logger.warn("Sync Sprint " + latestSprint.getId() + "/" + team.getId() + ": " + e.getMessage(), e);
		}
	}
}
