package de.paluch.jira.compare.sync.jira.client;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Jira Rest Client. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class JiraClient {

    private final String baseUrl;
    private final JiraRestClientProxy restProxy;
    private final BasicHttpContext localContext = new BasicHttpContext();
    private final DefaultHttpClient httpClient;

    /**
     * @param baseUrl
     * @throws URISyntaxException
     */
    public JiraClient(String baseUrl) throws URISyntaxException {

        this.baseUrl = baseUrl;

        httpClient = new DefaultHttpClient();
        ClientExecutor clientExecutor = new ApacheHttpClient4Executor(httpClient, localContext);

        ResteasyProviderFactory providerFactory = ResteasyProviderFactory.getInstance();

        restProxy = ProxyFactory.create(JiraRestClientProxy.class, new URI(baseUrl), clientExecutor, providerFactory);

    }

    /**
     * Find Issues by JQL.
     * 
     * @return List of Issue-Keys.
     */
    public java.util.List<String> findByJQL(String jql) {

        JiraRestSearchRequest request = new JiraRestSearchRequest();
        request.setJql(jql);
        try {
            JiraRestSearchResult result = restProxy.search(request);
            List<String> keys = new ArrayList<String>();
            for (JiraRestSearchResultIssue issue : result.getIssues()) {
                keys.add(issue.getKey());
            }

            return keys;
        } catch (ClientResponseFailure e) {
            System.err.println(e.getResponse().getEntity(String.class));
            return new ArrayList<String>();
        }

    }

    /**
     * Retrieve Issue by Key.
     * 
     * @param key
     * @return JiraRestIssue
     */
    public JiraRestIssue getIssue(String key) {

        JiraRestIssue result = JiraCache.getInstance().getIssue(key, getLoader(key));
        return result;

    }

    private Callable<JiraRestIssue> getLoader(final String key) {
        return new Callable<JiraRestIssue>() {

            /**
             * {@inheritDoc}
             */
            @Override
            public JiraRestIssue call() throws Exception {
                return restProxy.getIssue(key, "all");
            }

        };
    }

    /**
     * Login to Jira/Provide credentials.
     * 
     * @param username
     * @param password
     * @throws MalformedURLException
     */
    public void login(String username, String password) throws MalformedURLException {

        Credentials credentials = new UsernamePasswordCredentials(username, password);
        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
        AuthCache authCache = new BasicAuthCache();

        BasicScheme basicAuth = new BasicScheme();
        authCache.put(new HttpHost(new URL(baseUrl).getHost(), -1, new URL(baseUrl).getProtocol()), basicAuth);
        localContext.setAttribute(ClientContext.AUTH_CACHE, authCache);
    }

}
