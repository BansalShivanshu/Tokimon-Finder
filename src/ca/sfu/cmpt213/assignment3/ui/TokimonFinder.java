package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.DefaultMetaData;

public class TokimonFinder {

    private static int numToki;
    private static int numFoki;
    private static boolean hasFlagToki, hasFlagFoki, cheatMode;

    public static void main(String[] args) {
        if (args.length == 0) {
            initializeDefaults();
        } else {
            validateArgs(args);
        }

        System.out.println("nunToki " + numToki);
        System.out.println("numFoki " + numFoki);
        System.out.println("cheatMode " + cheatMode);
        System.out.println("flagToki " + hasFlagToki);
        System.out.println("flagFoki " + hasFlagFoki);
    }



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
                System.out.println("ERROR: Incorrect Flag: " + arg);
                System.out.println("Acceptable flags:");
                System.out.println("\t--numToki=X");
                System.out.println("\t--numFoki=X");
                System.out.println("\t--cheat");
                System.out.println("Exit Code : 2001");
                System.exit(2001); // status code for invalid argument
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
                int tokimons = Integer.parseInt(arg.substring(10));

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
                int fokimons = Integer.parseInt(arg.substring(10));

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
            }
        } else { // incorrect flag
            System.out.println("ERROR: Incorrect Flag: " + arg);
            System.out.println("Acceptable flags:");
            System.out.println("\t--numToki=X");
            System.out.println("\t--numFoki=X");
            System.out.println("\t--cheat");
            System.out.println("Exit Code : 2001");
            System.exit(2001); // status code for invalid argument
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
}
