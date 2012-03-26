package de.paluch.burndown.chart;

import java.util.Date;
import java.util.List;

import org.jfree.data.time.TimeSeries;
import org.junit.Test;

import de.paluch.burndown.SprintDaysGenerator;
import de.paluch.burndown.TestUtil;
import de.paluch.burndown.model.Sprint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 22.03.2012
 *<br>
 *<br>
 */
public class ChartDataFactoryTest
{

	private final ChartDataFactory sut = new ChartDataFactory();

	/**
	 * Test method for {@link de.paluch.burndown.chart.ChartDataFactory#createData(de.paluch.burndown.model.Sprint, java.util.List)}.
	 */
	@Test
	public void testCreateData()
	{

		Sprint sprint = TestUtil.createSprint();

		SprintDaysGenerator gen = new SprintDaysGenerator();
		List<Date> days = gen.generateSprintDays(sprint.getDays(), sprint.getStartDate());

		sut.createData(5, sprint, days);

		ChartData result = sut.getChartData();

		assertEquals(2, result.getBaselineSeries().getSeries().size());

		TimeSeries series1 = (TimeSeries) result.getBaselineSeries().getSeries().get(0);
		assertEquals(10, sprint.getDays());
		assertEquals(8, series1.getItemCount());
	}

	/**
	 * Test method for {@link de.paluch.burndown.chart.ChartDataFactory#getChartData()}.
	 */
	@Test
	public void testGetChartData()
	{

		assertNotNull(sut.getChartData());
	}

}
