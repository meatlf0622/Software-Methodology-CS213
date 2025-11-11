package org.example.project3_v3;

/**
 * emum class of departments in company
 * @author joshuaH, alexG
 */

public enum Department {

    /** Computer Science department. */
    CS("Computer Science"),
    /** Electrical Engineering department. */
    EE("Electrical Engineering"),
    /** Information Technology and Informatics department. */
    ITI("Information Technology and Informatics"),
    /** Mathematics department. */
    MATH("Mathematics"),
    /** Business Analytics and Information Technology department. */
    BAIT("Business Analytics and Information Technology");

    /** The full name of the department. */
    private final String name;

    /**
     * creates the department with the name it belongs to.
     * @param name , name of department
     */

    Department(String name) {
        this.name = name;
    }

    /**
     * returns the department name
     * @return name of department
     */

    @Override
    public String toString() {
        return name;
    }
}
