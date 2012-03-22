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
import de.paluch.burndown.chart.ChartDataFactory;
import de.paluch.burndown.chart.ChartFactory;
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




		SprintDaysGenerator gen = new SprintDaysGenerator();
		List<Date> days = gen.generateSprintDays(sprint.getDays(), sprint.getStartDate());


		ChartDataFactory factory = new ChartDataFactory();
		factory.createData(sprint, days);
		ChartFactory cf = new ChartFactory();

		ChartFrame frame = new ChartFrame("chart", cf.createChart(factory.getChartData()));
		frame.setPreferredSize(new java.awt.Dimension(500, 270));
		frame.pack();
		frame.setVisible(true);
		System.out.println("aa");
		Thread.sleep(1000);

	}


}
