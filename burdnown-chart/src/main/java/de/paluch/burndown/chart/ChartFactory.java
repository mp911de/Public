package de.paluch.burndown.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.ui.RectangleInsets;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 20.03.2012
 *<br>
 *<br>
 */
public class ChartFactory
{

	private Font font;

	/**
	 * @throws IOException
	 * @throws FontFormatException
	 *
	 */
	public ChartFactory() throws FontFormatException, IOException
	{

		Font baseFont = Font.createFont(Font.TRUETYPE_FONT, getClass()
				.getResourceAsStream("/HelveticaNeueLTCom-Lt.ttf"));

		font = baseFont.deriveFont(Font.PLAIN).deriveFont(14f);
	}

	public JFreeChart createChart(ChartData data)
	{

		DateAxis dates = new DateAxis("Sprint Days");
		NumberAxis hours = new NumberAxis("Hours");
		NumberAxis burnedHours = new NumberAxis("Burned");

		dates.setDateFormatOverride(new SimpleDateFormat("dd. MMM"));
		dates.setLabelFont(font);
		hours.setLabelFont(font);
		hours.setTickLabelFont(font);
		dates.setTickLabelFont(font);

		XYPlot plot = new XYPlot();
		hours.setTickUnit(new NumberTickUnit(25));

		plot.setDomainAxis(0, dates);
		plot.setRangeAxis(0, hours);
		plot.setDataset(0, data.getMainSeries());
		plot.setDataset(1, data.getBaselineSeries());

		XYLineAndShapeRenderer xyLineRenderer = new XYLineAndShapeRenderer(true, false);

		XYAreaRenderer xyAreaRenderer = new XYAreaRenderer();

		plot.setRenderer(0, xyLineRenderer);
		plot.setRenderer(1, xyAreaRenderer);
		plot.setShadowGenerator(new DefaultShadowGenerator());

		JFreeChart chart = new JFreeChart(plot);

		chart.setTitle(data.getTitle());
		setStyle(xyLineRenderer);
		setStyle(xyAreaRenderer);
		setStyle(chart);
		setStyle(plot);

		return chart;

	}

	/**
	 * @param plot
	 */
	private void setStyle(XYPlot plot)
	{

		plot.setDomainCrosshairVisible(false);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeCrosshairVisible(false);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(new Color(170, 170, 170));
		plot.setRangeGridlineStroke(new BasicStroke(1f));
	}

	/**
	 * @param chart
	 */
	private void setStyle(JFreeChart chart)
	{

		chart.getLegend().setItemFont(font);
		chart.getLegend().setFrame(new BlockFrame()
		{

			@Override
			public RectangleInsets getInsets()
			{

				return RectangleInsets.ZERO_INSETS;
			}

			@Override
			public void draw(Graphics2D g2, Rectangle2D area)
			{

			}
		});

		chart.getLegend().setPadding(10, 10,10, 10);
		chart.getLegend().setItemLabelPadding(new RectangleInsets(0, 5, 0, 15));

		chart.setPadding(new RectangleInsets(10, 10, 10, 10));
		chart.setBorderVisible(false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		chart.setBackgroundPaint(Color.WHITE);
	}

	/**
	 * @param xyAreaRenderer
	 */
	private void setStyle(XYAreaRenderer xyAreaRenderer)
	{

		xyAreaRenderer.setSeriesPaint(0, new Color(221, 37, 44));
		xyAreaRenderer.setSeriesPaint(1, new Color(237, 176, 51));
	}

	/**
	 * @param xyLineRenderer
	 */
	private void setStyle(XYLineAndShapeRenderer xyLineRenderer)
	{

		xyLineRenderer.setSeriesStroke(0, new BasicStroke(3.0f));
		xyLineRenderer.setSeriesShapesVisible(0, true);
		xyLineRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 10, 10));

		xyLineRenderer.setSeriesPaint(0, new Color(111, 164, 91));
		xyLineRenderer.setSeriesPaint(1, new Color(118, 151, 187));
		xyLineRenderer.setSeriesPaint(2, new Color(141, 207, 116));
	}
}
