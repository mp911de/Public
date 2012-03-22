package de.paluch.burndown.chart;

import java.util.Date;
import java.util.List;

import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.time.Day;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 20.03.2012
 *<br>
 *<br>
 */
public class ChartDataFactory
{

	private ChartData chartData = new ChartData();

	/**
	 *
	 */
	public ChartDataFactory()
	{

	}

	public void createData(Sprint sprint, List<Date> days)
	{

		double value = sprint.getPlanned();
		double sprintGoalPD = sprint.getPlanned();
		double idealPD = sprintGoalPD / days.size();

		boolean first = true;
		boolean previousData = false;
		for (Date date : days)
		{

			SprintEffort effort = getEffortFor(date, sprint.getEffort());
			if (effort != null)
			{

				boolean isLastEffortEntry = true;
				for (int i = days.indexOf(date) + 1; i < days.size(); i++)
				{
					SprintEffort otherEffort = getEffortFor(days.get(i), sprint.getEffort());
					if (otherEffort != null && (otherEffort.getBurned() != 0 || otherEffort.getUnplanned() != 0))
					{
						isLastEffortEntry = false;
						break;
					}
				}

				if (effort.getBurned() != 0 || effort.getUnplanned() != 0 || first || !isLastEffortEntry)
				{
					chartData.getBurndown().add(new TimeSeriesDataItem(new Day(date), value));
					first = false;
				}

				chartData.getBurned().add(new TimeSeriesDataItem(new Day(date), effort.getBurned()));
				chartData.getUnplanned().add(new TimeSeriesDataItem(new Day(date), effort.getUnplanned()));
				value -= effort.getBurned();
			}

			chartData.getIdeal().add(new TimeSeriesDataItem(new Day(date), sprintGoalPD));
			sprintGoalPD -= idealPD;
		}

		chartData.getMainSeries().addSeries(chartData.getBurndown());
		chartData.getMainSeries().addSeries(chartData.getIdeal());
		if (chartData.getBurndown()
				.getItemCount() > 1)
		{
			chartData.getMainSeries().addSeries(
					MovingAverage.createMovingAverage(chartData.getBurndown(), "Trend", chartData.getBurndown()
							.getItemCount() - 1, 1));
		}

		chartData.getBaselineSeries().addSeries(chartData.getUnplanned());
		chartData.getBaselineSeries().addSeries(chartData.getBurned());

	}

	/**
	 * @param date
	 * @param effort
	 * @return
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
	 * @return the chartData
	 */
	public ChartData getChartData()
	{

		return chartData;
	}

}
