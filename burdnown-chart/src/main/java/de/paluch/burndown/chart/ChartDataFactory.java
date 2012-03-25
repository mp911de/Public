package de.paluch.burndown.chart;

import java.util.Date;
import java.util.List;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesDataItem;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;

/**
 * Data-Factory to create Chart-Data from Sprint/Team-Model.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 20.03.2012
 *<br>
 *<br>
 */
public class ChartDataFactory
{

	private final ChartData chartData = new ChartData();
	public ChartDataFactory()
	{

	}

	/**
	 * Get SprintEffort for a specific Date.
	 * @param date
	 * @param effort
	 * @return SprintEffort
	 */
	private static SprintEffort getEffortFor(Date date, List<SprintEffort> effort)
	{

		for (SprintEffort sprintEffort : effort)
		{
			if (sprintEffort.getDate().equals(date))
			{
				return sprintEffort;
			}

		}
		return null;
	}

	/**
	 * Create Chart-Data.
	 * @param teamSize
	 * @param sprint
	 * @param days
	 */
	public void createData(int teamSize, Sprint sprint, List<Date> days)
	{

		double value = sprint.getPlanned();

		boolean first = true;
		Date lastDate = null;
		boolean isLastEffortEntry = true;
		int lastEffortIndex = -1;
		for (Date date : days)
		{

			lastDate = date;
			SprintEffort effort = ChartDataFactory.getEffortFor(date, sprint.getEffort());
			if (effort == null)
			{
				continue;
			}

			isLastEffortEntry = isLastEffortEntry(sprint, days, date);

			if (effort.getBurned() != 0 || effort.getUnplanned() != 0 || first || !isLastEffortEntry)
			{
				lastEffortIndex = days.indexOf(date);
				chartData.getBurndown().add(new TimeSeriesDataItem(new Day(date), value));
				first = false;
			}
			value -= effort.getBurned();

			chartData.getBurned().add(new TimeSeriesDataItem(new Day(date), effort.getBurned()));
			chartData.getUnplanned().add(new TimeSeriesDataItem(new Day(date), effort.getUnplanned()));
		}

		if (lastEffortIndex == days.size() - 2)
		{
			chartData.getBurndown().addOrUpdate(new TimeSeriesDataItem(new Day(lastDate), value));
		}

		chartData.getIdeal().addOrUpdate(new TimeSeriesDataItem(new Day(sprint.getStartDate()), sprint.getPlanned()));
		chartData.getIdeal().addOrUpdate(new TimeSeriesDataItem(new Day(lastDate), 0));

		chartData.getMainSeries().addSeries(chartData.getBurndown());
		chartData.getMainSeries().addSeries(chartData.getIdeal());

		chartData.getBaselineSeries().addSeries(chartData.getUnplanned());
		chartData.getBaselineSeries().addSeries(chartData.getBurned());
		chartData.setTeamsize(teamSize);
	}

	/**
	 * @return the chartData
	 */
	public ChartData getChartData()
	{

		return chartData;
	}

	/**
	 * @param sprint
	 * @param days
	 * @param date
	 * @return
	 */
	private boolean isLastEffortEntry(Sprint sprint, List<Date> days, Date date)
	{

		boolean isLastEffortEntry = true;
		for (int i = days.indexOf(date) + 1; i < days.size(); i++)
		{
			SprintEffort otherEffort = ChartDataFactory.getEffortFor(days.get(i), sprint.getEffort());
			if (otherEffort != null && (otherEffort.getBurned() != 0 || otherEffort.getUnplanned() != 0))
			{
				isLastEffortEntry = false;
				break;
			}
		}
		return isLastEffortEntry;
	}

}
