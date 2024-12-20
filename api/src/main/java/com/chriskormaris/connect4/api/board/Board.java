package com.chriskormaris.connect4.api.board;


import com.chriskormaris.connect4.api.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class Board {

	private int[][] gameBoard;

	private int numOfRows;
	private int numOfColumns;
	private int checkersInARow;

	// Immediate move that led to this board.
	private Move lastMove;

	// A variable to store the symbol of the player who played last,
	// leading to the current board state.
	private int lastPlayer;

	private int winner;
	private boolean overflow;
	private boolean gameOver;
	private int turn;


	// Default constructor
	public Board() {
		this(Constants.CONNECT_4_NUM_OF_ROWS, Constants.CONNECT_4_NUM_OF_COLUMNS, Constants.CONNECT_4_CHECKERS_IN_A_ROW);
	}

	public Board(int numOfRows, int numOfColumns, int checkersInARow) {
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;

		this.checkersInARow = checkersInARow;

		this.lastMove = new Move();
		this.lastPlayer = Constants.P2;
		this.winner = Constants.EMPTY;
		this.overflow = false;
		this.gameOver = false;
		this.turn = 1;

		this.gameBoard = new int[numOfRows][numOfColumns];
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				gameBoard[i][j] = Constants.EMPTY;
			}
		}
	}


	// copy constructor
	public Board(Board board) {
		numOfRows = board.getNumOfRows();
		numOfColumns = board.getNumOfColumns();
		checkersInARow = board.getCheckersInARow();

		lastMove = board.getLastMove();
		lastPlayer = board.getLastPlayer();
		winner = board.getWinner();

		this.overflow = board.isOverflow();
		this.gameOver = board.isGameOver();
		this.turn = board.getTurn();

		int N1 = board.getGameBoard().length;
		int N2 = board.getGameBoard()[0].length;
		this.gameBoard = new int[N1][N2];
		for (int i = 0; i < N1; i++) {
			for (int j = 0; j < N2; j++) {
				this.gameBoard[i][j] = board.getGameBoard()[i][j];
			}
		}
	}

	// Makes a move based on the given column.
	// It finds automatically in which row the checker should be inserted.
	public void makeMove(int col, int player) {
		try {
			// The variable "lastMove" must be changed before the variable
			// "gameBoard[][]" because of the function "getRowPosition(col)".
			int row = getEmptyRowPosition(col);
			this.lastMove = new Move(row, col);
			this.lastPlayer = player;
			this.gameBoard[row][col] = player;
			this.turn++;
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.err.println("Column " + (col + 1) + " is full!");
			setOverflow(true);
		}
	}

	// This function is used when we want to search the whole board,
	// without getting out of borders.
	public boolean canMove(int row, int col) {
		return (row > -1) && (col > -1) && (row <= numOfRows - 1) && (col <= numOfColumns - 1);
	}

	public boolean checkFullColumn(int col) {
		return gameBoard[0][col] != Constants.EMPTY;
	}

	// It returns the position of the first empty row in a column.
	public int getEmptyRowPosition(int col) {
		int rowPosition = -1;
		for (int row = 0; row < numOfRows; row++) {
			if (gameBoard[row][col] == Constants.EMPTY) {
				rowPosition = row;
			}
		}
		return rowPosition;
	}

	// Generates the children of the state.
	// The max number of the children is "numOfColumns".
	public ArrayList<Board> getChildren(int player) {
		ArrayList<Board> children = new ArrayList<>();
		for (int col = 0; col < numOfColumns; col++) {
			if (!checkFullColumn(col)) {
				Board child = new Board(this);
				child.makeMove(col, player);
				children.add(child);
			}
		}
		return children;
	}

	/* +1 for each 2 checkers in a row by Player 1,
	 * -1 for each 2 checkers in a row by Player 2.
	 * +10 for each 3 checkers in a row by Player 1,
	 * -10 for each 3 checkers in a row by Player 2.
	 * ...
	 * +10^i for each (i+2) checkers in a row by Player 1,
	 * -10^i for each (i+2) checkers in a row by Player 2.
	 * +10^(checkersInARow - 2) if "checkersInARow" pieces in a row by Player 1 exist,
	 * -10^(checkersInARow - 2) if "checkersInARow" pieces in a row by Player 2 exist. */
	public double evaluate() {
		double player1Score = 0;
		double player2Score = 0;

		if (checkWinState()) {
			if (winner == Constants.P1) {
				return Integer.MAX_VALUE;
			} else if (winner == Constants.P2) {
                return Integer.MIN_VALUE;
			}
		}

		for (int i = 2; i < checkersInARow; i++) {
			player1Score += countNInARow(i, Constants.P1) * Math.pow(10, (double) i - 2);
			player2Score += countNInARow(i, Constants.P2) * Math.pow(10, (double) i - 2);
		}

		// If the result is 0, then it's a draw.
		return player1Score - player2Score;
	}

	/*
	 * Terminal win check.
	 * It checks whether somebody has won the game.
	 */
	public boolean checkWinState() {
		int counter = countNInARow(checkersInARow, Constants.P1);
		if (counter > 0) {
			setWinner(Constants.P1);
			return true;
		}

		counter = countNInARow(checkersInARow, Constants.P2);
		if (counter > 0) {
			setWinner(Constants.P2);
			return true;
		}

		setWinner(Constants.EMPTY);  // set nobody as the winner
		return false;
	}

	public boolean checkForGameOver() {
		// Check if there is a winner.
		if (checkWinState()) {
			return true;
		}

		return checkForDraw();
	}

	// Check for an empty cell, i.e. check to find if it is a draw.
	// The game is in a draw state, if all cells are full
	// and nobody has won the game.
	public boolean checkForDraw() {
		for (int row = 0; row < numOfRows; row++) {
			for (int col = 0; col < numOfColumns; col++) {
				if (gameBoard[row][col] == Constants.EMPTY) {
					return false;
				}
			}
		}

		return true;
	}

	// It returns the frequency of "N" checkers in a row,
	// for the given player, with "checkersInARow - N" adjacent checkers
	// of the same player or empty tiles.
	// The aim is to search for checkers in a row,
	// with a potential to form a Connect-"checkersInARow".
	public int countNInARow(int N, int player) {
		int times = 0;

		// Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, horizontally.
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				if (canMove(i, j + checkersInARow - 1)) {
					// Check for "N" consecutive checkers of the same player in a row, horizontally.
					int k = 0;
					while (k < N && gameBoard[i][j + k] == player) {
						k++;
					}
					// Check for "checkersInARow - N" consecutive checkers of the same player
					// or empty tiles in a row, horizontally.
					if (k == N) {
						while (k < checkersInARow
								&& (gameBoard[i][j + k] == player || gameBoard[i][j + k] == Constants.EMPTY)) {
							k++;
						}
						if (k == checkersInARow) times++;
					}
				}
			}
		}

		// Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, vertically.
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				if (canMove(i - checkersInARow + 1, j)) {
					// Check for "N" consecutive checkers of the same player in a row, vertically.
					int k = 0;
					while (k < N && gameBoard[i - k][j] == player) {
						k++;
					}
					// Check for "checkersInARow - N" consecutive checkers of the same player or empty tiles in a row, vertically.
					if (k == N) {
						while (k < checkersInARow
								&& (gameBoard[i - k][j] == player || gameBoard[i - k][j] == Constants.EMPTY)) {
							k++;
						}
						if (k == checkersInARow) times++;
					}
				}
			}
		}

		// Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row,
		// in descending diagonal.
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				if (canMove(i + checkersInARow - 1, j + checkersInARow - 1)) {
					// Check for "N" consecutive checkers of the same player in a row, in descending diagonal.
					int k = 0;
					while (k < N && gameBoard[i + k][j + k] == player) {
						k++;
					}
					// Check for "checkersInARow - N" consecutive checkers of the same player
					// or empty tiles in a row, in descending diagonal.
					if (k == N) {
						while (k < checkersInARow
								&& (gameBoard[i + k][j + k] == player || gameBoard[i + k][j + k] == Constants.EMPTY)) {
							k++;
						}
						if (k == checkersInARow) times++;
					}
				}
			}
		}

		// Check for "checkersInARow" consecutive checkers of the same player
		// or empty tiles in a row, in ascending diagonal.
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				if (canMove(i - checkersInARow + 1, j + checkersInARow - 1)) {
					// Check for "N" consecutive checkers of the same player in a row, in ascending diagonal.
					int k = 0;
					while (k < N && gameBoard[i - k][j + k] == player) {
						k++;
					}
					// Check for "checkersInARow - N" consecutive checkers of the same player
					// or empty tiles in a row, in ascending diagonal.
					if (k == N) {
						while (k < checkersInARow
								&& (gameBoard[i - k][j + k] == player || gameBoard[i - k][j + k] == Constants.EMPTY)) {
							k++;
						}
						if (k == checkersInARow) times++;
					}
				}
			}
		}

		return times;
	}

	private String getGameBoardAsString() {
		StringBuilder output = new StringBuilder();
		output.append("|");
		for (int j = 1; j <= numOfColumns; j++) {
			output.append(" ").append(j).append(" |");
		}
		output.append("\n");
		if (numOfColumns == Constants.CONNECT_4_NUM_OF_COLUMNS) {
			output.append(" ---------------------------\n");
		} else if (numOfColumns == Constants.CONNECT_5_NUM_OF_COLUMNS) {
			output.append(" -------------------------------\n");
		}
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				char symbol = '-';
				if (gameBoard[i][j] == Constants.P1) {
					symbol = 'X';
				} else if (gameBoard[i][j] == Constants.P2) {
					symbol = 'O';
				}
				output.append("| ").append(symbol).append(" ");
				if (j == numOfColumns - 1) {
					output.append("|\n");
				}
			}
		}
		if (numOfColumns == Constants.CONNECT_4_NUM_OF_COLUMNS) {
			output.append(" ---------------------------\n");
			output.append("*****************************\n");
		} else if (numOfColumns == Constants.CONNECT_5_NUM_OF_COLUMNS) {
			output.append(" -------------------------------\n");
			output.append("*********************************\n");
		}
		return output.toString();
	}


	@Override
	public String toString() {
		return getGameBoardAsString();
	}

}
