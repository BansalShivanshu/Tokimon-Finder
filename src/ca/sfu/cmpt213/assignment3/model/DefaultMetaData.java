package ca.sfu.cmpt213.assignment3.model;

public class DefaultMetaData {
    private static final int NUM_ROW = 10;
    private static final int NUM_COL = 10;

    private static final int NUM_TOKI = 10;
    private static final int NUM_FOKI = 5;

    private static final int ARG_LENGTH = 3;

    private static final int LOWEST_NUM_TOKI = 0;
    private static final int LARGEST_NUM_TOKI = NUM_ROW * NUM_COL;

    private static final int LOWEST_NUM_FOKI = 0;
    private static final int LARGEST_NUM_FOKI = NUM_ROW * NUM_COL;


    public static int getNumRow() {
        return NUM_ROW;
    }

    public static int getNumCol() {
        return NUM_COL;
    }

    public static int getNumToki() {
        return NUM_TOKI;
    }

    public static int getNumFoki() {
        return NUM_FOKI;
    }

    public static int getArgLength() {
        return ARG_LENGTH;
    }

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
}
