package project1;

/**
 * Represents a fleet of vehicles
 * Stroes the vehicles in an array so we can use the add, remove, and search operations easily
 * @author alexG, joshuaH
 */
public class Fleet {
    /** The initial capacity of the fleet array. */
    private static final int CAPACITY = 4;
    /** Special value used when a vehicle is not found in the fleet. */
    private static final int NOT_FOUND = -1;
    /** The array holding the fleet of vehicles. */
    private Vehicle[] fleet;
    /** The current number of vehicles in the fleet */
    private int size;

    /**
     * Creates a new fleet
     */
    public Fleet() {
        this.fleet = new Vehicle[CAPACITY];
        this.size = 0;
    }

    /**
     * Finds the index of a vehicle using plate number using .equal in vehicle class
     * @param vehicle obj that we use to see if the vehicle is in the fleet
     * @return , returns 1 if the vehicle is in the fleet, and -1 otherwise
     */
    private int find(Vehicle vehicle) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].equals(vehicle)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * adds the new vehicle into the fleet
     * checks if the vehicle is in the fleet, if so then returns, if not adds the vehicle
     * also checks if the size needs to be increase or not
     * @param vehicle , the vehicle needed to be added
     */
    public void add(Vehicle vehicle) {
        if (contains(vehicle)){
            return;
        }
        if (size == fleet.length){
            grow();
        }
        fleet[size++] = vehicle;
    }

    /**
     *Grows the array by capacity
     */
    private void grow() {
        Vehicle[] newFleet = new Vehicle[fleet.length + CAPACITY];
        for(int i = 0; i < size; i++){
            newFleet[i] = fleet[i];
        }
        fleet = newFleet;
    }

    /**
     * removes the vehicle by overwriting the index with the last element
     * @param vehicle the vehicle that is to be removed
     */
    public void remove(Vehicle vehicle) {
        int idx = find(vehicle);
        if (idx == NOT_FOUND){
            return;
        }
        fleet[idx] = fleet[size - 1];
        fleet[size - 1] = null;
        size--;
    }

    /** Returns the actual Vehicle instance by plate, and null if not found */
    public Vehicle get(String plate) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].getPlate().equalsIgnoreCase(plate)){
                return fleet[i];
            }
        }
        return null;
    }

    /**
     * Checks if the vehicle is in the fleet
     * @param vehicle, vehicle to be checked
     * @return true if found, false otherwise
     */
    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOT_FOUND;
    }

    /**
     * prints the vehicles in the fleet, by make
     */
    public void printByMake() {
        if (size == 0) {
            System.out.println("There is no vehicle in the fleet.");
            return;
        }
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (fleet[j].compareTo(fleet[min]) < 0){
                    min = j;
                }
            }
            if (min != i) {
                Vehicle tmp = fleet[i];
                fleet[i] = fleet[min];
                fleet[min] = tmp;
            }
        }
        System.out.println("*List of vehicles in the fleet, ordered by make and date obtained.");
        for (int i = 0; i < size; i++){
            System.out.println(fleet[i]);
        }
        System.out.println("*end of list.");
        System.out.println();
    }
}
