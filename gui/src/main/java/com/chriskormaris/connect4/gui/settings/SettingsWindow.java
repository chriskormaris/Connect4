package com.chriskormaris.connect4.gui.settings;


import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;
import com.chriskormaris.connect4.gui.util.GameParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsWindow extends JFrame {

	private final JComboBox<String> guiStyleDropDown;
	private final JComboBox<String> gameModeDropDown;
	private final JComboBox<String> ai1TypeDropDown;
	private final JComboBox<String> ai2TypeDropDown;
	private final JComboBox<Integer> ai1MaxDepthDropDown;
	private final JComboBox<Integer> ai2MaxDepthDropDown;
	private final JComboBox<String> player1ColorDropDown;
	private final JComboBox<String> player2ColorDropDown;
	private final JComboBox<Integer> checkersInARowDropDown;

	private final JButton apply;
	private final JButton cancel;

	private final Component parentComponent;
	private final GameParameters newGameParameters;

	public SettingsWindow(Component parentComponent, GameParameters gameParameters, GameParameters newGameParameters) {
		super("Settings");

		this.parentComponent = parentComponent;
		this.newGameParameters = newGameParameters;

		int width = 450;
		int height = 450;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);

		EventHandler handler = new EventHandler();

		GuiStyle selectedGuiStyle = gameParameters.getGuiStyle();
		GameMode selectedMode = gameParameters.getGameMode();
		AiType selectedAi1Type = gameParameters.getAi1Type();
		AiType selectedAi2Type = gameParameters.getAi2Type();
		int ai1MaxDepth = gameParameters.getAi1MaxDepth() - 1;
		int ai2MaxDepth = gameParameters.getAi2MaxDepth() - 1;
		Color selectedPlayer1Color = gameParameters.getPlayer1Color();
		Color selectedPlayer2Color = gameParameters.getPlayer2Color();
		int checkersInARow = gameParameters.getCheckersInARow();

		JLabel guiStyleLabel = new JLabel("GUI style");
		JLabel gameModeLabel = new JLabel("Game mode");
		JLabel ai1TypeLabel = new JLabel("AI 1 type");
		JLabel ai2TypeLabel = new JLabel("AI 2 type (AiVsAi)");
		JLabel ai1MaxDepthLabel = new JLabel("AI 1 depth");
		JLabel ai2MaxDepthLabel = new JLabel("AI 2 depth (AiVsAi)");
		JLabel player1ColorLabel = new JLabel("Player 1 color");
		JLabel player2ColorLabel = new JLabel("Player 2 color");
		JLabel checkersInARowLabel = new JLabel("Checkers in a Row");

		add(guiStyleLabel);
		add(gameModeLabel);
		add(ai1TypeLabel);
		add(ai2TypeLabel);
		add(ai1MaxDepthLabel);
		add(ai2MaxDepthLabel);
		add(player1ColorLabel);
		add(player2ColorLabel);
		add(checkersInARowLabel);

		guiStyleDropDown = new JComboBox<>();
		guiStyleDropDown.addItem("Cross-platform style");
		guiStyleDropDown.addItem("System style");
		guiStyleDropDown.addItem("Nimbus style");

		if (selectedGuiStyle == GuiStyle.CROSS_PLATFORM_STYLE) {
			guiStyleDropDown.setSelectedIndex(0);
		} else if (selectedGuiStyle == GuiStyle.SYSTEM_STYLE) {
			guiStyleDropDown.setSelectedIndex(1);
		} else if (selectedGuiStyle == GuiStyle.NIMBUS_STYLE) {
			guiStyleDropDown.setSelectedIndex(2);
		}

		gameModeDropDown = new JComboBox<>();
		gameModeDropDown.addItem("Human Vs AI");
		gameModeDropDown.addItem("Human Vs Human");
		gameModeDropDown.addItem("AI Vs AI");

		if (selectedMode == GameMode.HUMAN_VS_AI) {
			gameModeDropDown.setSelectedIndex(0);
		} else if (selectedMode == GameMode.HUMAN_VS_HUMAN) {
			gameModeDropDown.setSelectedIndex(1);
		} else if (selectedMode == GameMode.AI_VS_AI) {
			gameModeDropDown.setSelectedIndex(2);
		}

		ai1TypeDropDown = new JComboBox<>();
		ai1TypeDropDown.addItem("Minimax AI");
		ai1TypeDropDown.addItem("Random AI");

		if (selectedAi1Type == AiType.MINIMAX_AI) {
			ai1TypeDropDown.setSelectedIndex(0);
		} else if (selectedAi1Type == AiType.RANDOM_AI) {
			ai1TypeDropDown.setSelectedIndex(1);
		}

		ai2TypeDropDown = new JComboBox<>();
		ai2TypeDropDown.addItem("Minimax AI");
		ai2TypeDropDown.addItem("Random AI");

		if (selectedAi2Type == AiType.MINIMAX_AI) {
			ai2TypeDropDown.setSelectedIndex(0);
		} else if (selectedAi2Type == AiType.RANDOM_AI) {
			ai2TypeDropDown.setSelectedIndex(1);
		}

		ai1MaxDepthDropDown = new JComboBox<>();
		ai1MaxDepthDropDown.addItem(1);
		ai1MaxDepthDropDown.addItem(2);
		ai1MaxDepthDropDown.addItem(3);
		ai1MaxDepthDropDown.addItem(4);
		ai1MaxDepthDropDown.addItem(5);
		ai1MaxDepthDropDown.addItem(6);
		ai1MaxDepthDropDown.addItem(7);

		ai2MaxDepthDropDown = new JComboBox<>();
		ai2MaxDepthDropDown.addItem(1);
		ai2MaxDepthDropDown.addItem(2);
		ai2MaxDepthDropDown.addItem(3);
		ai2MaxDepthDropDown.addItem(4);
		ai2MaxDepthDropDown.addItem(5);
		ai2MaxDepthDropDown.addItem(6);
		ai2MaxDepthDropDown.addItem(7);

		ai1MaxDepthDropDown.setSelectedIndex(ai1MaxDepth);
		ai2MaxDepthDropDown.setSelectedIndex(ai2MaxDepth);

		player1ColorDropDown = new JComboBox<>();
		player1ColorDropDown.addItem("Red");
		player1ColorDropDown.addItem("Yellow");
		player1ColorDropDown.addItem("Black");
		player1ColorDropDown.addItem("Green");
		player1ColorDropDown.addItem("Orange");
		player1ColorDropDown.addItem("Purple");

		if (selectedPlayer1Color == Color.RED) {
			player1ColorDropDown.setSelectedIndex(0);
		} else if (selectedPlayer1Color == Color.YELLOW) {
			player1ColorDropDown.setSelectedIndex(1);
		} else if (selectedPlayer1Color == Color.BLACK) {
			player1ColorDropDown.setSelectedIndex(2);
		} else if (selectedPlayer1Color == Color.GREEN) {
			player1ColorDropDown.setSelectedIndex(3);
		} else if (selectedPlayer1Color == Color.ORANGE) {
			player1ColorDropDown.setSelectedIndex(4);
		} else if (selectedPlayer1Color == Color.PURPLE) {
			player1ColorDropDown.setSelectedIndex(5);
		}

		player2ColorDropDown = new JComboBox<>();
		player2ColorDropDown.addItem("Red");
		player2ColorDropDown.addItem("Yellow");
		player2ColorDropDown.addItem("Black");
		player2ColorDropDown.addItem("Green");
		player2ColorDropDown.addItem("Orange");
		player2ColorDropDown.addItem("Purple");

		if (selectedPlayer2Color == Color.RED) {
			player2ColorDropDown.setSelectedIndex(0);
		} else if (selectedPlayer2Color == Color.YELLOW) {
			player2ColorDropDown.setSelectedIndex(1);
		} else if (selectedPlayer2Color == Color.BLACK) {
			player2ColorDropDown.setSelectedIndex(2);
		} else if (selectedPlayer2Color == Color.GREEN) {
			player2ColorDropDown.setSelectedIndex(3);
		} else if (selectedPlayer2Color == Color.ORANGE) {
			player2ColorDropDown.setSelectedIndex(4);
		} else if (selectedPlayer2Color == Color.PURPLE) {
			player2ColorDropDown.setSelectedIndex(5);
		}

		checkersInARowDropDown = new JComboBox<>();
		checkersInARowDropDown.addItem(4);
		checkersInARowDropDown.addItem(5);

		checkersInARowDropDown.setSelectedIndex(checkersInARow - 4);

		add(guiStyleDropDown);
		add(gameModeDropDown);
		add(ai1TypeDropDown);
		add(ai2TypeDropDown);
		add(ai1MaxDepthDropDown);
		add(ai2MaxDepthDropDown);
		add(player1ColorDropDown);
		add(player2ColorDropDown);
		add(checkersInARowDropDown);

		int x = 25;
		int y = 25;
		int distance = 35;
		int w = 200;
		int h = 25;
		guiStyleLabel.setBounds(x, y, w, h);
		gameModeLabel.setBounds(x, y = y + distance, w, h);
		ai1TypeLabel.setBounds(x, y = y + distance, w, h);
		ai2TypeLabel.setBounds(x, y = y + distance, w, h);
		ai1MaxDepthLabel.setBounds(x, y = y + distance, w, h);
		ai2MaxDepthLabel.setBounds(x, y = y + distance, w, h);
		player1ColorLabel.setBounds(x, y = y + distance, w, h);
		player2ColorLabel.setBounds(x, y = y + distance, w, h);
		checkersInARowLabel.setBounds(x, y + distance, w, h);

		x = 195;
		y = 25;
		guiStyleDropDown.setBounds(x, y, w, h);
		gameModeDropDown.setBounds(x, y = y + distance, w, h);
		ai1TypeDropDown.setBounds(x, y = y + distance, w, h);
		ai2TypeDropDown.setBounds(x, y = y + distance, w, h);
		ai1MaxDepthDropDown.setBounds(x, y = y + distance, w, h);
		ai2MaxDepthDropDown.setBounds(x, y = y + distance, w, h);
		player1ColorDropDown.setBounds(x, y = y + distance, w, h);
		player2ColorDropDown.setBounds(x, y = y + distance, w, h);
		checkersInARowDropDown.setBounds(x, y + distance, w, h);

		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);

		distance = 10;
		y = 355;
		w = 100;
		h = 30;
		apply.setBounds((width / 2) - 110 - (distance / 2), y, w, h);
		apply.addActionListener(handler);
		cancel.setBounds((width / 2) - 10 + (distance / 2), y, w, h);
		cancel.addActionListener(handler);
	}


	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == cancel) {
				dispose();
			} else if (ev.getSource() == apply) {
				try {
					GuiStyle guiStyle = GuiStyle.valueOf(guiStyleDropDown.getSelectedItem().toString().toUpperCase()
							.replace("-", "_").replace(" ", "_"));
					GameMode gameMode = GameMode.valueOf(gameModeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_"));
					AiType ai1Type = AiType.valueOf(ai1TypeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_"));
					AiType ai2Type = AiType.valueOf(ai2TypeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_"));
					int ai1MaxDepth = (int) ai1MaxDepthDropDown.getSelectedItem();
					int ai2MaxDepth = (int) ai2MaxDepthDropDown.getSelectedItem();
					Color player1Color = Color.valueOf(
							player1ColorDropDown.getSelectedItem().toString().toUpperCase()
					);
					Color player2Color = Color.valueOf(
							player2ColorDropDown.getSelectedItem().toString().toUpperCase()
					);
					int checkersInARow = (int) checkersInARowDropDown.getSelectedItem();
					int numOfRows = (checkersInARow == 4) ? 6 : 7;
					int numOfColumns = (checkersInARow == 4) ? 7 : 8;

					if (player1Color == player2Color) {
						JOptionPane.showMessageDialog(
								parentComponent,
								"Player 1 and Player 2 cannot have the same color of checkers!",
								"ERROR",
								JOptionPane.ERROR_MESSAGE
						);
						return;
					}

					// Change game parameters based on settings.
					newGameParameters.setGuiStyle(guiStyle);
					newGameParameters.setGameMode(gameMode);
					newGameParameters.setAi1Type(ai1Type);
					newGameParameters.setAi2Type(ai2Type);
					newGameParameters.setAi1MaxDepth(ai1MaxDepth);
					newGameParameters.setAi2MaxDepth(ai2MaxDepth);
					newGameParameters.setPlayer1Color(player1Color);
					newGameParameters.setPlayer2Color(player2Color);
					newGameParameters.setNumOfRows(numOfRows);
					newGameParameters.setNumOfColumns(numOfColumns);
					newGameParameters.setCheckersInARow(checkersInARow);

					JOptionPane.showMessageDialog(
							parentComponent,
							"Game settings have been changed.\n" +
									"The changes will be applied in the next new game.",
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
