import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
//import javax.swing.UIManager.LookAndFeelInfo;


public class Gui {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelMain;
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	static final int DEFAULT_WIDTH = 570;
	static final int DEFAULT_HEIGHT = 515;
	
	static boolean firstGame = true;

	static JButton col1_button = new JButton("1");
	static JButton col2_button = new JButton("2");
	static JButton col3_button = new JButton("3");
	static JButton col4_button = new JButton("4");
	static JButton col5_button = new JButton("5");
	static JButton col6_button = new JButton("6");
	static JButton col7_button = new JButton("7");

	static GameParameters game_params = new GameParameters();
	static int gameMode = game_params.getGameMode();
	static int maxDepth = game_params.getMaxDepth();
	static String player1Color = game_params.getPlayer1Color();
	static String player2Color = game_params.getPlayer2Color();
	
//	static GamePlayer ai = new GamePlayer();
	static MinimaxAI ai = new MinimaxAI(maxDepth, Board.X);
	
	//	Player 1 letter -> X. He plays First
	//	Player 2 letter -> O.
	
	public static JLabel checkerLabel = null;
	
	// for Undo operation
	public static int humanPlayerRowUndo;
	public static int humanPlayerColUndo;
	public static int humanPlayerLetter;
	public static JLabel humanPlayerCheckerLabelUndo;

