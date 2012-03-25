package de.paluch.burndown.sync.jira;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

/**
 * Sync single Sprint with Jira.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class SingleJiraSync extends AbstractJiraSyncJob
{

	public static void main(String[] args) throws JiraSyncException
	{

		SingleJiraSync sync = new SingleJiraSync();
		Sprint sprint = new DataAccess().getSprint("middleware", "latest");
		System.out.println(sprint);
		sync.syncSprint("middleware", sprint);
		System.out.println(sprint);

	}

	/**
	 *
	 * @param teamId
	 * @param sprint
	 * @throws JiraSyncException
	 */
	public void syncSprint(String teamId, Sprint sprint) throws JiraSyncException
	{

		setup();
		Team team = getTeam(getTeams(), teamId);
		if (team == null)
		{
			return;
		}

		JiraTeamSync jiraTeamSync = getTeamSync(teamId);
		if (jiraTeamSync == null)
		{
			return;
		}

		syncSprint(jiraTeamSync, team, sprint);
	}
}
