package connect4_tests;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connect4.Board;
import utilities.Constants;


public class BoardTest {

	static final int numOfRows = Constants.NUM_OF_ROWS;
	static final int numOfColumns = Constants.NUM_OF_COLUMNS;

	@BeforeEach
	public void setUp() throws Exception {
		
	}

//	@Test
//	public void testBoard() {
//		fail("Not yet implemented");
//	}
//
//
//	@Test
//	public void testGetLastMove() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetLastLetterPlayed() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetGameBoard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetWinner() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetLastMove() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetLastLetterPlayed() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetGameBoard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetWinner() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testMakeMoveInt() {
		Board b = new Board();
		
		b.makeMove(0, Constants.P1);
	  	Board.printBoard(b.getGameBoard());

		assertTrue("The board was not updated correctly.", b.getGameBoard()[5][0] == Constants.P1);
		assertTrue("The last player symbol played is not correct.", b.getLastPlayer() == Constants.P1);
		System.out.println("*****************************");
		System.out.println();
	}

//	@Test
//	public void testIsValidMoveIntInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsValidMoveInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCanMove() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckFullColumn() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetRowPosition() {
		Board b = new Board();
		
		int gameBoard[][] = new int[numOfRows][numOfColumns];
		for(int col = 0; col <numOfColumns; col++) {
			gameBoard[5][col] = Constants.P1;
		}
		b.setGameBoard(gameBoard);
	  	Board.printBoard(b.getGameBoard());

		for(int col=0; col<numOfColumns; col++) {
			int row = b.getEmptyRowPosition(col);
			assertTrue("The row result is not the expected one.", row == 4);
		}
		System.out.println("*****************************");
		System.out.println();
	}

//	@Test
//	public void testGetChildren() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testEvaluate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckWinState() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheck3InARow() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheck2InARow() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsTerminal() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPrint() {
//		fail("Not yet implemented");
//	}

}