	public Gui() {
		
		try {
			
			// Option 1
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Option 2
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			// Option 3
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		createNewGame();
		AddMenus();
		
	}
	
	// Adds the menu bars and items to the window
	private static void AddMenus() {
		
		// Menu bars and items
		JMenuBar menuBar;
		JMenu menu1;
		JMenuItem item11;
		JMenuItem item12;
		JMenuItem item13;
		JMenuItem item14;
		JMenu menu2;
		JMenuItem item21;
		JMenuItem item22;
		
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		menu1 = new JMenu("File");
		item11 = new JMenuItem("New Game");
		item12 = new JMenuItem("Undo    Ctrl+Z");
		item13 = new JMenuItem("Preferences");
		item14 = new JMenuItem("Exit");
		menu1.add(item11);
		menu1.add(item12);
		menu1.add(item13);
		menu1.add(item14);
		
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
				undo();
			}
		});
		
		item13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreferencesWindow prefs = new PreferencesWindow(game_params);
				prefs.setVisible(true);
			}
		});
		
		item14.addActionListener(new ActionListener() {
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
		layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.gif"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

		return layeredGameBoard;
	}
	
	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
			String button = KeyEvent.getKeyText(e.getKeyCode());
			
			board.setOverflowOccured(false);
			if (button.equals("1")) {
				makeMove(0);
			} else if (button.equals("2")) {
				makeMove(1);
			} else if (button.equals("3")) {
				makeMove(2);
			} else if (button.equals("4")) {
				makeMove(3);
			} else if (button.equals("5")) {
				makeMove(4);
			} else if (button.equals("6")) {
				makeMove(5);
			} else if (button.equals("7")) {
				makeMove(6);
			}
			
			else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                undo();
            }
			
			if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
					|| button.equals("5") || button.equals("6") || button.equals("7")) {
				if (!board.isOverflowOccured()) {
					game();
					saveUndoMove();
					if (gameMode == GameParameters.HumanVSAi) aiMove();
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		}
	};
	
	public static void undo() {
		// Undo implementation for Human VS Human mode
		if (game_params.getGameMode() == GameParameters.HumanVSHuman) {
			try {
				board.setGameOver(false);
				enableButtons();
				if (frameMainWindow.getKeyListeners().length == 0) {
					frameMainWindow.addKeyListener(gameKeyListener);
				}
				board.undoMove(board.getLastMove().getRow(), board.getLastMove().getCol(), humanPlayerLetter);
				layeredGameBoard.remove(checkerLabel);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
		
		// Undo implementation for Human VS AI mode
		else if (game_params.getGameMode() == GameParameters.HumanVSAi) {
			try {
				board.setGameOver(false);
				enableButtons();
				if (frameMainWindow.getKeyListeners().length == 0) {
					frameMainWindow.addKeyListener(gameKeyListener);
				}
				board.undoMove(board.getLastMove().getRow(), board.getLastMove().getCol(), humanPlayerLetter);
				layeredGameBoard.remove(checkerLabel);
				board.undoMove(humanPlayerRowUndo, humanPlayerColUndo, humanPlayerLetter);
				layeredGameBoard.remove(humanPlayerCheckerLabelUndo);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
	}
	
	// kaleitai otan xekinaei to paixnidi apo thn arxh
	public static void createNewGame() {
		board = new Board();
		
		// get the new parameters based on previously changed settings
		gameMode = game_params.getGameMode();
		maxDepth = game_params.getMaxDepth();
		player1Color = game_params.getPlayer1Color();
		player2Color = game_params.getPlayer2Color();
		
		// set the new max depth setting
		ai.setMaxDepth(maxDepth);
		
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		centerWindow(frameMainWindow, DEFAULT_WIDTH, DEFAULT_HEIGHT); // to kurio parathuro tha emfanizetai sto kentro
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);
		
		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		frameMainWindow.addKeyListener(gameKeyListener);
		frameMainWindow.setFocusable(true);
		
		// show window
		frameMainWindow.pack();
		//frameMainWindow.setVisible(true);

		if (gameMode == GameParameters.HumanVSAi)  {
			if (board.getLastLetterPlayed() == Board.X) {
				Move aiMove = ai.MiniMax(board);
				board.makeMove(aiMove.getCol(), Board.O);
				game();
			}
		}

	}
		
	// kentrarei to parathuro sthn othonh
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
	    frame.setLocation(x, y);
	}
	
	// briskei poios paiktis exei seira kai kanei eisagwgi sto Board
	public static void makeMove(int col) {
		board.setOverflowOccured(false);
		
		int previousRow = board.getLastMove().getRow();
		int previousCol = board.getLastMove().getCol();
		int previousLetter = board.getLastLetterPlayed();
		
		if (board.getLastLetterPlayed() == Board.O) {
			board.makeMove(col, Board.X);
		} else {
			board.makeMove(col, Board.O);
		}
		
		if (board.isOverflowOccured()) {
			board.getLastMove().setRow(previousRow);
			board.getLastMove().setCol(previousCol);
			board.setLastLetterPlayed(previousLetter);
		}

	}
	
	
	// topothetei checker ston pinaka
	public static void placeChecker(String color, int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + color + ".gif"));
		checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	public static void saveUndoMove() {
		humanPlayerRowUndo = board.getLastMove().getRow();
		humanPlayerColUndo = board.getLastMove().getCol();
		humanPlayerLetter = board.getLastLetterPlayed();
		humanPlayerCheckerLabelUndo = checkerLabel;
	}
	
	// gets called after makeMove(int col) is called
	public static void game() {
			
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();
		int currentPlayer = board.getLastLetterPlayed();
		
		if (currentPlayer == Board.X) {
			// topothetei checker sto [row][col] tou GUI
			placeChecker(player1Color, row, col);
		}
			
		if (currentPlayer == Board.O) {
			// topothetei checker sto [row][col] tou GUI
			placeChecker(player2Color, row, col);
		}
		
		if (board.checkGameOver()) {
			board.setGameOver(true);
			gameOver();
		}
		board.print();
		System.out.println("\n*****************************");
	}
	
	// kaleitai meta thn kinhsh tou human player, wste na paixei o computer AI
	public static void aiMove(){

		if (!board.isGameOver()) {
			// check if human player played last
			if (board.getLastLetterPlayed() == Board.X) {
				Move aiMove = ai.MiniMax(board);
				board.makeMove(aiMove.getCol(), Board.O);
				game();
			}
		}

	}
	
	public static void enableButtons() {
		col1_button.setEnabled(true);
		col2_button.setEnabled(true);
		col3_button.setEnabled(true);
		col4_button.setEnabled(true);
		col5_button.setEnabled(true);
		col6_button.setEnabled(true);
		col7_button.setEnabled(true);
	}
	
	public static void disableButtons() {
		col1_button.setEnabled(false);
		col2_button.setEnabled(false);
		col3_button.setEnabled(false);
		col4_button.setEnabled(false);
		col5_button.setEnabled(false);
		col6_button.setEnabled(false);
		col7_button.setEnabled(false);
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
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		enableButtons();
		
		if (firstGame) {
		
			col1_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					makeMove(0);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col2_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(1);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col3_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(2);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col4_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(3);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col5_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(4);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col6_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(5);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}
			});
			
			col7_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(6);
					if (!board.isOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVSAi) aiMove();
						frameMainWindow.requestFocusInWindow();
					}
				}

			});
				

			firstGame = false;
		}
		
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
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// pros8hkh antikeimenwn sto panelMain 
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	
	public static void gameOver() {
        
		int choice = 0;
		board.checkWinState();
		
		if (board.getWinner() == Board.X) {
			if (gameMode == GameParameters.HumanVSAi)
				choice = JOptionPane.showConfirmDialog(null, "You win! Start a new game?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (gameMode == GameParameters.HumanVSHuman)
				choice = JOptionPane.showConfirmDialog(null, "Player 1 wins! Start a new game?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else if (board.getWinner() == Board.O) {
			if (gameMode == GameParameters.HumanVSAi)
				choice = JOptionPane.showConfirmDialog(null, "Computer AI wins! Start a new game?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (gameMode == GameParameters.HumanVSHuman)
				choice = JOptionPane.showConfirmDialog(null, "Player 2 wins! Start a new game?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else {
			choice = JOptionPane.showConfirmDialog(null, "It's a draw! Start a new game?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		}
		
		if (choice == JOptionPane.YES_OPTION) {
			createNewGame();
			AddMenus();
		} else {
			// Disable buttons
			disableButtons();
			
			// Remove key listener
			frameMainWindow.removeKeyListener(frameMainWindow.getKeyListeners()[0]);
		}

	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Gui Connect4 = new Gui();
	}
	
}
