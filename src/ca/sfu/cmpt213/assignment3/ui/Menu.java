package ca.sfu.cmpt213.assignment3.ui;

import ca.sfu.cmpt213.assignment3.model.Spells;

/**
 *
 */
public class Menu {
    /*
    Member Variables
     */
    private Spells spells;


    /*
    Constructor
     */
    public Menu() {
        spells = Spells.getInstance();
    }


    /*
    Methods
     */

    public void displayMenu() {}

    public void getMenuOption() {

    }

    public void displaySpells() {
        System.out.println("Spells:");
        for (int i = 0; i < spells.numSpells(); i++) {
            System.out.println("\t" + (i + 1) + ". " + spells.getSpellAt(i) +
                    " [" + (spells.getNumTimesUsedAt(i) == 0 ? "Available" : "Not Available") + "]"
            );
        }
    }
}
