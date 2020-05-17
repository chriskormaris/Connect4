package connect4;


public class GameParameters {
	
	/* Default values */
	public static int guiStyle = Constants.SYSTEM_STYLE;
	public static int gameMode = Constants.HUMAN_VS_AI;
	public static int maxDepth1 = 5;
	public static int maxDepth2 = 5;
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
