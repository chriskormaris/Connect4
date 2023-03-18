package com.chriskormaris.connect4.gui;


import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.util.GameParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InsertCheckerWindow extends JFrame {

	public int row;
	public int column;

	public int player;

	private final JComboBox<Integer> row_drop_down;
	private final JComboBox<Integer> column_drop_down;
	private final JComboBox<String> player_drop_down;
	private final JButton apply;
	private final JButton cancel;

	private final Component parentComponent;
	private final Board board;
	private final GameParameters gameParameters;
	private final GUI gui;

	public InsertCheckerWindow(Component parentComponent, Board board, GameParameters gameParameters, GUI gui) {
		super("Insert Checker");

		this.parentComponent = parentComponent;
		this.board = board;
		this.gameParameters = gameParameters;
		this.gui = gui;

		int numOfRows = gameParameters.getNumOfRows();
		int numOfColumns = gameParameters.getNumOfColumns();

		row = numOfRows - 1;
		column = 0;
		player = Constants.P1;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		int width = 400;
		int height = 220;
		setSize(width, height);
		setLocationRelativeTo(parentComponent);
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

					if (board.getGameBoard()[row][column] == Constants.EMPTY) {
						board.setTurn(board.getTurn() + 1);
						gui.turnMessage.setText("Turn: " + board.getTurn());
					}

					board.getGameBoard()[row][column] = player;
					if (player == Constants.P1) {
						gui.placeChecker(gameParameters.getPlayer1Color(), row, column);
					} else if (player == Constants.P2) {
						gui.placeChecker(gameParameters.getPlayer2Color(), row, column);
					}
					System.out.println("Checker inserted!");

					System.out.println(board);

					if (board.checkForGameOver()) {
						gui.gameOver();
					}

					JOptionPane.showMessageDialog(
							parentComponent,
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
