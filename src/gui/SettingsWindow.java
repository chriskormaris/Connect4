package gui;

import javax.swing.*;

import connect4.GameParameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6651737783332653136L;
	
	private JLabel guiStyleLabel;
	private JLabel gameModeLabel;
	private JLabel maxDepthLabel;
	private JLabel player1ColorLabel;
	private JLabel player2ColorLabel;
	
	private JComboBox<String> gui_style_drop_down;
	private JComboBox<String> game_mode_drop_down;
	private JComboBox<Integer> max_depth_drop_down;
	private JComboBox<String> player1_color_drop_down;
	private JComboBox<String> player2_color_drop_down;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	private GameParameters game_params; 
	
	
	public SettingsWindow(GameParameters gp) {
		super("Settings");
		
		// copy passed argument object to class object
		this.game_params = gp; 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(450, 375);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		guiStyleLabel = new JLabel("GUI style: ");
		gameModeLabel = new JLabel("Game mode: ");
		maxDepthLabel = new JLabel("Minimax AI search depth: ");
		player1ColorLabel = new JLabel("Player 1 checker color: ");
		player2ColorLabel = new JLabel("Player 2 checker color: ");
		
		add(guiStyleLabel);
		add(gameModeLabel);
		add(maxDepthLabel);
		add(player1ColorLabel);
		add(player2ColorLabel);
		
		guiStyleLabel.setBounds(20, 25, 175, 20);
		gameModeLabel.setBounds(20, 75, 175, 20);
		maxDepthLabel.setBounds(20, 125, 175, 20);
		player1ColorLabel.setBounds(20, 175, 175, 20);
		player2ColorLabel.setBounds(20, 225, 175, 20);
		
		gui_style_drop_down = new JComboBox<String>();
		gui_style_drop_down.addItem("System style");
		gui_style_drop_down.addItem("Cross-Platform style");
		gui_style_drop_down.addItem("Nimbus style");
		
		int selectedGuiStyle = game_params.getGameMode();
		if (selectedGuiStyle == GameParameters.SystemStyle)
			gui_style_drop_down.setSelectedIndex(GameParameters.SystemStyle - 1);
		else if (selectedGuiStyle == GameParameters.CrossPlatformStyle)
			gui_style_drop_down.setSelectedIndex(GameParameters.CrossPlatformStyle - 1);
		else if (selectedGuiStyle == GameParameters.NimbusStyle)
			gui_style_drop_down.setSelectedIndex(GameParameters.NimbusStyle - 1);
		
		game_mode_drop_down = new JComboBox<String>();
		game_mode_drop_down.addItem("Human Vs AI");
		game_mode_drop_down.addItem("Human Vs Human");
		
		int selectedMode = game_params.getGameMode();
		if (selectedMode == GameParameters.HumanVsAi)
			game_mode_drop_down.setSelectedIndex(GameParameters.HumanVsAi - 1);
		else if (selectedMode == GameParameters.HumanVsHuman)
			game_mode_drop_down.setSelectedIndex(GameParameters.HumanVsHuman - 1);
		
		max_depth_drop_down = new JComboBox<Integer>();
		max_depth_drop_down.addItem(1);
		max_depth_drop_down.addItem(2);
		max_depth_drop_down.addItem(3);
		max_depth_drop_down.addItem(4);
		max_depth_drop_down.addItem(5);
		max_depth_drop_down.addItem(6);
		
		int index = game_params.getMaxDepth() - 1;
		max_depth_drop_down.setSelectedIndex(index);
		
		player1_color_drop_down = new JComboBox<String>();
		player1_color_drop_down.addItem("RED");
		player1_color_drop_down.addItem("YELLOW");
		player1_color_drop_down.addItem("BLACK");
		player1_color_drop_down.addItem("GREEN");
		player1_color_drop_down.addItem("ORANGE");
		player1_color_drop_down.addItem("PURPLE");
		
		int selectedPlayer1Color = game_params.getPlayer1Color();
		if (selectedPlayer1Color == GameParameters.RED)
			player1_color_drop_down.setSelectedIndex(GameParameters.RED - 1);
		else if (selectedPlayer1Color == GameParameters.YELLOW)
			player1_color_drop_down.setSelectedIndex(GameParameters.YELLOW - 1);
		else if (selectedPlayer1Color == GameParameters.BLACK)
			player1_color_drop_down.setSelectedIndex(GameParameters.BLACK - 1);
		else if (selectedPlayer1Color == GameParameters.GREEN)
			player1_color_drop_down.setSelectedIndex(GameParameters.GREEN - 1);
		else if (selectedPlayer1Color == GameParameters.ORANGE)
			player1_color_drop_down.setSelectedIndex(GameParameters.ORANGE - 1);
		else if (selectedPlayer1Color == GameParameters.PURPLE)
			player1_color_drop_down.setSelectedIndex(GameParameters.PURPLE - 1);
		
		player2_color_drop_down = new JComboBox<String>();
		player2_color_drop_down.addItem("RED");
		player2_color_drop_down.addItem("YELLOW");
		player2_color_drop_down.addItem("BLACK");
		player2_color_drop_down.addItem("GREEN");
		player2_color_drop_down.addItem("ORANGE");
		player2_color_drop_down.addItem("PURPLE");
		
		int selectedPlayer2Color = game_params.getPlayer2Color();
		if (selectedPlayer2Color == GameParameters.RED)
			player2_color_drop_down.setSelectedIndex(GameParameters.RED - 1);
		else if (selectedPlayer2Color == GameParameters.YELLOW)
			player2_color_drop_down.setSelectedIndex(GameParameters.YELLOW - 1);
		else if (selectedPlayer2Color == GameParameters.BLACK)
			player2_color_drop_down.setSelectedIndex(GameParameters.BLACK - 1);
		else if (selectedPlayer2Color == GameParameters.GREEN)
			player2_color_drop_down.setSelectedIndex(GameParameters.GREEN - 1);
		else if (selectedPlayer2Color == GameParameters.ORANGE)
			player2_color_drop_down.setSelectedIndex(GameParameters.ORANGE - 1);
		else if (selectedPlayer2Color == GameParameters.PURPLE)
			player2_color_drop_down.setSelectedIndex(GameParameters.PURPLE - 1);
		
		add(gui_style_drop_down);
		add(game_mode_drop_down);
		add(max_depth_drop_down);
		add(player1_color_drop_down);
		add(player2_color_drop_down);
		gui_style_drop_down.setBounds(220, 25, 160, 20);
		game_mode_drop_down.setBounds(220, 75, 160, 20);
		max_depth_drop_down.setBounds(220, 125, 160, 20);
		player1_color_drop_down.setBounds(220, 175, 160, 20);
		player2_color_drop_down.setBounds(220, 225, 160, 20);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		apply.setBounds(80, 275, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds(220, 275, 100, 30);
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
					int depth = (int) max_depth_drop_down.getSelectedItem();
					int player1Color = player1_color_drop_down.getSelectedIndex() + 1;
					int player2Color = player2_color_drop_down.getSelectedIndex() + 1;
										
					if(player1Color == player2Color) {
						JOptionPane.showMessageDialog(null,
								"Player 1 and Player 2 cannot have the same color of checkers!",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Change game parameters based on settings.
					game_params.setGuiStyle(guiStyle);
					game_params.setGameMode(gameMode);
					game_params.setMaxDepth(depth);
					game_params.setPlayer1Color(player1Color);
					game_params.setPlayer2Color(player2Color);
					
					JOptionPane.showMessageDialog(null,
							"Game settings have been changed.\nThe changes will be applied in the next game.",
							"", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			} // else if.
			
		} // action performed.
		
	} // inner class.
	
	
} // class end.
