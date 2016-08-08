public class GameParameters {
	
	private int maxDepth;
	private String playerColor;
	
	public GameParameters() {
		
		// arxikopoioume me maxDepth = 4, mporei na parei allh timh gia rythmish duskolias
		this.maxDepth = 4;
		
		this.playerColor = "RED";
		
	}

	public int getMaxDepth() {
		return this.maxDepth;
	}

	public void setMaxDepth(int difficulty) {
		this.maxDepth = difficulty;
	}
	
	public String getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(String color) {
		this.playerColor = color;
	}

}
