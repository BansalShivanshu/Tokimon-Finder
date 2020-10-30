package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.LetterGrid;
import ca.sfu.cmpt213.assignment3.model.DefaultMetaData;
import ca.sfu.cmpt213.assignment3.model.Grid;

import java.util.Arrays;
import java.util.Scanner;

public class TokimonFinder {

    /*
    Member Variables
     */
    private static int numToki;
    private static int numFoki;
    private static boolean hasFlagToki, hasFlagFoki, cheatMode;

    private static String position;
    private static char currentRow;
    private static int currentColumn;

    private static Scanner scanner;

    private static Menu menu;
    private static Grid grid;


    /*
    Main
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        menu = new Menu();

        /*
        Initialisation Stage
         */
        if (args.length == 0) {
            initializeDefaults();
        } else {
            validateArgs(args);
        }

        // Welcome the user
        welcome();

        // set the randomly generated grid with respect to
        // the number of Tokimons and Fokimons
        grid = Grid.getInstance();
        grid.setLetterGrid(numToki, numFoki);
//        char[][] letterGrid = LetterGrid.assginGrid(numToki, numFoki);

        // Print the grid before asking where to start
        // so that the user can have an idea of what the grid looks like
        if (cheatMode) {
            printCheatGrid();
        } else {
            printBaseGrid();
        }

        /*
        Get Initial Position
         */
        System.out.println("");
        do {
            System.out.print("Where would you like to Begin? (ex: B5): ");
            position = scanner.nextLine();
        } while (!validatePosition(position));


        int retVal = grid.setPosition(currentRow, currentColumn);
        System.out.println(retVal);
        printBaseGrid();
        grid.removeSymbol(currentRow, currentColumn);
        printBaseGrid();




