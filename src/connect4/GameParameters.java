package connect4;

import enumerations.Color;
import enumerations.GameMode;
import enumerations.GuiStyle;

public class GameParameters {
	
	private GameParameters() { }  // Prevents instantiation.

	/* Default values */
	public static GuiStyle guiStyle = GuiStyle.SYSTEM_STYLE;
	public static GameMode gameMode = GameMode.HUMAN_VS_MINIMAX_AI;
	public static int maxDepth1 = 5;
	public static int maxDepth2 = 5;
	public static Color player1Color = Color.RED;
	public static Color player2Color = Color.YELLOW;
	
}
