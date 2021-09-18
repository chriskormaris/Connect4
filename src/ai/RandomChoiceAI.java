package ai;

import connect4.Board;
import connect4.Move;
import gui.GUI;
import utility.Constants;

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
