package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import connect4.Board;
import connect4.GameParameters;

public class GuiHumanVsHuman {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	static GameParameters game_params = new GameParameters();
	static int player1Color = game_params.getPlayer1Color();
	static int player2Color = game_params.getPlayer2Color();
	
	//	Player 1 letter -> X. He plays First
	//	Player 2 letter -> O.
	
	public GuiHumanVsHuman() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		createNewGame();
	}
	
	// the main Connect-4 board
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 490));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.gif"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, 0, 1);

		return layeredGameBoard;
	}
	
	// To be called when the game starts for the first time.
	public static void createNewGame() {
		board = new Board();
                
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		// make the main window appear on the center
		centreWindow(frameMainWindow, 570, 490);
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);
		
		frameMainWindow.addWindowListener(new WindowAdapter() {
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
				//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
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
				
				if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
						|| button.equals("5") || button.equals("6") || button.equals("7")) {
					if (!board.hasOverflowOccured()) {
						game();
					} else {
						board.setOverflowOccured(false);
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
		
		frameMainWindow.setFocusable(true);
		
		// show window
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

	}
	
	// It centers the window on screen.
	public static void centreWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
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
		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// Gets called after makeMove(int col) is called.
	public static void game() {
	
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();
		
		if (board.getLastSymbolPlayed() == Board.X) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(player1Color, row, col);
		} else if (board.getLastSymbolPlayed() == Board.O) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(player2Color, row, col);
		} 
		if (board.checkGameOver()) {
			gameOver();
		}

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
		
		JButton col1_button = new JButton("1");
		col1_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(0);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col2_button = new JButton("2");
		col2_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(1);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col3_button = new JButton("3");
		col3_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(2);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col4_button = new JButton("4");
		col4_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(3);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col5_button = new JButton("5");
		col5_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(4);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col6_button = new JButton("6");
		col6_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(5);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
		JButton col7_button = new JButton("7");
		col7_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(6);
				if (!board.hasOverflowOccured()) {
					game();
				}
				frameMainWindow.requestFocusInWindow();
			}
		});
		
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
	
	public static void gameOver() {
		board.setGameOver(true);
	        
//		panelBoardNumbers.setVisible(false);
		frameGameOver = new JFrame("Game over!");
		frameGameOver.setBounds(620, 400, 350, 128);
		centreWindow(frameGameOver, 0, 0);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		
//		ImageIcon winIcon = new ImageIcon(ResourceLoader.load("images/win.gif"));
//		JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		board.checkWinState();
		if (board.getWinner() == Board.X) {
			winLabel = new JLabel("Player 1 (Red) wins! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Board.O) {
			winLabel = new JLabel("Player 2 (Yellow) wins! Start a new game?");
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
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		 GuiHumanVsHuman connect4 = new GuiHumanVsHuman();
		connect4.createNewGame();
	}
		
}
