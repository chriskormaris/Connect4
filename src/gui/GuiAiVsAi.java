package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import connect4.Board;
import connect4.GameParameters;
import connect4.MiniMaxAi;
import connect4.Move;

public class GuiAiVsAi {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;

	static GameParameters game_params = new GameParameters();
	// Initial maxDepth = 4. We can change this value for difficulty adjustment.
	static int maxDepth = game_params.getMaxDepth();
	static int player1Color = game_params.getPlayer1Color();
	static int player2Color = game_params.getPlayer2Color();
	
	static MiniMaxAi ai1;
	static MiniMaxAi ai2;

	public GuiAiVsAi() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		centerWindow(frameMainWindow, 570, 490);
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});

		// show window
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

		// AI VS AI implementation HERE
		ai1 = new MiniMaxAi(maxDepth, Board.X);
		ai2 = new MiniMaxAi(maxDepth, Board.O);
		while (!board.isGameOver()) {
//			if (board.getLastSymbolPlayed() == Board.O)
				aiMove(ai1);
			
			if (!board.isGameOver()) {
//				if (board.getLastSymbolPlayed() == Board.X)
					aiMove(ai2);
			}
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
		int currentPlayer = board.getLastSymbolPlayed();
		
		if (currentPlayer == Board.X) {
			// It places a red checker in the corresponding [row][col] of the GUI.
			placeChecker(player1Color, row, col);
		} else if (currentPlayer == Board.O) {
			// It places a yellow checker in the corresponding [row][col] of the GUI.
			placeChecker(player2Color, row, col);
		}
		
		if (board.checkGameOver()) {
			board.setGameOver(true);
			gameOver();
		}
		
		board.printBoard();
		System.out.println("\n*****************************");
		
	}
	
	public static void aiMove(MiniMaxAi ai){
		Move aiMove = ai.miniMax(board);
		board.makeMove(aiMove.getCol(), ai.getAiLetter());
		game();
	}
	
	/**
	 * Returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * It calls the "actionListener" function, when a click on a button is made.
	 */
	public static Component createContentComponents() {
		// main Connect-4 board creation
		layeredGameBoard = createLayeredBoard();

		// panel creation to store all the elements of the board
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// add components to panelMain
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
			String player1ColorString = GameParameters.getColorNameByNumber(player1Color);
			winLabel = new JLabel("AI 1 (" + player1ColorString + ") wins! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Board.O) {
			String player2ColorString = GameParameters.getColorNameByNumber(player2Color);
			winLabel = new JLabel("AI 2 (" + player2ColorString + ") wins! Start a new game?");
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
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		GuiAiVsAi connect4 = new GuiAiVsAi();
		GuiAiVsAi.createNewGame();
	}
		
}
