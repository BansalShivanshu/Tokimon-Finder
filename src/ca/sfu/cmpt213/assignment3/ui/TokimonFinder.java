package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.DefaultMetaData;
import ca.sfu.cmpt213.assignment3.model.Grid;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Shivanshu Bansal
 * This is the main class of the project and is
 * responsible for first hand communication with
 * the user.
 *
 * It is responsible for accepting and processing
 * input, validation of arguments, collaborating
 * with Grid class to facilitate manipulation of
 * the grid as the game progresses, and with menu
 * to facilitate user engagement.
 */
public class TokimonFinder {

    /*
    Member Variables
     */
    private static int numToki;
    private static int numFoki;
    private static boolean hasFlagToki, hasFlagFoki, cheatMode;
    private static int tokimonsCollected = 0;

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
        getNewLocation();


        int retVal = grid.setPosition(currentRow, currentColumn);
        if (retVal == 0) {
            tokimonFound();
        } else if (retVal == 1) {
            fokimonFound();
        }
        printBaseGrid();


        while (true) {
            menu.displayMenu();

            char choice = menu.getMenuOption();

            if (Character.toUpperCase(choice) == 'W') {
                // if already in the first row
                if (currentRow == DefaultMetaData.getRowLetterAt(0)) {
                    System.out.println("Cannot move up");
                    System.out.println("");
                    printBaseGrid();
                    continue;
                }
                // else
                moveUp();
            } else if (Character.toUpperCase(choice) == 'A') {
                // if already on the first column
                if (currentColumn == DefaultMetaData.getColNumberAt(0)) {
                    System.out.println("Cannot move left");
                    System.out.println("");
                    printBaseGrid();
                    continue;
                }
                // else
                moveLeft();
            } else if (Character.toUpperCase(choice) == 'S') {
                // if already on the last row
                if (currentRow == DefaultMetaData.getRowLetterAt(
                        DefaultMetaData.getNumRow() - 1
                )) {
                    System.out.println("Cannot move down");
                    System.out.println("");
                    printBaseGrid();
                    continue;
                }
                // else
                moveDown();
            } else if (Character.toUpperCase(choice) == 'D') {
                // if already on the last column
                if (currentColumn == DefaultMetaData.getColNumberAt(
                        DefaultMetaData.getNumCol() - 1
                )) {
                    System.out.println("Cannot move right");
                    System.out.println("");
                    printBaseGrid();
                    continue;
                }
                // else
                moveRight();
            } else if (choice == '1') {
                menu.displaySpells();
                int selection = menu.getSpellSelection();

                if (selection == -1) {
                    continue;
                }

                if (selection == 1) {
                    // remove from current position
                    grid.removeSymbol(currentRow, currentColumn);

                    // jump to another location
                    getNewLocation();
                } else if (selection == 2) {
                    // reveal tokimon
                    revealTokimon();
                    printBaseGrid();
                    continue;
                } else if (selection == 3) {
                    killFokimon();
                    printBaseGrid();
                    continue;
                }

            } else if (choice == '2') {
                printBaseGrid();
                continue;
            } else if (choice == '3') {
                printCheatGrid();
                System.out.println("GoodBye!");
                break;
            }

            // set the new position
            retVal = grid.setPosition(currentRow, currentColumn);
            if (retVal == 0) {
                tokimonFound();
            } else if (retVal == 1) {
                fokimonFound();
            }

            printBaseGrid();
        }

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
        System.out.println("");
        System.out.println("Starting out with " + numToki + " Tokimons and " + numFoki + " Fokimons");
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

    private static void getNewLocation() {
        do {
            System.out.print("Where would you like to Begin? (ex: B5): ");
            position = scanner.nextLine();
        } while (!validatePosition(position));


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

        System.out.println("Number of Tokimons Collected: " + tokimonsCollected);
        System.out.println("Number of Tokimons Left: " + numToki);
        System.out.println("Number of Fokimons Left: " + numFoki);
        System.out.println("Number of Spells Left  : " + spellsLeft());
        System.out.println("");
    }

    private static int spellsLeft() {
        return menu.getSpellsLeft();
    }

    /*
    Print the cheat grid taken from the Grid.java
     */
    private static void printCheatGrid() {
        System.out.println("    $ == Tokimon | X == Fokimon");
        System.out.println("");
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

    private static void moveUp() {
        // remove current position pointer
        grid.removeSymbol(currentRow, currentColumn);

        int indexOfRow = Arrays.binarySearch(DefaultMetaData.getRowLetters(),
                currentRow);
        // decrement the index
        indexOfRow--;
        currentRow = DefaultMetaData.getRowLetterAt(indexOfRow);
    }

    private static void moveLeft() {
        // remove current position pointer
        grid.removeSymbol(currentRow, currentColumn);

        int indexOfCol = Arrays.binarySearch(DefaultMetaData.getColNumbers(),
                currentColumn);
        // decrement the index
        indexOfCol--;
        currentColumn = DefaultMetaData.getColNumberAt(indexOfCol);
    }

    private static void moveDown() {
        // remove current position pointer
        grid.removeSymbol(currentRow, currentColumn);

        int indexOfRow = Arrays.binarySearch(DefaultMetaData.getRowLetters(),
                currentRow);
        // increment the index
        indexOfRow++;
        currentRow = DefaultMetaData.getRowLetterAt(indexOfRow);
    }

    private static void moveRight() {
        // remove current position pointer
        grid.removeSymbol(currentRow, currentColumn);

        int indexOfCol = Arrays.binarySearch(DefaultMetaData.getColNumbers(),
                currentColumn);
        // decrement the index
        indexOfCol++;
        currentColumn = DefaultMetaData.getColNumberAt(indexOfCol);
    }

    private static void tokimonFound() {
        // decrement tokimon
        numToki--;

        // increment the tokimons collected
        tokimonsCollected++;

        // congratulate user
        System.out.println("You Just Found a Tokimon!");

        // check if won the game
        if (numToki == 0) {
            printCheatGrid();

            System.out.println("YOU WON THE GAME!!!");
            System.out.println("!*!*!*!* CONGRATULATIONS *!*!*!*!");

            System.exit(0);
        }
    }

    private static void fokimonFound() {
        // decrement fokimon
        numFoki--;

        // notify the user
        System.out.println("!!!*** You just landed on a FOKIMON ***!!!");
        System.out.println("--------------- Game Over! ---------------");

        grid.showAFokimon(currentRow, currentColumn);
        printCheatGrid();

        System.exit(0);
    }

    private static void revealTokimon() {
        boolean revealed = grid.revealTokimon();
        if (revealed) {
            tokimonFound();
        } else {
            System.out.println("No tokimon to reveal");
            System.out.println("Unexpected Error Occurred");
        }
    }

    private static void killFokimon() {
        // decrement number of fokimons
        numFoki--;

        grid.killFokimon();

        System.out.println("               !!! A Fokimon has been Killed !!!");
        System.out.println("The location it had previously acquired is replaced now with blank space");;
    }
}
