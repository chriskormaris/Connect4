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
import com.chriskormaris.connect4.gui.frame.InsertCheckerFrame;
import com.chriskormaris.connect4.gui.frame.SettingsFrame;
import com.chriskormaris.connect4.gui.util.GameParameters;
import com.chriskormaris.connect4.gui.util.GuiConstants;
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


public class GUI extends JFrame {

	public GameParameters gameParameters;
	GameParameters newGameParameters;


	public Board board;


	JPanel panelMain;

	JLayeredPane layeredGameBoard;

	JButton[] buttons;

	public JLabel turnMessage;

	AI ai;

	// Player 1 symbol: X. Plays first.
	// Player 2 symbol: O.

	// These Stack objects are used for the "Undo" and "Redo" functionalities.
	Stack<Board> undoBoards;
	Stack<Board> redoBoards;

	JMenuItem undoItem;
	JMenuItem redoItem;

	JToolBar tools;
	JButton undoButton;
	JButton pauseButton;
	JButton startButton;
	JButton redoButton;
	JButton resetButton;

	boolean pause;

	private final KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!board.checkForGameOver()) {
				String keyText = KeyEvent.getKeyText(e.getKeyCode());

				for (int i = 0; i < gameParameters.getNumOfColumns(); i++) {
					if (keyText.equals(String.valueOf(i + 1))) {
						buttonClick(i);
						break;
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	};

	private final KeyListener undoRedoKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_Z) {
				undo();
			} else if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_Y) {
				redo();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	};

	public GUI() {
		this(GuiConstants.CONNECT_4_TITLE);
	}

	public GUI(String title) {
		super(title);

		newGameParameters = new GameParameters();
		gameParameters = new GameParameters(newGameParameters);

		board = new Board();

		undoBoards = new Stack<>();
		redoBoards = new Stack<>();

		buttons = new JButton[board.getNumOfColumns()];
		for (int i = 0; i < board.getNumOfColumns(); i++) {
			buttons[i] = new JButton(String.valueOf(i + 1));
			buttons[i].setFocusable(false);
		}

		configureGuiStyle();

		centerWindow(DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT);

		Component compMainWindowContents = createContentComponents();
		super.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		super.setFocusable(true);

		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Makes the board visible before adding menus.
		// super.setVisible(true);

		// show window
		super.pack();

		undoButton = new JButton("<<");
		pauseButton = new JButton("Pause");
		startButton = new JButton("Resume");
		redoButton = new JButton(">>");
		resetButton = new JButton("Reset");

		undoButton.setEnabled(false);
		redoButton.setEnabled(false);

		if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
			undoButton.setVisible(false);
			redoButton.setVisible(false);
		}

		undoButton.addActionListener(e -> {
			if (!pause) {
				undo();
			}
		});

		pauseButton.addActionListener(e -> {
			if (!pause) {
				setAllButtonsEnabled(false);
				super.removeKeyListener(gameKeyListener);
				pause = true;
				undoButton.setEnabled(false);
				pauseButton.setEnabled(false);
				startButton.setEnabled(true);
				redoButton.setEnabled(false);
				resetButton.setEnabled(false);

				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					undoButton.setVisible(false);
					pauseButton.setVisible(false);
					startButton.setVisible(true);
					redoButton.setVisible(false);
					resetButton.setVisible(false);
				}
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

				super.addKeyListener(gameKeyListener);

				pause = false;
				if (undoBoards.isEmpty()) {
					undoButton.setEnabled(false);
					if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
						undoButton.setVisible(false);
					}
				} else {
					undoButton.setEnabled(true);
					if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
						undoButton.setVisible(true);
					}
				}
				if (redoBoards.isEmpty()) {
					redoButton.setEnabled(false);
					if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
						redoButton.setVisible(false);
					}
				} else {
					redoButton.setEnabled(true);
					if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
						redoButton.setVisible(true);
					}
				}
				pauseButton.setEnabled(true);
				startButton.setEnabled(false);
				resetButton.setEnabled(true);
				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					pauseButton.setVisible(true);
					startButton.setVisible(false);
					resetButton.setVisible(true);
				}
			}
		});

		resetButton.addActionListener(e -> {
			if (!pause) {
				setAllButtonsEnabled(false);
				super.removeKeyListener(gameKeyListener);
				pause = false;
				undoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					undoButton.setVisible(false);
				}
				pauseButton.setEnabled(true);
				redoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					redoButton.setVisible(false);
				}
				startButton.setEnabled(false);
				startNewGame();
			}
		});

		undoButton.setFocusable(false);
		pauseButton.setFocusable(false);
		redoButton.setFocusable(false);
		startButton.setFocusable(false);
		resetButton.setFocusable(false);

		startButton.setEnabled(false);

		tools = new JToolBar();
		tools.setFloatable(false);
		super.add(tools, BorderLayout.PAGE_END);

		turnMessage = new JLabel("Turn: " + board.getTurn());

		tools.setLayout(new FlowLayout(FlowLayout.CENTER));
		tools.add(turnMessage);
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
	}

	// To be called when the game starts for the first time, or a new game starts.
	public void startNewGame() {
		gameParameters = new GameParameters(newGameParameters);

		if (gameParameters.getCheckersInARow() == CONNECT_4_CHECKERS_IN_A_ROW) {
			super.setTitle(CONNECT_4_TITLE);
		} else if (gameParameters.getCheckersInARow() == CONNECT_5_CHECKERS_IN_A_ROW) {
			super.setTitle(CONNECT_5_TITLE);
		}

		super.getContentPane().removeAll();

		configureGuiStyle();

		restoreDefaultValues();

		Component compMainWindowContents = createContentComponents();
		super.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		super.add(tools, BorderLayout.PAGE_END);
		turnMessage.setText("Turn: " + board.getTurn());

		super.getContentPane().revalidate();
		super.getContentPane().repaint();
		super.revalidate();
		super.repaint();

		System.out.println("Turn: " + board.getTurn());
		System.out.println(board);

		if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI) {
			initializeAi();
		} else if (gameParameters.getGameMode() == GameMode.AI_VS_AI) {
			setAllButtonsEnabled(false);
			playAiVsAi();
		}
	}

	private void restoreDefaultValues() {
		board = new Board(
				gameParameters.getNumOfRows(),
				gameParameters.getNumOfColumns(),
				gameParameters.getCheckersInARow()
		);

		buttons = new JButton[board.getNumOfColumns()];
		for (int i = 0; i < board.getNumOfColumns(); i++) {
			buttons[i] = new JButton(String.valueOf(i + 1));
			buttons[i].setFocusable(false);
		}

		if (gameParameters.getGameMode() != GameMode.AI_VS_AI) {
			setAllButtonsEnabled(true);
		}

		undoBoards.clear();
		redoBoards.clear();

		pauseButton.setVisible(true);
		if (gameParameters.getGuiStyle() != GuiStyle.SYSTEM) {
			undoButton.setVisible(true);
			startButton.setVisible(true);
			redoButton.setVisible(true);
		} else {
			undoButton.setVisible(false);
			startButton.setVisible(false);
			redoButton.setVisible(false);
		}
		resetButton.setVisible(true);
	}

	private void initializeAi() {
		if (gameParameters.getAi1Type() == AiType.MINIMAX_AI) {
			ai = new MinimaxAI(gameParameters.getAi1MaxDepth(), Constants.P2);
		} else if (gameParameters.getAi1Type() == AiType.MINIMAX_ALPHA_BETA_PRUNING_AI) {
			ai = new MinimaxAlphaBetaPruningAI(gameParameters.getAi1MaxDepth(), Constants.P2);
		} else if (gameParameters.getAi1Type() == AiType.RANDOM_AI) {
			ai = new RandomChoiceAI(Constants.P2, gameParameters.getNumOfColumns());
		}
	}

	// Add the menu bars and items to the window.
	private void addMenus() {
		// Add the menu bar.
		// Menu bars and items
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		redoItem = new JMenuItem("Redo    Ctrl+Y");
		JMenuItem saveGameItem = new JMenuItem("Save Game");
		JMenuItem restoreSavedGameItem = new JMenuItem("Restore Saved Game");
		JMenuItem insertCheckerItem = new JMenuItem("Insert Checker");
		JMenuItem exportToGifItem = new JMenuItem("Export to .gif");
		JMenuItem settingsItem = new JMenuItem("Settings");
		JMenuItem exitItem = new JMenuItem("Exit");

		JMenu helpMenu = new JMenu("Help");
		JMenuItem howToPlayItem = new JMenuItem("How to Play");
		JMenuItem aboutItem = new JMenuItem("About");

		undoItem.setEnabled(false);
		redoItem.setEnabled(false);

		newGameItem.addActionListener(e -> startNewGame());

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

		insertCheckerItem.addActionListener(e -> {
			InsertCheckerFrame insertCheckerFrame = new InsertCheckerFrame(this);
			insertCheckerFrame.setVisible(true);
		});

		exportToGifItem.addActionListener(e -> exportToGif());

		settingsItem.addActionListener(e -> {
			SettingsFrame settings = new SettingsFrame(this, newGameParameters);
			settings.setVisible(true);
		});

		exitItem.addActionListener(e -> System.exit(0));

		howToPlayItem.addActionListener(e -> JOptionPane.showMessageDialog(
				this,
				"Click on the buttons or press 1-" + board.getNumOfColumns() + " on your keyboard to insert a new checker."
						+ "\nTo win you must place " + board.getCheckersInARow() + " checkers in an row, horizontally, vertically or diagonally.",
				"How to Play",
				JOptionPane.INFORMATION_MESSAGE
		));

		aboutItem.addActionListener(e -> {
			JLabel label = new JLabel(
					"<html>&copy; Created by: Christos Kormaris, Athens 2016<br>"
							+ "Version " + GuiConstants.VERSION + "</html>"
			);
			JOptionPane.showMessageDialog(this, label, "About", JOptionPane.INFORMATION_MESSAGE);
		});

		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(redoItem);
		fileMenu.add(saveGameItem);
		fileMenu.add(restoreSavedGameItem);
		fileMenu.add(insertCheckerItem);
		fileMenu.add(exportToGifItem);
		fileMenu.add(settingsItem);
		fileMenu.add(exitItem);

		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		super.setJMenuBar(menuBar);
		// Make the board visible after adding the menus.
		super.setVisible(true);

		super.addKeyListener(gameKeyListener);
		super.addKeyListener(undoRedoKeyListener);
	}

	public void saveGame() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("grid.txt"));
			for (int i = 0; i < board.getNumOfRows(); i++) {
				for (int j = 0; j < board.getNumOfColumns(); j++) {
					if (board.getGameBoard()[i][j] != Constants.EMPTY) {
						bw.write(i + String.valueOf(j) + ":" + board.getGameBoard()[i][j] + "\n");
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


	public void restoreSavedGame() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("grid.txt"));
			String line;

			startNewGame();

			while ((line = br.readLine()) != null) {
				int row = Integer.parseInt(String.valueOf(line.charAt(0)));
				int column = Integer.parseInt(String.valueOf(line.charAt(1)));
				int player = Integer.parseInt(line.split(":")[1].trim());
				board.getGameBoard()[row][column] = player;
				if (player == Constants.P1) {
					placeChecker(gameParameters.getPlayer1Color(), row, column);
				} else if (player == Constants.P2) {
					placeChecker(gameParameters.getPlayer2Color(), row, column);
				}
				board.setTurn(board.getTurn() + 1);
			}
			System.out.println(board);
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


	public void exportToGif() {
		String gifName = JOptionPane.showInputDialog(
				"Please type the exported \".gif\" file name:",
				"simulation.gif"
		);

		BufferedImage bufferedImage = new BufferedImage(
				super.getSize().width,
				super.getSize().height,
				BufferedImage.TYPE_INT_ARGB
		);
		Graphics graphics = bufferedImage.createGraphics();
		super.paint(graphics);
		graphics.dispose();
		try {
			ImageIO.write(bufferedImage, "gif", new File(gifName));
			System.out.println("Exported .gif file!");
		} catch (Exception ex) {
			System.err.println("Error exporting .gif file!");
			System.err.flush();
		}
	}


	// This is the main Connect-4 board.
	public JLayeredPane initializeLayeredBoard() {
		layeredGameBoard = new JLayeredPane();

		ImageIcon imageBoard = null;
		if (gameParameters.getCheckersInARow() == CONNECT_4_CHECKERS_IN_A_ROW) {
			layeredGameBoard.setBorder(BorderFactory.createTitledBorder(CONNECT_4_TITLE));
			imageBoard = new ImageIcon(ResourceLoader.load(CONNECT_4_BOARD_IMG_PATH));
			super.setSize(new Dimension(DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT));
		} else if (gameParameters.getCheckersInARow() == CONNECT_5_CHECKERS_IN_A_ROW) {
			layeredGameBoard.setBorder(BorderFactory.createTitledBorder(CONNECT_5_TITLE));
			imageBoard = new ImageIcon(ResourceLoader.load(CONNECT_5_BOARD_IMG_PATH));
			super.setSize(new Dimension(DEFAULT_CONNECT_5_WIDTH, DEFAULT_CONNECT_5_HEIGHT));
		}

		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, 0, 1);

		return layeredGameBoard;
	}

	private void undo() {
		if (!undoBoards.isEmpty()) {
			try {
				board.setGameOver(false);

				if (gameParameters.getGameMode() != GameMode.AI_VS_AI) {
                    setAllButtonsEnabled(true);
				}

				redoBoards.push(new Board(board));

				board = undoBoards.pop();

				updateLayeredBoard();

				turnMessage.setText("Turn: " + board.getTurn());
				super.paint(super.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}

			if (undoBoards.isEmpty()) {
				undoItem.setEnabled(false);
				undoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					undoButton.setVisible(false);
				}
			}

			redoItem.setEnabled(true);
			redoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
				redoButton.setVisible(true);
			}

			System.out.println("Turn: " + board.getTurn());
			System.out.println(board);
		}
	}

	private void redo() {
		if (!redoBoards.isEmpty()) {
			// This is the "redo" implementation for "Human vs Human" mode.
			try {
				board.setGameOver(false);

				if (gameParameters.getGameMode() != GameMode.AI_VS_AI) {
					setAllButtonsEnabled(true);
				}

				undoBoards.push(new Board(board));

				board = new Board(redoBoards.pop());

				updateLayeredBoard();

				turnMessage.setText("Turn: " + board.getTurn());
				super.paint(super.getGraphics());

				boolean isGameOver = board.checkForGameOver();
				if (isGameOver) {
					gameOver();
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("There is no move to redo!");
				System.err.flush();
			}

			if (redoBoards.isEmpty()) {
				redoItem.setEnabled(false);
				redoButton.setEnabled(false);
				if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
					redoButton.setVisible(false);
				}
			}

			undoItem.setEnabled(true);
			undoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
				undoButton.setVisible(true);
			}

			System.out.println("Turn: " + board.getTurn());
			System.out.println(board);
		}
	}

	private void updateLayeredBoard() {
		Component[] components = layeredGameBoard.getComponentsInLayer(0);
		for (int i = 0; i < components.length; i++) {
			// Leave out the last component, which is the board ImageIcon.
			if (i < components.length - 1) {
				layeredGameBoard.remove(components[i]);
			}
		}
		for (int i = 0; i < board.getNumOfRows(); i++) {
			for (int j = 0; j < board.getNumOfColumns(); j++) {
				if (board.getGameBoard()[i][j] == Constants.P1) {
					placeChecker(gameParameters.getPlayer1Color(), i, j);
				} else if (board.getGameBoard()[i][j] == Constants.P2) {
					placeChecker(gameParameters.getPlayer2Color(), i, j);
				}
			}
		}
	}

	public void playAiVsAi() {
		AI ai1;
		if (gameParameters.getAi1Type() == AiType.MINIMAX_AI) {
			ai1 = new MinimaxAI(gameParameters.getAi1MaxDepth(), Constants.P1);
		} else if (gameParameters.getAi1Type() == AiType.MINIMAX_ALPHA_BETA_PRUNING_AI) {
			ai1 = new MinimaxAlphaBetaPruningAI(gameParameters.getAi1MaxDepth(), Constants.P1);
		} else {
			ai1 = new RandomChoiceAI(Constants.P1, gameParameters.getNumOfColumns());
		}

		AI ai2;
		if (gameParameters.getAi2Type() == AiType.MINIMAX_AI) {
			ai2 = new MinimaxAI(gameParameters.getAi2MaxDepth(), Constants.P2);
		} else if (gameParameters.getAi2Type() == AiType.MINIMAX_ALPHA_BETA_PRUNING_AI) {
			ai2 = new MinimaxAlphaBetaPruningAI(gameParameters.getAi2MaxDepth(), Constants.P2);
		} else {
			ai2 = new RandomChoiceAI(Constants.P2, gameParameters.getNumOfColumns());
		}

		while (!board.isGameOver()) {
			aiMove(ai1);

			if (!board.isGameOver()) {
				try {
					if (gameParameters.getGameMode() == GameMode.AI_VS_AI) {
						Thread.sleep(Constants.AI_MOVE_MILLISECONDS);
						super.paint(super.getGraphics());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				aiMove(ai2);
			}

			if (!board.isGameOver()) {
				try {
					if (gameParameters.getGameMode() == GameMode.AI_VS_AI) {
						Thread.sleep(Constants.AI_MOVE_MILLISECONDS);
						super.paint(super.getGraphics());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void configureGuiStyle() {
		try {
			if (gameParameters.getGuiStyle() == GuiStyle.CROSS_PLATFORM) {
				// Option 1
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
				// Option 2
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (gameParameters.getGuiStyle() == GuiStyle.NIMBUS) {
				// Option 3
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			}
		} catch (Exception ex1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}

	// It centers the window on screen.
	public void centerWindow(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (screenSize.getWidth() - super.getWidth() - width) / 2;
		int y = (int) (screenSize.getHeight() - super.getHeight() - height) / 2;
		super.setLocation(x, y);
	}

	// It finds which player plays next and makes a move on the board.
	public void makeMove(int col) {
		int previousRow = board.getLastMove().getRow();
		int previousCol = board.getLastMove().getColumn();
		int previousPlayer = board.getLastPlayer();

		if (!(gameParameters.getGameMode() == GameMode.HUMAN_VS_AI && previousPlayer == Constants.P1)) {
			undoBoards.push(new Board(board));
		}

		board.setOverflow(false);

		if (previousPlayer == Constants.P2) {
			board.makeMove(col, Constants.P1);
		} else {
			board.makeMove(col, Constants.P2);
		}

		if (board.isOverflow()) {
			board.getLastMove().setRow(previousRow);
			board.getLastMove().setColumn(previousCol);
			board.setLastPlayer(previousPlayer);

			undoBoards.pop();
		}
	}

	// It places a checker on the board.
	public void placeChecker(Color color, int row, int col) {
		String colorString = String.valueOf(color).charAt(0) + String.valueOf(color).toLowerCase().substring(1);
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + colorString + ".png"));

		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, 0, 0);
	}

	// Gets called after makeMove(int, col) is called.
	public boolean game() {
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
		System.out.println(board);

		boolean isGameOver = board.checkForGameOver();
		if (isGameOver) {
			gameOver();
		} else {
			undoButton.setEnabled(true);
			if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
				undoButton.setVisible(true);
			}
			undoItem.setEnabled(true);
		}

		redoBoards.clear();
		redoButton.setEnabled(false);
		if (gameParameters.getGuiStyle() == GuiStyle.SYSTEM) {
			redoButton.setVisible(false);
		}
		redoItem.setEnabled(false);

		return isGameOver;
	}

	// Gets called after the human player makes a move. It makes a Minimax or Random AI move.
	public void aiMove(AI ai) {
		Move aiMove = ai.getNextMove(board);
		makeMove(aiMove.getColumn());
		game();
	}

	public void setAllButtonsEnabled(boolean enabled) {
		if (enabled) {
			for (int i = 0; i < buttons.length; i++) {
				JButton button = buttons[i];
				int column = i;

				if (button.getActionListeners().length == 0) {
					button.addActionListener(e -> buttonClick(column));
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

	private void buttonClick(int column) {
		makeMove(column);

		if (!board.isOverflow()) {
			boolean isGameOver = game();
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI && !isGameOver) {
				aiMove(ai);
			}
		}
		super.requestFocusInWindow();
	}

	/**
	 * It returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * It calls the "actionListener" function, when a click on a button is made.
	 */
	public Component createContentComponents() {
		// Create a panel to set up the board buttons.
		JPanel panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, board.getNumOfColumns(), board.getNumOfRows(), 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

		for (JButton button : buttons) {
			panelBoardNumbers.add(button);
		}

		// main Connect-4 board creation
		layeredGameBoard = initializeLayeredBoard();

		// panel creation to store all the elements of the board
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// add button and main board components to panelMain
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		super.setResizable(false);
		return panelMain;
	}

	// It gets called only of the game is over.
	// We can check if the game is over by calling the method "checkGameOver()"
	// of the class "Board".
	public void gameOver() {
		board.setGameOver(true);

		int choice = 0;
		if (board.getWinner() == Constants.P1) {
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI)
				choice = JOptionPane.showConfirmDialog(
						this,
						"You win! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(
						this,
						"Player 1 wins! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
			else if (gameParameters.getGameMode() == GameMode.AI_VS_AI)
				choice = JOptionPane.showConfirmDialog(
						this,
						"Minimax AI 1 wins! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
		} else if (board.getWinner() == Constants.P2) {
			if (gameParameters.getGameMode() == GameMode.HUMAN_VS_AI)
				choice = JOptionPane.showConfirmDialog(
						this,
						"Computer AI wins! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
			else if (gameParameters.getGameMode() == GameMode.HUMAN_VS_HUMAN)
				choice = JOptionPane.showConfirmDialog(
						this,
						"Player 2 wins! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
			else if (gameParameters.getGameMode() == GameMode.AI_VS_AI)
				choice = JOptionPane.showConfirmDialog(
						this,
						"Minimax AI 2 wins! Start a new game?",
						"Game Over",
						JOptionPane.YES_NO_OPTION
				);
		} else if (board.checkForDraw()) {
			choice = JOptionPane.showConfirmDialog(
					this,
					"It's a draw! Start a new game?",
					"Game Over",
					JOptionPane.YES_NO_OPTION
			);
		}

		// Disable buttons
		setAllButtonsEnabled(false);

		if (choice == JOptionPane.YES_OPTION) {
			startNewGame();
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI();

		// Here, you can change the game parameters, before running the application.
		// You can also change them later, from the Settings window.
        /*
		gui.newGameParameters = new GameParameters();
		gui.newGameParameters.setGuiStyle(GuiStyle.SYSTEM_STYLE);
		gui.newGameParameters.setGameMode(GameMode.HUMAN_VS_AI);
 		gui.newGameParameters.setGameMode(GameMode.HUMAN_VS_HUMAN);
		gui.newGameParameters.setGameMode(GameMode.AI_VS_AI);
		gui.newGameParameters.setAi1MaxDepth(4);
		gui.newGameParameters.setAi1MaxDepth(4);
		gui.newGameParameters.setPlayer1Color(Color.RED);
		gui.newGameParameters.setPlayer2Color(Color.YELLOW);
		*/

		gui.startNewGame();
	}

}
