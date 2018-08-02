import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PreferencesWindow extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	
	private JComboBox<String> drop_down_1;
	private JComboBox<Integer> drop_down_2;
	private JComboBox<String> drop_down_3;
	private JComboBox<String> drop_down_4;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	private GameParameters game_params; 
	
	
	public PreferencesWindow(GameParameters gp) {
		super("Preferences");
		
		// copy passed argument object to class object
		this.game_params = gp; 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		label1 = new JLabel("Game mode: ");
		label2 = new JLabel("Minimax AI search depth: ");
		label3 = new JLabel("Player 1 checker color: ");
		label4 = new JLabel("Player 2 checker color: ");
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		label1.setBounds(20, 25, 175, 20);
		label2.setBounds(20, 75, 175, 20);
		label3.setBounds(20, 125, 175, 20);
		label4.setBounds(20, 175, 175, 20);
		
		drop_down_1 = new JComboBox<String>();
		drop_down_1.addItem("Human VS AI");
		drop_down_1.addItem("Human VS Human");
		
		int selectedMode = game_params.getGameMode();
		if (selectedMode == GameParameters.HumanVSAi)
			drop_down_1.setSelectedIndex(0);
		else if (selectedMode == GameParameters.HumanVSHuman)
			drop_down_1.setSelectedIndex(1);
		
		drop_down_2 = new JComboBox<Integer>();
		drop_down_2.addItem(1);
		drop_down_2.addItem(2);
		drop_down_2.addItem(3);
		drop_down_2.addItem(4);
		drop_down_2.addItem(5);
		drop_down_2.addItem(6);
		
		int index = game_params.getMaxDepth() - 1;
		drop_down_2.setSelectedIndex(index);
		
		
		drop_down_3 = new JComboBox<String>();
		drop_down_3.addItem("RED");
		drop_down_3.addItem("YELLOW");
		drop_down_3.addItem("BLACK");
		drop_down_3.addItem("GREEN");
		drop_down_3.addItem("ORANGE");
		drop_down_3.addItem("PURPLE");
		
		String selectedPlayer1Color = game_params.getPlayer1Color();
		if (selectedPlayer1Color.equals("RED"))
			drop_down_3.setSelectedIndex(0);
		else if (selectedPlayer1Color.equals("YELLOW"))
			drop_down_3.setSelectedIndex(1);
		else if (selectedPlayer1Color.equals("BLACK"))
			drop_down_3.setSelectedIndex(2);
		else if (selectedPlayer1Color.equals("GREEN"))
			drop_down_3.setSelectedIndex(3);
		else if (selectedPlayer1Color.equals("ORANGE"))
			drop_down_3.setSelectedIndex(4);
		else if (selectedPlayer1Color.equals("PURPLE"))
			drop_down_3.setSelectedIndex(5);
		
		drop_down_4 = new JComboBox<String>();
		drop_down_4.addItem("RED");
		drop_down_4.addItem("YELLOW");
		drop_down_4.addItem("BLACK");
		drop_down_4.addItem("GREEN");
		drop_down_4.addItem("ORANGE");
		drop_down_4.addItem("PURPLE");
		
		String selectedPlayer2Color = game_params.getPlayer2Color();
		if (selectedPlayer2Color.equals("RED"))
			drop_down_4.setSelectedIndex(0);
		else if (selectedPlayer2Color.equals("YELLOW"))
			drop_down_4.setSelectedIndex(1);
		else if (selectedPlayer2Color.equals("BLACK"))
			drop_down_4.setSelectedIndex(2);
		else if (selectedPlayer2Color.equals("GREEN"))
			drop_down_4.setSelectedIndex(3);
		else if (selectedPlayer2Color.equals("ORANGE"))
			drop_down_4.setSelectedIndex(4);
		else if (selectedPlayer2Color.equals("PURPLE"))
			drop_down_4.setSelectedIndex(5);
		
		
		add(drop_down_1);
		add(drop_down_2);
		add(drop_down_3);
		add(drop_down_4);
		drop_down_1.setBounds(220,25,160,20);
		drop_down_2.setBounds(220,75,160,20);
		drop_down_3.setBounds(220,125,160,20);
		drop_down_4.setBounds(220,175,160,20);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		apply.setBounds(80, 225, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds(220, 225, 100, 30);
		cancel.addActionListener(handler);
	}


	private class EventHandler implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			
			if(ev.getSource() == cancel) {
				dispose();
			}
			
			else if(ev.getSource() == apply) {
				try {
					
					String game_mode_string = (String)drop_down_1.getSelectedItem();
					int depth = (int) drop_down_2.getSelectedItem();
					String player1_color = (String)drop_down_3.getSelectedItem();
					String player2_color = (String)drop_down_4.getSelectedItem();
					
					int game_mode = (game_mode_string.equals("Human VS AI")) ? GameParameters.HumanVSAi : GameParameters.HumanVSHuman;
					
					if(player1_color == player2_color) {
						JOptionPane.showMessageDialog(null , "Player 1 and Player 2 cannot have the same color for their checkers!!" , "ERROR" , JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// change game parameters bases on settings
					game_params.setGameMode(game_mode);
					game_params.setMaxDepth(depth);
					game_params.setPlayer1Color(player1_color);
					game_params.setPlayer2Color(player2_color);
					
					JOptionPane.showMessageDialog(null , "Game settings have been changed.\nThe changes will be applied in the next game." , "" , JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			} //else if.
			
		} //action performed.
		
	} //inner class.
	
} //class end.
