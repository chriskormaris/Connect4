package utility;

import enumeration.AiType;
import enumeration.Color;
import enumeration.GameMode;
import enumeration.GuiStyle;

public class GameParameters {

	private GuiStyle guiStyle;
	private GameMode gameMode;
	private AiType aiType;
	private int maxDepth1;
	private int maxDepth2;
	private Color player1Color;
	private Color player2Color;

	private int numOfRows;
	private int numOfColumns;
	private int checkersInARow;

	/* Default values */
	public GameParameters() {
		guiStyle = GuiStyle.SYSTEM_STYLE;
		gameMode = GameMode.HUMAN_VS_AI;
		aiType = AiType.MINIMAX_AI;
		maxDepth1 = 5;
		maxDepth2 = 5;
		player1Color = Color.RED;
		player2Color = Color.YELLOW;

		numOfRows = 6;
		numOfColumns = 7;
		checkersInARow = 4;
	}

	public GameParameters(GameParameters otherGameParameters) {
		this.guiStyle = otherGameParameters.guiStyle;
		this.gameMode = otherGameParameters.gameMode;
		this.aiType = otherGameParameters.aiType;
		this.maxDepth1 = otherGameParameters.maxDepth1;
		this.maxDepth2 = otherGameParameters.maxDepth2;
		this.player1Color = otherGameParameters.player1Color;
		this.player2Color = otherGameParameters.player2Color;
		this.numOfRows = otherGameParameters.numOfRows;
		this.numOfColumns = otherGameParameters.numOfColumns;
		this.checkersInARow = otherGameParameters.checkersInARow;
	}

	public GameParameters(GuiStyle guiStyle, GameMode gameMode, AiType aiType, int maxDepth1, int maxDepth2,
						  Color player1Color, Color player2Color, int numOfRows, int numOfColumns, int checkersInARow) {
		this.guiStyle = guiStyle;
		this.gameMode = gameMode;
		this.aiType = aiType;
		this.maxDepth1 = maxDepth1;
		this.maxDepth2 = maxDepth2;
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
		this.checkersInARow = checkersInARow;
	}

	public GuiStyle getGuiStyle() {
		return guiStyle;
	}

	public void setGuiStyle(GuiStyle guiStyle) {
		this.guiStyle = guiStyle;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public AiType getAiType() {
		return aiType;
	}

	public void setAiType(AiType aiType) {
		this.aiType = aiType;
	}

	public int getMaxDepth1() {
		return maxDepth1;
	}

	public void setMaxDepth1(int maxDepth1) {
		this.maxDepth1 = maxDepth1;
	}

	public int getMaxDepth2() {
		return maxDepth2;
	}

	public void setMaxDepth2(int maxDepth2) {
		this.maxDepth2 = maxDepth2;
	}

	public Color getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(Color player1Color) {
		this.player1Color = player1Color;
	}

	public Color getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(Color player2Color) {
		this.player2Color = player2Color;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public int getNumOfColumns() {
		return numOfColumns;
	}

	public void setNumOfColumns(int numOfColumns) {
		this.numOfColumns = numOfColumns;
	}

	public int getCheckersInARow() {
		return checkersInARow;
	}

	public void setCheckersInARow(int checkersInARow) {
		this.checkersInARow = checkersInARow;
	}

}
