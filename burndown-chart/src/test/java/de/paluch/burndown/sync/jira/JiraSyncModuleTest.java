package de.paluch.burndown.sync.jira;

import java.util.Arrays;

import javax.xml.bind.JAXB;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;
import de.paluch.burndown.sync.jira.client.JiraClient;
import de.paluch.burndown.sync.jira.client.JiraRestIssue;
import de.paluch.burndown.sync.jira.model.JiraSync;
import de.paluch.burndown.sync.jira.model.JiraTeamSync;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 26.03.2012
 *<br>
 *<br>
 */
@RunWith(MockitoJUnitRunner.class)
public class JiraSyncModuleTest
{

	@Mock
	private JiraClient client;

	private JiraSprintSyncWorker sut = null;

	private JiraSync sync;

	private JiraTeamSync teamSync;

	private Sprint sprint;

	@Before
	public void before() throws Exception
	{

		sync = JAXB.unmarshal(getClass().getResourceAsStream("/jira-sync.xml"), JiraSync.class);
		teamSync = sync.getTeamSync().get(0);
		sprint = JAXB.unmarshal(getClass().getResourceAsStream("/middleware/22.xml"), Sprint.class);

		sut = new JiraSprintSyncWorker("", "", "");
		sut.setClient(client);

	}

	@Test
	public void test() throws Exception
	{

		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getJsonFactory().createJsonParser(getClass().getResourceAsStream("/DHDIYMW-824.json"));
		JiraRestIssue issue824 = mapper.readValue(jp, mapper.constructType(JiraRestIssue.class));

		jp = mapper.getJsonFactory().createJsonParser(getClass().getResourceAsStream("/DHDIYMW-902.json"));
		JiraRestIssue issue902 = mapper.readValue(jp, mapper.constructType(JiraRestIssue.class));

		when(client.findSprintIssues("DHDIYMW", "Sprint 22")).thenReturn(Arrays.asList("DHDIYMW-824", "DHDIYMW-902"));
		when(client.getIssue("DHDIYMW-824")).thenReturn(issue824);
		when(client.getIssue("DHDIYMW-902")).thenReturn(issue902);

		sprint.setPlanned(0);
		sut.syncSprint(teamSync, sprint);

		assertEquals(4, sprint.getPlanned(), 0.1);

		SprintEffort effort20 = sprint.getEffort().get(1);
		assertEquals(0, effort20.getBurned().doubleValue(), 0.1);
		assertEquals(1, effort20.getUnplanned().doubleValue(), 0.1);

		SprintEffort effort23 = sprint.getEffort().get(4);
		assertEquals(0, effort23.getBurned().doubleValue(), 0.1);
		assertEquals(1, effort23.getUnplanned().doubleValue(), 0.1);

		SprintEffort effort26 = sprint.getEffort().get(6);
		assertEquals(4, effort26.getBurned().doubleValue(), 0.1);
		assertEquals(0, effort26.getUnplanned().doubleValue(), 0.1);
	}
}
