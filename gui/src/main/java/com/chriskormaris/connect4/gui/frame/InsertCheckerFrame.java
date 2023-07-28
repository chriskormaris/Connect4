package com.chriskormaris.connect4.gui.frame;


import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InsertCheckerFrame extends JFrame {

	public int row;
	public int column;

	public int player;

	private final JComboBox<Integer> row_drop_down;
	private final JComboBox<Integer> column_drop_down;
	private final JComboBox<String> player_drop_down;
	private final JButton apply;
	private final JButton cancel;

	private final GUI gui;

	public InsertCheckerFrame(GUI gui) {
		super("Insert Checker");

		this.gui = gui;

		int numOfRows = gui.gameParameters.getNumOfRows();
		int numOfColumns = gui.gameParameters.getNumOfColumns();

		row = numOfRows - 1;
		column = 0;
		player = Constants.P1;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		int width = 400;
		int height = 220;
		setSize(width, height);
		setLocationRelativeTo(gui);
		setResizable(false);

		EventHandler handler = new EventHandler();

		JLabel rowLabel = new JLabel("Row: ");
		JLabel columnLabel = new JLabel("Column: ");
		JLabel playerLabel = new JLabel("Player: ");

		add(rowLabel);
		add(columnLabel);
		add(playerLabel);

		row_drop_down = new JComboBox<>();
		for (int i = 0; i < numOfRows; i++) {
			row_drop_down.addItem(i + 1);
		}
		row_drop_down.setSelectedIndex(row);

		column_drop_down = new JComboBox<>();
		for (int j = 0; j < numOfColumns; j++) {
			column_drop_down.addItem(j + 1);
		}
		column_drop_down.setSelectedIndex(column);

		player_drop_down = new JComboBox<>();
		player_drop_down.addItem("Player 1");
		player_drop_down.addItem("Player 2");
		player_drop_down.setSelectedIndex(0);

		add(row_drop_down);
		add(column_drop_down);
		add(player_drop_down);

		rowLabel.setBounds(25, 25, 175, 20);
		columnLabel.setBounds(25, 55, 175, 20);
		playerLabel.setBounds(25, 85, 175, 20);

		row_drop_down.setBounds(195, 25, 160, 20);
		column_drop_down.setBounds(195, 55, 160, 20);
		player_drop_down.setBounds(195, 85, 160, 20);

		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);

		int distance = 10;
		apply.setBounds((width / 2) - 110 - (distance / 2), 130, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds((width / 2) - 10 + (distance / 2), 130, 100, 30);
		cancel.addActionListener(handler);
	}


	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == cancel) {
				dispose();
			} else if (ev.getSource() == apply) {
				try {

					row = row_drop_down.getSelectedIndex();
					column = column_drop_down.getSelectedIndex();
					player = player_drop_down.getSelectedIndex();

					player = (player == 0) ? Constants.P1 : Constants.P2;

					if (gui.board.getGameBoard()[row][column] == Constants.EMPTY) {
						gui.board.setTurn(gui.board.getTurn() + 1);
						gui.turnMessage.setText("Turn: " + gui.board.getTurn());
					}

					gui.board.getGameBoard()[row][column] = player;
					if (player == Constants.P1) {
						gui.placeChecker(gui.gameParameters.getPlayer1Color(), row, column);
					} else if (player == Constants.P2) {
						gui.placeChecker(gui.gameParameters.getPlayer2Color(), row, column);
					}
					System.out.println("Checker inserted!");

					System.out.println(gui.board);

					if (gui.board.checkForGameOver()) {
						gui.gameOver();
					}

					JOptionPane.showMessageDialog(
							gui,
							"Checker has been inserted into the board.",
							"Notice",
							JOptionPane.INFORMATION_MESSAGE
					);
					dispose();
				} catch (Exception ex) {
					System.err.println("ERROR : " + ex.getMessage());
				}

			}  // else if.

		}  // action performed.

	}  // inner class.


}  // class end.
