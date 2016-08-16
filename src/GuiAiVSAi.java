import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GuiAiVSAi {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	// arxikopoioume me maxDepth = 4, mporei na parei allh timh gia rythmish duskolias 
	static MinimaxAI ai1 = new MinimaxAI(4, Board.X);
	static MinimaxAI ai2 = new MinimaxAI(4, Board.O);
	
	public GuiAiVSAi() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		createNewGame();
	}
	
	// o kurios pinakas tou Connect-4
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 490));
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
                
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		centreWindow(frameMainWindow, 570, 490); // to kurio parathuro tha emfanizetai sto kentro
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
		while (!board.isGameOver()) {
			
			if (board.getLastLetterPlayed() == Board.O) {
				aiMove(ai1, Board.X);
//				Move aiMove = ai.MiniMax(board);
//				board.makeMove(aiMove.getCol(), Board.X);
//				game();
			}
			
			
			if (!board.isGameOver()) {
				
				if (board.getLastLetterPlayed() == Board.X) {
					aiMove(ai2, Board.O);
//					Move ai2Move = ai2.MiniMax(board);
//					board.makeMove(ai2Move.getCol(), Board.O);
//					game();
				}
				
			}
			
		}

	}
	
	// kentrarei to parathuro sthn othonh
	public static void centreWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
	    frame.setLocation(x, y);
	}
	
	// topothetei checker ston pinaka
	public static void placeChecker(String color, int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + color + ".gif"));
		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// kaleitai kathe fora pou eisagetai mia kinhsh ston pinaka
	public static void game() {
	
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();

		int lastPlayerLetter = board.getLastLetterPlayed();
		if (lastPlayerLetter == Board.X) {
			// topothetei kokkino checker sto [row][col] tou GUI
			placeChecker("RED", row, col);
		} else if (lastPlayerLetter == Board.O) {
			// topothetei kitrino checker sto [row][col] tou GUI
			placeChecker("YELLOW", row, col);
		}
		if (board.checkGameOver()) {
			board.setGameOver(true);
			gameOver();
		}

	}
	
	public static void aiMove(MinimaxAI player, int checker){
		Move aiMove = player.MiniMax(board);
		board.makeMove(aiMove.getCol(), checker);
		game();
	}
	
	/**
	 * Returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * Kalei ton actionListener otan ginetai click me to pontiki panw se button.
	 */
	public static Component createContentComponents() {
		// dhmiourgia tou kuriou pinaka tou Connect-4
		layeredGameBoard = createLayeredBoard();

		// dhmiourgia panel gia na krathsoume ola ta stoixeia tou pinaka
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// pros8hkh antikeimenwn sto panelMain 
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	public static void gameOver() {
		        		        
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
			winLabel = new JLabel("AI 1 (Red) wins! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Board.O) {
			winLabel = new JLabel("AI 2 (Yellow) wins! Start a new game?");
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
		GuiAiVSAi Connect4 = new GuiAiVSAi();
	}
		
}
