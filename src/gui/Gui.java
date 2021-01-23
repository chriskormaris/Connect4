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
import java.util.Stack;

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
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import connect4.Board;
import connect4.Constants;
import connect4.GameParameters;
import connect4.MiniMaxAi;
import connect4.Move;
import enumerations.Color;
import enumerations.GameMode;
import enumerations.GuiStyle;


public class Gui {
	
	static final int numOfRows = Constants.NUM_OF_ROWS;
	static final int numOfColumns = Constants.NUM_OF_COLUMNS;
	static final int inARow = Constants.IN_A_ROW;
	
	static Board board;
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelMain;
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	static final int DEFAULT_WIDTH = 570;
	static final int DEFAULT_HEIGHT = 525;
		
	static JButton[] buttons;

    static JLabel turnMessage;
    
	static MiniMaxAi ai;

	
	
	public static JLabel checkerLabel = null;
	
	
	static Stack<Board> undoBoards = new Stack<Board>();
	static Stack<JLabel> undoCheckerLabels = new Stack<JLabel>();
	static Stack<Board> redoBoards = new Stack<Board>();
	static Stack<JLabel> redoCheckerLabels = new Stack<JLabel>();

	
	static JMenuBar menuBar;
	static JMenu fileMenu;
	static JMenuItem newGameItem;
	static JMenuItem undoItem;
	static JMenuItem redoItem;
	static JMenuItem settingsItem;
	static JMenuItem exitItem;
	static JMenu helpMenu;
	static JMenuItem howToPlayItem;
	static JMenuItem aboutItem;
	
	public Gui() {
		buttons = new JButton[numOfColumns];
		for (int i=0; i<numOfColumns; i++) {
			buttons[i] = new JButton(i+1+"");
			buttons[i].setFocusable(false);
		}
	}
	
	
	
