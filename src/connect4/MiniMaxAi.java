package connect4;


import java.util.ArrayList;
import java.util.Random;


public class MiniMaxAi {
	
		
		private int maxDepth;
		
	    
		private int aiPlayer;

		public MiniMaxAi() {
			maxDepth = 4;
			aiPlayer = Constants.P2;
		}
		
		public MiniMaxAi(int maxDepth, int aiLetter) {
			this.maxDepth = maxDepth;
			this.aiPlayer = aiLetter;
		}
		
		public int getMaxDepth() {
			return maxDepth;
		}

		public void setMaxDepth(int maxDepth) {
			this.maxDepth = maxDepth;
		}

		public int getAiPlayer() {
			return aiPlayer;
		}

		public void setAiPlayer(int aiPlayer) {
			this.aiPlayer = aiPlayer;
		}

	    
		public Move miniMax(Board board) {
	        
	        if (aiPlayer == Constants.P1) {
	            return max(new Board(board), 0);
	        }
	        
	        else {
	            return min(new Board(board), 0);
	        }
		}

	   
		public Move max(Board board, int depth) {
	        Random r = new Random();

	       
			if((board.checkForGameOver()) || (depth == maxDepth)) {
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
				return lastMove;
			}
	       
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P1));
			Move maxMove = new Move(Integer.MIN_VALUE);
			for (Board child : children) {
	            
				Move move = min(child, depth + 1);
	            
				if (move.getValue() >= maxMove.getValue()) {
	                if ((move.getValue() == maxMove.getValue())) {
	                    
	                    if (r.nextInt(2) == 0) {
	                        maxMove.setRow(child.getLastMove().getRow());
	                        maxMove.setColumn(child.getLastMove().getColumn());
	                        maxMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                    maxMove.setRow(child.getLastMove().getRow());
	                    maxMove.setColumn(child.getLastMove().getColumn());
	                    maxMove.setValue(move.getValue());
	                }
				}
			}
			return maxMove;
		}

	   
		public Move min(Board board, int depth) {
	        Random r = new Random();

			if((board.checkForGameOver()) || (depth == maxDepth)) {
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
				return lastMove;
			}
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P2));
			Move minMove = new Move(Integer.MAX_VALUE);
			for (Board child : children) {
				Move move = max(child, depth + 1);
				if(move.getValue() <= minMove.getValue()) {
	                if ((move.getValue() == minMove.getValue())) {
	                    if (r.nextInt(2) == 0) {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setColumn(child.getLastMove().getColumn());
	                        minMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setColumn(child.getLastMove().getColumn());
	                        minMove.setValue(move.getValue());
	                }
	            }
	        }
	        return minMove;
		}
		
		
		
		public Move miniMaxAlphaBeta(Board board) {
	        
	        if (aiPlayer == Constants.P1) {
	            return maxAlphaBeta(new Board(board), 0, Double.MAX_VALUE, Double.MIN_VALUE);
	        }
	        
	        else {
	            return minAlphaBeta(new Board(board), 0, Double.MIN_VALUE, Double.MAX_VALUE);
	        }
		}

		
	   
		public Move maxAlphaBeta(Board board, int depth, double a, double b) {
	        Random r = new Random();

	       
			if((board.checkForGameOver()) || (depth == maxDepth)) {
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
				return lastMove;
			}
	       
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P1));
			Move maxMove = new Move(Integer.MIN_VALUE);
			for (Board child : children) {
	            
				Move move = minAlphaBeta(child, depth + 1, a, b);
	            
				if (move.getValue() >= maxMove.getValue()) {
	                if ((move.getValue() == maxMove.getValue())) {
	                    
	                    if (r.nextInt(2) == 0) {
	                        maxMove.setRow(child.getLastMove().getRow());
	                        maxMove.setColumn(child.getLastMove().getColumn());
	                        maxMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                    maxMove.setRow(child.getLastMove().getRow());
	                    maxMove.setColumn(child.getLastMove().getColumn());
	                    maxMove.setValue(move.getValue());
	                }
				}
				
				
				if (maxMove.getValue() >= b) {
					// System.out.println("Beta pruning: " + b);
					return maxMove;
				}
				
				
				a = (a > maxMove.getValue()) ? a : maxMove.getValue();
			}
			return maxMove;
		}

	    
		public Move minAlphaBeta(Board board, int depth, double a, double b) {
	        Random r = new Random();

			if((board.checkForGameOver()) || (depth == maxDepth)) {
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), board.evaluate());
				return lastMove;
			}
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Constants.P2));
			Move minMove = new Move(Integer.MAX_VALUE);
			for (Board child : children) {
				Move move = maxAlphaBeta(child, depth + 1, a, b);
				if(move.getValue() <= minMove.getValue()) {
	                if ((move.getValue() == minMove.getValue())) {
	                    if (r.nextInt(2) == 0) {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setColumn(child.getLastMove().getColumn());
	                        minMove.setValue(move.getValue());
	                    }
	                }
	                else {
	                        minMove.setRow(child.getLastMove().getRow());
	                        minMove.setColumn(child.getLastMove().getColumn());
	                        minMove.setValue(move.getValue());
	                }
	            }
				
				
				if (minMove.getValue() <= a) {
					
					return minMove;
				}
				
	           
				b = (b < minMove.getValue()) ? b : minMove.getValue();
	        }
	        return minMove;
		}
		
}
