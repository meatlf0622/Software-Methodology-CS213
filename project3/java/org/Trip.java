package org.example.project3_v3;

/**
 * Trip class, holds the information of a completed trip.
 * @author joshuaH, alexG
 */

public class Trip {
    /** The booking that this trip is on. */
    private Booking booking;
    /** The beginning mileage at the start of the trip. */
    private int beginMileage;
    /** The ending mileage at the end of the trip. */
    private int endMileage;

    /** Indicates whether a surcharge applies (pickup and drop-off differ). */
    private boolean surcharge;

    /** The pickup campus captured when the trip is created. */
    private Campus pickup;

    /**
     * constructs a trip with its booking and mileage details
     * @param booking, booking object that holds the booking information.
     * @param beginMileage, beginning mileage for the trip
     * @param endMileage, is the ending mileage for the trip
     * @param surcharge  true if the trip ended at a different campus (surcharge applies)
     */
    public Trip(Booking booking, int beginMileage, int endMileage, boolean surcharge) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
        this.surcharge = surcharge;
        this.pickup = booking.getVehicle().getCampus();
    }

    /**
     * Returns the booking associated with this trip.
     * @return the original booking
     */
    public Booking getBooking() { return booking; }

    /**
     * Returns the mileage before the trip.
     * @return the previous mileage reading
     */
    public int getMileageOld() { return beginMileage; }

    /**
     * Returns the mileage after the trip.
     * @return the updated mileage reading
     */
    public int getMileageNew() { return endMileage; }

    /**
     * Returns the pickup campus recorded when the trip was recorded.
     * @return the pickup campus
     */
    public Campus getPickup() { return pickup; }

    /**
     * Calculates and returns the number of miles used during the trip.
     * @return the mileage difference
     */
    public int used() {
        return endMileage - beginMileage;
    }

    /**
     * Returns whether the trip had a surcharge applied.
     * @return true if the drop off campus differed from pickup; otherwise false
     */
    public boolean hasSurcharge() {
        return surcharge;
    }
    /**
     * Compares this trip to another for equality based on their booking
     * @param obj the object to compare
     * @return true if both trips refer to the same booking; otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;
        Trip trip = (Trip) obj;
        return booking.equals(trip.booking);
    }



    /**
     * Returns a string representation of the trip
     * @return returns the trip int string format
     */

    @Override
    public String toString() {
        String drop = booking.getDropoff().displayName() + (surcharge ? "**" : "");
        return booking.getVehicle().getPlate() + " " +
                booking.getBegin() + " ~ " + booking.getEnd() +
                " mileage(old): " + beginMileage +
                " mileage(new): " + endMileage +
                " mileage(used): " + used() +
                " [dropped off: " + drop + "] [picked up: " + pickup.displayName() + "]";
    }
}
