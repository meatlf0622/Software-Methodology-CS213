package org.example.project3_v3;

import util.Date;

/**
 *Booking class, represent a booking of a vehicle by an eligble employee with a start and a end dat,
 * Stroes beginning and end dates, employee, vehicle and dropoff
 * @author joshuaH, alexG
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
     * campus that vehicle that is booked is being dropped off on
     */
    private Campus dropoff;

    /**
     * Constructs a new booking object
     *
     * @param begin    the start date
     * @param end      the end date
     * @param employee  the employee who did the booking
     * @param vehicle   the vehicle being booked
     * @param dropoff   the drop off campus for this booking
     */
    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle, Campus dropoff) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
        this.dropoff = dropoff;
    }

    /**
     * Returns the beginning date of the booking
     * @return the booking's start date
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * Returns the ending date of the booking
     * @return the booking's end date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Returns the employee who made the booking
     * @return the employee who booked the vehicle
     */
    public Employee getEmployee(){
        return employee;
    }

    /**
     * Returns the vehicle assigned to booking
     * @return the booked vehicle
     */
    public Vehicle getVehicle(){
        return vehicle;
    }

    /**
     * Returns the drop off campus for the booked vehicle.
     * @return the drop off campus
     */
    public Campus getDropoff() {
        return dropoff;
    }

    /**
     * Checks if booking parts matches the other booking
     * @param obj the reference object with which to compare.
     * @return true if all parts of booking matches the other booking
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)) return false;
        Booking b = (Booking) obj;
        return vehicle.equals(b.vehicle)
                && begin.equals(b.begin)
                && end.equals(b.end);
    }

    /**
     * Returns the string representation of booking
     * @return the booking in string form
     */

    @Override
    public String toString() {
        // Example: 71707X:TOYOTA [Cook:New Brunswick] 11/3/2025 ~ 11/3/2025 [drop off:Cook] [RAMESH]
        return vehicle.getPlate() + ":" + vehicle.getMake() +
                " [" + vehicle.getCampus().displayName() + ":" + vehicle.getCampus().city() + "] " +
                begin + " ~ " + end +
                " [drop off:" + dropoff.displayName() + "] [" + employee.name() + "]";
    }
}
