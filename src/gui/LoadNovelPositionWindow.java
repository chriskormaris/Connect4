package gui;


import connect4.Board;
import utility.Constants;
import utility.GameParameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoadNovelPositionWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6651737783332653136L;
	
	static final int numOfRows = Constants.NUM_OF_ROWS;
	static final int numOfColumns = Constants.NUM_OF_COLUMNS;

	private final JComboBox<Integer> row_drop_down;
	private final JComboBox<Integer> column_drop_down;
	private final JComboBox<String> player_drop_down;
	
	private final JButton apply;
	private final JButton cancel;

	public static int width = 400;
	public static int height = 220;
	
	
	public static int row = numOfRows-1;
	public static int column = 0;
	public static int player = Constants.P1;
	
	
	public LoadNovelPositionWindow() {
		super("Load Novel Position");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);

		EventHandler handler = new EventHandler();

		JLabel rowLabel = new JLabel("Row: ");
		JLabel columnLabel = new JLabel("Column: ");
		JLabel playerLabel = new JLabel("Player: ");
		
		add(rowLabel);
		add(columnLabel);
		add(playerLabel);
		
		row_drop_down = new JComboBox<>();
		for (int i=0; i<numOfRows; i++) {
			row_drop_down.addItem(i+1);
		}
		row_drop_down.setSelectedIndex(row);
		
		column_drop_down = new JComboBox<>();
		for (int j=0; j<numOfColumns; j++) {
			column_drop_down.addItem(j+1);
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
			}
			
			else if (ev.getSource() == apply) {
				try {
					
					row = row_drop_down.getSelectedIndex();
					column = column_drop_down.getSelectedIndex();
					player = player_drop_down.getSelectedIndex();
					
					player = (player == 0) ? Constants.P1 : Constants.P2;
					

					if (Connect4Gui.board.getGameBoard()[row][column] == Constants.EMPTY) {
						Connect4Gui.board.setTurn(Connect4Gui.board.getTurn() + 1);
						Connect4Gui.turnMessage.setText("Turn: " + Connect4Gui.board.getTurn());
					}
				    
					Connect4Gui.board.getGameBoard()[row][column] = player;
					if (player == Constants.P1)
						Connect4Gui.placeChecker(GameParameters.player1Color, row, column);
					else if (player == Constants.P2)
						Connect4Gui.placeChecker(GameParameters.player2Color, row, column);
					
					Board.printBoard(Connect4Gui.board.getGameBoard());
					
					if (Connect4Gui.board.checkForGameOver()) {
						Connect4Gui.gameOver();
					}
					
					
					JOptionPane.showMessageDialog(null, "Novel position loaded.", "Notice", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			}  // else if.
			
		}  // action performed.
		
	}  // inner class.
	
	
}  // class end.
