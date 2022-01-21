package com.chriskormaris.connect4.api.ai;


import com.chriskormaris.connect4.api.connect4.Board;
import com.chriskormaris.connect4.api.connect4.Move;
import com.chriskormaris.connect4.api.utility.Constants;

import java.util.ArrayList;
import java.util.Random;


public class MiniMaxAI extends AI {

    // Variable that holds the maximum depth the MiniMaxAi algorithm will reach for this player.
    private int maxDepth;

    private boolean alphaBeta;

    public MiniMaxAI() {
        super(Constants.P2);
        maxDepth = 2;
        alphaBeta = true;
    }

    public MiniMaxAI(int maxDepth, int aiPlayer, boolean alphaBeta) {
        super(aiPlayer);
        this.maxDepth = maxDepth;
        this.alphaBeta = alphaBeta;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean isAlphaBeta() {
        return alphaBeta;
    }

    public void setAlphaBeta(boolean alphaBeta) {
        this.alphaBeta = alphaBeta;
    }

    @Override
    public Move getNextMove(Board board) {
        if (alphaBeta) {
            return miniMaxAlphaBeta(board);
        } else {
            return miniMax(board);
        }
    }

    // Initiates the MiniMax algorithm
    public Move miniMax(Board board) {
        // If P1 plays then it wants to MAXimize the heuristics value.
        if (getAiPlayer() == Constants.P1) {
            return max(new Board(board), 0);
        }
        // If P2 plays then it wants to MINimize the heuristics value.
        else {
            return min(new Board(board), 0);
        }
    }

    // The max and min functions are called interchangeably, one after another until a max depth is reached
    public Move max(Board board, int depth) {
        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((board.checkForGameOver()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        // The children-moves of the state are calculated
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P1));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            // And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            // The child-move with the greatest value is selected and returned by max
            if (move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    // If the heuristic has the same value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0) {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setColumn(child.getLastMove().getColumn());
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setColumn(child.getLastMove().getColumn());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

    // Min works similarly to max.
    public Move min(Board board, int depth) {
        Random r = new Random();

        if ((board.checkForGameOver()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P2));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children) {
            Move move = max(child, depth + 1);
            if (move.getValue() <= minMove.getValue()) {
                if ((move.getValue() == minMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setColumn(child.getLastMove().getColumn());
                        minMove.setValue(move.getValue());
                    }
                } else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setColumn(child.getLastMove().getColumn());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }


    // Initiates the MiniMax algorithm
    public Move miniMaxAlphaBeta(Board board) {
        // If P1 plays then it wants to MAXimize the heuristics value.
        if (getAiPlayer() == Constants.P1) {
            return maxAlphaBeta(new Board(board), 0, Double.MAX_VALUE, Double.MIN_VALUE);
        }
        // If P2 plays then it wants to MINimize the heuristics value.
        else {
            return minAlphaBeta(new Board(board), 0, Double.MIN_VALUE, Double.MAX_VALUE);
        }
    }


    // The max and min functions are called interchangeably, one after another until a max depth is reached.
    public Move maxAlphaBeta(Board board, int depth, double a, double b) {
        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((board.checkForGameOver()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        // The children-moves of the state are calculated
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P1));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            // And for each child min is called, on a lower depth.
            Move move = minAlphaBeta(child, depth + 1, a, b);
            // The child-move with the greatest value is selected and returned by max.
            if (move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    // If the heuristic has the same value, then we randomly choose one of the two moves.
                    if (r.nextInt(2) == 0) {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setColumn(child.getLastMove().getColumn());
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setColumn(child.getLastMove().getColumn());
                    maxMove.setValue(move.getValue());
                }
            }

            // Beta pruning.
            if (maxMove.getValue() >= b) {
                // System.out.println("Beta pruning: " + b);
                return maxMove;
            }

            // Update the a of the current max node.
            a = (a > maxMove.getValue()) ? a : maxMove.getValue();
        }
        return maxMove;
    }

    // Min works similarly to max.
    public Move minAlphaBeta(Board board, int depth, double a, double b) {
        Random r = new Random();

        if ((board.checkForGameOver()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
            return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P2));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children) {
            Move move = maxAlphaBeta(child, depth + 1, a, b);
            if (move.getValue() <= minMove.getValue()) {
                if ((move.getValue() == minMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setColumn(child.getLastMove().getColumn());
                        minMove.setValue(move.getValue());
                    }
                } else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setColumn(child.getLastMove().getColumn());
                    minMove.setValue(move.getValue());
                }
            }

            // Alpha pruning
            if (minMove.getValue() <= a) {
                // System.out.println("Alpha pruning: " + a);
                return minMove;
            }

            // Update the b of the current min node.
            b = (b < minMove.getValue()) ? b : minMove.getValue();
        }
        return minMove;
    }

}