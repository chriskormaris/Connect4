package ai;

import connect4.Board;
import connect4.Move;
import utility.Constants;
import utility.GameParameters;

import java.util.Random;

public class RandomChoiceAi {

    private final int aiPlayer;

    public RandomChoiceAi(int aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public int getAiPlayer() {
        return aiPlayer;
    }

    // Initiates the random move
    public Move randomMove(Board board) {
        Random r = new Random();

        if ((board.checkForGameOver())) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        int col;
        do {
            col = r.nextInt(Constants.IN_A_ROW);
        } while (board.checkFullColumn(col));

        int row = board.getEmptyRowPosition(col);

        return new Move(row, col, aiPlayer);
    }

}