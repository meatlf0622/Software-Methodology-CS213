package org.example.project3_v3;

import util.Date;

/**
 * Represents a vehicle of type truck
 * @author joshuaH, alexG
 */
public class Truck extends Vehicle {

    /**
     * Constructs a new {@code Truck} with the specified vehicle details.
     *
     * @param plate    the 6 character license plate ID
     * @param obtained the date when the vehicle was acquired
     * @param make     the make of the truck
     * @param mileage  the current odometer reading
     * @param campus   the campus where the truck is currently located
     */
    public Truck(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }
    /**
     * Calculates the base charge for this truck trip.
     * @param mileageUsed the number of miles driven during the trip
     * @return the total base charge
     */
    @Override
    public double charge(int mileageUsed) {
        return 2.99 * mileageUsed;
    }

    /**
     * Returns the flat surcharge for drop-off at a different campus.
     * @param mileageUsed      the number of miles driven
     * @param dropOffDifferent true  if dropped off at a different campus
     * @return 39.99 if different campuses; otherwise 0
     */
    @Override
    public double surcharge(int mileageUsed, boolean dropOffDifferent) {
        return dropOffDifferent ? 39.99 : 0.0;
    }

    /**
     * Returns the type name for vehicle
     * @return the string truck
     */
    @Override
    protected String vehicleTypeName() {
        return "truck";
    }
}
