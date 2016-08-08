import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	
	Board board;
	
	MyKeyListener (Board board) {
		this.board = board;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
		String button = KeyEvent.getKeyText(e.getKeyCode());
		
		if (button.equals("1")) {
			board.makeMove(0, Board.X);
		} else if (button.equals("2")) {
			board.makeMove(1, Board.X);
		} else if (button.equals("3")) {
			board.makeMove(2, Board.X);
		} else if (button.equals("4")) {
			board.makeMove(3, Board.X);
		} else if (button.equals("5")) {
			board.makeMove(4, Board.X);
		} else if (button.equals("6")) {
			board.makeMove(5, Board.X);
		} else if (button.equals("7")) {
			board.makeMove(6, Board.X);
		}
		
		if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
				|| button.equals("5") || button.equals("6") || button.equals("7")) {
			Gui.game();
			Gui.aiMove();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
	}
	
}
