package org.example.project3_v3;

import util.Date;

/**
 * Sedan enum class that holds the sedan object
 * @author joshuaH, alexG
 */
public class Sedan extends Vehicle {

    /**
     * Constructs a new Sedan object with the specified attributes.
     * @param plate    the license plate ID
     * @param obtained the date the vehicle was obtained
     * @param make     the make of the vehicle
     * @param mileage  the current odometer reading
     * @param campus   the current campus where the vehicle is located
     */
    public Sedan(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    /**
     * Calculates the base mileage charge for the trip.
     * @param mileageUsed the number of miles driven during the trip
     * @return the total base charge
     */
    @Override
    public double charge(int mileageUsed) {
        return 1.79 * mileageUsed;
    }

    /**
     * Calculates the surcharge when the vehicle is dropped off at a different campus.
     * If the drop off is at the same campus, the surcharge is none
     * @param mileageUsed  the number of miles used
     * @param dropOffDifferent  if dropped off at a different campus
     * @return the  surcharge, or 0 if not applicable
     */
    @Override
    public double surcharge(int mileageUsed, boolean dropOffDifferent) {
        if (!dropOffDifferent){
            return 0.0;
        }
        double fee = 0.25 * mileageUsed;
        return Math.min(fee, 32.99);
    }

    /**
     * Returns the string label representing this vehicle type.
     * @return the string sedan
     */
    @Override
    protected String vehicleTypeName() {
        return "sedan";
    }
}
