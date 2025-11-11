package project1;

/**
 * Booking class, represents a booking of a vehicle by an eligble employee with a start and end date
 * Strores beging and end dates, the employee name and vehicle
 * @author JoshuaH, AlexG
 */
public class Booking {
    /** start of date */
    private Date begin;
    /** end of date */
    private Date end;
    /** employee who wants to book*/
    private Employee employee;
    /** vehicle that employee wants to book */
    private Vehicle vehicle;

    /**
     * Booking constructor, creates a mew Booking
     * @param begin , the start date
     * @param end , the end date
     * @param employee , the employee who did the booking
     * @param vehicle , the vehicle that is being booked
     */
    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    /**
     * Gets the begin date
     * @return , returns the begin date
     */
    public Date getBegin(){
        return begin;
    }

    /**
     * Gets the end date
     * @return , return the end date
     */
    public Date getEnd(){
        return end;
    }

    /**
     * gets the employee who is booking
     * @return , returns the employee
     */
    public Employee getEmployee(){
        return employee;
    }

    /**
     * gets the vehicle information of this booking
     * @return, returns the vehicle information
     */
    public Vehicle getVehicle(){
        return vehicle;
    }

    /**
     * Checks if booking parts matches the other booking
     * @param obj the reference object with which to compare.
     * @return true if all parts of booking matches the other booking
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)){
            return false;
        }
        Booking otherBook = (Booking) obj;
        return this.vehicle.equals(otherBook.vehicle) &&
                this.begin.equals(otherBook.begin) &&
                this.end.equals(otherBook.end);
    }

    /**
     * Returns the string representation of booking
     * @return the booking in string form
     */
    @Override
    public String toString() {
        return vehicle + " [beginning " + begin + " ending " + end + ":" + employee + "]";
    }
}
