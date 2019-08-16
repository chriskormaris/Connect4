package connect4;

public class GameParameters {
	
	private int guiStyle;
	private int gameMode;
	private int maxDepth;
	private int player1Color;
	private int player2Color;
	
	// GUI styles
	public static final int SystemStyle = 1;
	public static final int CrossPlatformStyle = 2;
	public static final int NimbusStyle = 3;
	
	// Colors
	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int BLACK = 3;
	public static final int GREEN = 4;
	public static final int ORANGE = 5;
	public static final int PURPLE = 6;
	
	// Game modes
	public static final int HumanVsAi = 1;
	public static final int HumanVsHuman = 2;
	public static final int AiVsAi = 3;

	// Default values
	public GameParameters() {
		this.guiStyle = SystemStyle;
		this.maxDepth = 4;
		this.player1Color = RED;
		this.player2Color = YELLOW;
		this.gameMode = HumanVsAi;
	}
	
	// Given values
	public GameParameters(int guiStyle, int maxDepth, int player1Color, int player2Color,
			int gameMode) {
		this.guiStyle = guiStyle;
		this.maxDepth = 4;
		this.player1Color = RED;
		this.player2Color = YELLOW;
		this.gameMode = HumanVsAi;
	}
	
	public int getGuiStyle() {
		return guiStyle;
	}

	public void setGuiStyle(int guiStyle) {
		this.guiStyle = guiStyle;
	}

	public int getMaxDepth() {
		return this.maxDepth;
	}

	public void setMaxDepth(int difficulty) {
		this.maxDepth = difficulty;
	}
	
	public int getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(int player1Color) {
		this.player1Color = player1Color;
	}
	
	public int getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(int player2Color) {
		this.player2Color = player2Color;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public static final String getColorNameByNumber(int number) {
		switch (number) {
			case 1:
				return "RED";
			case 2:
				return "YELLOW";
			case 3:
				return "BLACK";
			case 4:
				return "GREEN";
			case 5:
				return "ORANGE";
			case 6:
				return "PURPLE";
			default:
				return "RED";
		}
	}
	
}
