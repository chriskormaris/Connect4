package connect4;


public class Move {
	
	private int row;
	private int column;
	private int value;
	
	public Move() {
		
	}
	
	public Move(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public Move(int value) {
		this.value = value;
	}
	
	public Move(int row, int col, int value) {
		this.row = row;
		this.column = col;
		this.value = value;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int col) {
		this.column = col;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
