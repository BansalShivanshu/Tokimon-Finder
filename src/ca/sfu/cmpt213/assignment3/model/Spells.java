package ca.sfu.cmpt213.assignment3.model;

/**
 * @author Shivanshu Bansal
 * Spells.java is responsible for handling various
 * spells that are available to a user throughout
 * the course of their gameplay
 *
 *  Does not have static methods because the class
 *  using this should contain a set of spells i.e. an object
 *
 *  Since the class is mutable and can potentially be
 *  instantiated at sever places, it is Singleton
 */
public class Spells {

    /*
    Member Variables
     */
    private String[] spells;
    private int[] timesUsed;

    /*
    Singleton Support
     */
    private static Spells instance;
    private Spells() {
        this.spells = new String[]{
                "Jump to another location",
                "Reveal location of a Tokimon",
                "Kill a Fokimon"
        };

        this.timesUsed = new int[] {
                0, 0, 0
        };
    }
    public static Spells getInstance() {
        if (instance == null) {
            instance = new Spells();
        }
        return instance;
    }


    /*
    Methods
     */

    /**
     *  Increments the time a spell has been used by one
     *     Acceptable Indices:
     *         0: Jump Spell
     *         1: Reveal Tokimon Spell
     *         2: Kill a Fokimon Spell
     */
    public void updateSpellUsed(int index) {
        if (index < 0 || index >= timesUsed.length) {
            throw new IllegalArgumentException("Index out of bound (Spells)");
        } else {
            timesUsed[index]++;
        }
    }

    /**
     *  Returns the spell at index
     *     Acceptable Indices:
     *         0: Jump Spell
     *         1: Reveal Tokimon Spell
     *         2: Kill a Fokimon Spell
     */
    public String getSpellAt(int index) {
        if (index < 0 || index >= spells.length) {
            throw new IllegalArgumentException("Index out of bounds (Spells)");
        } else {
            return spells[index];
        }
    }

    /**
     *  Return number of times spell at index has been used
     *     Acceptable Indices:
     *         0: Jump Spell
     *         1: Reveal Tokimon Spell
     *         2: Kill a Fokimon Spell
     */
    public int getNumTimesUsedAt(int index) {
        if (index < 0 || index >= timesUsed.length) {
            throw new IllegalArgumentException("Index out of bounds (Spells)");
        } else {
            return timesUsed[index];
        }
    }

    public int numSpells() {
        return spells.length;
    }


}
