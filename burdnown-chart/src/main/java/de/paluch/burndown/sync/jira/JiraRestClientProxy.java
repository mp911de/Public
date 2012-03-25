package de.paluch.burndown.sync.jira;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
@Path("rest/api/latest")
public interface JiraRestClientProxy
{

	@Path("issue/{issueKey}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIssue(@PathParam("issueKey")
	String issueKey);

}
