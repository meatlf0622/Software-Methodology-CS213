package util;

/**
 * Represents a calander with year month and day.
 * Provides validation, comparison and toString methods
 * @author joshuaH, alexG
 */

public class Date implements Comparable<Date> {

    /** The year */
    private int year;

    /** The month*/
    private int month;

    /** The day of the month */
    private int day;

    /**
     * Number of years in a quadrennial period, 4 years.
     */

    public static final int QUADRENNIAL = 4;

    /**
     * Number of years in a centennial period, 100 years.
     */

    public static final int CENTENNIAL = 100;

    /** Used for leap year re-inclusion: every 400 years. */
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Creates a new Date object
     * @param month, the month of date
     * @param day, the day of the date
     * @param year, the year of the date
     */

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Gets the year
     * @return returns the year when needed
     */
    public int getYear(){
        return year;
    }
    /**
     * gets the month
     * @return returns the month when needed
     */
    public int getMonth(){
        return month;
    }


    /**
     * gets the date
     * @return returns the day when needed
     */
    public int getDay(){
        return day;
    }

    /**
     * checks if the date is valid date on the calendar
     * @return returns true if the date is valid, false if otherwise
     */

    public boolean isValid() {
        if (month < 1 || month > 12) return false;
        if (day < 1) return false;

        int daysInMonth; // days in month
        switch (month) {
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                daysInMonth = 31;
                break;
            case 4: case 6: case 9: case 11:
                daysInMonth = 30;
                break;
            case 2:
                daysInMonth = isLeap() ? 29 : 28;
                break;
            default:
                return false;
        }
        return day <= daysInMonth;
    }

    /**
     * checks if the year is a leap year
     * @return true if it is
     */

    private boolean isLeap() {
        if (year % QUADRENNIAL != 0) return false;
        if (year % CENTENNIAL != 0) return true;
        return year % QUATERCENTENNIAL == 0;
    }
    /**
     * Compares this date to another date obj
     * @param other, the date that is being compared
     * @return returns - if it is earlier, + if it is later, and 0 if they are equal
     */

    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    /**
     * compares the contents of one date object to another to check if they are the same or not
     * @param obj   the reference object with which to compare.
     * @return true if the two dates are the same
     */

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Date)) return false;
        Date otherDate  = (Date) obj;
        return year == otherDate .year && month == otherDate .month && day == otherDate.day;
    }

    /**
     * Returns the string represntaion of the date in MM/DD/YYYY form.
     * @return, returns a string of the date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
