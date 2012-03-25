package de.paluch.burndown.sync.jira;

import java.io.File;

import javax.xml.bind.JAXB;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.sync.jira.model.JiraSync;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSyncDataAccess
{

	public final static String SYNC_CONFIG_FILE = "jira-sync.xml";

	public JiraSync getJiraSync()
	{

		File teamsFile = getSyncConfigFile();
		return JAXB.unmarshal(teamsFile, JiraSync.class);

	}

	/**
	 * @return
	 */
	private File getSyncConfigFile()
	{

		if (System.getProperty(DataAccess.DATA_LOCATION) == null)
		{
			throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
		}
		File syncConfigFile = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), SYNC_CONFIG_FILE);
		return syncConfigFile;
	}
}
