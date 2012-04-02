package de.paluch.burndown.sync.jira.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a>
 */
public class JiraCache {

    private static JiraCache instance = new JiraCache();

    private final Map<String, JiraRestIssue> issues = new ConcurrentHashMap<String, JiraRestIssue>();
    private final boolean useCache = false;

    /**
     * @return the instance
     */
    public static JiraCache getInstance() {
        return instance;
    }

    /**
     * Add Issue to cache if cache is used.
     * 
     * @param issue
     */
    public void addIssue(JiraRestIssue issue) {
        if (useCache) {
            issues.put(issue.getKey(), issue);
        }
    }

    /**
     * @param issueKey
     * @return JiraRestIssue
     */
    public JiraRestIssue getIssue(String issueKey) {
        return issues.get(issueKey);
    }

}