	private static void AddMenus() {
		
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		redoItem = new JMenuItem("Redo    Ctrl+Y");
		settingsItem = new JMenuItem("Settings");
		exitItem = new JMenuItem("Exit");
		
		helpMenu = new JMenu("Help");
		howToPlayItem = new JMenuItem("How to Play");
		aboutItem = new JMenuItem("About");
		
		undoItem.setEnabled(false);
		redoItem.setEnabled(false);

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
		
		redoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});
		
		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsWindow settings = new SettingsWindow();
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
						"Click on the buttons or press 1-" + numOfColumns + " on your keyboard to insert a new checker."
						+ "\nTo win you must place " + inARow + " checkers in an row, horizontally, vertically or diagonally.",
						"How to Play", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("<html><center>© Created by: Christos Kormaris<br>"
						+ "Version " + Constants.VERSION + "</center></html>");
				JOptionPane.showMessageDialog(frameMainWindow, label, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(redoItem);
		fileMenu.add(settingsItem);
		fileMenu.add(exitItem);
		
		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		frameMainWindow.setJMenuBar(menuBar);
		
		frameMainWindow.setVisible(true);
		
	}
	
	
	
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
	
	
	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
			String keyText = KeyEvent.getKeyText(e.getKeyCode());
			
			for (int i=0; i<Constants.NUM_OF_COLUMNS; i++) {
				if (keyText.equals(i+1+"")) {
			        undoBoards.push(new Board(board));
					makeMove(i);
					
					if (!board.isOverflow()) {
						boolean isGameOver = game();
						if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI && !isGameOver) { 
							aiMove(ai);
						}
					}
					break;
				}
			}
			if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Z)) {
                undo();
            }
			else if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Y)) {
				redo();
            }
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	};
	
	
	private static void undo() {
		if (!undoBoards.isEmpty()) {
			// This is the undo implementation for "Human Vs Human" mode.
			if (GameParameters.gameMode == GameMode.HUMAN_VS_HUMAN) {
				try {
					board.setGameOver(false);
					
					setAllButtonsEnabled(true);
					
					if (frameMainWindow.getKeyListeners().length == 0) {
						frameMainWindow.addKeyListener(gameKeyListener);
					}
					
					JLabel previousCheckerLabel = undoCheckerLabels.pop();
					
					redoBoards.push(new Board(board));
					redoCheckerLabels.push(previousCheckerLabel);

					board = undoBoards.pop();
					layeredGameBoard.remove(previousCheckerLabel);
					
					turnMessage.setText("Turn: " + board.getTurn());
					frameMainWindow.paint(frameMainWindow.getGraphics());
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
			
			
			else if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI) {
				try {
					board.setGameOver(false);
					setAllButtonsEnabled(true);
					
					if (frameMainWindow.getKeyListeners().length == 0) {
						frameMainWindow.addKeyListener(gameKeyListener);
					}
					
					JLabel previousAiCheckerLabel = undoCheckerLabels.pop();
					JLabel previousHumanCheckerLabel = undoCheckerLabels.pop();

					redoBoards.push(new Board(board));
					redoCheckerLabels.push(previousAiCheckerLabel);
					redoCheckerLabels.push(previousHumanCheckerLabel);
					
					board = undoBoards.pop();
					layeredGameBoard.remove(previousAiCheckerLabel);
					layeredGameBoard.remove(previousHumanCheckerLabel);
					
					turnMessage.setText("Turn: " + board.getTurn());
					frameMainWindow.paint(frameMainWindow.getGraphics());
				} catch (NullPointerException|ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}

			if (undoBoards.isEmpty()) {
				undoItem.setEnabled(false);
			}
			
			redoItem.setEnabled(true);

			System.out.println("Turn: " + board.getTurn());
			Board.printBoard(board.getGameBoard());
		}
	}
	
	
	private static void redo() {
		if (!redoBoards.isEmpty()) {
			// This is the redo implementation for "Human Vs Human" mode.
			if (GameParameters.gameMode == GameMode.HUMAN_VS_HUMAN) {
				try {
					board.setGameOver(false);
					
					setAllButtonsEnabled(true);
					
					if (frameMainWindow.getKeyListeners().length == 0) {
						frameMainWindow.addKeyListener(gameKeyListener);
					}
					
					JLabel redoCheckerLabel = redoCheckerLabels.pop();
					
					undoBoards.push(new Board(board));
					undoCheckerLabels.push(redoCheckerLabel);
					
					board = new Board(redoBoards.pop());
					layeredGameBoard.add(redoCheckerLabel, 0, 0);
					
					turnMessage.setText("Turn: " + board.getTurn());
					frameMainWindow.paint(frameMainWindow.getGraphics());
					
					boolean isGameOver = board.checkForGameOver(); 
					if (isGameOver) {
						gameOver();
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("There is no move to redo!");
					System.err.flush();
				}
			}
			
			
			else if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI) {
				try {
					board.setGameOver(false);
					setAllButtonsEnabled(true);
					
					if (frameMainWindow.getKeyListeners().length == 0) {
						frameMainWindow.addKeyListener(gameKeyListener);
					}
					
					JLabel redoAiCheckerLabel = redoCheckerLabels.pop();
					JLabel redoHumanCheckerLabel = redoCheckerLabels.pop();

					undoBoards.push(new Board(board));
					undoCheckerLabels.push(redoAiCheckerLabel);
					undoCheckerLabels.push(redoHumanCheckerLabel);
					
					board = new Board(redoBoards.pop());
					layeredGameBoard.add(redoAiCheckerLabel, 0, 0);
					layeredGameBoard.add(redoHumanCheckerLabel, 0, 0);
					
					turnMessage.setText("Turn: " + board.getTurn());
					frameMainWindow.paint(frameMainWindow.getGraphics());
					
					boolean isGameOver = board.checkForGameOver(); 
					if (isGameOver) {
						gameOver();
					}
				} catch (NullPointerException|ArrayIndexOutOfBoundsException ex) {
					System.err.println("There is no move to redo!");
					System.err.flush();
				}
			}
			
			if (redoBoards.isEmpty())
				redoItem.setEnabled(false);
			
			undoItem.setEnabled(true);
			
			System.out.println("Turn: " + board.getTurn());
			Board.printBoard(board.getGameBoard());
		}
	}
	
	
	
	public static void createNewGame() {
		
		configureGuiStyle();
		
		if (GameParameters.gameMode != GameMode.MINIMAX_AI_VS_MINIMAX_AI) {
			setAllButtonsEnabled(true);
		}
		
		board = new Board();
		
		undoBoards.clear();
		undoCheckerLabels.clear();
		
		redoBoards.clear();
		redoCheckerLabels.clear();
		
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
		
		if (frameMainWindow.getKeyListeners().length == 0) {
			frameMainWindow.addKeyListener(gameKeyListener);
		}
		
		frameMainWindow.setFocusable(true);
		
		
		frameMainWindow.pack();
		JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        frameMainWindow.add(tools, BorderLayout.PAGE_END);
        turnMessage = new JLabel("Turn: " + board.getTurn());
        tools.add(turnMessage);
		
		AddMenus();
		
		System.out.println("Turn: " + board.getTurn());
		Board.printBoard(board.getGameBoard());
		
		if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI) {
			ai = new MiniMaxAi(GameParameters.maxDepth1, Constants.P2);
		} else if (GameParameters.gameMode == GameMode.MINIMAX_AI_VS_MINIMAX_AI) {
			setAllButtonsEnabled(false);
			
			// AI VS AI implementation here
			// Initial maxDepth = 4. We can change this value for difficulty adjustment.
			MiniMaxAi ai1 = new MiniMaxAi(GameParameters.maxDepth1, Constants.P1);
			MiniMaxAi ai2 = new MiniMaxAi(GameParameters.maxDepth2, Constants.P2);
			
			while (!board.isGameOver()) {
				aiMove(ai1);
				
				if (!board.isGameOver()) {
					aiMove(ai2);
				}
			}
		}
		
	}
	
	
	private static void configureGuiStyle() {
		try {
			if (GameParameters.guiStyle == GuiStyle.SYSTEM_STYLE) {
				// Option 1
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (GameParameters.guiStyle == GuiStyle.CROSS_PLATFORM_STYLE) {
				// Option 2
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (GameParameters.guiStyle == GuiStyle.NIMBUS_STYLE) {
				// Option 3
			    for (LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			}
		} catch (Exception e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e2) {
				e2.printStackTrace();	
			}
		}
	}
	
	
	
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
	    int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
	    frame.setLocation(x, y);
	}
	
	
	
	public static void makeMove(int col) {
		board.setOverflow(false);
		
		int previousRow = board.getLastMove().getRow();
		int previousCol = board.getLastMove().getColumn();
		int previousLetter = board.getLastPlayer();
		
		if (board.getLastPlayer() == Constants.P2) {
			board.makeMove(col, Constants.P1);
		} else {
			board.makeMove(col, Constants.P2);
		}
		
		if (board.isOverflow()) {
			board.getLastMove().setRow(previousRow);
			board.getLastMove().setColumn(previousCol);
			board.setLastPlayer(previousLetter);
			
			undoBoards.pop();
		}

	}
	
	
	
	public static void placeChecker(Color color, int row, int col) {
		String colorString = String.valueOf(color).charAt(0) + String.valueOf(color).toLowerCase().substring(1);
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + colorString + ".png"));
		
		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);
		
		undoCheckerLabels.push(checkerLabel);
		
		try {
			if (GameParameters.gameMode == GameMode.MINIMAX_AI_VS_MINIMAX_AI) {
				Thread.sleep(200);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean game() {
		
		turnMessage.setText("Turn: " + board.getTurn());

		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getColumn();
		int currentPlayer = board.getLastPlayer();
		
		if (currentPlayer == Constants.P1) {
			
			placeChecker(GameParameters.player1Color, row, col);
		}
		
		if (currentPlayer == Constants.P2) {
			
			placeChecker(GameParameters.player2Color, row, col);
		}
		
		System.out.println("Turn: " + board.getTurn());
		Board.printBoard(board.getGameBoard());
		
		boolean isGameOver = board.checkForGameOver(); 
		if (isGameOver) {
			gameOver();
		}
		
		undoItem.setEnabled(true);
		
		redoBoards.clear();
		redoCheckerLabels.clear();
		redoItem.setEnabled(false);

		return isGameOver;
	}
	
	
	
	public static void aiMove(MiniMaxAi ai){
		
		Move aiMove = ai.miniMaxAlphaBeta(board);
		board.makeMove(aiMove.getColumn(), ai.getAiPlayer());
		game();
	}
	
	
	public static void setAllButtonsEnabled(boolean b) {
		if (b) {
			
			for (int i=0; i<buttons.length; i++) {
				JButton button = buttons[i];
				int column = i;
				
				if (button.getActionListeners().length == 0) { 
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
					        undoBoards.push(new Board(board));
							makeMove(column);
							
							if (!board.isOverflow()) {
								boolean isGameOver = game();
								if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI && !isGameOver) {
									aiMove(ai);
								}
							}
							frameMainWindow.requestFocusInWindow();
						}
					});
				}
			}
		
		} else {
			
			for (JButton button: buttons) {
				for (ActionListener actionListener: button.getActionListeners()) {
					button.removeActionListener(actionListener);
				}
			}
		
		}
	}
	
	
	
	public static Component createContentComponents() {
		
		
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, numOfColumns, numOfRows, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		for (JButton button: buttons) {
			panelBoardNumbers.add(button);
		}
		
		
		layeredGameBoard = createLayeredBoard();
		
		
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	
	
	public static void gameOver() {
		board.setGameOver(true);
		
		int choice = 0;
		if (board.getWinner() == Constants.P1) {
			if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"You win! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (GameParameters.gameMode == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 1 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (GameParameters.gameMode == GameMode.MINIMAX_AI_VS_MINIMAX_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Minimax AI 1 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
		} else if (board.getWinner() == Constants.P2) {
			if (GameParameters.gameMode == GameMode.HUMAN_VS_MINIMAX_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Computer AI wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (GameParameters.gameMode == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 2 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (GameParameters.gameMode == GameMode.MINIMAX_AI_VS_MINIMAX_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Minimax AI 2 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
		} else if (board.checkForDraw()) {
			choice = JOptionPane.showConfirmDialog(null,
					"It's a draw! Start a new game?",
					"Game Over", JOptionPane.YES_NO_OPTION);
		}
		
		
		setAllButtonsEnabled(false);

		
		for (KeyListener keyListener: frameMainWindow.getKeyListeners()) {
			frameMainWindow.removeKeyListener(keyListener);
		}
		
		if (choice == JOptionPane.YES_OPTION) {
			createNewGame();
		}

	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		Gui connect4 = new Gui();
		
		
		
		connect4.createNewGame();
	}
	
	
}
