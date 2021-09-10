package utility;

import enumeration.AiType;
import enumeration.Color;
import enumeration.GameMode;
import enumeration.GuiStyle;

public class GameParameters {
	
	private GameParameters() { }  // Prevents instantiation.

	/* Default values */
	public static GuiStyle guiStyle = GuiStyle.SYSTEM_STYLE;
	public static GameMode gameMode = GameMode.HUMAN_VS_AI;
	public static AiType aiType = AiType.MINIMAX_AI;
	public static int maxDepth1 = 5;
	public static int maxDepth2 = 5;
	public static Color player1Color = Color.RED;
	public static Color player2Color = Color.YELLOW;

	public static int numOfRows = 6;
	public static int numOfColumns = 7;
	public static int checkersInARow = 4;

}
