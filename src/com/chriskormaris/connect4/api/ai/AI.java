package com.chriskormaris.connect4.api.ai;

import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;

public abstract class AI {

	// Variable that holds which player plays.
	private int aiPlayer;

	public AI(int aiPlayer) {
		this.aiPlayer = aiPlayer;
	}

	public int getAiPlayer() {
		return aiPlayer;
	}

	public void setAiPlayer(int aiPlayer) {
		this.aiPlayer = aiPlayer;
	}

	public abstract Move getNextMove(Board board);

}
