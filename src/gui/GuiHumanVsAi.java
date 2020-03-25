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
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import connect4.Board;
import connect4.Constants;
import connect4.GameParameters;
import connect4.MiniMaxAi;
import connect4.Move;


public class GuiHumanVsAi {
	
	static Board board;
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	// Menu bars and items.
	static JMenuBar menuBar;
	static JMenu menu1;
	static JMenuItem item11;
	static JMenuItem item12;
	static JMenuItem item13;
	static JMenu menu2;
	static JMenuItem item21;
	static JMenuItem item22;

	static final int DEFAULT_WIDTH = 570;
	static final int DEFAULT_HEIGHT = 490;
	
	static boolean firstGame = true;

	static JButton col1_button = new JButton("1");
	static JButton col2_button = new JButton("2");
	static JButton col3_button = new JButton("3");
	static JButton col4_button = new JButton("4");
	static JButton col5_button = new JButton("5");
	static JButton col6_button = new JButton("6");
	static JButton col7_button = new JButton("7");
	
    static JLabel turnMessage;

	static MiniMaxAi ai = new MiniMaxAi(GameParameters.maxDepth1, Constants.X);
	
	// Human player letter -> X. He plays first.
	// Minimax AI letter -> O.
	
	public GuiHumanVsAi() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		createNewGame();
	}
		
	// the main Connect-4 board
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.png"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, 0, 1);

		return layeredGameBoard;
	}
	
	// To be called when the game starts for the first time
	// or a new game starts.
	public static void createNewGame() {
		board = new Board();
		
		// set the new difficulty setting
		ai.setMaxDepth(GameParameters.maxDepth1);
             
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		// make the main window appear on the center
		centerWindow(frameMainWindow, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		
		frameMainWindow.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
				String button = KeyEvent.getKeyText(e.getKeyCode());
				
				board.setOverflowOccured(false);
				int previousRow = board.getLastMove().getRow();
				int previousCol = board.getLastMove().getCol();
				int previousLetter = board.getLastSymbolPlayed();

				if (button.equals("1")) {
					board.makeMove(0, Constants.X);
				} else if (button.equals("2")) {
					board.makeMove(1, Constants.X);
				} else if (button.equals("3")) {
					board.makeMove(2, Constants.X);
				} else if (button.equals("4")) {
					board.makeMove(3, Constants.X);
				} else if (button.equals("5")) {
					board.makeMove(4, Constants.X);
				} else if (button.equals("6")) {
					board.makeMove(5, Constants.X);
				} else if (button.equals("7")) {
					board.makeMove(6, Constants.X);
				}
				
				if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
						|| button.equals("5") || button.equals("6") || button.equals("7")) {
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
		
		frameMainWindow.setFocusable(true);
		
		// Add the turn label.
		JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        frameMainWindow.add(tools, BorderLayout.PAGE_END);
        turnMessage = new JLabel("Turn: " + board.getTurn());
        tools.add(turnMessage);
		
		// show window
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

		if (board.getLastSymbolPlayed() == Constants.X) {
			Move aiMove = ai.miniMax(board);
			board.makeMove(aiMove.getCol(), Constants.O);
			game();
		}

	}
	
	
	// It centers the window on screen.
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
	    frame.setLocation(x, y);
	}
	
	// It places a checker on the board.
	public static void placeChecker(int color, int row, int col) {
		String colorString = GameParameters.getColorNameByNumber(color);
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + colorString + ".png"));
		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// Gets called after makeMove(int, col) is called.
	public static void game() {
	
        turnMessage.setText("Turn: " + board.getTurn());
		
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();

		int currentPlayer = board.getLastSymbolPlayed();
		
		if (currentPlayer == Constants.X) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(GameParameters.player1Color, row, col);
		}
		
		if (currentPlayer == Constants.O) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(GameParameters.player2Color, row, col);
		}

		System.out.println("Turn: " + board.getTurn());
		Board.printBoard(board.getGameBoard());
		System.out.println("\n*****************************");
		
		if (board.checkGameOver()) {
			gameOver();
		}
		
	}
	
	
	// Gets called after the human player makes a move. It makes an minimax AI move.
	public static void aiMove(){

		if (!board.isGameOver()) {
			// check if human player played last
			if (board.getLastSymbolPlayed() == Constants.X) {
				Move aiMove = ai.miniMax(board);
				board.makeMove(aiMove.getCol(), Constants.O);
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
		
		// Create a panel to set up the board buttons.
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 6, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		enableButtons();
		
		if (firstGame) {
			
			col1_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(0, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col2_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(1, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col3_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(2, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col4_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(3, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col5_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(4, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col6_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(5, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
					}
					frameMainWindow.requestFocusInWindow();
				}
			});
			
			col7_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setOverflowOccured(false);
					int previousRow = board.getLastMove().getRow();
					int previousCol = board.getLastMove().getCol();
					int previousLetter = board.getLastSymbolPlayed();
					board.makeMove(6, Constants.X);
					if (!board.hasOverflowOccured()) {
						game();
						aiMove();
					} else {
						board.getLastMove().setRow(previousRow);
						board.getLastMove().setCol(previousCol);
						board.setLastSymbolPlayed(previousLetter);
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
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// add button and main board components to panelMain
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	
	// It gets called only of the game is over.
	// We can check if the game is over by calling the method "checkGameOver()"
	// of the class "Board".
	public static void gameOver() {
		board.setGameOver(true);
		
		disableButtons();
		frameMainWindow.removeKeyListener(frameMainWindow.getKeyListeners()[0]);
		
//		panelBoardNumbers.setVisible(false);
		frameGameOver = new JFrame("Game over!");
		frameGameOver.setBounds(620, 400, 350, 128);
		centerWindow(frameGameOver, 0, 0);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		
//		ImageIcon winIcon = new ImageIcon(ResourceLoader.load("images/win.png"));
//		JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		board.checkWinState();
		if (board.getWinner() == Constants.X) {
			winLabel = new JLabel("You win! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Constants.O) {
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
			@Override
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				createNewGame();
			}
		});
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		quitButton.addActionListener(new ActionListener() {
			@Override
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
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		GuiHumanVsAi connect4 = new GuiHumanVsAi();
		connect4.createNewGame();
	}
	
}
