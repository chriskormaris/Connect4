package connect4;


public class GameParameters {
	
	/* Default values */
	public static int guiStyle = Constants.SystemStyle;
	public static int gameMode = Constants.HumanVsAi;
	public static int maxDepth = 4;
	public static int player1Color = Constants.RED;
	public static int player2Color = Constants.YELLOW;
	
	
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
