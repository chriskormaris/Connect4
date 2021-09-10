package ai;

import connect4.Board;
import connect4.Move;

import java.util.Random;

public class RandomChoiceAi {

    private int aiPlayer;

    public RandomChoiceAi(int aiLetter) {
        this.aiPlayer = aiLetter;
    }

    public int getAiPlayer() {
        return aiPlayer;
    }

    // Initiates the random move
    public Move randomMove(Board board) {
        Random r = new Random();

        if ((board.checkForGameOver())) {
            return new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
        }
        int col = r.nextInt(7);
        while (board.checkFullColumn(col)) {
            col = r.nextInt(7);
        }

        int row = board.getEmptyRowPosition(col);

        return new Move(row, col, aiPlayer);
    }

}