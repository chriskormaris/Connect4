package com.chriskormaris.connect4.api.util;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	// Board values
	public static final int P1 = 1;  // Player 1
	public static final int P2 = 2;  // Player 2
	public static final int EMPTY = 0;

	public static final int DEFAULT_MAX_DEPTH = 5;

	public static final int CONNECT_4_NUM_OF_ROWS = 6;
	public static final int CONNECT_4_NUM_OF_COLUMNS = 7;
	public static final int CONNECT_4_CHECKERS_IN_A_ROW = 4;

	public static final int CONNECT_5_NUM_OF_ROWS = 7;
	public static final int CONNECT_5_NUM_OF_COLUMNS = 8;
	public static final int CONNECT_5_CHECKERS_IN_A_ROW = 5;

}
