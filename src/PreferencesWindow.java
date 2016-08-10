import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PreferencesWindow extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> drop_down_1;
	private JComboBox<String> drop_down_2;
	private JComboBox<Integer> drop_down_3;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	private GameParameters game_params; 
	
	
	public PreferencesWindow(GameParameters gp) {
		super("Preferences");
		
		// copy passed argument object to class object
		this.game_params = gp; 
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		handler = new EventHandler();
		
		label1 = new JLabel("Game mode: ");
		label2 = new JLabel("Player 1 checker color: ");
		label3 = new JLabel("Minimax AI search depth: ");
		add(label1);
		add(label2);
		add(label3);
		label1.setBounds(10, 50, 200, 20);
		label2.setBounds(10, 100, 200, 20);
		label3.setBounds(10, 150, 200, 20);
		
		drop_down_1 = new JComboBox<String>();
		drop_down_1.addItem("Human VS AI");
		drop_down_1.addItem("Human VS Human");
		
		int selectedMode = game_params.getGameMode();
		if (selectedMode == GameParameters.HumanVSAi)
			drop_down_1.setSelectedIndex(0);
		else
			drop_down_1.setSelectedIndex(1);
		
		drop_down_2 = new JComboBox<String>();
		drop_down_2.addItem("RED");
		drop_down_2.addItem("YELLOW");
		
		String selectedColor = game_params.getPlayerColor();
		if (selectedColor.equals("RED"))
			drop_down_2.setSelectedIndex(0);
		else
			drop_down_2.setSelectedIndex(1);
		
		drop_down_3 = new JComboBox<Integer>();
		drop_down_3.addItem(1);
		drop_down_3.addItem(2);
		drop_down_3.addItem(3);
		drop_down_3.addItem(4);
		drop_down_3.addItem(5);
		drop_down_3.addItem(6);
		
		int index = game_params.getMaxDepth() - 1;
		drop_down_3.setSelectedIndex(index);
		
		add(drop_down_1);
		add(drop_down_2);
		add(drop_down_3);
		drop_down_1.setBounds(220,50,160,20);
		drop_down_2.setBounds(220,100,160,20);
		drop_down_3.setBounds(220,150,160,20);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		apply.setBounds(50 , 220 , 100 , 30);
		apply.addActionListener(handler);
		cancel.setBounds(230 , 220 , 100 , 30);
		cancel.addActionListener(handler);
	}
	
	
	public class EventHandler implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			
			if(ev.getSource() == cancel) {
				dispose();
			}
			
			else if(ev.getSource() == apply) {
				try {
					
					String game_mode_string = (String)drop_down_1.getSelectedItem();
					String player_color = (String)drop_down_2.getSelectedItem();
					int depth = (int) drop_down_3.getSelectedItem();
					
					int game_mode = (game_mode_string.equals("Human VS AI")) ? GameParameters.HumanVSAi : GameParameters.HumanVSHuman; 
					
					// change game parameters bases on settings
					game_params.setGameMode(game_mode);
					game_params.setMaxDepth(depth);
					game_params.setPlayerColor(player_color);
					
					JOptionPane.showMessageDialog(null , "Game settings have been changed.\nThe changes will be applied in the next game." , "" , JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e)
				{
					System.err.println("ERROR : "+e.getMessage());
				}
				
			}//else if.
			
		}//action performed.
		
	}//inner class.
	
}//class end.
