package ca.sfu.cmpt213.assignment3.model;

public class DefaultMetaData {
    private static final int NUM_ROW = 10;
    private static final int NUM_COL = 10;

    private static final int NUM_TOKI = 10;
    private static final int NUM_FOKI = 5;

    private static final int ARG_LENGTH = 3;

    private static final int LOWEST_NUM_TOKI = 5;
    private static final int LARGEST_NUM_TOKI = NUM_ROW * NUM_COL;

    private static final int LOWEST_NUM_FOKI = 5;
    private static final int LARGEST_NUM_FOKI = NUM_ROW * NUM_COL;

    private static final int LOWER_POSITION_LENGTH = 2;
    private static final int UPPER_POSITION_LENGTH = 3;

    private static final char[] ROW_LETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
    private static final int[] COL_NUMBERS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    private static final char[] gridValues = { 'T', 'F', 'N' };



    public static int getNumRow() {
        return NUM_ROW;
    }

    public static int getNumCol() {
        return NUM_COL;
    }

    public static int getNumToki() { return NUM_TOKI; }

    public static int getNumFoki() { return NUM_FOKI; }

    public static int getArgLength() { return ARG_LENGTH; }

    public static int getLowestNumToki() {
        return LOWEST_NUM_TOKI;
    }

    public static int getLargestNumToki() {
        return LARGEST_NUM_TOKI;
    }

    public static int getLowestNumFoki() {
        return LOWEST_NUM_FOKI;
    }

    public static int getLargestNumFoki() {
        return LARGEST_NUM_FOKI;
    }

    public static int getLowerPositionLength() {
        return LOWER_POSITION_LENGTH;
    }

    public static int getUpperPositionLength() {
        return UPPER_POSITION_LENGTH;
    }

    public static char[] getRowLetters() {
        return ROW_LETTERS;
    }

    public static int[] getColNumbers() {
        return COL_NUMBERS;
    }

    public static char[] getGridValues() {
        return gridValues;
    }

    public static char getGridValue(int index) {
        if (index < 0 || index >= gridValues.length) {
            throw new IllegalArgumentException("Index out of Bound (GridValues)");
        } else {
            return gridValues[index];
        }
    }
}
