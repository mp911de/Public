package de.paluch.burndown.sync.jira;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.atlassian.jira.rpc.soap.client.JiraSoapServiceServiceLocator;
import com.atlassian.jira.rpc.soap.client.RemoteAuthenticationException;
import com.atlassian.jira.rpc.soap.client.RemoteException;
import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.RemotePermissionException;
import com.atlassian.jira.rpc.soap.client.RemoteValidationException;
import com.atlassian.jira.rpc.soap.client.RemoteWorklog;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraClient
{

	private String sessionId;
	private final JiraSoapService port;
	private final String baseUrl;
	private final JiraRestClientProxy restProxy;
	private final BasicHttpContext localContext = new BasicHttpContext();
	private DefaultHttpClient httpClient;

	/**
	 * @param baseUrl
	 * @throws ServiceException
	 * @throws IOException
	 * @throws URISyntaxException
	 *
	 */
	public JiraClient(String baseUrl) throws IOException, ServiceException, URISyntaxException
	{

		this.baseUrl = baseUrl;
		JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		port = locator.getJirasoapserviceV2(new URL(baseUrl + "/rpc/soap/jirasoapservice-v2"));

		DefaultHttpClient httpClient = new DefaultHttpClient();
		ClientExecutor clientExecutor = new ApacheHttpClient4Executor(httpClient, localContext);

		ResteasyProviderFactory providerFactory = ResteasyProviderFactory.getInstance();

		restProxy = ProxyFactory.create(JiraRestClientProxy.class, new URI(baseUrl), clientExecutor, providerFactory);

	}

	public static void main(String[] args) throws Exception
	{

		JiraClient client = new JiraClient("http://bugs.paluch.biz/jira/");
		client.login("mark", "campari");

		client.getOriginalEstimate("INTERNOTE-144");

	}

	public RemoteIssue[] findSprintIssues(String projectKey, String versionIdentifier) throws RemoteException,
			java.rmi.RemoteException
	{

		String jql = "project = " + projectKey + " and fixVersion = \"" + versionIdentifier + "\"";
		return port.getIssuesFromJqlSearch(sessionId, jql, 1000);
	}

	/**
	 * @param key
	 * @return original estimate in minutes.
	 */
	public int getOriginalEstimate(String key)
	{

		String json = restProxy.getIssue(key);

		try
		{
			JSONObject object = new JSONObject(json);
			JSONObject timetracking = object.getJSONObject("fields").getJSONObject("timetracking");
			JSONObject values = timetracking.getJSONObject("value");
			if (values.has("timeoriginalestimate"))
			{
				return values.getInt("timeoriginalestimate");
			}
		}
		catch (JSONException e)
		{
		}
		return 0;
	}

	/**
	 *
	 * @param issueKey
	 * @return Resolution-Date.
	 * @throws RemotePermissionException
	 * @throws RemoteAuthenticationException
	 * @throws RemoteException
	 * @throws java.rmi.RemoteException
	 */
	public Date getResolutionDate(String issueKey) throws RemotePermissionException, RemoteAuthenticationException,
			RemoteException, java.rmi.RemoteException
	{

		Calendar cal = port.getResolutionDateByKey(sessionId, issueKey);
		if (cal == null)
		{
			return null;
		}

		return cal.getTime();
	}

	/**
	 * @param key
	 * @return worklog in minutes.
	 * @throws java.rmi.RemoteException
	 * @throws RemoteException
	 * @throws RemoteValidationException
	 * @throws RemotePermissionException
	 */
	public Map<Date, Integer> getWorklog(String key) throws RemotePermissionException, RemoteValidationException,
			RemoteException,
			java.rmi.RemoteException
	{

		Map<Date, Integer> effort = new HashMap<Date, Integer>();
		RemoteWorklog[] worklog = port.getWorklogs(sessionId, key);
		for (RemoteWorklog remoteWorklog : worklog)
		{

			Date date = remoteWorklog.getStartDate().getTime();
			Integer time = Math.round(remoteWorklog.getTimeSpentInSeconds() / 60f);
			if (effort.containsKey(date))
			{
				time += effort.get(key);

			}
			effort.put(date, time);
		}

		return effort;
	}

	/**
	 * Login to Jira.
	 * @param username
	 * @param password
	 * @throws RemoteAuthenticationException
	 * @throws RemoteException
	 * @throws java.rmi.RemoteException
	 * @throws URISyntaxException
	 * @throws MalformedURLException
	 */
	public void login(String username, String password) throws RemoteAuthenticationException, RemoteException,
			java.rmi.RemoteException, URISyntaxException, MalformedURLException
	{

		sessionId = port.login(username, password);

		Credentials credentials = new UsernamePasswordCredentials(username, password);
		httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
		AuthCache authCache = new BasicAuthCache();

		BasicScheme basicAuth = new BasicScheme();
		authCache.put(new HttpHost(new URL(baseUrl).getHost()), basicAuth);
		localContext.setAttribute(ClientContext.AUTH_CACHE, authCache);
	}

	/**
	 * Logout and destroy session.
	 * @throws java.rmi.RemoteException
	 */
	public void logout() throws java.rmi.RemoteException
	{

		port.logout(sessionId);
	}

}
