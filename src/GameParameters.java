public class GameParameters {
	
	private int maxDepth;
	private String playerColor;
	private int gameMode;
	
	public static final int HumanVSAi = 1;
	public static final int HumanVSHuman = 2;
	
	public GameParameters() {
		// Default values
		this.maxDepth = 4;
		this.playerColor = "RED";
		this.gameMode = HumanVSAi;
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

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

}
