package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.DefaultMetaData;

import java.util.Arrays;
import java.util.Scanner;

public class TokimonFinder {

    /*
    Member Variables
     */
    private static int numToki;
    private static int numFoki;
    private static boolean hasFlagToki, hasFlagFoki, cheatMode;

    private static String initialPosition;

    private static Scanner scanner;


    /*
    Main
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);

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

        /*
        Initial Position
         */
        System.out.println("");
        do {
            System.out.print("Where would you like to Begin? (ex: B5): ");
            initialPosition = scanner.nextLine();
        } while (!validatePosition(initialPosition));


    }


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
        System.out.println("Column Letters: " + Arrays.toString(DefaultMetaData.getRowLetters()));
        System.out.println("Row Numbers: " + Arrays.toString(DefaultMetaData.getColNumbers()));
        System.out.println("Initial Position Correct Usage: [Row Letter][Column Number]");
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

    private static boolean validatePosition(String position) {
        if (position.length() < DefaultMetaData.getLowerPositionLength() ||
        position.length() > DefaultMetaData.getUpperPositionLength()) {
            System.out.println("ERROR: Incorrect Initial Position " + position);
            System.out.println("\tPotentially out of Range");
            System.out.println("Correct Usage: [Row Letter][Column Number]\n");
            return false;
        }

        char col = position.charAt(0);
        char[] colArr = DefaultMetaData.getRowLetters();
        if(Arrays.binarySearch(colArr, Character.toUpperCase(col)) < 0 ||
                Arrays.binarySearch(colArr, Character.toUpperCase(col)) >= colArr.length) {
            System.out.println("First character should be a letter");
            return false;
        }

        String row = position.substring(1);
        try {
            int temp = Integer.parseInt(row);
        } catch (NumberFormatException e) {
            System.out.println("Second character and beyond must be a number");
            return false;
        }

        return true;
    }
}
