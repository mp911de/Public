package de.paluch.burndown.jsf.impl;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.paluch.burndown.DataAccess;
import de.paluch.burndown.TestUtil;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.Team;

import static org.junit.Assert.*;

import static org.mockito.Mockito.verify;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 22.03.2012
 *<br>
 *<br>
 */
@RunWith(MockitoJUnitRunner.class)
public class SprintControllerTest
{

	@InjectMocks
	private SprintController sut;

	@Mock
	private DataAccess dataAccess;

	/**
	 * Test method for {@link de.paluch.burndown.jsf.impl.SprintController#save()}.
	 */
	@Test
	public void testSave()
	{

		SprintListModel model = new SprintListModel();
		Sprint sprint = TestUtil.createSprint();
		model.setNewSprint(false);
		model.setOldStartDate(sprint.getStartDate());
		model.setSprint(sprint);

		model.setTeam(new Team("teamId"));
		sut.DATA_ACCESS = dataAccess;
		sut.setSprintListModel(model);
		sut.save();

		verify(dataAccess).storeSprint("teamId", sprint);
	}

	/**
	 * Test method for {@link de.paluch.burndown.jsf.impl.SprintController#save()}.
	 */
	@Test
	public void testSaveShift()
	{

		SprintListModel model = new SprintListModel();
		Sprint sprint = TestUtil.createSprint();
		model.setNewSprint(false);
		model.setOldStartDate(sprint.getStartDate());

		assertTrue(sprint.getStartDate().equals(sprint.getEffort().get(0).getDate()));

		sprint.setStartDate(new Date(0));
		model.setSprint(sprint);

		assertFalse(sprint.getStartDate().equals(sprint.getEffort().get(0).getDate()));

		model.setTeam(new Team("teamId"));
		sut.DATA_ACCESS = dataAccess;
		sut.setSprintListModel(model);
		sut.save();

		verify(dataAccess).storeSprint("teamId", sprint);
		assertTrue(sprint.getStartDate().equals(sprint.getEffort().get(0).getDate()));
	}

}
