package org.example.project3_v3;

import util.Date;

/**
 * Represents a vehicle in the fleet
 * Stores license plates, the date obtained, the make, and mileage info, and updates it when needed
 * @author joshuaH, alexG
 */

public abstract class Vehicle implements Comparable<Vehicle> {

    /** The license plate number of the vehicle. */
    private String plate;
    /** The date when the vehicle was obtained. */
    private Date obtained;
    /** The make (manufacturer) of the vehicle. */
    private Make make;
    /** The current odometer reading (mileage) of the vehicle. */
    private int mileage;
    /** Current campus location. */
    protected Campus campus;

    /**
     * Construtor class that creates a new vehicle
     * @param plate, the license plate
     * @param obtained, the date the vehicle was obtaiene
     * @param make, the make of the vehicle
     * @param mileage, the current miles on the vehicle
     * @param campus   the campus where the vehicle is currently located
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage, Campus campus) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
        this.campus = campus;
    }

    /** @return the license plate  */
    public String getPlate() {
        return plate;
    }

    /** @return the acquisition date */
    public Date getObtained() {
        return obtained;
    }

    /** @return the make */
    public Make getMake() {
        return make;
    }

    /** @return the current mileage  */
    public int getMileage() {
        return mileage;
    }

    /** @return the current campus location */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Updates the mileage
     * @param m the new mileage value
     */
    public void setMileage(int m) {
        mileage = m;
    }

    /**
     * Updates the current campus location.
     * @param c the new camppus where the vehicle is parked
     */
    public void setCampus(Campus c) {
        campus = c;
    }

    /**
     * Calculates the base per-mile charge for a completed trip.
     * @param mileageUsed the number of miles driven during the trip
     * @return the base charge amount
     */
    public abstract double charge(int mileageUsed);

    /**
     * Calculates any applicable surcharge for the trip.
     * @param mileageUsed  the number of miles driven
     * @param surchargeApplied true  if drop off campus differs from pickup
     * @return the surcharge amount
     */
    public abstract double surcharge(int mileageUsed, boolean surchargeApplied);

    /**
     * Compares this vehicle with another for ordering.
     * @param o the other vehicle to compare
     * @return a negative integer, zero, or a positive integer if this vehicle
     *         is less than, equal to, or greater than the specified vehicle
     */
    @Override
    public int compareTo(Vehicle o) {
        int byMake = this.make.toString().compareTo(o.make.toString());
        if (byMake != 0){
            return byMake;
        }
        return this.obtained.compareTo(o.obtained);
    }

    /**
     * Checks equality between two vehicles based on license plate.
     * @param obj the object to compare
     * @return true if both vehicles have the same plate
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) obj;
        return this.plate.equalsIgnoreCase(vehicle.plate);
    }

    /**
     * Returns the string representation of a vehicle in the required output format.
     * @return a formatted {@code String} describing the vehicle
     */
    @Override
    public String toString() {
        return plate + "[" + make + ":" + vehicleTypeName() + "] " + obtained +
                " [mileage:" + mileage + "] [" + campus.displayName() + ":" + campus.city() + "]";
    }

    /**
     * Returns the lowercase type name for use in formatted output.
     * @return the string name of this vehicle type
     */
    protected abstract String vehicleTypeName();

    /**
     * Factory method that constructs the correct vehicle subclass
     * based on the final character of the license plate.
     * @param plate    the license plate identifier
     * @param obtained the date obtained
     * @param make     the vehicle make
     * @param mileage  the mileage
     * @param campus   the current campus location
     * @return the corresponding  vehicle object, or null if invalid
     */
    public static Vehicle fromPlateAndBasics(String plate, Date obtained, Make make, int mileage, Campus campus) {
        char t = Character.toUpperCase(plate.charAt(5));
        switch (t) {
            case 'S': return new Sedan(plate, obtained, make, mileage, campus);
            case 'D': return new Utility(plate, obtained, make, mileage, campus);
            case 'X': return new Truck(plate, obtained, make, mileage, campus);
            default:  return null;
        }
    }

    /**
     * Validates the format of a license plate string.
     * @param plate the license plate to validate
     * @return null if valid  otherwise an error message string
     */
    public static String validatePlate(String plate) {
        if (plate == null || plate.length() != 6){
            return "license plate number must be exactly 6 characters.";
        }

        for (int i = 0; i < 5; i++)
            if (!Character.isDigit(plate.charAt(i)))
                return "first 5 characters must be numbers.";
        char last = Character.toUpperCase(plate.charAt(5));
        if (!(last == 'S' || last == 'D' || last == 'X'))
            return "last character is not a valid vehicle type.";
        return null;
    }
}
