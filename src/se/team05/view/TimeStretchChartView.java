package se.team05.view;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import se.team05.content.Result;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class TimeStretchChartView extends GraphicalView
{
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static int maxTime = 60;
	private static int minTime = 0;
	private static long minTimeStamp;
	private static long maxTimeStamp;
	private static XYMultipleSeriesRenderer renderer;

	private TimeStretchChartView(Context context, AbstractChart abstractChart)
	{
		super(context, abstractChart);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method returns a new graphical view as a pie chart view. It uses the
	 * income and costs and the static color constants of the class to create
	 * the chart.
	 * 
	 * @param context
	 *            the context
	 * @param income
	 *            the total income
	 * @param costs
	 *            the total cost
	 * @return a GraphicalView object as a pie chart
	 */
	public static GraphicalView getNewInstance(Context context, List<Result> results, String nameOfRoute)
	{
		if (results.size()!=1)
		{	
			int resultMaxTime = Collections.max(results).getTime()/60;
			maxTime = resultMaxTime >= maxTime ? resultMaxTime:maxTime;
		}
		minTimeStamp = results.get(0).getTimestamp() * 1000;
		maxTimeStamp = results.get(results.size() - 1).getTimestamp() * 1000;
		String title = "Route: "+nameOfRoute;
		renderer = createRendere();


		setChartSettings();
		renderer.setXLabels(5);
		renderer.setYLabels(10);
		SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(0);
		seriesRenderer.setDisplayChartValues(true);
		return ChartFactory.getTimeChartView(context, createDateTimeSet(title, results), renderer, DATE_FORMAT);
	}

	protected static XYMultipleSeriesDataset createDateTimeSet(String title, List<Result> results)
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		TimeSeries series = new TimeSeries(title);
		for (Result result : results)
		{
			Date date = new Date(result.getTimestamp() * 1000);
			series.add(date, result.getTime() / 60);
		}
		dataset.addSeries(series);
		return dataset;
	}

	protected static XYMultipleSeriesRenderer createRendere()
	{

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setXLabelsColor(Color.BLUE);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setShowGridX(true);
		renderer.setGridColor(Color.BLACK);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setYLabelsColor(VISIBLE, Color.BLUE);
		renderer.setMargins(new int[] { 20, 50, 15, 20 });
		XYSeriesRenderer xySerieRenderer = new XYSeriesRenderer();
		xySerieRenderer.setColor(Color.BLUE);
		xySerieRenderer.setPointStyle(PointStyle.CIRCLE);
		renderer.addSeriesRenderer(xySerieRenderer);

		return renderer;
	}

	protected static void setChartSettings()
	{
		renderer.setChartTitle("TimePassed");
		renderer.setYTitle("Minutes");
		renderer.setXAxisMin(minTimeStamp);
		renderer.setXAxisMax(maxTimeStamp);
		renderer.setYAxisMin(minTime);
		renderer.setYAxisMax(maxTime);
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setApplyBackgroundColor(true);
	}

}
