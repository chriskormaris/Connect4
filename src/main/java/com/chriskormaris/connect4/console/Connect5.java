package com.chriskormaris.connect4.console;


import com.chriskormaris.connect4.api.ai.AI;
import com.chriskormaris.connect4.api.ai.MinimaxAlphaBetaPruningAI;
import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import com.chriskormaris.connect4.api.util.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Connect5 {

	public static void main(String[] args) {

		int numOfRows = Constants.CONNECT_5_NUM_OF_ROWS;
		int numOfColumns = Constants.CONNECT_5_NUM_OF_COLUMNS;
		int checkersInARow = Constants.CONNECT_5_CHECKERS_IN_A_ROW;

		StringBuilder validNumbers = new StringBuilder();
		for (int i = 0; i < numOfColumns; i++) {
			if (i < numOfColumns - 2) {
				validNumbers.append(i + 1).append(", ");
			} else if (i == numOfColumns - 2) {
				validNumbers.append(i + 1).append(" or ");
			} else if (i == numOfColumns - 1) {
				validNumbers.append(i + 1);
			}
		}

		// We create the AI computer player "O" and the Connect-N board.
		// The "maxDepth" for the Minimax algorithm is set to 3.
		// Feel free to change the values.
		// The bigger the value of "maxDepth" is, the more difficult the game is.
		int XColumnPosition;
		AI ai = new MinimaxAlphaBetaPruningAI(5, Constants.P2);
		Board connect5Board = new Board(numOfRows, numOfColumns, checkersInARow);

		// Uncomment this, for "O" to play first
		// board.setLastLetterPlayed(Board.X);

		System.out.println("Minimax Connect-" + checkersInARow + "!\n");
		System.out.println("\n*****************************");
		Board.printBoard(connect5Board.getGameBoard());
		System.out.println();

		Scanner in = new Scanner(System.in);
		// While the game has not finished
		while (!connect5Board.checkForGameOver()) {
			switch (connect5Board.getLastPlayer()) {

				// If "O" played last, then "X" plays now.
				// "X" is the user-player
				case Constants.P2:
					System.out.print("Human player moves.");
					try {
						do {
							System.out.print("\nGive column (1-" + numOfColumns + "): ");
							XColumnPosition = in.nextInt();
						} while (connect5Board.checkFullColumn(XColumnPosition - 1));
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.err.println("\nValid numbers are: " + validNumbers + ".\n");
						break;
					} catch (InputMismatchException ex) {
						System.err.println("\nInput an integer number.");
						System.err.println("\nValid numbers are: " + validNumbers + ".\n");
						break;
					}
					connect5Board.makeMove(XColumnPosition - 1, Constants.P1);
					System.out.println();
					break;

				// If "X" played last, then "O" plays now.
				// "O" is the AI computer
				case Constants.P1:
					System.out.println("AI player moves.");

					// Make Minimax move.
					Move aiMove = ai.getNextMove(connect5Board);

					connect5Board.makeMove(aiMove.getColumn(), Constants.P2);
					System.out.println();
					break;

				default:
					break;
			}
			System.out.println("Turn: " + connect5Board.getTurn());
			Board.printBoard(connect5Board.getGameBoard());
		}
		in.close();

		System.out.println();

		if (connect5Board.getWinner() == Constants.P1) {
			System.out.println("Human player wins!");
		} else if (connect5Board.getWinner() == Constants.P2) {
			System.out.println("AI player wins!");
		} else {
			System.out.println("It's a draw!");
		}

		System.out.println("Game over.");
	}

}
