package ca.sfu.cmpt213.assignment3.model;

import java.rmi.UnexpectedException;
import java.util.Arrays;

public class Grid {
    private String[][] grid;
    private String[][] letterGrid;

    /*
    Singleton Support
     */
    private static Grid instance;
    private Grid() {
        // initialise the grid
        grid = new String[DefaultMetaData.getNumRow() + 1][DefaultMetaData.getNumCol() + 1];
        initializeGrid();

        letterGrid = new String[DefaultMetaData.getNumRow() + 1][DefaultMetaData.getNumCol() + 1];
    }
    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }


    /*
    Methods
     */
    private void initializeGrid() {
        for (int row = 0; row <= DefaultMetaData.getNumRow(); row++) {
            for (int col = 0; col <= DefaultMetaData.getNumCol(); col++) {
                // first cell is empty
                if (row == 0 && col == 0) {
                    grid[row][col] = String.valueOf(' ');
//                    continue;
                } else if (row == 0) {  // first row should have the column numbers
                    grid[row][col] = String.valueOf(DefaultMetaData.getColNumberAt(col - 1));
                } else {
                    if (col == 0) {
                        grid[row][col] = String.valueOf(DefaultMetaData.getRowLetterAt(row - 1));
                    } else {
                        grid[row][col] = Character.toString(DefaultMetaData.getSymbolAt(0));   // Assign '~' to each cell
                    }
                }
            }
        }
    }



    public String getGridCellAt(int row, int col) {
        if (row < 0 || row >= DefaultMetaData.getNumRow() + 1) {
            throw new IllegalArgumentException("Row out of bound (Grid)");
        }
        if (col < 0 || col >= DefaultMetaData.getNumCol() + 1) {
            throw new IllegalArgumentException("Column out of bound (Grid)");
        }
        return grid[row][col];
    }

    public String getGridCheatCellAt(int row, int col) {
        if (row < 0 || row >= DefaultMetaData.getNumRow() + 1) {
            throw new IllegalArgumentException("Row out of bound (Grid)");
        }
        if (col < 0 || col >= DefaultMetaData.getNumCol() + 1) {
            throw new IllegalArgumentException("Column out of bound (Grid)");
        }
        return letterGrid[row][col];
    }

    public int getGridRowLength() {
        return DefaultMetaData.getNumRow() + 1;
    }

    public int getGridColLength() {
        return DefaultMetaData.getNumCol() + 1;
    }

    public void setLetterGrid(int numToki, int numFoki) {
        char[][] lGrid = LetterGrid.assginGrid(numToki, numFoki);

        for (int row = 0; row <= DefaultMetaData.getNumRow(); row++) {
            for (int col = 0; col <= DefaultMetaData.getNumCol(); col++) {
                // first cell is empty
                if (row == 0 && col == 0) {
                    letterGrid[row][col] = String.valueOf(' ');
//                    continue;
                } else if (row == 0) {  // first row should have the column numbers
                    letterGrid[row][col] = String.valueOf(DefaultMetaData.getColNumberAt(col - 1));
                } else {
                    if (col == 0) {
                        letterGrid[row][col] = String.valueOf(DefaultMetaData.getRowLetterAt(row - 1));
                    } else {
                        letterGrid[row][col] = Character.toString(lGrid[row - 1][col - 1]);   // Assign '~' to each cell
                    }
                }
            }
        }

    }

    public void removeSymbol(char currentRow, int currentColumn) {
        validatePosition(currentRow, currentColumn);

        int row = getRowNum(currentRow);

        if (grid[row][currentColumn].length() > 1) {
            if (grid[row][currentColumn].charAt(1) == DefaultMetaData.getSymbolAt(3)) {
                grid[row][currentColumn] = String.valueOf(grid[row][currentColumn].charAt(0));
            }
        } else if (grid[row][currentColumn].equals(String.valueOf(DefaultMetaData.getSymbolAt(3)))) {
            // if the current symbol is "@"
            grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(2));
        }
    }

    /**
     *  Returns
     *  -1: found nothing or unexpected token
     *  0: found a Tokimon
     *  1: found a Fokimon
     */
    public int setPosition(char currentRow, int currentColumn) {
        validatePosition(currentRow, currentColumn);

        int row = getRowNum(currentRow);

// TODO: FINISH THIS

        String character = grid[row][currentColumn];
        String letterCharacter = letterGrid[row][currentColumn];

        // if '~' is found
        if (character.equals(String.valueOf(DefaultMetaData.getSymbolAt(0)))) {
            if (letterCharacter.equals(String.valueOf(DefaultMetaData.getGridValue(2)))) {
                // found nothing
                // new string value = " @"
                grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(3));
                return -1;
            } else if (letterCharacter.equals(String.valueOf(DefaultMetaData.getGridValue(0)))) {
                // found a tokimon
                // new stirng value = "$@"
                grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(1)) + // insert symbol $
                        DefaultMetaData.getSymbolAt(3);
                return 0;
            } else if (letterCharacter.equals(String.valueOf(DefaultMetaData.getGridValue(1)))) {
                // found a fokimon
                // new string value = "@"
                grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(3));
                return 1;
            }
        } else if (character.equals(String.valueOf(DefaultMetaData.getSymbolAt(1)))) { // if a tokimon was already found
            // make the new symbol = "$@"
            grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(1)) + // insert symbol $
                    DefaultMetaData.getSymbolAt(3);
            return -1;
        } else if (character.equals(String.valueOf(DefaultMetaData.getSymbolAt(2)))) {  // already visited location
            // make the symbol = "@"
            grid[row][currentColumn] = String.valueOf(DefaultMetaData.getSymbolAt(3));
            return -1;
        }   // dont do anything if the current character is "@"

        return -1;
    }

    private void validatePosition(char currentRow, int currentColumn) {
        char[] rowArr = DefaultMetaData.getRowLetters();
        if(Arrays.binarySearch(rowArr, Character.toUpperCase(currentRow)) < 0 ||
                Arrays.binarySearch(rowArr, Character.toUpperCase(currentRow)) >= rowArr.length) {
            System.out.println("First character should be a letter");
            throw new IllegalArgumentException("current row out of bound (grid)");
        }

        int[] colArr = DefaultMetaData.getColNumbers();
        if (Arrays.binarySearch(colArr, currentColumn) < 0 ||
                Arrays.binarySearch(colArr, currentColumn) >= colArr.length) {
            System.out.println("Number not found");
            throw new IllegalArgumentException("current column out of bounds (grid)");
        }
    }

    private int getRowNum(char currentRow) {
        int index = Arrays.binarySearch(DefaultMetaData.getRowLetters(), currentRow);
        return ++index;
    }
}

