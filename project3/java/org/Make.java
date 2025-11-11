package org.example.project3_v3;

/**
 * Make of vehicles that are taken by fleet
 * @author joshuaH, alexG
 */

public enum Make {
    /** Ford vehicles. */
    FORD,
    /** Chevy vehicles. */
    CHEVY,
    /** Toyota vehicles. */
    TOYOTA,
    /** Honda vehicles. */
    HONDA;

    /**
     * converts a text string into enum cosntants
     * @param s the text representing the vehicle make
     * @return make from the text, null if otherwise
     */
    public static Make fromText(String s) {
        if (s == null) return null;
        try {
            return Make.valueOf(s.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
