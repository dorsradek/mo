package org.mo.app.gui;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import pl.eit.mo.core.HRMatrix;
import pl.eit.mo.dto.Dupa;

@SuppressWarnings("serial")
public class GanttChartPanel extends JPanel {

	private IntervalCategoryDataset dataset = new TaskSeriesCollection();
	private JFreeChart chart = createChart(dataset);
	private ChartPanel chartPanel = new ChartPanel(chart);

	public void reCreate() {
		this.remove(chartPanel);
		dataset = createDataset();
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
	}

	public static IntervalCategoryDataset createDataset() {

		final TaskSeries s = new TaskSeries("Scheduled");
		
		for (Dupa dupa : HRMatrix.dupas) {
			if (dupa.end == 0) {
				dupa.end = dupa.start;
			}
			s.add(new Task(String.valueOf(dupa.name), new SimpleTimePeriod(
					date(dupa.start), date(dupa.end))));
		}
		final TaskSeriesCollection collection = new TaskSeriesCollection();
		collection.add(s);

		return collection;
	}

	private static Date date(final int day) {

		final Calendar calendar = Calendar.getInstance();
		calendar.set(InputDataPanel.date.get(Calendar.YEAR),
				InputDataPanel.date.get(Calendar.MONTH),
				InputDataPanel.date.get(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DAY_OF_YEAR, day);
		final Date result = calendar.getTime();
		return result;
	}

	private JFreeChart createChart(final IntervalCategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createGanttChart("Gantt Chart",
				"Task", "Date", dataset, true, true, false);
		return chart;
	}

}