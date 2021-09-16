package ai;

import connect4.Board;
import connect4.Move;
import gui.GUI;

import java.util.Random;

public class RandomChoiceAI extends AI {

    public RandomChoiceAI(int aiPlayer) {
        super(aiPlayer);
    }

    // Initiates the random move
    @Override
    public Move getNextMove(Board board) {
        Random r = new Random();

        if ((board.checkForGameOver())) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        int col;
        do {
            col = r.nextInt(GUI.gameParameters.getCheckersInARow());
        } while (board.checkFullColumn(col));

        int row = board.getEmptyRowPosition(col);

        return new Move(row, col, getAiPlayer());
    }

}