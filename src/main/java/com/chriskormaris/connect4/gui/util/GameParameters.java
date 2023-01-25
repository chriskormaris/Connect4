package com.chriskormaris.connect4.gui.util;

import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;

public class GameParameters {

	private GuiStyle guiStyle;
	private GameMode gameMode;

	private AiType ai1Type;
	private AiType ai2Type;

	private int ai1MaxDepth;
	private int ai2MaxDepth;

	private Color player1Color;
	private Color player2Color;

	private int numOfRows;
	private int numOfColumns;
	private int checkersInARow;

	/* Default values */
	public GameParameters() {
		guiStyle = GuiStyle.SYSTEM_STYLE;
		gameMode = GameMode.HUMAN_VS_AI;

		ai1Type = AiType.MINIMAX_AI;
		ai2Type = AiType.MINIMAX_AI;

		ai1MaxDepth = 5;
		ai2MaxDepth = 5;

		player1Color = Color.RED;
		player2Color = Color.YELLOW;

		numOfRows = Constants.CONNECT_4_NUM_OF_ROWS;
		numOfColumns = Constants.CONNECT_4_NUM_OF_COLUMNS;
		checkersInARow = Constants.CONNECT_4_CHECKERS_IN_A_ROW;
	}

	public GameParameters(GameParameters otherGameParameters) {
		this.guiStyle = otherGameParameters.getGuiStyle();
		this.gameMode = otherGameParameters.getGameMode();
		this.ai1Type = otherGameParameters.getAi1Type();
		this.ai2Type = otherGameParameters.getAi2Type();
		this.ai1MaxDepth = otherGameParameters.getAi1MaxDepth();
		this.ai2MaxDepth = otherGameParameters.getAi2MaxDepth();
		this.player1Color = otherGameParameters.getPlayer1Color();
		this.player2Color = otherGameParameters.getPlayer2Color();
		this.numOfRows = otherGameParameters.getNumOfRows();
		this.numOfColumns = otherGameParameters.getNumOfColumns();
		this.checkersInARow = otherGameParameters.getCheckersInARow();
	}

	public GameParameters(
			GuiStyle guiStyle,
			GameMode gameMode,
			AiType ai1Type,
			AiType ai2Type,
			int ai1MaxDepth,
			int ai2MaxDepth,
			Color player1Color,
			Color player2Color,
			int numOfRows,
			int numOfColumns,
			int checkersInARow
	) {
		this.guiStyle = guiStyle;
		this.gameMode = gameMode;
		this.ai1Type = ai1Type;
		this.ai2Type = ai2Type;
		this.ai1MaxDepth = ai1MaxDepth;
		this.ai2MaxDepth = ai2MaxDepth;
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

	public AiType getAi1Type() {
		return ai1Type;
	}

	public void setAi1Type(AiType ai1Type) {
		this.ai1Type = ai1Type;
	}

	public AiType getAi2Type() {
		return ai2Type;
	}

	public void setAi2Type(AiType ai2Type) {
		this.ai2Type = ai2Type;
	}

	public int getAi1MaxDepth() {
		return ai1MaxDepth;
	}

	public void setAi1MaxDepth(int ai1MaxDepth) {
		this.ai1MaxDepth = ai1MaxDepth;
	}

	public int getAi2MaxDepth() {
		return ai2MaxDepth;
	}

	public void setAi2MaxDepth(int ai2MaxDepth) {
		this.ai2MaxDepth = ai2MaxDepth;
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
