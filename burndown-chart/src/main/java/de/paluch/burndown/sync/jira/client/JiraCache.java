package de.paluch.burndown.sync.jira.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a>
 */
public class JiraCache {

    private static JiraCache instance = new JiraCache();
    private final Map<String, JiraRestIssue> issues = new ConcurrentHashMap<String, JiraRestIssue>();

    /**
     * @return the instance
     */
    public static JiraCache getInstance() {
        return instance;
    }

    /**
     * @return the issues
     */
    public Map<String, JiraRestIssue> getIssues() {
        return issues;
    }

}
