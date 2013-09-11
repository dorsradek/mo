package org.mo.app.gui;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.mo.app.App;

@SuppressWarnings("serial")
public class OtherChartPanel extends JPanel {

	private XYDataset ds = new DefaultXYDataset();
	private JFreeChart chart = ChartFactory.createXYLineChart("Wykres funkcji celu",
			"Iteracje", "Wartość funkji celu", ds, PlotOrientation.VERTICAL, true, true, false);
	private ChartPanel chartPanel = new ChartPanel(chart);

	public void reCreate() {
		this.remove(chartPanel);
		ds = createDataset();
		chart = ChartFactory.createXYLineChart("Wykres funkcji celu",
				"Iteracje", "Wartość funkji celu", ds, PlotOrientation.VERTICAL, true, true, false);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
	}

	private XYDataset createDataset() {

		DefaultXYDataset ds = new DefaultXYDataset();

		double[][] data = new double[2][App.outData.getGoalFunctionValues()
				.size()];

		int index = 0;
		for (Double value : App.outData.getGoalFunctionValues()) {
			System.out.println(index + ";" + value);

			data[0][index] = Double.valueOf(index);
			data[1][index] = value;
			index++;
		}

		ds.addSeries("Funkcja celu", data);

		return ds;
	}

}
