package project1;

/**
 * Represents a vehicle in the fleet
 * Stores license plates, the date obtained, the make, and mileage info, and updates it when needed
 * @author joshuaH, alexG
 */
public class Vehicle implements Comparable<Vehicle> {
    /** The license plate number of the vehicle. */
    private String plate;
    /** The date when the vehicle was obtained. */
    private Date obtained;
    /** The make (manufacturer) of the vehicle. */
    private Make make;
    /** The current odometer reading (mileage) of the vehicle. */
    private int mileage;

    /**
     * Construtor class that creates a new vehicle
     * @param plate, the license plate
     * @param obtained, the date the vehicle was obtaiene
     * @param make, the make of the vehicle
     * @param mileage, the current miles on the vehicle
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }

    /**
     * gets the license plate
     * @return , returns the plate of the object
     */
    public String getPlate(){
        return plate;
    }
    /**
     * gets the date that the vehicle was obtained
     * @return , returns the date of the object
     */
    public Date getObtained(){
        return obtained;
    }
    /**
     * gets the make of the vehicle
     * @return , returns the make of the object
     */
    public Make getMake(){
        return make;
    }
    /**
     * gets the mileage of the vehicle
     * @return , returns the mileage  of the object
     */
    public int getMileage(){
        return mileage;
    }

    /**
     * sets the mileage
     * @param mileage, the new mileage that will be used to update the current mileage
     */
    public void setMileage(int mileage){
        this.mileage = mileage;
    }

    /**
     * Compares current vehicle to another, comparing make and the obtained date
     * @param otherVehicle, the vehicle being compared.
     * @return returns - if current vehicle is < than the other, + if it is greater than the other, and 0 if equal.
     */
    @Override
    public int compareTo(Vehicle otherVehicle) {
        int makeCmp = this.make.toString().compareTo(otherVehicle.make.toString());
        if (makeCmp != 0){
            return makeCmp;
        }
        return this.obtained.compareTo(otherVehicle.obtained);
    }

    /**
     * makes sure the plate are the same as other plate.
     * @param obj   the reference object with which to compare.
     * @return returns true if they are equal and false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle)){
            return false;
        }
        Vehicle otherVehicle = (Vehicle) obj;
        return this.plate.equalsIgnoreCase(otherVehicle.plate);
    }

    /**
     * Makes a string representation of the vehicle
     * @return , return the string of the vehicle in a formatted way.
     */
    @Override
    public String toString(){
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    /**
     * Test cases to insure compareTO vehicles work
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println("Tests: ");

        Vehicle v1 = new Vehicle("12345A", new Date(1, 10, 2015), Make.FORD, 50000);
        Vehicle v2 = new Vehicle("12345B", new Date(5, 15, 2012), Make.HONDA, 40000);
        Vehicle v3 = new Vehicle("12345C", new Date(3, 20, 2015), Make.FORD, 30000);
        Vehicle v4 = new Vehicle("12345D", new Date(1, 10, 2015), Make.FORD, 60000);

        // Case 1: Different makes ford vs honda
        System.out.println("v1 vs v2: " + v1.compareTo(v2));
        // Expected: negative (because ford < honda alphabetically)

        // Case 2: Same make, compare by obtained date v1 vs v3
        System.out.println("v1 vs v3: " + v1.compareTo(v3));
        // Expected: negative (1/10/2015 is earlier than 3/20/2015)

        // Case 3: Same make and same date v1 vs v4
        System.out.println("v1 vs v4: " + v1.compareTo(v4));
        // Expected: 0 (both FORD, both 1/10/2015)

        // Case 4: Reverse ordering check v2 vs v1
        System.out.println("v2 vs v1: " + v2.compareTo(v1));
        // Expected: positive (HONDA > FORD)
    }

}
