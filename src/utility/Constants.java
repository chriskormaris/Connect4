package utility;


public class Constants {

    public static final String VERSION = "2.5.2";
    public static final int AI_MOVE_MILLISECONDS = 250;

    // Board values
    public static final int P1 = 1;  // Player 1
    public static final int P2 = 2;  // Player 2
    public static final int EMPTY = 0;

    public static final String CONNECT_4_BOARD_IMG_PATH = "images/Board4.png";
    public static final String CONNECT_5_BOARD_IMG_PATH = "images/Board5.png";

    public static final int DEFAULT_CONNECT_4_WIDTH = 570;
    public static final int DEFAULT_CONNECT_4_HEIGHT = 550;

    public static final int DEFAULT_CONNECT_5_WIDTH = 648;
    public static final int DEFAULT_CONNECT_5_HEIGHT = 630;

    public static final int CONNECT_4_NUM_OF_ROWS = 6;
    public static final int CONNECT_4_NUM_OF_COLUMNS = 7;
    public static final int CONNECT_4_CHECKERS_IN_A_ROW = 4;

    public static final int CONNECT_5_NUM_OF_ROWS = 7;
    public static final int CONNECT_5_NUM_OF_COLUMNS = 8;
    public static final int CONNECT_5_CHECKERS_IN_A_ROW = 5;

    private Constants() {
    }  // Prevents instantiation.

}
