package de.paluch.jira.compare;

import java.io.File;

import javax.xml.bind.JAXB;

import de.paluch.jira.compare.sync.jira.model.JiraConfig;
import de.paluch.jira.compare.sync.jira.model.JiraQueryResult;

/**
 * File-Based Data-Accessor for JiraSync config. See also {@link DataAccess}. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class DataAccess {

    public final static String SYNC_CONFIG_FILE = "jira-config.xml";
    public final static String DATA_LOCATION = "jiracompare.data.dir";

    public JiraConfig getJiraConfig() {

        File teamsFile = getSyncConfigFile();
        return JAXB.unmarshal(teamsFile, JiraConfig.class);

    }

    public JiraQueryResult getQueryResult(long hash) {

        File cacheFile = getCacheFile(hash);

        if (!cacheFile.exists()) {
            return null;
        }

        return JAXB.unmarshal(cacheFile, JiraQueryResult.class);
    }

    public void storeQueryResult(long hash, JiraQueryResult result) {
        File cacheFile = getCacheFile(hash);
        JAXB.marshal(result, cacheFile);

    }

    /**
     * @param hash
     * @return
     */
    private File getCacheFile(long hash) {
        String filename = Long.toHexString(hash) + ".xml";

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }
        File cacheDir = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), "cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File cacheFile = new File(cacheDir, filename);
        return cacheFile;
    }

    /**
     * @return
     */
    private File getSyncConfigFile() {

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }
        File syncConfigFile = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), SYNC_CONFIG_FILE);
        return syncConfigFile;
    }
}
