package de.paluch.jira.compare.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.paluch.jira.compare.DataAccess;
import de.paluch.jira.compare.sync.jira.client.JiraClient;
import de.paluch.jira.compare.sync.jira.client.JiraRestIssue;
import de.paluch.jira.compare.sync.jira.model.JiraConfig;
import de.paluch.jira.compare.sync.jira.model.JiraQuery;
import de.paluch.jira.compare.sync.jira.model.JiraQueryResult;

/**
 * </ul> <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 21.03.2012 <br>
 * <br>
 */
@Path("/query/news")
public class QueryResource {

    @GET
    @Path("{query}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response executeQuery(@PathParam("query") String query) throws Exception {

        return Response.ok(checkForChanges(query, false)).build();

    }

    @GET
    @Path("{query}/detail")
    @Produces(MediaType.TEXT_PLAIN)
    public Response executeQueryAndCheckIssues(@PathParam("query") String query) throws Exception {

        return Response.ok(checkForChanges(query, true)).build();

    }

    /**
     * Check whether there are new Issues or the old one changed.
     * 
     * @param query
     * @param withDetail
     * @return true/false
     * @throws Exception
     */
    private boolean checkForChanges(String query, boolean withDetail) throws Exception {
        System.out.println("Query for " + query);
        JiraConfig config = new DataAccess().getJiraConfig();

        String jql = getJql(config, query);

        JiraClient client = getClient(config);

        JiraQueryResult old = new DataAccess().getQueryResult(jql.hashCode());

        JiraQueryResult newResult = fetchData(withDetail, jql, client);

        new DataAccess().storeQueryResult(jql.hashCode(), newResult);
        if (old == null) {
            return true;
        }

        if (areThereNewIssues(old, newResult)) {
            return true;
        } else if (withDetail) {

            if (old.getHashCodeDetail() == null) {
                return true;
            }
            return !old.getHashCodeDetail().equals(newResult.getHashCodeDetail());
        }

        return false;
    }

    /**
     * @param withDetail
     * @param jql
     * @param client
     * @return
     */
    private JiraQueryResult fetchData(boolean withDetail, String jql, JiraClient client) {
        List<String> keys = client.findByJQL(jql);
        System.out.println("Query for " + jql + ", found " + keys.size() + " items");
        JiraQueryResult newResult = new JiraQueryResult();
        newResult.setJql(jql);
        newResult.getIssueKeys().addAll(keys);

        if (withDetail) {
            List<JiraRestIssue> issues = new ArrayList<JiraRestIssue>();
            for (String key : keys) {
                JiraRestIssue issue = client.getIssue(key);
                if (issue != null) {
                    issues.add(issue);
                }
            }

            newResult.setHashCodeDetail((long) issues.hashCode());
        }
        return newResult;
    }

    /**
     * @param config
     * @param query
     * @return
     */
    private String getJql(JiraConfig config, String query) {

        if (config != null) {
            for (JiraQuery jiraQuery : config.getQueries()) {
                if (jiraQuery.getId().equals(query)) {
                    return jiraQuery.getJql();
                }
            }
        }
        throw new WebApplicationException(Status.NOT_FOUND);

    }

    private JiraClient getClient(JiraConfig config) throws Exception {

        JiraClient client = new JiraClient(config.getBaseUrl());
        client.login(config.getUsername(), config.getPassword());

        return client;
    }

    /**
     * @param old
     * @param newResult
     * @return
     */
    private boolean areThereNewIssues(JiraQueryResult old, JiraQueryResult newResult) {

        for (String key : newResult.getIssueKeys()) {
            if (!old.getIssueKeys().contains(key)) {
                return true;
            }
        }

        return false;
    }

}