        // set the position on the grid
//        grid.setPosition(currentRow, currentColumn);




    }   // end of main


    /*
    Welcome Code
     */
    private static void welcome() {
        System.out.println("");
        System.out.println("********** Welcome to the Tokimon Finder Game **********");
        System.out.println("****** Enjoy the Experience as a Tokimon Trainer ******");
        System.out.println("");

        System.out.println("Correct Usage: java -jar [path to jar file] [potential --numToki=X flag] " +
                "[potential --numFoki=X flag] [potential --cheat mode flag]");
        System.out.println("Flags Avaiable:");
        System.out.println("\t--numToki=X for defining number of Tokimons (>= 5)");
        System.out.println("\t--numFoki=X for defining number of Fokimons (>= 5");
        System.out.println("\t--cheat for turning cheat mode on (displays all locations at start)");
        System.out.println("\t\tIn cheat mode T = Tokimon, F = Fokimon, N = Nothing");

        System.out.println("Column Letters: " + Arrays.toString(DefaultMetaData.getRowLetters()));
        System.out.println("Row Numbers: " + Arrays.toString(DefaultMetaData.getColNumbers()));
        System.out.println("Initial Position Correct Usage: [Row Letter][Column Number]");
        System.out.println("");
    }

    /*
    Validation Code
     */
    private static void initializeDefaults() {
        numToki = DefaultMetaData.getNumToki();
        numFoki = DefaultMetaData.getNumFoki();
        cheatMode = false;
    }

    private static void validateArgs(String[] args) {
        // check if number of acceptable arguments are exceeded
        if (args.length > DefaultMetaData.getArgLength()) {
            System.out.println("ERROR: Incorrect Usage");
            System.out.println("Correct Usage: java -jar [path to jar file] [potential --numToki=X flag] " +
                    "[potential --numFoki=X flag] [potential --cheat mode flag]");
            System.out.println("Exit Code : 1001");
            System.exit(1001); // status code for invalid usage
        }

        // check if flags are proper
        for (String arg : args) {
            if (arg.length() != 7 && arg.length() < 11) {       // --numToki=X will be of length at least 11 and --cheat of 7
                incorrectFlags(arg);
            } else if (arg.substring(0, 2).equals("--")) {
                validateFlag(arg);
            }
        }

        checkNullFlags();
    }

    private static void validateFlag(String arg) {
        if (arg.length() == 7 && arg.substring(2, 7).equals("cheat")) {
            if (cheatMode) {
                System.out.println("Repetition of flags not allowed");
                System.out.println("Exit Code : 2001");
                System.exit(2001);
            }
            cheatMode = true;
        } else if (arg.length() >= 11) {

            if (arg.substring(2, 9).equals("numToki")) {

                // validate the number of Tokimons
                int tokimons = 0;
                try {
                    tokimons = Integer.parseInt(arg.substring(10));
                } catch (NumberFormatException e) {
                    System.out.println("Integer Value Expected");
                    System.out.println("Error Code: 1111");
                    System.exit(1111);
                }


                if (hasFlagToki) {
                    System.out.println("Repetition of flags not allowed");
                    System.out.println("Exit Code : 2002");
                    System.exit(2001);
                } else if (tokimons < DefaultMetaData.getLowestNumToki() || tokimons > DefaultMetaData.getLargestNumToki()) {
                    System.out.println("Invalid number of tokimons");
                    System.out.println("Range of Tokimons: [" + DefaultMetaData.getLowestNumToki() + ", " +
                            DefaultMetaData.getLargestNumToki() + "]");
                    System.out.println("Exit Code : 2002");
                    System.exit(2002); // code for invalid tokimon number
                } else if (tokimons > DefaultMetaData.getLargestNumToki() - numFoki) {
                    System.out.println("Number of Tokimons + Fokimons > Largest number possible(" +
                            DefaultMetaData.getLargestNumToki() + ")");
                    System.out.println("Exit Code : 2002");
                    System.exit(2002);
                } else {
                    hasFlagToki = true;
                    numToki = tokimons;
                }

            } else if (arg.substring(2, 9).equals("numFoki")) {

                // validate the number of Fokimons
                int fokimons = 0;
                try {
                    fokimons = Integer.parseInt(arg.substring(10));
                } catch (NumberFormatException e) {
                    System.out.println("Integer Value Expected");
                    System.out.println("Error Code: 1111");
                    System.exit(1111);
                }


                if (hasFlagFoki) {
                    System.out.println("Repetition of flags not allowed");
                    System.out.println("Exit Code : 2001");
                    System.exit(2001);
                } else if (fokimons < DefaultMetaData.getLowestNumFoki() || fokimons > DefaultMetaData.getLargestNumFoki()) {
                    System.out.println("Invalid number of fokimons");
                    System.out.println("Range of Fokimons: [" + DefaultMetaData.getLowestNumFoki() + ", " +
                            DefaultMetaData.getLargestNumFoki() + "]");
                    System.out.println("Exit Code : 2003");
                    System.exit(2003);
                } else if (fokimons > DefaultMetaData.getLargestNumFoki() - numToki) {
                    System.out.println("Number of Fokimons + Tokimons > Largest number possible(" +
                            DefaultMetaData.getLargestNumFoki() + ")");
                    System.out.println("Exit Code : 2003");
                    System.exit(2003);
                } else {
                    hasFlagFoki = true;
                    numFoki = fokimons;
                }
            } else {
                incorrectFlags(arg);
            }
        } else { // incorrect flag
            incorrectFlags(arg);
        }
    }

    private static void checkNullFlags() {
        if (!hasFlagToki) {     // if no numToki flag set to default
            numToki = DefaultMetaData.getNumToki();
        }
        if (!hasFlagFoki) {     // if no numFoki flag set to default
            numFoki = DefaultMetaData.getNumFoki();
        }
    }

    private static void incorrectFlags(String arg) {
        System.out.println("ERROR: Incorrect Flag: " + arg);
        System.out.println("Acceptable flags:");
        System.out.println("\t--numToki=X");
        System.out.println("\t--numFoki=X");
        System.out.println("\t--cheat");
        System.out.println("Exit Code : 2001");
        System.exit(2001); // status code for invalid argument
    }

    /*
    Print the basic grid taken from the Grid.java
     */
    private static void printBaseGrid() {
        for (int row = 0; row < grid.getGridRowLength(); row++) {
            for (int col = 0; col < grid.getGridColLength(); col++) {
                System.out.print(grid.getGridCellAt(row, col) + "\t");
            }
            System.out.println("");
        }
    }

    /*
    Print the cheat grid taken from the Grid.java
     */
    private static void printCheatGrid() {
        for (int row = 0; row < grid.getGridRowLength(); row++) {
            for (int col = 0; col < grid.getGridColLength(); col++) {
                System.out.print(grid.getGridCheatCellAt(row, col) + "\t");
            }
            System.out.println("");
        }
    }

    private static boolean validatePosition(String position) {
        if (position.length() < DefaultMetaData.getLowerPositionLength() ||
        position.length() > DefaultMetaData.getUpperPositionLength()) {
            System.out.println("ERROR: Incorrect Initial Position " + position);
            System.out.println("\tPotentially out of Range");
            System.out.println("Correct Usage: [Row Letter][Column Number]\n");
            return false;
        }

        char row = position.charAt(0);
        char[] rowArr = DefaultMetaData.getRowLetters();
        if(Arrays.binarySearch(rowArr, Character.toUpperCase(row)) < 0 ||
                Arrays.binarySearch(rowArr, Character.toUpperCase(row)) >= rowArr.length) {
            System.out.println("First character should be a letter");
            return false;
        }
        currentRow = Character.toUpperCase(row);

        String column = position.substring(1);
        try {
            currentColumn = Integer.parseInt(column);
        } catch (NumberFormatException e) {
            System.out.println("Second character and beyond must be a number");
            return false;
        }
        int[] colArr = DefaultMetaData.getColNumbers();
        if (Arrays.binarySearch(colArr, currentColumn) < 0 ||
            Arrays.binarySearch(colArr, currentColumn) >= colArr.length) {
            System.out.println("Number not found");
            return false;
        }


        return true;
    }


}
