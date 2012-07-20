package de.paluch.jira.compare.sync.jira.client;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a>
 */
public class JiraCache {

    private static JiraCache instance = new JiraCache();

    private final Cache<String, JiraRestIssue> cache;

    /**
     * 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public JiraCache() {

        cache = (Cache) CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(20, TimeUnit.MINUTES)
                .concurrencyLevel(2).build();
    }

    /**
     * @return the instance
     */
    public static JiraCache getInstance() {
        return instance;
    }

    /**
     * @param issueKey
     * @param loader
     * @return JiraRestIssue
     */
    public JiraRestIssue getIssue(String issueKey, Callable<JiraRestIssue> loader) {
        try {
            return cache.get(issueKey, loader);
        } catch (ExecutionException e) {
            return null;
        }
    }

}
