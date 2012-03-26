package de.paluch.burndown;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;


/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 22.03.2012
 *<br>
 *<br>
 */
public class TestUtil
{

	/**
	 * @return
	 */
	public static Sprint createSprint()
	{

		Sprint sprint = new Sprint();
		sprint.setId("21");
		sprint.setDays(10);
		sprint.setPlanned(216);
		sprint.setStartDate(java.sql.Date.valueOf("2011-01-17"));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-17"), 1, 1));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-18"), 24, 0));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-19"), 16, 0));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-20"), 8, 0));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-23"), 0, 0));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-24"), 8, 0));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-25"), 62, 4));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-26"), 10, 3.5));
		sprint.getEffort().add(new SprintEffort(java.sql.Date.valueOf("2011-01-27"), 32, 7));
		return sprint;
	}

}
