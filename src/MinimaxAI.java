import java.util.ArrayList;
import java.util.Random;

public class MinimaxAI {
	
		//Variable that holds the maximum depth the MiniMax algorithm will reach for this player
		private int maxDepth;
	    //Variable that holds which letter this player controls
		private int playerLetter;
		
		public int getMaxDepth() {
			return maxDepth;
		}

		public void setMaxDepth(int maxDepth) {
			this.maxDepth = maxDepth;
		}

		public int getPlayerLetter() {
			return playerLetter;
		}

		public void setPlayerLetter(int playerLetter) {
			this.playerLetter = playerLetter;
		}

		public MinimaxAI() {
			maxDepth = 4;
			playerLetter = Board.X;
		}
		
		public MinimaxAI(int maxDepth, int playerLetter) {
			this.maxDepth = maxDepth;
			this.playerLetter = playerLetter;
		}

	    //Initiates the MiniMax algorithm
		public Move MiniMax(Board board) {
	        //If the X plays then it wants to MAXimize the heuristics value
	        if (playerLetter == Board.X) {
	            return max(new Board(board), 0);
	        }
	        //If the O plays then it wants to MINimize the heuristics value
	        else {
	            return min(new Board(board), 0);
	        }
		}

	    // The max and min functions are called interchangingly, one after another until a max depth is reached
		public Move max(Board board, int depth) {
	        Random r = new Random();

	        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
	         * then a heuristic is calculated on the state and the move returned.
	         */
			if((board.checkGameOver()) || (depth == maxDepth))
			{
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
				return lastMove;
			}
	        //The children-moves of the state are calculated
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.X));
			Move maxMove = new Move(Integer.MIN_VALUE);
			for (Board child : children) {
	            //And for each child min is called, on a lower depth
				Move move = min(child, depth + 1);
	            //The child-move with the greatest value is selected and returned by max
				if(move.getValue() >= maxMove.getValue()) {
	                if ((move.getValue() == maxMove.getValue())) {
	                    //If the heuristic has the same value then we randomly choose one of the two moves
	                    if (r.nextInt(2) == 0) {
	                        maxMove.setRow(child.getLastMove().getRow());
	                        maxMove.setCol(child.getLastMove().getCol());
	                        maxMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                    maxMove.setRow(child.getLastMove().getRow());
	                    maxMove.setCol(child.getLastMove().getCol());
	                    maxMove.setValue(move.getValue());
	                }
				}
			}
			return maxMove;
		}

	    //Min works similarly to max
		public Move min(Board board, int depth) {
	        Random r = new Random();

			if((board.checkGameOver()) || (depth == maxDepth)) {
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
				return lastMove;
			}
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
			Move minMove = new Move(Integer.MAX_VALUE);
			for (Board child : children) {
				Move move = max(child, depth + 1);
				if(move.getValue() <= minMove.getValue()) {
	                if ((move.getValue() == minMove.getValue())) {
	                    if (r.nextInt(2) == 0) {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setCol(child.getLastMove().getCol());
	                        minMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setCol(child.getLastMove().getCol());
	                        minMove.setValue(move.getValue());
	                }
	            }
	        }
	        return minMove;
		}
		
}
