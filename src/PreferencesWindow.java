import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PreferencesWindow extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> drop_down_1;
	private JComboBox<Integer> drop_down_2;
	
	private JLabel label1;
	private JLabel label2;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	private GameParameters game_params; 
	
	
	public PreferencesWindow(GameParameters gp) {
		super("PREFERENCES");
		
		// copy passed argument object to class object
		this.game_params = gp; 
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		handler = new EventHandler();
		
		label1 = new JLabel("Player's checker color: ");
		label2 = new JLabel("Minimax search depth: ");
		add(label1);
		add(label2);
		label1.setBounds(10, 50, 200, 20);
		label2.setBounds(10, 100, 200, 20);
		
		drop_down_1 = new JComboBox<String>();
		drop_down_1.addItem("RED");
		drop_down_1.addItem("YELLOW");
		
		String selectedColor = game_params.getPlayerColor();
		if (selectedColor.equals("RED"))
			drop_down_1.setSelectedIndex(0);
		else
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
		
		add(drop_down_1);
		add(drop_down_2);
		drop_down_1.setBounds(230,50,150,20);
		drop_down_2.setBounds(230,100,150,20);
		
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
					
					String player_color     = (String)drop_down_1.getSelectedItem();
					int depth            = (int) drop_down_2.getSelectedItem();
					
					
					// change game parameters bases on settings
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
