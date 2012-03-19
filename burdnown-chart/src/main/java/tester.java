import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.time.Day;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.ui.RectangleInsets;

import de.paluch.burndown.SprintDaysGenerator;
import de.paluch.burndown.model.Sprint;
import de.paluch.burndown.model.SprintEffort;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 19.03.2012
 *<br>
 *<br>
 */
public class tester
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{

		XYPlot plot = new XYPlot();

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

		DateAxis dates = new DateAxis("Sprint Days");
		NumberAxis hours = new NumberAxis("Hours");
		NumberAxis burnedHours = new NumberAxis("Burned");
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeriesCollection tsc2 = new TimeSeriesCollection();

		SprintDaysGenerator gen = new SprintDaysGenerator();
		List<Date> days = gen.generateSprintDays(sprint.getDays(), sprint.getStartDate());
		TimeSeries ts = new TimeSeries("Days");
		TimeSeries ideal = new TimeSeries("Ideal");
		TimeSeries burned = new TimeSeries("Burned");
		TimeSeries unplanned = new TimeSeries("Unplanned");
		double value = sprint.getPlanned();

		double sprintGoalPD = sprint.getPlanned();;
		double idealPD = sprintGoalPD / days.size();

		for (Date date : days)
		{

			SprintEffort effort = getEffortFor(date, sprint.getEffort());
			if (effort != null)
			{
				ts.add(new TimeSeriesDataItem(new Day(date), value));
				burned.add(new TimeSeriesDataItem(new Day(date), effort.getBurned()));
				unplanned.add(new TimeSeriesDataItem(new Day(date), effort.getUnplanned()));
				value -= effort.getBurned();
			}

			ideal.add(new TimeSeriesDataItem(new Day(date), sprintGoalPD));
			sprintGoalPD -= idealPD;

		}

		hours.setTickUnit(new NumberTickUnit(25));

		tsc.addSeries(ts);
		tsc.addSeries(ideal);
		tsc.addSeries(MovingAverage.createMovingAverage(ts, "Average", ts.getItemCount() - 1, 1));
		tsc2.addSeries(unplanned);
		tsc2.addSeries(burned);

		plot.setDomainAxis(0, dates);
		plot.setRangeAxis(0, hours);
		plot.setDataset(0, tsc);
		plot.setDataset(1, tsc2);

		XYLineAndShapeRenderer xy = new XYLineAndShapeRenderer(true, false);

		XYAreaRenderer xyarea = new XYAreaRenderer();

		xy.setSeriesStroke(0, new BasicStroke(3.0f));
		xy.setSeriesPaint(0, new Color(111, 164, 91));
		xy.setSeriesPaint(1, new Color(118, 151, 187));
		xy.setSeriesPaint(2, new Color(141, 207, 116));

		xyarea.setSeriesPaint(0, new Color(221, 37, 44));
		xyarea.setSeriesPaint(1, new Color(237, 176, 51));

		plot.setRenderer(0, xy);
		plot.setRenderer(1, xyarea);
		plot.setShadowGenerator(new DefaultShadowGenerator());

		JFreeChart chart = new JFreeChart(plot);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		chart.setTitle("Sprint " + sprint.getId());
		plot.setDomainCrosshairVisible(false);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeCrosshairVisible(false);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(new Color(170, 170, 170));
		plot.setRangeGridlineStroke(new BasicStroke(1f));

		ChartFrame cf = new ChartFrame("chart", chart);
		cf.setPreferredSize(new java.awt.Dimension(500, 270));
		cf.pack();
		cf.setVisible(true);
		System.out.println("aa");
		Thread.sleep(1000);

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
}
