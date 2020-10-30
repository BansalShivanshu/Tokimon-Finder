package ca.sfu.cmpt213.assignment3.model;

import java.util.Arrays;

public class LetterGrid {
    /**
     *  Assign positions to tokimons and fokimons on a matrix randomly
     *     PreConditios: Has determined number of tokimons and fokimons
     *                     to be placed
     *     Returns a matrix of character values where
     *         'T' stands for Tokimons
     *         'F' stands for Fokimons
     *         'N' stands for Nothing (Empty Location)
     */
    public static char[][] assginGrid(int numToki, int numFoki) {
        char[][] letterGrid = new char[DefaultMetaData.getNumRow()][DefaultMetaData.getNumCol()];

        // assign nothing value to al elements of the letterGrid
        assign_N(letterGrid);

        // Assign the tokimons
        while (numToki != 0) {
            // Math.random() * (max - min + 1) + min
            // Min in this case is 0
            // Max in this case is numColumn or numRow
            int col = (int) (Math.random() * (DefaultMetaData.getNumCol()));
            int row = (int) (Math.random() * (DefaultMetaData.getNumRow()));

            // check is anything at [row][col]
            if (letterGrid[row][col] == DefaultMetaData.getGridValue(2)) {
                // insert the 'T' value to indicate a tokimon
                letterGrid[row][col] = DefaultMetaData.getGridValue(0);
                numToki--;
            }
        }

        // Assign the fokimons
        while (numFoki != 0) {
            int col = (int) (Math.random() * (DefaultMetaData.getNumCol()));
            int row = (int) (Math.random() * (DefaultMetaData.getNumRow()));

            // check is anything at [row][col]
            if (letterGrid[row][col] == DefaultMetaData.getGridValue(2)) {
                // insert the 'T' value to indicate a fokimon
                letterGrid[row][col] = DefaultMetaData.getGridValue(1);
                numFoki--;
            }
        }

        return letterGrid;
    }

    /*
    Assign the value 'N' to all fields of matrix letterGrid
     */
    private static void assign_N(char[][] letterGrid) {
        for (int row = 0; row < DefaultMetaData.getNumRow(); row++) {
            for (int col = 0; col < DefaultMetaData.getNumCol(); col++) {
                letterGrid[row][col] = DefaultMetaData.getGridValue(2); // put the 'N' value
            }
        }
    }
}
