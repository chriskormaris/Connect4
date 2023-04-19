package com.chriskormaris.connect4.api.board;


import com.chriskormaris.connect4.api.util.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

	private static final int NUM_OF_ROWS = Constants.CONNECT_4_NUM_OF_ROWS;
	private static final int NUM_OF_COLUMNS = Constants.CONNECT_4_NUM_OF_COLUMNS;

	@Test
	public void testMakeMoveInt() {
		Board board = new Board();

		board.makeMove(0, Constants.P1);
		System.out.println(board);

		assertEquals(Constants.P1, board.getGameBoard()[5][0], "The board was not updated correctly.");
		assertEquals(Constants.P1, board.getLastPlayer(), "The last player symbol played is not correct.");
		System.out.println("*****************************");
		System.out.println();
	}

    /*
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
		System.out.println(board);

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
