public class Move {
	
	private int row;
	private int col;
	private int value;
	
	public Move() {
		row = -1;
		col = -1;
		value = 0;
	}
	
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
		this.value = -1;
	}
	
	public Move(int value) {
		this.row = -1;
		this.col = -1;
		this.value = value;
	}
	
	public Move(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
