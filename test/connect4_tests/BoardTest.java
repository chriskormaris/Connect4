package connect4_tests;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connect4.Board;
import connect4.Constants;


public class BoardTest {

	static final int numOfRows = Constants.NUM_OF_ROWS;
	static final int numOfColumns = Constants.NUM_OF_COLUMNS;

	@BeforeEach
	public void setUp() throws Exception {
		
	}



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

