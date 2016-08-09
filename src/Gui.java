import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Gui {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	//Menu bars and items.
	static JMenuBar menuBar;
	static JMenu menu1;
	static JMenuItem item11;
	static JMenuItem item12;
	static JMenuItem item13;
	static JMenu menu2;
	static JMenuItem item21;
	static JMenuItem item22;

//	private static GamePlayer ai = new GamePlayer();
	static GameParameters game_params = new GameParameters();
	private static GamePlayer ai = new GamePlayer(game_params.getMaxDepth(), Board.X);
	
//	private static int humanPlayerLetter = Board.X; // (X=1)
//	private static int aiPlayerLetter = Board.O; // (O=-1)
	
	public Gui () {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) { }
		createNewGame();
		AddMenus();
	}
	
	//Adds the menu bars and items to the window
	private static void AddMenus() {
		//adding the menu bar.
		menuBar = new JMenuBar();
		
		menu1 = new JMenu("File");
		item11 = new JMenuItem("New Game");
		item12 = new JMenuItem("Preferences");
		item13 = new JMenuItem("Exit");
		menu1.add(item11);
		menu1.add(item12);
		menu1.add(item13);
		
		menu2 = new JMenu("Help");
		item21 = new JMenuItem("How to Play");
		item22 = new JMenuItem("About");
		menu2.add(item21);
		menu2.add(item22);

		
		item11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewGame();
				AddMenus();
			}
		});
		
		item12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreferencesWindow prefs = new PreferencesWindow(game_params);
				prefs.setVisible(true);
			}
		});
		
		item13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		item21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null , "Click on the buttons or press 1-7 on your keyboard to insert a new checker.\nTo win you must place 4 checkers in an row, horizontally, vertically or diagonally." , "How to Play" , JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		item22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null , "© Created by: Chris Kormaris" , "About" , JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		frameMainWindow.setJMenuBar(menuBar);
		frameMainWindow.setVisible(true);
		
	}
		
	// o kurios pinakas tou Connect-4
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 520));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.gif"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

		return layeredGameBoard;
	}
	
	// kaleitai otan xekinaei to paixnidi apo thn arxh
	public static void createNewGame() {
		board = new Board();
		
		// set the new difficulty setting
		ai.setMaxDepth(game_params.getMaxDepth());
		
		// debugging
//		System.out.println("difficulty:" + gp.getDifficulty());
//		System.out.println("color:" + gp.getColor());
             
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		centerWindow(frameMainWindow, 570, 490); // to kurio parathuro tha emfanizetai sto kentro
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		
		KeyListener keylistener = new MyKeyListener(board);
		frameMainWindow.addKeyListener(keylistener);
		frameMainWindow.setFocusable(true);

		// show window
		frameMainWindow.pack();
		//frameMainWindow.setVisible(true);

		if (board.getLastLetterPlayed() == Board.X) {
			Move aiMove = ai.MiniMax(board);
			board.makeMove(aiMove.getCol(), Board.O);
			game();
		}

	}
	
	// kentrarei to parathuro sthn othonh
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
	    frame.setLocation(x, y);
	}
	
	// topothetei kokkino checker ston pinaka
	public static void paintItRed(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon redIcon = new ImageIcon(ResourceLoader.load("images/Human.gif"));
		JLabel redIconLabel = new JLabel(redIcon);
		redIconLabel.setBounds(27 + xOffset, 27 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
		layeredGameBoard.add(redIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// topothetei kitrino checker ston pinaka
	public static void paintItYellow(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon yellowIcon = new ImageIcon(ResourceLoader.load("images/Computer_AI.gif"));
		JLabel yellowIconLabel = new JLabel(yellowIcon);
		yellowIconLabel.setBounds(27 + xOffset, 27 + yOffset, yellowIcon.getIconWidth(),yellowIcon.getIconHeight());
		layeredGameBoard.add(yellowIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// kaleitai kathe fora pou eisagetai mia kinhsh ston pinaka
	public static void game() {
	
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();

		int lastPlayerLetter = board.getLastLetterPlayed();
		
		if (game_params.getPlayerColor() == "RED") {
			if (lastPlayerLetter == Board.X) {
				// topothetei kokkino checker sto [row][col] tou GUI
				paintItRed(row, col);
			} else if (lastPlayerLetter == Board.O) {
				// topothetei kitrino checker sto [row][col] tou GUI
				paintItYellow(row, col);
			}
			if (board.isTerminal()) {
				gameOver();
			}
		} else if (game_params.getPlayerColor() == "YELLOW") {
			if (lastPlayerLetter == Board.X) {
				// topothetei kitrino checker sto [row][col] tou GUI
				paintItYellow(row, col);
			} else if (lastPlayerLetter == Board.O) {
				// topothetei kokkino checker sto [row][col] tou GUI
				paintItRed(row, col);
			}
			if (board.isTerminal()) {
				gameOver();
			}
		}
		

	}
	
	// kaleitai meta thn kinhsh tou human player, wste na paixei o computer AI
	public static void aiMove(){

		if (!board.isTerminal()) {
			if (board.getLastLetterPlayed() == Board.X) {
				Move aiMove = ai.MiniMax(board);
				board.makeMove(aiMove.getCol(), Board.O);
				game();
			}
		}

	}
	
	/**
	 * Returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * Kalei ton actionListener otan ginetai click me to pontiki panw se button.
	 */
	public static Component createContentComponents() {
		// dhmiourgia panel gia na organwsoume ta buttons tou pinaka
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 6, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 24, 2, 24));
		
		JButton col1_button = new JButton("1");
		col1_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(0, Board.X);
//				} else {
//					board.makeMove(0, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col2_button = new JButton("2");
		col2_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(1, Board.X);
				}
//				else {
//					board.makeMove(1, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col3_button = new JButton("3");
		col3_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(2, Board.X);
				}
//				else {
//					board.makeMove(2, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col4_button = new JButton("4");
		col4_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(3, Board.X);
			}
//			else {
//					board.makeMove(3, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col5_button = new JButton("5");
		col5_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(4, Board.X);
			}
