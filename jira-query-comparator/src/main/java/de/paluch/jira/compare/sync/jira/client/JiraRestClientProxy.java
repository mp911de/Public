package de.paluch.jira.compare.sync.jira.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Rest Client Proxy for Jira Rest API 2.0.alpha1. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
@Path("rest/api/2")
public interface JiraRestClientProxy {

    /**
     * @param issueKey
     * @param fields
     * @return JiraRestIssue
     */
    @Path("issue/{issueKey}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JiraRestIssue getIssue(@PathParam("issueKey") String issueKey, @QueryParam("fields") String fields);

    /**
     * @param jiraRestSearchRequest
     * @return JiraRestSearchResult
     */
    @Path("search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JiraRestSearchResult search(JiraRestSearchRequest jiraRestSearchRequest);

}
