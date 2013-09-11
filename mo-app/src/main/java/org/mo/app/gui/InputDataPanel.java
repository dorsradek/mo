package org.mo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.mo.app.App;

import pl.eit.mo.core.impl.movements.InteligentRandEmployeeMovement;
import pl.eit.mo.core.impl.movements.RandRandEmployeeMovement;
import pl.eit.mo.core.impl.movements.RandRandFieldMovement;
import pl.eit.mo.core.impl.repairalgorithms.RandRepairAlgorithm;
import pl.eit.mo.core.impl.repairalgorithms.UpRepairAlgorithm;
import pl.eit.mo.core.interfaces.IMovement;
import pl.eit.mo.core.interfaces.IRepairAlgorithm;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class InputDataPanel extends JPanel {

	private JPanel sth = new JPanel();
	private JPanel calendarPanel = new JPanel();
	private JPanel backgroundPanel = new JPanel();
	private JPanel algorithmPanel = new JPanel();
	private JPanel otherSetPanel = new JPanel();
	private JPanel movementPanel = new JPanel();
	private JPanel startButtonPanel = new JPanel();
	private JLabel label = new JLabel();
	public static JLabel scoreLabel = new JLabel();
	private JButton startButton = new JButton("Start");
	private JCalendar calendar = new JCalendar();
	public static Calendar date;

	public static int periosInDays;
	// IRepairAlgorithm
	public static int iMaxNumberOfDayRepairsProbes;
	public static int iNumberOfRepairsProbes;
	// IMovement
	public static int maxNumberOfMovementProbes;
	public static int numberOfActionsInDay;
	public static int weakMovementTabooIterations;
	public static int mediumMovementTabooIterations;
	public static int goodMovementTabooIterations;
	public static int theBestMovementTabooIterations;
	// HRAllocator
	public static int numberOfIterations;

	private SpinnerModel periosInDaysModel;
	private SpinnerModel iMaxNumberOfDayRepairsProbesModel;
	private SpinnerModel iNumberOfRepairsProbesModel;
	private SpinnerModel maxNumberOfMovementProbesModel;
	private SpinnerModel numberOfActionsInDayModel;
	private SpinnerModel weakMovementTabooIterationsModel;
	private SpinnerModel mediumMovementTabooIterationsModel;
	private SpinnerModel goodMovementTabooIterationsModel;
	private SpinnerModel theBestMovementTabooIterationsModel;
	private SpinnerModel numberOfIterationsModel;

	private JComboBox<IRepairAlgorithm> algorithmsComboBox;
	private List<IRepairAlgorithm> algorithmsList = new ArrayList<IRepairAlgorithm>();
	DefaultComboBoxModel algorithmsListModel;

	private JComboBox<IRepairAlgorithm> algorithmsComboBoxChosen;
	private List<IRepairAlgorithm> algorithmsListChosen = new ArrayList<IRepairAlgorithm>();
	DefaultComboBoxModel algorithmsListChosenModel;

	private JComboBox<IMovement> movementsComboBox;
	private List<IMovement> movementsList = new ArrayList<IMovement>();
	DefaultComboBoxModel movementsListModel;

	private JComboBox<IMovement> movementsComboBoxChosen;
	private List<IMovement> movementsListChosen = new ArrayList<IMovement>();
	DefaultComboBoxModel movementsListChosenModel;

	RandRepairAlgorithm algorithm1 = new RandRepairAlgorithm();
	UpRepairAlgorithm algorithm2 = new UpRepairAlgorithm();

	InteligentRandEmployeeMovement movement1 = new InteligentRandEmployeeMovement(1);
	RandRandEmployeeMovement movement2 = new RandRandEmployeeMovement();
	RandRandFieldMovement movement3 = new RandRandFieldMovement();

	private JComboBox createComboBox(DefaultComboBoxModel model) {

		JComboBox<IRepairAlgorithm> algorithmsComboBox = new JComboBox<IRepairAlgorithm>(model);
		return algorithmsComboBox;
	}

	public InputDataPanel() {

		algorithmsList.add(algorithm1);
		algorithmsList.add(algorithm2);
		algorithmsListModel = new DefaultComboBoxModel(this.algorithmsList.toArray());
		algorithmsListChosenModel = new DefaultComboBoxModel(this.algorithmsListChosen.toArray());

		movementsList.add(movement1);
		movementsList.add(movement2);
		movementsList.add(movement3);
		movementsListModel = new DefaultComboBoxModel(this.movementsList.toArray());
		movementsListChosenModel = new DefaultComboBoxModel(this.movementsListChosen.toArray());

		algorithmsComboBox = createComboBox(algorithmsListModel);
		movementsComboBox = createComboBox(movementsListModel);

		algorithmsComboBoxChosen = createComboBox(algorithmsListChosenModel);
		movementsComboBoxChosen = createComboBox(movementsListChosenModel);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getUserData();
				/*try {
					App.doSth();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(App.frame, "Wprowadź poprawne dane!", "Błąd", JOptionPane.ERROR_MESSAGE);
				}*/
				Runnable runnable = new App();
			    Thread newThread = new Thread(runnable);
			    newThread.start();
			    
				
			}
		});

		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		backgroundPanel.setPreferredSize(new Dimension(400, 400));
		label = new JLabel("WYNIK:");
		label.setFont(new Font("Serif", Font.BOLD, 48));
		label.setForeground(Color.RED);
		label.setPreferredSize(new Dimension(300, 60));
		backgroundPanel.add(label);

		scoreLabel = new JLabel("");
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 48));
		scoreLabel.setForeground(Color.BLUE);
		scoreLabel.setPreferredSize(new Dimension(300, 60));
		backgroundPanel.add(scoreLabel);
		this.add(backgroundPanel);

		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
		backgroundPanel.setPreferredSize(new Dimension(100, 100));
		backgroundPanel.setBorder(BorderFactory.createTitledBorder("Dane wejściowe"));
		this.add(backgroundPanel);

		label = new JLabel();
		label.setText("Data początkowa: ");
		label.setPreferredSize(new Dimension(230, 100));
		calendarPanel = new JPanel();
		calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.X_AXIS));
		calendarPanel.add(label);
		calendarPanel.add(calendar);
		calendarPanel.setPreferredSize(new Dimension(100, 80));

		algorithmPanel = new JPanel();
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.setPreferredSize(new Dimension(100, 120));
		algorithmPanel.setBorder(BorderFactory.createTitledBorder("Algorytm"));

		// Algorytm
		label = new JLabel();
		label.setText("Algorytm:");
		label.setPreferredSize(new Dimension(100, 100));
		algorithmsComboBox.setPreferredSize(new Dimension(200, 100));
		JButton addAlgorithmButton = new JButton("+");
		addAlgorithmButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(algorithmsComboBox.getSelectedItem() != null) {
					IRepairAlgorithm alg = (IRepairAlgorithm) algorithmsComboBox.getSelectedItem();
					iMaxNumberOfDayRepairsProbes = (Integer) iMaxNumberOfDayRepairsProbesModel.getValue();
					iNumberOfRepairsProbes = (Integer) iNumberOfRepairsProbesModel.getValue();
					alg.setMaxNumberOfDayRepairsProbes(iMaxNumberOfDayRepairsProbes);
					alg.setNumberOfRepairsProbes(iNumberOfRepairsProbes);
					algorithmsListChosenModel.addElement(alg);
					algorithmsListModel.removeElement(alg);
					App.repairAlgorithms.add(alg);
				}
			}
		});

		addAlgorithmButton.setPreferredSize(new Dimension(42, 100));
		sth = new JPanel();
		sth.setLayout(new BoxLayout(sth, BoxLayout.X_AXIS));
		sth.add(label);
		sth.add(algorithmsComboBox);
		sth.add(addAlgorithmButton);
		sth.setPreferredSize(new Dimension(100, 100));
		algorithmPanel.add(sth);

		label = new JLabel();
		label.setText("Algorytm wybrany:");
		label.setPreferredSize(new Dimension(100, 100));
		algorithmsComboBoxChosen.setPreferredSize(new Dimension(200, 100));
		JButton removeAlgorithmButton = new JButton("-");
		removeAlgorithmButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(algorithmsComboBoxChosen.getSelectedItem() != null) {
					IRepairAlgorithm alg = (IRepairAlgorithm) algorithmsComboBoxChosen.getSelectedItem();
					algorithmsListModel.addElement(alg);
					algorithmsListChosenModel.removeElement(alg);
					App.repairAlgorithms.remove(alg);
				}
			}
		});
		removeAlgorithmButton.setPreferredSize(new Dimension(42, 100));
		sth = new JPanel();
		sth.setLayout(new BoxLayout(sth, BoxLayout.X_AXIS));
		sth.add(label);
		sth.add(algorithmsComboBoxChosen);
		sth.add(removeAlgorithmButton);
		sth.setPreferredSize(new Dimension(100, 100));
		algorithmPanel.add(sth);

		iMaxNumberOfDayRepairsProbesModel = createLabelAndSpinner(algorithmPanel, "Maksymalna liczba prób naprawy każdego dnia:");
		iNumberOfRepairsProbesModel = createLabelAndSpinner(algorithmPanel, "Liczba prób naprawy danym algorytmem:");

		movementPanel = new JPanel();
		movementPanel.setLayout(new BoxLayout(movementPanel, BoxLayout.Y_AXIS));
		movementPanel.setPreferredSize(new Dimension(100, 200));
		movementPanel.setBorder(BorderFactory.createTitledBorder("Ruch"));

		// Movement
		label = new JLabel();
		label.setText("Ruch:");
		label.setPreferredSize(new Dimension(100, 100));
		movementsComboBox.setPreferredSize(new Dimension(200, 100));
		JButton addMovementButton = new JButton("+");
		addMovementButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(movementsComboBox.getSelectedItem() != null) {
					IMovement mov = (IMovement) movementsComboBox.getSelectedItem();
					
					maxNumberOfMovementProbes = (Integer) maxNumberOfMovementProbesModel.getValue();
					numberOfActionsInDay = (Integer) numberOfActionsInDayModel.getValue();
					weakMovementTabooIterations = (Integer) weakMovementTabooIterationsModel.getValue();
					mediumMovementTabooIterations = (Integer) mediumMovementTabooIterationsModel.getValue();
					goodMovementTabooIterations = (Integer) goodMovementTabooIterationsModel.getValue();
					theBestMovementTabooIterations = (Integer) theBestMovementTabooIterationsModel.getValue();
					
					mov.setMaxNumberOfMovementProbes(InputDataPanel.maxNumberOfMovementProbes);
					mov.setNumberOfActionsInDay(InputDataPanel.numberOfActionsInDay);
					mov.setWeakMovementTabooIterations(InputDataPanel.weakMovementTabooIterations);
					mov.setMediumMovementTabooIterations(InputDataPanel.mediumMovementTabooIterations);
					mov.setGoodMovementTabooIterations(InputDataPanel.goodMovementTabooIterations);
					mov.setTheBestMovementTabooIterations(InputDataPanel.theBestMovementTabooIterations);
					movementsListChosenModel.addElement(mov);
					movementsListModel.removeElement(mov);
					App.movements.add(mov);
				}
			}
		});
		addMovementButton.setPreferredSize(new Dimension(42, 100));
		sth = new JPanel();
		sth.setLayout(new BoxLayout(sth, BoxLayout.X_AXIS));
		sth.add(label);
		sth.add(movementsComboBox);
		sth.add(addMovementButton);
		sth.setPreferredSize(new Dimension(100, 100));
		movementPanel.add(sth);

		label = new JLabel();
		label.setText("Ruch wybrany:");
		label.setPreferredSize(new Dimension(100, 100));
		movementsComboBoxChosen.setPreferredSize(new Dimension(200, 100));
		JButton removeMovementButton = new JButton("-");
		removeMovementButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(movementsComboBoxChosen.getSelectedItem() != null) {
					IMovement mov = (IMovement) movementsComboBoxChosen.getSelectedItem();
					movementsListModel.addElement(mov);
					movementsListChosenModel.removeElement(mov);
					App.movements.remove(mov);
				}
			}
		});
		removeMovementButton.setPreferredSize(new Dimension(42, 100));
		sth = new JPanel();
		sth.setLayout(new BoxLayout(sth, BoxLayout.X_AXIS));
		sth.add(label);
		sth.add(movementsComboBoxChosen);
		sth.add(removeMovementButton);
		sth.setPreferredSize(new Dimension(100, 100));
		movementPanel.add(sth);

		maxNumberOfMovementProbesModel = createLabelAndSpinner(movementPanel, "Maksymalna liczba prób znalezienia ruchu:");
		numberOfActionsInDayModel = createLabelAndSpinner(movementPanel, "Liczba podjętych akcji tym ruchem w jednym dniu:");
		weakMovementTabooIterationsModel = createLabelAndSpinner(movementPanel, "Liczba iteracji na liscie tabu <50:");
		mediumMovementTabooIterationsModel = createLabelAndSpinner(movementPanel, "Liczba iteracji na liscie tabu 50-80:");
		goodMovementTabooIterationsModel = createLabelAndSpinner(movementPanel, "Liczba iteracji na liscie tabu 80-100:");
		theBestMovementTabooIterationsModel = createLabelAndSpinner(movementPanel, "Liczba iteracji na liscie tabu >100:");

		otherSetPanel = new JPanel();
		otherSetPanel.setLayout(new BoxLayout(otherSetPanel, BoxLayout.Y_AXIS));
		otherSetPanel.setPreferredSize(new Dimension(100, 40));
		otherSetPanel.setBorder(BorderFactory.createTitledBorder("Inne"));
		otherSetPanel.add(calendarPanel);

		periosInDaysModel = createLabelAndSpinner(otherSetPanel, "Okres rozliczeniowy:");
		numberOfIterationsModel = createLabelAndSpinner(otherSetPanel, "Liczba powtórzeń algorytmu:");

		startButtonPanel = new JPanel();
		startButtonPanel.setLayout(new BoxLayout(startButtonPanel, BoxLayout.X_AXIS));
		startButtonPanel.add(startButton);
		startButtonPanel.setPreferredSize(new Dimension(50, 60));

		backgroundPanel.add(algorithmPanel);
		backgroundPanel.add(movementPanel);
		backgroundPanel.add(otherSetPanel);
		backgroundPanel.add(startButtonPanel);
	}

	private SpinnerModel createLabelAndSpinner(JPanel panel, String text) {
		label = new JLabel();
		label.setText(text);
		label.setPreferredSize(new Dimension(320, 100));
		SpinnerModel model = new SpinnerNumberModel(1, 1, 999, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setPreferredSize(new Dimension(20, 100));
		sth = new JPanel();
		sth.setLayout(new BoxLayout(sth, BoxLayout.X_AXIS));
		sth.add(label);
		sth.add(spinner);
		sth.setPreferredSize(new Dimension(100, 100));
		panel.add(sth);
		return model;
	}

	private void getUserData() {
		
		date = calendar.getCalendar();
		periosInDays = (Integer) periosInDaysModel.getValue();		
		numberOfIterations = (Integer) numberOfIterationsModel.getValue();
	}
}
