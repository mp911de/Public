package de.paluch.burndown.chart;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 20.03.2012
 *<br>
 *<br>
 */
public class ChartData
{

	private TimeSeriesCollection mainSeries;
	private TimeSeriesCollection baselineSeries;
	private TimeSeries burndown;
	private TimeSeries ideal;
	private TimeSeries burned;
	private TimeSeries unplanned;
	private String title;
	private int teamsize;

	public ChartData()
	{

		mainSeries = new TimeSeriesCollection();
		baselineSeries = new TimeSeriesCollection();
		burndown = new TimeSeries("Burndown");
		ideal = new TimeSeries("Ideal");
		burned = new TimeSeries("Burned");
		unplanned = new TimeSeries("Unplanned");
	}
	/**
	 * @return the baselineSeries
	 */
	public TimeSeriesCollection getBaselineSeries()
	{

		return baselineSeries;
	}
	/**
	 * @return the burndown
	 */
	public TimeSeries getBurndown()
	{

		return burndown;
	}
	/**
	 * @return the burned
	 */
	public TimeSeries getBurned()
	{

		return burned;
	}
	/**
	 * @return the ideal
	 */
	public TimeSeries getIdeal()
	{

		return ideal;
	}
	/**
	 * @return the mainSeries
	 */
	public TimeSeriesCollection getMainSeries()
	{

		return mainSeries;
	}
	/**
	 * @return the teamsize
	 */
	public int getTeamsize()
	{

		return teamsize;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{

		return title;
	}
	/**
	 * @return the unplanned
	 */
	public TimeSeries getUnplanned()
	{

		return unplanned;
	}
	/**
	 * @param baselineSeries the baselineSeries to set
	 */
	public void setBaselineSeries(TimeSeriesCollection baselineSeries)
	{

		this.baselineSeries = baselineSeries;
	}
	/**
	 * @param burndown the burndown to set
	 */
	public void setBurndown(TimeSeries burndown)
	{

		this.burndown = burndown;
	}
	/**
	 * @param burned the burned to set
	 */
	public void setBurned(TimeSeries burned)
	{

		this.burned = burned;
	}
	/**
	 * @param ideal the ideal to set
	 */
	public void setIdeal(TimeSeries ideal)
	{

		this.ideal = ideal;
	}
	/**
	 * @param mainSeries the mainSeries to set
	 */
	public void setMainSeries(TimeSeriesCollection mainSeries)
	{

		this.mainSeries = mainSeries;
	}
	/**
	 * @param teamsize the teamsize to set
	 */
	public void setTeamsize(int teamsize)
	{

		this.teamsize = teamsize;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{

		this.title = title;
	}

	/**
	 * @param unplanned the unplanned to set
	 */
	public void setUnplanned(TimeSeries unplanned)
	{

		this.unplanned = unplanned;
	}

}