package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.Grid;
import ca.sfu.cmpt213.assignment3.model.Spells;

import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * @author Shivanshu Bansal
 * Menu.java is responsile for displaying
 * and processing menu options which ultimately
 * facilitate user engagement.
 *
 * It collaborates with Spells to allow users
 * to use different spells during gameplay and
 * know their spell status
 */
public class Menu {
    /*
    Member Variables
     */
    private Spells spells;
    private int spellsLeft;


    /*
    Constructor
     */
    public Menu() {
        spells = Spells.getInstance();
        spellsLeft = spells.numSpells();
    }


    /*
    Methods
     */

    public void displayMenu() {
        System.out.println("Press:");
        System.out.println("\tW to Move Up");
        System.out.println("\tA to Move Left");
        System.out.println("\tS to Move Down");
        System.out.println("\tD to Move Right");
        System.out.println("\t1. Use a Spell");
        System.out.println("\t2. Print Grid");
        System.out.println("\t3. Exit");
    }

    public char getMenuOption() {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.print("Choice: ");
            choice = scanner.next().charAt(0);
        } while (!validateInput(choice));

        return choice;
    }

    public void displaySpells() {
        System.out.println("Spells:");
        if (spellsLeft == 0) {
            System.out.println("!*!*! No more spells left !*!*!");
        }
        for (int i = 0; i < spells.numSpells(); i++) {
            System.out.println("\t" + (i + 1) + ". " + spells.getSpellAt(i) +
                    " [" + (spells.getNumTimesUsedAt(i) == 0 ? "Available" : "Not Available") + "]"
            );
        }
        System.out.println("\t" + (spells.numSpells() + 1) + ". " + "Back");
    }

    /**
     * @return -1 if back option selected
     * @return  selection index
     * Also updates the number of times a spell has been used
     */
    public int getSpellSelection() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.print("Choice: ");
            choice = scanner.nextInt();

            if (choice == spells.numSpells() + 1) return -1;

        } while (!validateSpellInput(choice));

        // update number of times the spell has been used
        spells.updateSpellUsed(choice - 1);

        // decrement number of spells left
        spellsLeft--;

        return choice;
    }

    public int getSpellsLeft() {
        return spellsLeft;
    }


    /*
    Private Methods
     */
    private boolean validateInput(char choice) {
        if (Character.toUpperCase(choice) != 'W' &&
            Character.toUpperCase(choice) != 'A' &&
            Character.toUpperCase(choice) != 'S' &&
            Character.toUpperCase(choice) != 'D' &&
                choice != '1' && choice != '2' && choice != '3') {
            return false;
        }
        return true;
    }

    private boolean validateSpellInput(int choice) {
        if (choice < 1 || choice > spells.numSpells()) {
            return false;
        }

        // check if spell requested is available
        if (spells.getNumTimesUsedAt(choice - 1) != 0) {
            // spell is not available
            return false;
        }

        return true;
    }

}
