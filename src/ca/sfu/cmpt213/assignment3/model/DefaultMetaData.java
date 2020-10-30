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

    private static final char[] symbols = { '~', '$', ' ', '@' };



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
        return ROW_LETTERS.clone();
    }

    public static char getRowLetterAt(int index) {
        if (index < 0 || index >= ROW_LETTERS.length) {
            throw new IllegalArgumentException("Index Out Of Bound (getRowLetterAt())");
        }
        return ROW_LETTERS[index];
    }

    public static int[] getColNumbers() {
        return COL_NUMBERS.clone();
    }

    public static int getColNumberAt(int index) {
        if (index < 0 || index >= COL_NUMBERS.length) {
            throw new IllegalArgumentException("Index out of bound (getColNumberAt()");
        }
        return COL_NUMBERS[index];
    }

    /**
     *  Returns the character value acceptable by a letter grid
     *     Allowed Indices:
     *         0: 'T'  Tokimon
     *         1: 'F'  Fokimon
     *         2: 'N'  Nothing
     */
    public static char getGridValue(int index) {
        if (index < 0 || index >= gridValues.length) {
            throw new IllegalArgumentException("Index out of Bound (GridValues)");
        } else {
            return gridValues[index];
        }
    }

    /**
     *  Returns the character symbols acceptable to main grid
     *     Allowed Indices:
     *         0: '~'  Indicated Unknown (Unvisited)
     *         1: '$'  Indicates a Found Tokimon
     *         2: ' '  (Space) Indicates a Visited but Empty Location
     *         3: '@'   Player's Current Position
     */
    public static char getSymbolAt(int index) {
        if (index < 0 || index >= symbols.length) {
            throw new IllegalArgumentException("Index out of Bound (Symbols)");
        } else {
            return symbols[index];
        }
    }
}
