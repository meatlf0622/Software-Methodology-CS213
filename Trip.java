package project1;

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

    /**
     * constructs a trup with its booking and mileage details
     * @param booking, booking object that holds the booking information.
     * @param beginMileage, beginning mileage for the trip
     * @param endMileage, is the ending mileage for the trip
     */
    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }
    /**
     * gets booking
     * @return returns booking when needed
     */
    public Booking getBooking(){
        return booking;
    }

    /**
     * gets the beginning mileage
     * @return return beginning mileage when needed
     */
    public int getBeginMileage(){
        return beginMileage;
    }

    /**
     *  gets the end mileage
     * @return returns end mileage when needed
     */
    public int getEndMileage(){
        return endMileage;
    }

    /**
     * Checks if the two booking
     * @param obj   the reference object with which to compare.
     * @return false if the booking is not the same as the other booking, returns true if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) {
            return false;
        }

        Trip otherBooking = (Trip) obj;
        return this.booking.equals(otherBooking.booking);
    }

    /**
     * Returns a string representation of the trip
     * @return returns the trip int string format
     */
    @Override
    public String toString() {
        String plate = booking.getVehicle().getPlate();
        String begin = booking.getBegin().toString();
        String end = booking.getEnd().toString();
        int used = endMileage - beginMileage;
        return plate + " " + begin + " ~ " + end +
                " original mileage: " + beginMileage +
                " current mileage: " + endMileage +
                " mileage used: " + used;
    }
}
