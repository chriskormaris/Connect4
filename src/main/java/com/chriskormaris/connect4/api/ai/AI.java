package com.chriskormaris.connect4.api.ai;

import com.chriskormaris.connect4.api.board.Board;
import com.chriskormaris.connect4.api.board.Move;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AI {

	// Variable that holds which player plays.
	private int aiPlayer;

	public AI(int aiPlayer) {
		this.aiPlayer = aiPlayer;
	}

	public abstract Move getNextMove(Board board);

}
