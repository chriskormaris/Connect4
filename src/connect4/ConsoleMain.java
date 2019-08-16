package connect4;

import java.util.Scanner;
import java.util.InputMismatchException; // gia thn Scanner
// import java.util.Random;

public class ConsoleMain {
	
	public static void main(String[] args) {
		
		// We create the AI computer player "O" and the Connect-4 board.
        // The "maxDepth" for the MiniMax algorithm is set to 4.
		// Feel free to change the values.
		// The bigger the value of "maxDepth" is, the more difficult the game is. 
		int XColumnPosition;
		int maxDepth = 4;
		MiniMaxAi OPlayer = new MiniMaxAi(maxDepth, Board.O);
		Board connect4 = new Board();

        // Uncomment this, for "O" to play first
		//board.setLastLetterPlayed(Board.X);

		System.out.println("Connect-4!\n");
		System.out.println("\n*****************************");
		connect4.printBoard();
		System.out.println();
        // While the game has not finished
		while(!connect4.checkGameOver()) {
			System.out.println("\n*****************************");
			switch (connect4.getLastSymbolPlayed()) {
			
					
                // If "O" played last, then "X" plays now.
				// "X" is the user-player
				case Board.O:
                    System.out.print("Human 'X' moves.");
                    try {
        				do {
        					System.out.print("\nGive column (1-7): ");
							@SuppressWarnings("resource")
							Scanner in = new Scanner(System.in);
        					XColumnPosition = in.nextInt();
        				} while (connect4.checkFullColumn(XColumnPosition-1));
        			} catch (ArrayIndexOutOfBoundsException e){
        				System.err.println("\nValid numbers are 1,2,3, 4,5, 6 or 7.\n");
        				break;
        			} catch (InputMismatchException e){
        				System.err.println("\nInput an integer number.\n");
        				break;
        			}
					connect4.makeMove(XColumnPosition-1, Board.X);
					System.out.println();
					break;
					
                // If "X" played last, then "O" plays now.
				// "O" is the AI computer
				case Board.X:
                    System.out.println("AI 'O' moves.");
                    
                    // minimax move
					Move OMove = OPlayer.miniMax(connect4);
					
					// random move
					//Random r = new Random();
					//int randomNum = r.nextInt(7);
					//connect4.makeMove(randomNum, Board.O);

					connect4.makeMove(OMove.getCol(), Board.O);
					System.out.println();
					break;
					
				default:
					break;
			}
			connect4.printBoard();
		}
		
		System.out.println();

		if (connect4.getWinner() == Board.X) {
			System.out.println("Human player 'X' wins!");
		} else if (connect4.getWinner() == Board.O) {
			System.out.println("AI computer 'O' wins!");
		} else {
			System.out.println("It's a draw!");
		}
		
		System.out.println("Game over.");
				
	}
	
}
