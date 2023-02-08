package com.chriskormaris.connect4.gui;


import com.chriskormaris.connect4.api.ai.AI;
import com.chriskormaris.connect4.api.ai.MinimaxAI;
import com.chriskormaris.connect4.api.ai.MinimaxAlphaBetaPruningAI;
import com.chriskormaris.connect4.api.ai.RandomChoiceAI;
import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;
import com.chriskormaris.connect4.gui.util.GameParameters;
import com.chriskormaris.connect4.gui.util.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import static com.chriskormaris.connect4.api.util.Constants.CONNECT_4_CHECKERS_IN_A_ROW;
import static com.chriskormaris.connect4.api.util.Constants.CONNECT_5_CHECKERS_IN_A_ROW;
import static com.chriskormaris.connect4.gui.util.GuiConstants.CONNECT_4_BOARD_IMG_PATH;
import static com.chriskormaris.connect4.gui.util.GuiConstants.CONNECT_4_TITLE;
import static com.chriskormaris.connect4.gui.util.GuiConstants.CONNECT_5_BOARD_IMG_PATH;
import static com.chriskormaris.connect4.gui.util.GuiConstants.CONNECT_5_TITLE;
import static com.chriskormaris.connect4.gui.util.GuiConstants.DEFAULT_CONNECT_4_HEIGHT;
import static com.chriskormaris.connect4.gui.util.GuiConstants.DEFAULT_CONNECT_4_WIDTH;
import static com.chriskormaris.connect4.gui.util.GuiConstants.DEFAULT_CONNECT_5_HEIGHT;
import static com.chriskormaris.connect4.gui.util.GuiConstants.DEFAULT_CONNECT_5_WIDTH;
import static com.chriskormaris.connect4.gui.util.GuiConstants.VERSION;


public class GUI {

	public static GameParameters gameParameters = new GameParameters();
	public static GameParameters newGameParameters = new GameParameters(gameParameters);

	static int NUM_OF_ROWS;
	static int NUM_OF_COLUMNS;
	static int CHECKERS_IN_A_ROW;

	static Board board;
	static JFrame frameMainWindow;

	static JPanel panelMain;
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;

	static JButton[] buttons;

	static JLabel turnMessage;

	static AI ai;

	// Player 1 symbol: X. Plays first.
	// Player 2 symbol: O.

	// These Stack objects are used for the "Undo" and "Redo" functionalities.
	static Stack<Board> undoBoards = new Stack<>();
	static Stack<JLabel> undoCheckerLabels = new Stack<>();
	static Stack<Board> redoBoards = new Stack<>();
	static Stack<JLabel> redoCheckerLabels = new Stack<>();

	// Menu bars and items
	static JMenuBar menuBar;
	static JMenu fileMenu;
	static JMenuItem newGameItem;
	static JMenuItem undoItem;
	static JMenuItem redoItem;
	static JMenuItem saveGameItem;
	static JMenuItem restoreSavedGameItem;
	static JMenuItem loadNovelPositionItem;
	static JMenuItem exportToGifItem;
	static JMenuItem settingsItem;
	static JMenuItem exitItem;
	static JMenu helpMenu;
	static JMenuItem howToPlayItem;
	static JMenuItem aboutItem;

	static JButton undoButton;
	static JButton redoButton;
	static boolean pause;

	public GUI() {

	}

