import java.util.ArrayList;

// h ergasia einai basismeni panw stin triliza tou lab04
public class Board {
	
	// board values
	public static final int X = 1; // player 1
	public static final int O = -1; // player 2
	public static final int EMPTY = 0;
	
	 // h kinhsh pou odigise sti dimiourgia autou tou pinaka
    private Move lastMove;
    
    // metabliti pou periexei poios paiktis epaixe teleutaios,
    // san apotelesma na dimiourgithei autos o pinakas
    private int lastLetterPlayed;
    
    private int winner;
	private int [][] gameBoard;
	
	// used for exception handling
	private boolean overflowedColumn;
	
	private boolean isGameOver;
	
	// constructor
	public Board() {
		lastMove = new Move();
		lastLetterPlayed = O;
		winner = 0;
		gameBoard = new int[6][7];
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				gameBoard[i][j] = EMPTY;
			}
		}
	}
	
	// copy constructor
	public Board(Board board) {
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		winner = board.winner;
		gameBoard = new int[6][7];
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
	
	public Move getLastMove() {
		return lastMove;
	}
	
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public boolean isOverflowedColumn() {
		return overflowedColumn;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void setLastMove(Move lastMove) {
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed) {
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
	public void setGameBoard(int[][] gameBoard) {
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}
	
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public void setOverflowedColumn(boolean overflowedColumn) {
		this.overflowedColumn = overflowedColumn;
	}
	
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	// kanei mia kinhsh, basei seiras kai sthlhs
	public void makeMove(int row, int col, int letter) {
		lastMove = new Move(row, col);
		gameBoard[row][col] = letter;
		lastLetterPlayed = letter;
	}
	
	// kanei mia kinhsh, basei sthlhs
	public void makeMove(int col, int letter) {
		// h lastMove prepei na allaxei prin thn gameBoard[][] logw ths getRowPosition(int col)
		lastMove = new Move(getRowPosition(col), col);
		gameBoard[getRowPosition(col)][col] = letter;
		lastLetterPlayed = letter;
	}
	
	// checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int col) {
		if ((row == -1) || (col == -1) || (row > 5) || (col > 6)) {
			return false;
		}
		if(gameBoard[row][col] != EMPTY) {
			return false;
		}
		return true;
	}
	
	// checks whether a move is valid; whether a square is empty
	// mono sthlh san eisodo gia na elegxoume an einai gemath 
	public boolean isValidMove(int col) {
		int row = getRowPosition(col);
		
		if ((row == -1) || (col == -1) || (row > 5) || (col > 6)) {
			return false;
		}
		if(gameBoard[row][col] != EMPTY) {
			return false;
		}
		return true;
	}
	
	// xrisimopoieitai otan theloume na kanoume anazhthsh se olo ton pinaka
	// xwris na bgoume exw apo ta oria tou pinaka
	public boolean canMove(int row, int col) {
		if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
			return false;
		}
		return true;
	}
		
	public boolean checkFullColumn(int col) {
		if (gameBoard[0][col] == EMPTY)
			return false;
		return true;
	}
	
	// epistrefei th thesi ths teleutaias EMPTY seiras mias sthlhs
	public int getRowPosition(int col) {
		int rowPosition = -1;
		for (int row=0; row<6; row++) {
			if (gameBoard[row][col] == EMPTY) {
				rowPosition = row;
			}
		}
		return rowPosition;
	}
	
	/* Generates the children of the state
     * The max number of the children is 7,
     * because we have 7 columns
     */
	public ArrayList<Board> getChildren(int letter) {
		ArrayList<Board> children = new ArrayList<Board>();
		for(int col=0; col<7; col++) {
			if(isValidMove(col)) {
				Board child = new Board(this);
				child.makeMove(col, letter);
				children.add(child);
			}
		}
		return children;
	}
	
	public int evaluate() {
		// +100 'X' wins, -100 'O' wins,
		// +10 three 'X' in a row, -10 three 'O' in a row,
		// +1 two 'X' in a row, -1 two 'O' in a row
		int Xlines = 0;
		int Olines = 0;

        if (checkWinState()) {
			if(getWinner() == X) {
				Xlines = Xlines + 100;
			} else {
				Olines = Olines + 100;
			}
		}
		
        Xlines  = Xlines + check3InARow(X)*10 + check2InARow(X);
        Olines  = Olines + check3InARow(O)*10 + check2InARow(O);
		
        // if the result is 0, then it'a a draw 
		return Xlines - Olines;
	}
	
	/*
	 * terminal win test
	 */
	//elegxei kai tipwnei an exei nikisei kapoios to paixnidi
	public boolean checkWinState() {
				
		//elegxos gia 4 checkers sti seira se grammi
		for (int i=5; i>=0; i--) {
			for (int j=0; j<4; j++) {
				if (gameBoard[i][j] == gameBoard[i][j+1] && gameBoard[i][j] == gameBoard[i][j+2] && gameBoard[i][j] == gameBoard[i][j+3]
						&& gameBoard[i][j] != EMPTY) {
					setWinner(gameBoard[i][j]);
					return true;
				}
			}
		}
		
		//elegxos gia 4 checkers sti seira se stili
		for (int i=5; i>=3; i--) {
			for (int j=0; j<7; j++) {
				if (gameBoard[i][j] == gameBoard[i-1][j] && gameBoard[i][j] == gameBoard[i-2][j] && gameBoard[i][j] == gameBoard[i-3][j]
						&& gameBoard[i][j] != EMPTY) {
					setWinner(gameBoard[i][j]);
					return true;
				}
			}
		}
		
		//elegxos gia 4 checkers sti seira se f8inousa diagwnio
		for (int i=0; i<3; i++) {
			for (int j=0; j<4; j++) {
				if (gameBoard[i][j] == gameBoard[i+1][j+1] && gameBoard[i][j] == gameBoard[i+2][j+2] && gameBoard[i][j] == gameBoard[i+3][j+3] 
						&& gameBoard[i][j] != EMPTY) {
					setWinner(gameBoard[i][j]);
					return true;
				}
			}
		}
		
		//elegxos gia 4 checkers sti seira se auxousa diagwnio
		for (int i=0; i<6; i++) {
			for (int j=0; j<7; j++) {
				if (canMove(i-3,j+3)) {
					if (gameBoard[i][j] == gameBoard[i-1][j+1] && gameBoard[i][j] == gameBoard[i-2][j+2] && gameBoard[i][j] == gameBoard[i-3][j+3] 
							&& gameBoard[i][j] != EMPTY) {
						setWinner(gameBoard[i][j]);
						return true;
					}
				}
			}
		}
		
		setWinner(0);
		return false;

	}
	
    public boolean checkGameOver() {
    	// elegxos an uparxei nikitis
    	if (checkWinState()) {
    		return true;
    	}
    	
    	// elegxos an einai uparxei adeio keli,
    	// diladi an uparxei isopalia
    	for(int row=0; row<6; row++) {
			for(int col=0; col<7; col++) {
				if(gameBoard[row][col] == EMPTY) {
                    return false;
                }
            }
        }
    	
    	return true;
    }
	
	// epistrefei poses fores uparxoun 3 checkers sti seira,
	// tou sigkekrimenou paikti
	public int check3InARow(int player) {
		
		int times = 0;
		
		//elegxos gia 3 checkers sti seira se grammi
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i, j + 2)) {
					if (gameBoard[i][j] == gameBoard[i][j + 1] && gameBoard[i][j] == gameBoard[i][j + 2]
							&& gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 3 checkers sti seira se stili
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i - 2, j)) {
					if (gameBoard[i][j] == gameBoard[i - 1][j] && gameBoard[i][j] == gameBoard[i - 2][j]
							&& gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 3 checkers sti seira se f8inousa diagwnio
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i + 2, j + 2)) {
					if (gameBoard[i][j] == gameBoard[i + 1][j + 1] && gameBoard[i][j] == gameBoard[i + 2][j + 2]
							&& gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 3 checkers sti seira se auxousa diagwnio
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i - 2, j + 2)) {
					if (gameBoard[i][j] == gameBoard[i - 1][j + 1] && gameBoard[i][j] == gameBoard[i - 2][j + 2]
							&& gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		return times;
				
	}
	
	// epistrefei poses fores uparxoun 2 checkers sti seira,
	// tou sigkekrimenou paikti
	public int check2InARow(int player) {
		
		int times = 0;
		
		// elegxos gia 2 checkers sti seira se grammi
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i, j + 1)) {
					if (gameBoard[i][j] == gameBoard[i][j + 1] && gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 2 checkers sti seira se stili
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i - 1, j)) {
					if (gameBoard[i][j] == gameBoard[i - 1][j] && gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 2 checkers sti seira se f8inousa diagwnio
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i + 1, j + 1)) {
					if (gameBoard[i][j] == gameBoard[i + 1][j + 1] && gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		// elegxos gia 2 checkers sti seira se auxousa diagwnio
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMove(i - 1, j + 1)) {
					if (gameBoard[i][j] == gameBoard[i - 1][j + 1] && gameBoard[i][j] == player) {
						times++;
					}
				}
			}
		}

		return times;
				
	}

    //tipwnei ton pinaka
  	public void print() {
  		
  		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
  		System.out.println();
  		for (int i=0; i<6; i++) {
  			for (int j=0; j<7; j++) {
  				if (j!=6) {
  					if (gameBoard[i][j] == 1) {
  						System.out.print("| " + "X" + " ");
  					} else if (gameBoard[i][j] == -1) {
  						System.out.print("| " + "O" + " ");
  					} else {
  						System.out.print("| " + "-" + " ");
  					}
  				} else {
  					if (gameBoard[i][j] == 1) {
  						System.out.println("| " + "X" + " |");
  					} else if (gameBoard[i][j] == -1) {
  						System.out.println("| " + "O" + " |");
  					} else {
  						System.out.println("| " + "-" + " |");
  					}
  				}
  			}
  		}
  		
  	}

}
