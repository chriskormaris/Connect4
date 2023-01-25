package com.chriskormaris.connect4.api.ai;

import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.GUI;

import java.util.Random;

public class RandomChoiceAI extends AI {

	public RandomChoiceAI() {
		super(Constants.P2);
	}

	public RandomChoiceAI(int aiPlayer) {
		super(aiPlayer);
	}

	// Initiates the random move.
	@Override
	public Move getNextMove(Board board) {
		Random r = new Random();

		int col;
		do {
			col = r.nextInt(GUI.gameParameters.getNumOfColumns());
		} while (board.checkFullColumn(col));

		int row = board.getEmptyRowPosition(col);

		return new Move(row, col, getAiPlayer());
	}

}