	// Add the menu bars and items to the window.
	private static void addMenus() {
		// Add the menu bar.
		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		redoItem = new JMenuItem("Redo    Ctrl+Y");
		saveGameItem = new JMenuItem("Save Game");
		restoreSavedGameItem = new JMenuItem("Restore Saved Game");
		loadNovelPositionItem = new JMenuItem("Load Novel Position");
		exportToGifItem = new JMenuItem("Export to .gif");
		settingsItem = new JMenuItem("Settings");
		exitItem = new JMenuItem("Exit");

		helpMenu = new JMenu("Help");
		howToPlayItem = new JMenuItem("How to Play");
		aboutItem = new JMenuItem("About");

		undoItem.setEnabled(false);
		redoItem.setEnabled(false);

		newGameItem.addActionListener(e -> createNewGame());

		undoItem.addActionListener(e -> undo());

		redoItem.addActionListener(e -> redo());

		saveGameItem.addActionListener(e -> {
			saveGame();
			System.out.println("Game saved!");
		});

		restoreSavedGameItem.addActionListener(e -> {
			restoreSavedGame();
			System.out.println("Last saved game restored!");
		});

		loadNovelPositionItem.addActionListener(e -> {
			LoadNovelPositionWindow lnpw = new LoadNovelPositionWindow();
			lnpw.setVisible(true);
			System.out.println("Novel game position loaded!");
		});

		exportToGifItem.addActionListener(e -> exportToGif());

		settingsItem.addActionListener(e -> {
			SettingsWindow settings = new SettingsWindow();
			settings.setVisible(true);
		});

		exitItem.addActionListener(e -> System.exit(0));

		howToPlayItem.addActionListener(e -> JOptionPane.showMessageDialog(panelMain,
				"Click on the buttons or press 1-" + NUM_OF_COLUMNS + " on your keyboard to insert a new checker."
						+ "\nTo win you must place " + CHECKERS_IN_A_ROW + " checkers in an row, horizontally, vertically or diagonally.",
				"How to Play", JOptionPane.INFORMATION_MESSAGE));

		aboutItem.addActionListener(e -> {
			JLabel label = new JLabel("<html><center>© Created by: Christos Kormaris<br>"
					+ "Version " + VERSION + "</center></html>");
			JOptionPane.showMessageDialog(panelMain, label, "About", JOptionPane.INFORMATION_MESSAGE);
		});

		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(redoItem);
		fileMenu.add(saveGameItem);
		fileMenu.add(restoreSavedGameItem);
		fileMenu.add(loadNovelPositionItem);
		fileMenu.add(exportToGifItem);
		fileMenu.add(settingsItem);
		fileMenu.add(exitItem);

		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		frameMainWindow.setJMenuBar(menuBar);
		// Make the board visible after adding the menus.
		frameMainWindow.setVisible(true);
	}

