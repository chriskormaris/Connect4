package com.chriskormaris.connect4.api.ai;

import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import com.chriskormaris.connect4.api.util.Constants;

import java.util.Random;

public class RandomChoiceAI extends AI {

	private static final Random RANDOM = new Random();

	private final int numOfColumns;

	public RandomChoiceAI() {
		super(Constants.P2);
		this.numOfColumns = Constants.CONNECT_4_NUM_OF_COLUMNS;
	}

	public RandomChoiceAI(int aiPlayer, int numOfColumns) {
		super(aiPlayer);
		this.numOfColumns = numOfColumns;
	}

	// Initiates the random move.
	@Override
	public Move getNextMove(Board board) {
		int col;
		do {
			col = RANDOM.nextInt(this.numOfColumns);
		} while (board.checkFullColumn(col));

		int row = board.getEmptyRowPosition(col);

		return new Move(row, col, getAiPlayer());
	}

}
