package de.paluch.burndown.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.ui.RectangleInsets;

/**
 * Chart-Factory for the JFreeChart. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 20.03.2012 <br>
 * <br>
 */
public class ChartFactory {

    private final Font font;

    /**
     * @throws IOException
     * @throws FontFormatException
     */
    public ChartFactory() throws FontFormatException, IOException {

        Font baseFont = Font.createFont(Font.TRUETYPE_FONT, getClass()
                .getResourceAsStream("/HelveticaNeueLTCom-Lt.ttf"));

        font = baseFont.deriveFont(Font.PLAIN).deriveFont(14f);
    }

    /**
     * Create Burndown Chart.
     * 
     * @param data
     * @return JFreeChart
     */
    public JFreeChart createChart(ChartData data) {

        XYPlot plot = setupAxesAndCreatePlot(data);

        setData(data, plot);

        XYLineAndShapeRenderer xyLineRenderer = new XYLineAndShapeRenderer();
        xyLineRenderer.setBaseShapesVisible(false);

        XYAreaRenderer xyAreaRenderer = new XYAreaRenderer();

        plot.setRenderer(0, xyLineRenderer);
        plot.setRenderer(1, xyAreaRenderer);
        plot.setShadowGenerator(new DefaultShadowGenerator());

        JFreeChart chart = new JFreeChart(plot);

        chart.setTitle(new TextTitle(data.getTitle(), font.deriveFont(Font.BOLD).deriveFont(18f)));

        setStyle(xyLineRenderer);
        setStyle(xyAreaRenderer);
        setStyle(chart);
        setStyle(plot);

        if (data.getSubtitle() != null) {
            chart.addSubtitle(new TextTitle(data.getSubtitle(), font.deriveFont(10f)));
        }

        return chart;

    }

    /**
     * @param data
     * @param plot
     */
    private void setData(ChartData data, XYPlot plot) {

        plot.setDataset(0, data.getMainSeries());
        plot.setDataset(1, data.getBaselineSeries());

        plot.mapDatasetToRangeAxis(1, 1);
    }

    /**
     * @param chart
     */
    private void setStyle(JFreeChart chart) {

        chart.getLegend().setItemFont(font);
        chart.getLegend().setFrame(new BlockFrame() {

            @Override
            public void draw(Graphics2D g2, Rectangle2D area) {

            }

            @Override
            public RectangleInsets getInsets() {

                return RectangleInsets.ZERO_INSETS;
            }
        });

        chart.getLegend().setPadding(10, 10, 10, 10);
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
    private void setStyle(XYAreaRenderer xyAreaRenderer) {

        xyAreaRenderer.setSeriesPaint(0, new Color(221, 37, 44));
        xyAreaRenderer.setSeriesPaint(1, new Color(237, 176, 51));
    }

    /**
     * @param xyLineRenderer
     */
    private void setStyle(XYLineAndShapeRenderer xyLineRenderer) {

        xyLineRenderer.setSeriesStroke(0, new BasicStroke(3.0f));
        xyLineRenderer.setSeriesShapesVisible(0, true);
        xyLineRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 10, 10));
        xyLineRenderer.setSeriesOutlineStroke(0, new BasicStroke(3.0f));

        xyLineRenderer.setSeriesPaint(0, new Color(111, 164, 91));
        xyLineRenderer.setSeriesPaint(1, new Color(118, 151, 187));
        xyLineRenderer.setSeriesPaint(2, new Color(141, 207, 116));
    }

    /**
     * @param plot
     */
    private void setStyle(XYPlot plot) {

        plot.setDomainCrosshairVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(170, 170, 170));
        plot.setRangeGridlineStroke(new BasicStroke(1f));
    }

    /**
     * @return
     */
    private XYPlot setupAxesAndCreatePlot(ChartData chartData) {

        DateAxis dates = new DateAxis("Sprint Days");
        dates.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
        // dates.setDateFormatOverride(new SimpleDateFormat("dd. MM"));

        TickUnits tu = new TickUnits();
        tu.add(new DateTickUnit(DateTickUnitType.DAY, 1, new SimpleDateFormat("dd. MM")));
        tu.add(new DateTickUnit(DateTickUnitType.DAY, 2, new SimpleDateFormat("dd. MM")));
        tu.add(new DateTickUnit(DateTickUnitType.DAY, 3, new SimpleDateFormat("dd. MM")));
        tu.add(new DateTickUnit(DateTickUnitType.DAY, 4, new SimpleDateFormat("dd. MM")));

        dates.setTickLabelInsets(new RectangleInsets(10, 5, 0, 5));
        dates.setStandardTickUnits(tu);
        dates.setLowerMargin(0.01d);
        dates.setUpperMargin(0.01d);

        NumberAxis hours = new NumberAxis("Burndown");
        NumberAxis burnedHours = new NumberAxis("Effort");
        burnedHours.setAutoRange(false);
        burnedHours.setRange(0, chartData.getTeamsize() * 8 * 2);

        dates.setLabelFont(font);
        dates.setTickLabelFont(font);

        hours.setLabelFont(font);
        hours.setTickLabelFont(font);
        hours.setTickUnit(new NumberTickUnit(25));

        burnedHours.setLabelFont(font);
        burnedHours.setTickLabelFont(font);
        burnedHours.setTickUnit(new NumberTickUnit(10));

        XYPlot plot = new XYPlot();
        plot.setDomainAxis(dates);
        plot.setRangeAxis(0, hours);
        plot.setRangeAxis(1, burnedHours);
        return plot;
    }
}