//			else {
//					board.makeMove(4, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col6_button = new JButton("6");
		col6_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(5, Board.X);
				}
//				else {
//					board.makeMove(5, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col7_button = new JButton("7");
		col7_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getLastLetterPlayed() == Board.O) {
					board.makeMove(6, Board.X);
			}
//			else {
//					board.makeMove(6, Board.O);
//				}
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		panelBoardNumbers.add(col1_button);
		panelBoardNumbers.add(col2_button);
		panelBoardNumbers.add(col3_button);
		panelBoardNumbers.add(col4_button);
		panelBoardNumbers.add(col5_button);
		panelBoardNumbers.add(col6_button);
		panelBoardNumbers.add(col7_button);

		// dhmiourgia tou kuriou pinaka tou Connect-4, mazi me ta buttons 
		layeredGameBoard = createLayeredBoard();

		// dhmiourgia panel gia na krathsoume ola ta stoixeia tou pinaka
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// pros8hkh antikeimenwn sto panelMain 
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	public static void gameOver() {
		        		        
//		panelBoardNumbers.setVisible(false);
		frameGameOver = new JFrame("Game over!");
		frameGameOver.setBounds(620, 400, 350, 128);
		centerWindow(frameGameOver, 0, 0);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		
//		ImageIcon winIcon = new ImageIcon(ResourceLoader.load("images/win.gif"));
//		JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		board.checkWinState();
		if (board.getWinner() == Board.X) {
			winLabel = new JLabel("You win! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Board.O) {
			winLabel = new JLabel("Computer AI wins! Start a new game?");
			winPanel.add(winLabel);
		} else {
			winLabel = new JLabel("It's a draw! Start a new game?");
			winPanel.add(winLabel);
		}
		winLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		winPanel.add(winLabel, BorderLayout.NORTH);
		
		JButton yesButton = new JButton("Yes");
		yesButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				createNewGame();
				AddMenus();
			}
		});
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				System.exit(0);
			}
		});
		
		JPanel subPanel = new JPanel();
		subPanel.add(yesButton);
		subPanel.add(quitButton);
		
		winPanel.add(subPanel, BorderLayout.CENTER);
		frameGameOver.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameGameOver.setResizable(false);
		frameGameOver.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Gui Connect4 = new Gui();
	}
	
}
