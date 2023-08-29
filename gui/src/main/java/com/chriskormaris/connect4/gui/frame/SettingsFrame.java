package com.chriskormaris.connect4.gui.frame;


import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;
import com.chriskormaris.connect4.gui.util.GameParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class SettingsFrame extends JFrame {

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

	public SettingsFrame(Component parentComponent, GameParameters newGameParameters) {
		super("Settings");

		this.parentComponent = parentComponent;
		this.newGameParameters = newGameParameters;

		int width = 475;
		int height = 450;

		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setLayout(null);
		super.setSize(width, height);
		super.setLocationRelativeTo(parentComponent);
		super.setResizable(false);

		GuiStyle selectedGuiStyle = newGameParameters.getGuiStyle();
		GameMode selectedMode = newGameParameters.getGameMode();
		AiType selectedAi1Type = newGameParameters.getAi1Type();
		AiType selectedAi2Type = newGameParameters.getAi2Type();
		int ai1MaxDepth = newGameParameters.getAi1MaxDepth() - 1;
		int ai2MaxDepth = newGameParameters.getAi2MaxDepth() - 1;
		Color selectedPlayer1Color = newGameParameters.getPlayer1Color();
		Color selectedPlayer2Color = newGameParameters.getPlayer2Color();
		int checkersInARow = newGameParameters.getCheckersInARow();

		List<JLabel> labels = new ArrayList<>();
		List<JComponent> components = new ArrayList<>();

		JLabel guiStyleLabel = new JLabel("GUI style");
		JLabel gameModeLabel = new JLabel("Game mode");
		JLabel ai1TypeLabel = new JLabel("AI 1 type");
		JLabel ai2TypeLabel = new JLabel("AI 2 type (AI vs AI)");
		JLabel ai1MaxDepthLabel = new JLabel("AI 1 depth");
		JLabel ai2MaxDepthLabel = new JLabel("AI 2 depth (AI vs AI)");
		JLabel player1ColorLabel = new JLabel("Player 1 color");
		JLabel player2ColorLabel = new JLabel("Player 2 color");
		JLabel checkersInARowLabel = new JLabel("Checkers in a Row");

		labels.add(guiStyleLabel);
		labels.add(gameModeLabel);
		labels.add(ai1TypeLabel);
		labels.add(ai2TypeLabel);
		labels.add(ai1MaxDepthLabel);
		labels.add(ai2MaxDepthLabel);
		labels.add(player1ColorLabel);
		labels.add(player2ColorLabel);
		labels.add(checkersInARowLabel);

		guiStyleDropDown = new JComboBox<>();
		guiStyleDropDown.addItem("Cross-platform");
		guiStyleDropDown.addItem("System");
		guiStyleDropDown.addItem("Nimbus");

		if (selectedGuiStyle == GuiStyle.CROSS_PLATFORM) {
			guiStyleDropDown.setSelectedIndex(0);
		} else if (selectedGuiStyle == GuiStyle.SYSTEM) {
			guiStyleDropDown.setSelectedIndex(1);
		} else if (selectedGuiStyle == GuiStyle.NIMBUS) {
			guiStyleDropDown.setSelectedIndex(2);
		}

		gameModeDropDown = new JComboBox<>();
		gameModeDropDown.addItem("Human vs AI");
		gameModeDropDown.addItem("Human vs Human");
		gameModeDropDown.addItem("AI vs AI");

		if (selectedMode == GameMode.HUMAN_VS_AI) {
			gameModeDropDown.setSelectedIndex(0);
		} else if (selectedMode == GameMode.HUMAN_VS_HUMAN) {
			gameModeDropDown.setSelectedIndex(1);
		} else if (selectedMode == GameMode.AI_VS_AI) {
			gameModeDropDown.setSelectedIndex(2);
		}

		ai1TypeDropDown = new JComboBox<>();
		ai1TypeDropDown.addItem("Minimax AI");
		ai1TypeDropDown.addItem("Minimax Alpha-Beta Pruning AI");
		ai1TypeDropDown.addItem("Random AI");

		if (selectedAi1Type == AiType.MINIMAX_AI) {
			ai1TypeDropDown.setSelectedIndex(0);
		} else if (selectedAi1Type == AiType.MINIMAX_ALPHA_BETA_PRUNING_AI) {
			ai1TypeDropDown.setSelectedIndex(1);
		} else if (selectedAi1Type == AiType.RANDOM_AI) {
			ai1TypeDropDown.setSelectedIndex(2);
		}

		ai2TypeDropDown = new JComboBox<>();
		ai2TypeDropDown.addItem("Minimax AI");
		ai2TypeDropDown.addItem("Minimax Alpha-Beta Pruning AI");
		ai2TypeDropDown.addItem("Random AI");

		if (selectedAi2Type == AiType.MINIMAX_AI) {
			ai2TypeDropDown.setSelectedIndex(0);
		} else if (selectedAi2Type == AiType.MINIMAX_ALPHA_BETA_PRUNING_AI) {
			ai2TypeDropDown.setSelectedIndex(1);
		} else if (selectedAi2Type == AiType.RANDOM_AI) {
			ai2TypeDropDown.setSelectedIndex(2);
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

		components.add(guiStyleDropDown);
		components.add(gameModeDropDown);
		components.add(ai1TypeDropDown);
		components.add(ai2TypeDropDown);
		components.add(ai1MaxDepthDropDown);
		components.add(ai2MaxDepthDropDown);
		components.add(player1ColorDropDown);
		components.add(player2ColorDropDown);
		components.add(checkersInARowDropDown);

		int x = 25;
		int y = 25;
		int distance = 35;
		int w = 200;
		int h = 25;
		for (JLabel label : labels) {
			label.setBounds(x, y, w, h);
			y = y + distance;
			super.add(label);
		}

		x = 200;
		y = 25;
		w = 220;
		for (JComponent component : components) {
			component.setBounds(x, y, w, h);
			y = y + distance;
			super.add(component);
		}

		apply = new JButton("Apply");
		cancel = new JButton("Cancel");

		distance = 10;
		y = 355;
		w = 100;
		h = 30;
		EventHandler handler = new EventHandler();
		apply.setBounds((width / 2) - 110 - (distance / 2), y, w, h);
		apply.addActionListener(handler);
		cancel.setBounds((width / 2) - 10 + (distance / 2), y, w, h);
		cancel.addActionListener(handler);

		super.add(apply);
		super.add(cancel);
	}


	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == cancel) {
				dispose();
			} else if (event.getSource() == apply) {
				try {
					GuiStyle guiStyle = GuiStyle.valueOf(guiStyleDropDown.getSelectedItem().toString().toUpperCase()
							.replace("-", "_"));
					GameMode gameMode = GameMode.valueOf(gameModeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_"));
					AiType ai1Type = AiType.valueOf(ai1TypeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_").replace("-", "_"));
					AiType ai2Type = AiType.valueOf(ai2TypeDropDown.getSelectedItem().toString().toUpperCase()
							.replace(" ", "_").replace("-", "_"));
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
