package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import connect4.Board;
import connect4.GameParameters;
import connect4.MiniMaxAi;
import connect4.Move;


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

	static GameParameters game_params;
	static int gameMode;
	static int maxDepth;
	static int player1Color;
	static int player2Color;
	
	static MiniMaxAi ai;

	//	Player 1 symbol: X. Player 1 plays first.
	//	Player 2 symbol: O.
	
	public static JLabel checkerLabel = null;
	
	// for Undo operation
	private static int humanPlayerUndoRow;
	private static int humanPlayerUndoCol;
	private static int humanPlayerUndoLetter;
	private static JLabel humanPlayerUndoCheckerLabel;

	// Menu bars and items
	static JMenuBar menuBar;
	static JMenu fileMenu;
	static JMenuItem newGameItem;
	static JMenuItem undoItem;
	static JMenuItem settingsItem;
	static JMenuItem exitItem;
	static JMenu helpMenu;
	static JMenuItem howToPlayItem;
	static JMenuItem aboutItem;
	
	public Gui() {
		
		try {
			// Option 1
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// Adds the menu bars and items to the window.
	private static void AddMenus() {
		
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		settingsItem = new JMenuItem("Settings");
		exitItem = new JMenuItem("Exit");
		
		undoItem.setEnabled(false);

		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(settingsItem);
		fileMenu.add(exitItem);
		
		helpMenu = new JMenu("Help");
		howToPlayItem = new JMenuItem("How to Play");
		aboutItem = new JMenuItem("About");
		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);

		newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewGame();
			}
		});
		
		undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		
		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsWindow settings = new SettingsWindow(game_params);
				settings.setVisible(true);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		howToPlayItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Click on the buttons or press 1-7 on your keyboard to insert a new checker."
						+ "\nTo win you must place 4 checkers in an row, horizontally, vertically or diagonally.",
						"How to Play", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"© Created by: Christos Kormaris\nVersion 2.0",
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		frameMainWindow.setJMenuBar(menuBar);
		// Makes the board visible after adding menus.
		frameMainWindow.setVisible(true);
		
	}
	
	
	// This is the main Connect-4 board.
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.gif"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, 0, 1);

		return layeredGameBoard;
	}
	
	
	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
			String button = KeyEvent.getKeyText(e.getKeyCode());
			
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
			
			else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                undo();
            }
			
			if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
					|| button.equals("5") || button.equals("6") || button.equals("7")) {
				if (!board.hasOverflowOccured()) {
					game();
					saveUndoMove();
					if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		}
	};
	
	
	private static void undo() {
		// This is the undo implementation for Human VS Human mode.
		if (game_params.getGameMode() == GameParameters.HumanVsHuman) {
			try {
				board.setGameOver(false);
				enableButtons();
				if (frameMainWindow.getKeyListeners().length == 0) {
					frameMainWindow.addKeyListener(gameKeyListener);
				}
				board.undoMove(board.getLastMove().getRow(), board.getLastMove().getCol(), humanPlayerUndoLetter);
				layeredGameBoard.remove(checkerLabel);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
		
		// This is the undo implementation for Human VS AI mode.
		else if (game_params.getGameMode() == GameParameters.HumanVsAi) {
			try {
				board.setGameOver(false);
				enableButtons();
				if (frameMainWindow.getKeyListeners().length == 0) {
					frameMainWindow.addKeyListener(gameKeyListener);
				}
				board.undoMove(board.getLastMove().getRow(), board.getLastMove().getCol(), humanPlayerUndoLetter);
				layeredGameBoard.remove(checkerLabel);
				board.undoMove(humanPlayerUndoRow, humanPlayerUndoCol, humanPlayerUndoLetter);
				layeredGameBoard.remove(humanPlayerUndoCheckerLabel);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
		undoItem.setEnabled(false);
	}
	
	
	// To be called when the game starts for the first time
	// or a new game starts.
	public static void createNewGame() {
		
		configureGuiStyle();

		board = new Board();
		
		// get the new parameters based on previously changed settings
		gameMode = game_params.getGameMode();
		maxDepth = game_params.getMaxDepth();
		player1Color = game_params.getPlayer1Color();
		player2Color = game_params.getPlayer2Color();
		
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		// make the main window appear on the center
		centerWindow(frameMainWindow, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
		// Makes the board visible before adding menus.
		// frameMainWindow.setVisible(true);

		AddMenus();

		if (gameMode == GameParameters.HumanVsAi) {
			ai = new MiniMaxAi(maxDepth, Board.O);
			if (board.getLastSymbolPlayed() == Board.X)
				aiMove(ai);
		}
		
	}
	
	private static void configureGuiStyle() {
		try {
			if (game_params.getGuiStyle() == GameParameters.SystemStyle) {
				// Option 1
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (game_params.getGuiStyle() == GameParameters.CrossPlatformStyle) {
				// Option 2
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (game_params.getGuiStyle() == GameParameters.NimbusStyle) {
				// Option 3
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// It centers the window on screen.
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
	    int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
	    frame.setLocation(x, y);
	}
	
	
	// It finds which player plays next and makes a move on the board.
	public static void makeMove(int col) {
		board.setOverflowOccured(false);
		
		int previousRow = board.getLastMove().getRow();
		int previousCol = board.getLastMove().getCol();
		int previousLetter = board.getLastSymbolPlayed();
		
		if (board.getLastSymbolPlayed() == Board.O) {
			board.makeMove(col, Board.X);
		} else {
			board.makeMove(col, Board.O);
		}
		
		if (board.hasOverflowOccured()) {
			board.getLastMove().setRow(previousRow);
			board.getLastMove().setCol(previousCol);
			board.setLastSymbolPlayed(previousLetter);
		}

	}
	
	
	// It places a checker on the board.
	public static void placeChecker(int color, int row, int col) {
		String colorString = GameParameters.getColorNameByNumber(color);
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + colorString + ".gif"));
		checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	
	public static void saveUndoMove() {
		humanPlayerUndoRow = board.getLastMove().getRow();
		humanPlayerUndoCol = board.getLastMove().getCol();
		humanPlayerUndoLetter = board.getLastSymbolPlayed();
		humanPlayerUndoCheckerLabel = checkerLabel;
	}
	
	
	// Gets called after makeMove(int col) is called.
	public static void game() {
		
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();
		int currentPlayer = board.getLastSymbolPlayed();
		
		if (currentPlayer == Board.X) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(player1Color, row, col);
		}
		
		if (currentPlayer == Board.O) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(player2Color, row, col);
		}
		
		gameOver();
		
		board.printBoard();
		System.out.println("\n*****************************");
		
		undoItem.setEnabled(true);
	}
	
	
	// Gets called after the human player makes a move. It makes a Minimax AI move.
	public static void aiMove(MiniMaxAi ai){
		Move aiMove = ai.miniMax(board);
		board.makeMove(aiMove.getCol(), ai.getAiLetter());
		game();
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
	 * It calls the "actionListener" function, when a click on a button is made.
	 */
	public static Component createContentComponents() {
		
		// Create a panel to set up the board buttons.
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 6, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		enableButtons();
		
		if (firstGame) {
		
			col1_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					makeMove(0);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col2_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(1);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col3_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(2);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col4_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(3);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col5_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(4);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col6_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(5);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col7_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeMove(6);
					if (!board.hasOverflowOccured()) {
						game();
						saveUndoMove();
						if (gameMode == GameParameters.HumanVsAi) aiMove(ai);
					}
					frameMainWindow.requestFocusInWindow();
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

		// main Connect-4 board creation
		layeredGameBoard = createLayeredBoard();

		// panel creation to store all the elements of the board
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// add button and main board components to panelMain
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	
	public static void gameOver() {
		int choice = 0;
		
		if (board.checkWinState()) {
			board.setGameOver(true);
		} else {
			return;
		}
		
		if (board.getWinner() == Board.X) {
			if (gameMode == GameParameters.HumanVsAi)
				choice = JOptionPane.showConfirmDialog(null,
						"You win! Start a new game?",
						"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (gameMode == GameParameters.HumanVsHuman)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 1 wins! Start a new game?",
						"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else if (board.getWinner() == Board.O) {
			if (gameMode == GameParameters.HumanVsAi)
				choice = JOptionPane.showConfirmDialog(null,
						"Computer AI wins! Start a new game?",
						"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (gameMode == GameParameters.HumanVsHuman)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 2 wins! Start a new game?",
						"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else {
			choice = JOptionPane.showConfirmDialog(null,
					"It's a draw! Start a new game?",
					"GAME OVER", JOptionPane.YES_NO_OPTION);
		}
		
		if (choice == JOptionPane.YES_OPTION) {
			createNewGame();
		} else {
			// Disable buttons
			disableButtons();
			
			// Remove key listener
			frameMainWindow.removeKeyListener(frameMainWindow.getKeyListeners()[0]);
		}

	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		Gui connect4 = new Gui();
		
		game_params = new GameParameters();
		gameMode = game_params.getGameMode();
		maxDepth = game_params.getMaxDepth();
		player1Color = game_params.getPlayer1Color();
		player2Color = game_params.getPlayer2Color();
		
		connect4.createNewGame();
	}
	
	
}
