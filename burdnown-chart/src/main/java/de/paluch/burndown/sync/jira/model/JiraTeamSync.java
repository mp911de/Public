package de.paluch.burndown.sync.jira.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JiraTeamSync
{

	@XmlAttribute
	private String teamId;
	private String projectKey;
	private boolean unplanned;
	private String unplannedFlagFieldId;
	private String unplannedFlagName;
	private EffortMode effortMode;
	private String storyPointsFieldId;
	private String sprintVersionNameScheme;

	/**
	 * @return the effortMode
	 */
	public EffortMode getEffortMode()
	{

		return effortMode;
	}

	/**
	 * @return the projectKey
	 */
	public String getProjectKey()
	{

		return projectKey;
	}

	/**
	 * @return the sprintVersionNameScheme
	 */
	public String getSprintVersionNameScheme()
	{

		return sprintVersionNameScheme;
	}

	/**
	 * @return the storyPointsCustomField
	 */
	public String getStoryPointsFieldId()
	{

		return storyPointsFieldId;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId()
	{

		return teamId;
	}

	/**
	 * @return the unplannedFlagField
	 */
	public String getUnplannedFlagFieldId()
	{

		return unplannedFlagFieldId;
	}

	/**
	 * @return the unplannedFlagName
	 */
	public String getUnplannedFlagName()
	{

		return unplannedFlagName;
	}

	/**
	 * @return the unplanned
	 */
	public boolean isUnplanned()
	{

		return unplanned;
	}

	/**
	 * @param effortMode the effortMode to set
	 */
	public void setEffortMode(EffortMode effortMode)
	{

		this.effortMode = effortMode;
	}

	/**
	 * @param projectKey the projectKey to set
	 */
	public void setProjectKey(String projectKey)
	{

		this.projectKey = projectKey;
	}

	/**
	 * @param sprintVersionNameScheme the sprintVersionNameScheme to set
	 */
	public void setSprintVersionNameScheme(String sprintVersionNameScheme)
	{

		this.sprintVersionNameScheme = sprintVersionNameScheme;
	}

	/**
	 * @param storyPointsCustomField the storyPointsCustomField to set
	 */
	public void setStoryPointsFieldId(String storyPointsCustomField)
	{

		storyPointsFieldId = storyPointsCustomField;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId)
	{

		this.teamId = teamId;
	}

	/**
	 * @param unplanned the unplanned to set
	 */
	public void setUnplanned(boolean unplanned)
	{

		this.unplanned = unplanned;
	}

	/**
	 * @param unplannedFlagField the unplannedFlagField to set
	 */
	public void setUnplannedFlagFieldId(String unplannedFlagField)
	{

		unplannedFlagFieldId = unplannedFlagField;
	}

	/**
	 * @param unplannedFlagName the unplannedFlagName to set
	 */
	public void setUnplannedFlagName(String unplannedFlagName)
	{

		this.unplannedFlagName = unplannedFlagName;
	}

}