	public static void saveGame() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("grid.txt"));
			for (int i = 0; i < NUM_OF_ROWS; i++) {
				for (int j = 0; j < NUM_OF_COLUMNS; j++) {
					if (board.getGameBoard()[i][j] != Constants.EMPTY) {
						bw.write(i + "" + j + ":" + board.getGameBoard()[i][j] + "\n");
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException | NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}


	public static void restoreSavedGame() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("grid.txt"));
			String line;

			createNewGame();

			while ((line = br.readLine()) != null) {
				int row = Integer.parseInt(line.charAt(0) + "");
				int column = Integer.parseInt(line.charAt(1) + "");
				int player = Integer.parseInt(line.split(":")[1].trim());
				board.getGameBoard()[row][column] = player;
				if (player == Constants.P1) {
					placeChecker(gameParameters.getPlayer1Color(), row, column);
				} else if (player == Constants.P2) {
					placeChecker(gameParameters.getPlayer2Color(), row, column);
				}
				board.setTurn(board.getTurn() + 1);
			}
			Board.printBoard(board.getGameBoard());
			turnMessage.setText("Turn: " + board.getTurn());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException | NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}


	public static void exportToGif() {
		String gifName = JOptionPane.showInputDialog("Please type the exported \".gif\" file name:",
				"simulation.gif");

		BufferedImage bi = new BufferedImage(
				frameMainWindow.getSize().width,
				frameMainWindow.getSize().height,
				BufferedImage.TYPE_INT_ARGB
		);
		Graphics g = bi.createGraphics();
		frameMainWindow.paint(g);
		g.dispose();
		try {
			ImageIO.write(bi, "gif", new File(gifName));
			System.out.println("Exported .gif file!");
		} catch (Exception ex) {
			System.err.println("Error exporting .gif file!");
			System.err.flush();
		}
	}


	// This is the main Connect-4 board.
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();

		ImageIcon imageBoard = null;
		if (gameParameters.getCheckersInARow() == CONNECT_4_CHECKERS_IN_A_ROW) {
			layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT));
			layeredGameBoard.setBorder(BorderFactory.createTitledBorder(CONNECT_4_TITLE));
			imageBoard = new ImageIcon(ResourceLoader.load(CONNECT_4_BOARD_IMG_PATH));
		} else if (gameParameters.getCheckersInARow() == CONNECT_5_CHECKERS_IN_A_ROW) {
			layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_CONNECT_5_WIDTH, DEFAULT_CONNECT_5_HEIGHT));
			layeredGameBoard.setBorder(BorderFactory.createTitledBorder(CONNECT_5_TITLE));
			imageBoard = new ImageIcon(ResourceLoader.load(CONNECT_5_BOARD_IMG_PATH));
		}

		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, 0, 1);

		return layeredGameBoard;
	}

	private static void undo() {
		if (!undoBoards.isEmpty()) {
			// This is the "undo" implementation for "Human Vs Human" mode.
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN) {
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

			// This is the "undo" implementation for "Human Vs AI" mode.
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI) {
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
				} catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}

			if (undoBoards.isEmpty()) {
				undoItem.setEnabled(false);
				undoButton.setEnabled(false);
			}

			redoItem.setEnabled(true);
			redoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(true);

			System.out.println("Turn: " + board.getTurn());
			Board.printBoard(board.getGameBoard());
		}
	}

	private static void redo() {
		if (!redoBoards.isEmpty()) {
			// This is the "redo" implementation for "Human Vs Human" mode.
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN) {
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

			// This is the "redo" implementation for "Human Vs AI" mode.
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI) {
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
				} catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
					System.err.println("There is no move to redo!");
					System.err.flush();
				}
			}

			if (redoBoards.isEmpty()) {
				redoItem.setEnabled(false);
				redoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);
			}

			undoItem.setEnabled(true);
			undoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(true);

			System.out.println("Turn: " + board.getTurn());
			Board.printBoard(board.getGameBoard());
		}
	}

	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
			String keyText = KeyEvent.getKeyText(e.getKeyCode());

			for (int i = 0; i < gameParameters.getNumOfColumns(); i++) {
				if (keyText.equals(i + 1 + "")) {
					undoBoards.push(new Board(board));
					makeMove(i);

					if (!board.isOverflow()) {
						boolean isGameOver = game();
						if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI && !isGameOver) {
							aiMove(ai);
						}
					}
					break;
				}
			}
			if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Z)) {
				undo();
			} else if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Y)) {
				redo();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		}
	};

	// To be called when the game starts for the first time
	// or a new game starts.
	public static void createNewGame() {
		gameParameters = new GameParameters(newGameParameters);

		NUM_OF_ROWS = gameParameters.getNumOfRows();
		NUM_OF_COLUMNS = gameParameters.getNumOfColumns();
		CHECKERS_IN_A_ROW = gameParameters.getCheckersInARow();

		buttons = new JButton[NUM_OF_COLUMNS];
		for (int i = 0; i < NUM_OF_COLUMNS; i++) {
			buttons[i] = new JButton(i + 1 + "");
			buttons[i].setFocusable(false);
		}

		configureGuiStyle();

		if (gameParameters.getGameMode() != GameMode.AI_VS_AI) {
			setAllButtonsEnabled(true);
		}

		board = new Board(gameParameters.getNumOfRows(), gameParameters.getNumOfColumns(), gameParameters.getCheckersInARow());

		undoBoards.clear();
		undoCheckerLabels.clear();

		redoBoards.clear();
		redoCheckerLabels.clear();

		if (frameMainWindow != null) frameMainWindow.dispose();
		if (gameParameters.getCheckersInARow() == CONNECT_4_CHECKERS_IN_A_ROW) {
			frameMainWindow = new JFrame("Minimax Connect-4");
			// make the main window appear on the center
			centerWindow(frameMainWindow, DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT);
		} else if (gameParameters.getCheckersInARow() == CONNECT_5_CHECKERS_IN_A_ROW) {
			frameMainWindow = new JFrame("Minimax Connect-5");
			// make the main window appear on the center
			centerWindow(frameMainWindow, DEFAULT_CONNECT_5_WIDTH, DEFAULT_CONNECT_5_HEIGHT);
		}
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

		// show window
		frameMainWindow.pack();
		// Makes the board visible before adding menus.
		// frameMainWindow.setVisible(true);

		// Add the turn label.
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		frameMainWindow.add(tools, BorderLayout.PAGE_END);
		turnMessage = new JLabel("Turn: " + board.getTurn());
		tools.add(turnMessage);

		undoButton = new JButton("<<");
		JButton pauseButton = new JButton("Pause");
		JButton startButton = new JButton("Resume");
		redoButton = new JButton(">>");
		JButton resetButton = new JButton("Reset");

		undoButton.setEnabled(false);
		if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(false);
		redoButton.setEnabled(false);
		if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);

		undoButton.addActionListener(e -> {
			if (!pause) {
				undo();
			}
		});

		pauseButton.addActionListener(e -> {
			if (!pause) {
				setAllButtonsEnabled(false);
				frameMainWindow.removeKeyListener(frameMainWindow.getKeyListeners()[0]);
				pause = true;
				undoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(false);
				pauseButton.setEnabled(false);
				redoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);
				startButton.setEnabled(true);
				resetButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) resetButton.setVisible(false);
			}
		});

		redoButton.addActionListener(e -> {
			if (!pause) {
				redo();
			}
		});

		startButton.addActionListener(e -> {
			if (pause) {
				setAllButtonsEnabled(true);

				if (frameMainWindow.getKeyListeners().length == 0) {
					frameMainWindow.addKeyListener(gameKeyListener);
				}

				pause = false;
				if (undoBoards.isEmpty()) {
					undoButton.setEnabled(false);
					if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(false);
				} else {
					undoButton.setEnabled(true);
					if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(true);
				}
				pauseButton.setEnabled(true);
				if (redoBoards.isEmpty()) {
					redoButton.setEnabled(false);
					if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);
				} else {
					redoButton.setEnabled(true);
					if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(true);
				}
				startButton.setEnabled(false);
				resetButton.setEnabled(true);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) resetButton.setVisible(true);
			}
		});

		resetButton.addActionListener(e -> {
			if (!pause) {
				setAllButtonsEnabled(false);
				for (KeyListener keyListener : frameMainWindow.getKeyListeners()) {
					frameMainWindow.removeKeyListener(keyListener);
				}
				pause = false;
				undoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(false);
				pauseButton.setEnabled(true);
				redoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);
				startButton.setEnabled(false);
				createNewGame();
			}
		});

		undoButton.setFocusable(false);
		pauseButton.setFocusable(false);
		redoButton.setFocusable(false);
		startButton.setFocusable(false);
		resetButton.setFocusable(false);

		startButton.setEnabled(false);

		tools.setLayout(new FlowLayout(FlowLayout.CENTER));
		tools.add(new JLabel(" "));
		tools.add(undoButton);
		tools.add(new JLabel(" "));
		tools.add(pauseButton);
		tools.add(new JLabel(" "));
		tools.add(startButton);
		tools.add(new JLabel(" "));
		tools.add(redoButton);
		tools.add(new JLabel(" "));
		tools.add(resetButton);

		addMenus();

		System.out.println("Turn: " + board.getTurn());
		Board.printBoard(board.getGameBoard());

		if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI) {
			if (gameParameters.getAi1Type() == AiType.MINIMAX_AI) {
				ai = new MinimaxAlphaBetaPruningAI(gameParameters.getAi1MaxDepth(), Constants.P2);
			} else if (gameParameters.getAi1Type() == AiType.RANDOM_AI) {
				ai = new RandomChoiceAI(Constants.P2);
			}
		} else if (gameParameters.getGameMode() == GameMode.AI_VS_AI) {
			setAllButtonsEnabled(false);
			playAiVsAi();
		}

	}

	public static void playAiVsAi() {
		AI ai1;
		AI ai2;
		if (gameParameters.getAi1Type() == AiType.MINIMAX_AI) {
			ai1 = new MinimaxAI(gameParameters.getAi1MaxDepth(), Constants.P1);
		} else {
			ai1 = new RandomChoiceAI(Constants.P1);
		}
		if (gameParameters.getAi2Type() == AiType.MINIMAX_AI) {
			ai2 = new MinimaxAlphaBetaPruningAI(gameParameters.getAi2MaxDepth(), Constants.P2);
		} else {
			ai2 = new RandomChoiceAI(Constants.P2);
		}

		while (!board.isGameOver()) {
			aiMove(ai1);

			if (!board.isGameOver()) {
				aiMove(ai2);
			}
		}
	}

	private static void configureGuiStyle() {
		try {
			if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM_STYLE) {
				// Option 1
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (gameParameters.getGuiStyle() == GuiStyle.CROSS_PLATFORM_STYLE) {
				// Option 2
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) {
				// Option 3
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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

	// It centers the window on screen.
	public static void centerWindow(Window frame, int width, int height) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
		int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
		frame.setLocation(x, y);
	}

	// It finds which player plays next and makes a move on the board.
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

	// It places a checker on the board.
	public static void placeChecker(Color color, int row, int col) {
		String colorString = String.valueOf(color).charAt(0) + String.valueOf(color).toLowerCase().substring(1);
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + colorString + ".png"));

		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);

		undoCheckerLabels.push(checkerLabel);

		try {
			if (gameParameters.getGameMode() == GameMode.AI_VS_AI) {
				Thread.sleep(Constants.AI_MOVE_MILLISECONDS);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Gets called after makeMove(int, col) is called.
	public static boolean game() {
		turnMessage.setText("Turn: " + board.getTurn());

		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getColumn();
		int currentPlayer = board.getLastPlayer();

		if (currentPlayer == Constants.P1) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(gameParameters.getPlayer1Color(), row, col);
		}

		if (currentPlayer == Constants.P2) {
			// It places a checker in the corresponding [row][col] of the GUI.
			placeChecker(gameParameters.getPlayer2Color(), row, col);
		}

		System.out.println("Turn: " + board.getTurn());
		Board.printBoard(board.getGameBoard());

		boolean isGameOver = board.checkForGameOver();
		if (isGameOver) {
			gameOver();
		} else {
			undoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) undoButton.setVisible(true);
			undoItem.setEnabled(true);
		}

		redoBoards.clear();
		redoCheckerLabels.clear();
		redoButton.setEnabled(false);
		if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS_STYLE) redoButton.setVisible(false);
		redoItem.setEnabled(false);

		return isGameOver;
	}

	// Gets called after the human player makes a move. It makes a Minimax or Random AI move.
	public static void aiMove(AI ai) {
		Move aiMove = ai.getNextMove(board);
		board.makeMove(aiMove.getColumn(), ai.getAiPlayer());
		game();
	}

	public static void setAllButtonsEnabled(boolean b) {
		if (b) {

			for (int i = 0; i < buttons.length; i++) {
				JButton button = buttons[i];
				int column = i;

				if (button.getActionListeners().length == 0) {
					button.addActionListener(e -> {
						undoBoards.push(new Board(board));
						makeMove(column);

						if (!board.isOverflow()) {
							boolean isGameOver = game();
							if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI && !isGameOver) {
								aiMove(ai);
							}
						}
						frameMainWindow.requestFocusInWindow();
					});
				}
			}

		} else {

			for (JButton button : buttons) {
				for (ActionListener actionListener : button.getActionListeners()) {
					button.removeActionListener(actionListener);
				}
			}

		}
	}

	/**
	 * It returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * It calls the "actionListener" function, when a click on a button is made.
	 */
	public static Component createContentComponents() {

		// Create a panel to set up the board buttons.
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, NUM_OF_COLUMNS, NUM_OF_ROWS, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

		for (JButton button : buttons) {
			panelBoardNumbers.add(button);
		}

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

	// It gets called only of the game is over.
	// We can check if the game is over by calling the method "checkGameOver()"
	// of the class "Board".
	public static void gameOver() {
		board.setGameOver(true);

		int choice = 0;
		if (board.getWinner() == Constants.P1) {
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"You win! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 1 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (gameParameters.getGameMode() == GameMode.AI_VS_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Minimax AI 1 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
		} else if (board.getWinner() == Constants.P2) {
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Computer AI wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(null,
						"Player 2 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
			else if (gameParameters.getGameMode() == GameMode.AI_VS_AI)
				choice = JOptionPane.showConfirmDialog(null,
						"Minimax AI 2 wins! Start a new game?",
						"Game Over", JOptionPane.YES_NO_OPTION);
		} else if (board.checkForDraw()) {
			choice = JOptionPane.showConfirmDialog(null,
					"It's a draw! Start a new game?",
					"Game Over", JOptionPane.YES_NO_OPTION);
		}

		// Disable buttons
		setAllButtonsEnabled(false);

		// Remove key listener
		for (KeyListener keyListener : frameMainWindow.getKeyListeners()) {
			frameMainWindow.removeKeyListener(keyListener);
		}

		if (choice == JOptionPane.YES_OPTION) {
			createNewGame();
		}

	}

	public static void main(String[] args) {
		// GUI gui = new GUI();

		// These are the default values.
		// Feel free to change them, before running.
		// You can also change them later, from the GUI window.
		/*
		gameParameters.guiStyle = Constants.SystemStyle;
		// gameParameters.gameMode = Constants.HumanVsAi;
 		// gameParameters.gameMode = Constants.HumanVsHuman;
		gameParameters.gameMode = Constants.AiVsAi;
		gameParameters.maxDepth1 = 4;
		gameParameters.maxDepth2 = 4;
		gameParameters.player1Color = Constants.RED;
		gameParameters.player2Color = Constants.YELLOW;
		*/

		createNewGame();
	}

}
