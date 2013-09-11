package org.mo.app.gui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class TabbedMainPanel extends JTabbedPane {

	public static GanttChartPanel ganttChart = new GanttChartPanel();
	public static OtherChartPanel otherChart = new OtherChartPanel();
	public static JPanel inputData = new InputDataPanel();
	
	public TabbedMainPanel() {
		
		this.addTab("Okno podstawowe", inputData);
		this.setMnemonicAt(0, KeyEvent.VK_1);

		this.addTab("Wykres Gantta", ganttChart);
		this.setMnemonicAt(1, KeyEvent.VK_2);

		this.addTab("Wykres funkcji celu", otherChart);
		this.setMnemonicAt(2, KeyEvent.VK_3);
		
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

}
