package com.chriskormaris.connect4.api.board;


import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.GUI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.fail;

public class BoardTest {

	static final int NUM_OF_ROWS = GUI.gameParameters.getNumOfRows();
	static final int NUM_OF_COLUMNS = GUI.gameParameters.getNumOfColumns();

	@BeforeAll
	public static void setUp() {

	}

    /*
	@Test
	public void testBoard() {
		fail("Not yet implemented");
	}


	@Test
	public void testGetLastMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLastLetterPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGameBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWinner() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLastMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLastLetterPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGameBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWinner() {
		fail("Not yet implemented");
	}
	*/

	@Test
	public void testMakeMoveInt() {
		Board board = new Board();

		board.makeMove(0, Constants.P1);
		Board.printBoard(board.getGameBoard());

		assertEquals(Constants.P1, board.getGameBoard()[5][0], "The board was not updated correctly.");
		assertEquals(Constants.P1, board.getLastPlayer(), "The last player symbol played is not correct.");
		System.out.println("*****************************");
		System.out.println();
	}

    /*
	@Test
	public void testIsValidMoveIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidMoveInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckFullColumn() {
		fail("Not yet implemented");
	}
	*/

	@Test
	public void testGetRowPosition() {
		Board board = new Board();

		int[][] gameBoard = new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
		for (int col = 0; col < NUM_OF_COLUMNS; col++) {
			gameBoard[5][col] = Constants.P1;
		}
		board.setGameBoard(gameBoard);
		Board.printBoard(board.getGameBoard());

		for (int col = 0; col < NUM_OF_COLUMNS; col++) {
			int row = board.getEmptyRowPosition(col);
			assertEquals(4, row, "The row result is not the expected one.");
		}
		System.out.println("*****************************");
		System.out.println();
	}

    /*
	@Test
	public void testGetChildren() {
		fail("Not yet implemented");
	}

	@Test
	public void testEvaluate() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckWinState() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheck3InARow() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheck2InARow() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsTerminal() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrint() {
		fail("Not yet implemented");
	}
	*/

}
