package org.example.project3_v3;

import util.Date;

/**
 * Represents a vehicle of type utility
 *     @author joshuaH, alexG
 */
public class Utility extends Vehicle {

    /**
     * Constructs a new utility vehicle with the given attributes.
     *
     * @param plate    the 6 character license plate
     * @param obtained the datethe vehicle was obtained
     * @param make     the make of the vehicle
     * @param mileage  the current odometer reading
     * @param campus   the campus where the vehicle is currently located
     */
    public Utility(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    /**
     * Calculates the base charge for a trip taken by this utility vehicle.
     * @param mileageUsed the number of miles driven during the trip
     * @return the base charge
     */
    @Override
    public double charge(int mileageUsed) {
        return 1.99 * mileageUsed;
    }

    /**
     * Calculates the surcharge when the drop-off campus differs from the pickup campus.
     * @param mileageUsed      the number of miles driven
     * @param dropOffDifferent true if drop-off campus differs from pickup campus
     * @return the surcharge amount
     */
    @Override
    public double surcharge(int mileageUsed, boolean dropOffDifferent) {
        if (!dropOffDifferent) return 0.0;
        double fee = 0.30 * mileageUsed;
        return Math.min(fee, 35.99);
    }

    /**
     * Returns the vehicle type name
     * @return the string utility
     */
    @Override
    protected String vehicleTypeName() {
        return "utility";
    }
}
