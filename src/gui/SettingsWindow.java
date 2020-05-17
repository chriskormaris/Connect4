package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import connect4.Constants;
import connect4.GameParameters;


public class SettingsWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6651737783332653136L;
	
	private JLabel guiStyleLabel;
	private JLabel gameModeLabel;
	private JLabel maxDepth1Label;
	private JLabel maxDepth2Label;
	private JLabel player1ColorLabel;
	private JLabel player2ColorLabel;
	
	private JComboBox<String> gui_style_drop_down;
	private JComboBox<String> game_mode_drop_down;
	private JComboBox<Integer> max_depth1_drop_down;
	private JComboBox<Integer> max_depth2_drop_down;
	private JComboBox<String> player1_color_drop_down;
	private JComboBox<String> player2_color_drop_down;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
		
	public static int width = 400;
	public static int height = 320;
	
	
	public SettingsWindow() {
		super("Settings");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		int selectedGuiStyle = GameParameters.guiStyle;
		int selectedMode = GameParameters.gameMode;
		int maxDepth1 = GameParameters.maxDepth1 - 1;
		int maxDepth2 = GameParameters.maxDepth2 - 1;
		int selectedPlayer1Color = GameParameters.player1Color;
		int selectedPlayer2Color = GameParameters.player2Color;

		guiStyleLabel = new JLabel("GUI style");
		gameModeLabel = new JLabel("Game mode");
		maxDepth1Label = new JLabel("AI 1 depth");
		maxDepth2Label = new JLabel("AI 2 depth (AiVsAi)");
		player1ColorLabel = new JLabel("Player 1 color");
		player2ColorLabel = new JLabel("Player 2 color");
		
		add(guiStyleLabel);
		add(gameModeLabel);
		add(maxDepth1Label);
		add(maxDepth2Label);
		add(player1ColorLabel);
		add(player2ColorLabel);
		
		gui_style_drop_down = new JComboBox<String>();
		gui_style_drop_down.addItem("System style");
		gui_style_drop_down.addItem("Cross-platform style");
		gui_style_drop_down.addItem("Nimbus style");
		
		if (selectedGuiStyle == Constants.SYSTEM_STYLE)
			gui_style_drop_down.setSelectedIndex(Constants.SYSTEM_STYLE - 1);
		else if (selectedGuiStyle == Constants.CROSS_PLATFORM_STYLE)
			gui_style_drop_down.setSelectedIndex(Constants.CROSS_PLATFORM_STYLE - 1);
		else if (selectedGuiStyle == Constants.NIMBUS_STYLE)
			gui_style_drop_down.setSelectedIndex(Constants.NIMBUS_STYLE - 1);
		
		game_mode_drop_down = new JComboBox<String>();
		game_mode_drop_down.addItem("Human Vs Minimax AI");
		game_mode_drop_down.addItem("Human Vs Human");
		game_mode_drop_down.addItem("Minimax AI Vs Minimax AI");
		
		if (selectedMode == Constants.HUMAN_VS_AI)
			game_mode_drop_down.setSelectedIndex(Constants.HUMAN_VS_AI - 1);
		else if (selectedMode == Constants.HUMAN_VS_HUMAN)
			game_mode_drop_down.setSelectedIndex(Constants.HUMAN_VS_HUMAN - 1);
		else if (selectedMode == Constants.AI_VS_AI)
			game_mode_drop_down.setSelectedIndex(Constants.AI_VS_AI - 1);
		
		max_depth1_drop_down = new JComboBox<Integer>();
		max_depth1_drop_down.addItem(1);
		max_depth1_drop_down.addItem(2);
		max_depth1_drop_down.addItem(3);
		max_depth1_drop_down.addItem(4);
		max_depth1_drop_down.addItem(5);
		max_depth1_drop_down.addItem(6);
		max_depth1_drop_down.addItem(7);
		
		max_depth2_drop_down = new JComboBox<Integer>();
		max_depth2_drop_down.addItem(1);
		max_depth2_drop_down.addItem(2);
		max_depth2_drop_down.addItem(3);
		max_depth2_drop_down.addItem(4);
		max_depth2_drop_down.addItem(5);
		max_depth2_drop_down.addItem(6);
		max_depth2_drop_down.addItem(7);
		
		max_depth1_drop_down.setSelectedIndex(maxDepth1);
		max_depth2_drop_down.setSelectedIndex(maxDepth2);
		
		player1_color_drop_down = new JComboBox<String>();
		player1_color_drop_down.addItem("Red");
		player1_color_drop_down.addItem("Yellow");
		player1_color_drop_down.addItem("Black");
		player1_color_drop_down.addItem("Green");
		player1_color_drop_down.addItem("Orange");
		player1_color_drop_down.addItem("Purple");
		
		if (selectedPlayer1Color == Constants.RED)
			player1_color_drop_down.setSelectedIndex(Constants.RED - 1);
		else if (selectedPlayer1Color == Constants.YELLOW)
			player1_color_drop_down.setSelectedIndex(Constants.YELLOW - 1);
		else if (selectedPlayer1Color == Constants.BLACK)
			player1_color_drop_down.setSelectedIndex(Constants.BLACK - 1);
		else if (selectedPlayer1Color == Constants.GREEN)
			player1_color_drop_down.setSelectedIndex(Constants.GREEN - 1);
		else if (selectedPlayer1Color == Constants.ORANGE)
			player1_color_drop_down.setSelectedIndex(Constants.ORANGE - 1);
		else if (selectedPlayer1Color == Constants.PURPLE)
			player1_color_drop_down.setSelectedIndex(Constants.PURPLE - 1);
		
		player2_color_drop_down = new JComboBox<String>();
		player2_color_drop_down.addItem("Red");
		player2_color_drop_down.addItem("Yellow");
		player2_color_drop_down.addItem("Black");
		player2_color_drop_down.addItem("Green");
		player2_color_drop_down.addItem("Orange");
		player2_color_drop_down.addItem("Purple");
		
		if (selectedPlayer2Color == Constants.RED)
			player2_color_drop_down.setSelectedIndex(Constants.RED - 1);
		else if (selectedPlayer2Color == Constants.YELLOW)
			player2_color_drop_down.setSelectedIndex(Constants.YELLOW - 1);
		else if (selectedPlayer2Color == Constants.BLACK)
			player2_color_drop_down.setSelectedIndex(Constants.BLACK - 1);
		else if (selectedPlayer2Color == Constants.GREEN)
			player2_color_drop_down.setSelectedIndex(Constants.GREEN - 1);
		else if (selectedPlayer2Color == Constants.ORANGE)
			player2_color_drop_down.setSelectedIndex(Constants.ORANGE - 1);
		else if (selectedPlayer2Color == Constants.PURPLE)
			player2_color_drop_down.setSelectedIndex(Constants.PURPLE - 1);
		
		add(gui_style_drop_down);
		add(game_mode_drop_down);
		add(max_depth1_drop_down);
		add(max_depth2_drop_down);
		add(player1_color_drop_down);
		add(player2_color_drop_down);

		guiStyleLabel.setBounds(25, 25, 175, 20);
		gameModeLabel.setBounds(25, 55, 175, 20);
		maxDepth1Label.setBounds(25, 85, 175, 20);
		maxDepth2Label.setBounds(25, 115, 175, 20);
		player1ColorLabel.setBounds(25, 145, 175, 20);
		player2ColorLabel.setBounds(25, 175, 175, 20);
		
		gui_style_drop_down.setBounds(195, 25, 160, 20);
		game_mode_drop_down.setBounds(195, 55, 160, 20);
		max_depth1_drop_down.setBounds(195, 85, 160, 20);
		max_depth2_drop_down.setBounds(195, 115, 160, 20);
		player1_color_drop_down.setBounds(195, 145, 160, 20);
		player2_color_drop_down.setBounds(195, 175, 160, 20);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		
		int distance = 10;
		apply.setBounds((int) (width / 2) - 110 - (int) (distance / 2), 230, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds((int) (width / 2) - 10 + (int) (distance / 2), 230, 100, 30);
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
					
					int guiStyle = gui_style_drop_down.getSelectedIndex() + 1;
					int gameMode = game_mode_drop_down.getSelectedIndex() + 1;
					int maxDepth1 = (int) max_depth1_drop_down.getSelectedItem();
					int maxDepth2 = (int) max_depth2_drop_down.getSelectedItem();
					int player1Color = player1_color_drop_down.getSelectedIndex() + 1;
					int player2Color = player2_color_drop_down.getSelectedIndex() + 1;
										
					if (player1Color == player2Color) {
						JOptionPane.showMessageDialog(null,
								"Player 1 and Player 2 cannot have the same color of checkers!",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Change game parameters based on settings.
					GameParameters.guiStyle = guiStyle;
					GameParameters.gameMode = gameMode;
					GameParameters.maxDepth1 = maxDepth1;
					GameParameters.maxDepth2 = maxDepth2;
					GameParameters.player1Color = player1Color;
					GameParameters.player2Color = player2Color;
					
					JOptionPane.showMessageDialog(null,
							"Game settings have been changed.\nThe changes will be applied in the next new game.",
							"Notice", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			}  // else if.
			
		}  // action performed.
		
	}  // inner class.
	
	
}  // class end.
