package com.chriskormaris.connect4.gui.util;

import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.api.util.Constants;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
		guiStyle = GuiStyle.CROSS_PLATFORM;
		gameMode = GameMode.HUMAN_VS_AI;

		ai1Type = AiType.MINIMAX_AI;
		ai2Type = AiType.MINIMAX_AI;

		ai1MaxDepth = Constants.DEFAULT_MAX_DEPTH;
		ai2MaxDepth = Constants.DEFAULT_MAX_DEPTH;

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

}
