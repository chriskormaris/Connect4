package connect4;


public class GameParameters {
	
	/* Default values */
	public static int guiStyle = Constants.SystemStyle;
	public static int gameMode = Constants.HumanVsAi;
	public static int maxDepth1 = 3;
	public static int maxDepth2 = 3;
	public static int player1Color = Constants.RED;
	public static int player2Color = Constants.YELLOW;
	
	
	public static final String getColorNameByNumber(int number) {
		switch (number) {
			case 1:
				return "Red";
			case 2:
				return "Yellow";
			case 3:
				return "Black";
			case 4:
				return "Green";
			case 5:
				return "Orange";
			case 6:
				return "Purple";
			default:
				return "Red";
		}
	}
	
	
}
