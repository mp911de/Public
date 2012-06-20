package de.paluch.burndown;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXB;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;
import de.paluch.burndown.model.Teams;

/**
 * File-Based Sprint Data Accessor. You have to specify burndown.data.dir System-Property in order to use the Data
 * Accessor. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 20.03.2012 <br>
 * <br>
 */
public class DataAccess {

    public final static String DATA_LOCATION = "burndown.data.dir";
    public final static String TEAMS_FILE = "teams.xml";
    private static final String BD_XML_SUFFIX = ".xml";

    public Sprint getSprint(String teamId, String sprintId) {

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }

        String localSprintId = sprintId;

        if (sprintId.equals("latest")) {
            List<String> sprintIds = listSprints(teamId);
            if (!sprintIds.isEmpty()) {
                localSprintId = sprintIds.get(0);
            }
        }

        File sprintFile = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), teamId + "/" + localSprintId
                + DataAccess.BD_XML_SUFFIX);
        if (sprintFile.exists()) {
            return JAXB.unmarshal(sprintFile, Sprint.class);
        }
        return null;

    }

    public Teams getTeams() {

        File teamsFile = getTeamsFile();
        return JAXB.unmarshal(teamsFile, Teams.class);

    }

    public List<String> listSprints(String teamId) {

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }
        File sprintsDir = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), teamId);
        List<String> result = new ArrayList<String>();
        File[] files = sprintsDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {

                return name.toLowerCase().endsWith(DataAccess.BD_XML_SUFFIX);
            }
        });

        if (files != null) {

            Arrays.sort(files, new Comparator<File>() {

                /**
                 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
                 */
                @Override
                public int compare(File o1, File o2) {

                    String name1 = o1.getName();
                    String name2 = o2.getName();

                    name1 = name1.substring(0, name1.indexOf('.'));
                    name2 = name2.substring(0, name2.indexOf('.'));

                    int sprint1 = Integer.parseInt(name1);
                    int sprint2 = Integer.parseInt(name2);

                    return (sprint2 - sprint1);
                }
            });
            for (File file : files) {
                String id = file.getName();
                int index = id.indexOf(DataAccess.BD_XML_SUFFIX);
                id = id.substring(0, index);
                result.add(id);
            }
        }

        return result;
    }

    public void saveOrUpdateTeam(Team team) {

        Teams teams = getTeams();
        Team teamToRemove = null;

        for (Team existing : teams.getTeams()) {
            if (existing.getId() != null && team.getId() != null && existing.getId().equals(team.getId())) {
                teamToRemove = existing;
                break;
            }
        }

        if (teamToRemove != null) {
            teams.getTeams().remove(teamToRemove);
        }
        teams.getTeams().add(team);

        JAXB.marshal(teams, getTeamsFile());

    }

    public void storeSprint(String teamId, Sprint sprint) {

        sprint.setLastChanged(new Date());

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }
        File folder = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), teamId);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File sprintFile = new File(folder, sprint.getId() + DataAccess.BD_XML_SUFFIX);
        JAXB.marshal(sprint, sprintFile);

    }

    /**
     * @return
     */
    private File getTeamsFile() {

        if (System.getProperty(DataAccess.DATA_LOCATION) == null) {
            throw new IllegalStateException("System property " + DataAccess.DATA_LOCATION + " not set.");
        }
        File teamsFile = new File(new File(System.getProperty(DataAccess.DATA_LOCATION)), DataAccess.TEAMS_FILE);
        return teamsFile;
    }
}
