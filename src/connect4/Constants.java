package connect4;


public class Constants {
	
	private Constants() { }  // Prevents instantiation.
	
	public static final double VERSION = 2.5;
	
	public static final int NUM_OF_ROWS = 6;
	public static final int NUM_OF_COLUMNS = 7;
	public static final int IN_A_ROW = 4;
	
	// Board values
	public static final int P1 = 1;  // Player 1
	public static final int P2 = 2;  // Player 2
	public static final int EMPTY = 0;
	
	// GUI styles
	public static final int SYSTEM_STYLE = 1;
	public static final int CROSS_PLATFORM_STYLE = 2;
	public static final int NIMBUS_STYLE = 3;
	
	// Colors
	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int BLACK = 3;
	public static final int GREEN = 4;
	public static final int ORANGE = 5;
	public static final int PURPLE = 6;
	
	// Game modes
	public static final int HUMAN_VS_AI = 1;
	public static final int HUMAN_VS_HUMAN = 2;
	public static final int AI_VS_AI = 3;
	
}
