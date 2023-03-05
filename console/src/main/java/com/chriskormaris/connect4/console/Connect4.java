package com.chriskormaris.connect4.console;


import com.chriskormaris.connect4.api.ai.AI;
import com.chriskormaris.connect4.api.ai.MinimaxAlphaBetaPruningAI;
import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import com.chriskormaris.connect4.api.util.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Connect4 {

	public static void main(String[] args) {
		int numOfColumns = Constants.CONNECT_4_NUM_OF_COLUMNS;
		int checkersInARow = Constants.CONNECT_4_CHECKERS_IN_A_ROW;

		StringBuilder validNumbers = new StringBuilder();
		for (int i = 0; i < numOfColumns; i++) {
			if (i < numOfColumns - 2) {
				validNumbers.append(i + 1).append(", ");
			} else if (i == numOfColumns - 2) {
				validNumbers.append(i + 1).append(" or ");
			} else {
				validNumbers.append(i + 1);
			}
		}

		// We create the AI computer player "O" and the Connect-N board.
		// The "maxDepth" for the Minimax algorithm is set to 3.
		// Feel free to change the values.
		// The bigger the value of "maxDepth" is, the more difficult the game is.
		int XColumnPosition;
		AI ai = new MinimaxAlphaBetaPruningAI(5, Constants.P2);
		Board connect4Board = new Board();

		System.out.println("Minimax Connect-" + checkersInARow + "!\n");
		System.out.println("\n*****************************");
		System.out.println(connect4Board);
		System.out.println();

		Scanner in = new Scanner(System.in);
		// While the game has not finished
		while (!connect4Board.checkForGameOver()) {
			int currentPlayer = connect4Board.getLastPlayer() == Constants.P2 ? Constants.P1 : Constants.P2;
			switch (currentPlayer) {

				// "X" is the human-player.
				case Constants.P1:
					System.out.print("Human player moves.");
					try {
						do {
							System.out.print("\nGive column (1-" + numOfColumns + "): ");
							XColumnPosition = in.nextInt();
						} while (connect4Board.checkFullColumn(XColumnPosition - 1));
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.err.println("\nValid numbers are: " + validNumbers + ".\n");
						break;
					} catch (InputMismatchException ex) {
						System.err.println("\nInput an integer number.");
						System.err.println("\nValid numbers are: " + validNumbers + ".\n");
						break;
					}
					connect4Board.makeMove(XColumnPosition - 1, Constants.P1);
					System.out.println();
					break;

				// "O" is the computer AI.
				case Constants.P2:
					System.out.println("AI player moves.");

					// Make Minimax move.
					Move aiMove = ai.getNextMove(connect4Board);

					connect4Board.makeMove(aiMove.getColumn(), Constants.P2);
					System.out.println();
					break;

				default:
					break;
			}
			System.out.println("Turn: " + connect4Board.getTurn());
			System.out.println(connect4Board);
		}
		in.close();

		System.out.println();

		if (connect4Board.getWinner() == Constants.P1) {
			System.out.println("Human player wins!");
		} else if (connect4Board.getWinner() == Constants.P2) {
			System.out.println("AI player wins!");
		} else {
			System.out.println("It's a draw!");
		}

		System.out.println("Game over.");
	}

}
