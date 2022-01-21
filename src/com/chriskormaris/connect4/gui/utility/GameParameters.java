package com.chriskormaris.connect4.gui.utility;

import com.chriskormaris.connect4.api.enumeration.AiType;
import com.chriskormaris.connect4.gui.enumeration.Color;
import com.chriskormaris.connect4.api.enumeration.GameMode;
import com.chriskormaris.connect4.gui.enumeration.GuiStyle;
import com.chriskormaris.connect4.api.utility.Constants;

public class GameParameters {

    private GuiStyle guiStyle;
    private GameMode gameMode;
    private AiType aiType;
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
        aiType = AiType.MINIMAX_AI;
        ai1MaxDepth = 5;
        ai2MaxDepth = 5;
        player1Color = Color.RED;
        player2Color = Color.YELLOW;

        numOfRows = com.chriskormaris.connect4.api.utility.Constants.CONNECT_4_NUM_OF_ROWS;
        numOfColumns = com.chriskormaris.connect4.api.utility.Constants.CONNECT_4_NUM_OF_COLUMNS;
        checkersInARow = Constants.CONNECT_4_CHECKERS_IN_A_ROW;
    }

    public GameParameters(GameParameters otherGameParameters) {
        this.guiStyle = otherGameParameters.guiStyle;
        this.gameMode = otherGameParameters.gameMode;
        this.aiType = otherGameParameters.aiType;
        this.ai1MaxDepth = otherGameParameters.ai1MaxDepth;
        this.ai2MaxDepth = otherGameParameters.ai2MaxDepth;
        this.player1Color = otherGameParameters.player1Color;
        this.player2Color = otherGameParameters.player2Color;
        this.numOfRows = otherGameParameters.numOfRows;
        this.numOfColumns = otherGameParameters.numOfColumns;
        this.checkersInARow = otherGameParameters.checkersInARow;
    }

    public GameParameters(GuiStyle guiStyle, GameMode gameMode, AiType aiType, int ai1MaxDepth, int ai2MaxDepth,
                          Color player1Color, Color player2Color, int numOfRows, int numOfColumns, int checkersInARow) {
        this.guiStyle = guiStyle;
        this.gameMode = gameMode;
        this.aiType = aiType;
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

    public AiType getAiType() {
        return aiType;
    }

    public void setAiType(AiType aiType) {
        this.aiType = aiType;
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
