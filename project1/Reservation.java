package project1;
/**
 * Represents the collection of the bookings
 * stores the bookings in an array and uses methods add, cancel and print for ease
 * @author alexG, joshuaH
 */
public class Reservation {
    /** The initial capacity of the bookings array. */
    private static final int CAPACITY = 4;
    /** Negative value returned when a booking is not found. */
    private static final int NOT_FOUND = -1;
    /** The array of bookings in this reservation. */
    private Booking[] bookings;
    /** The current number of bookings. */
    private int size;

    /**
     * creates a new reservation
     */
    public Reservation() {
        bookings = new Booking[CAPACITY];
        size = 0;
    }

    /**
     * checks if the booking is currently in the reservation
     * @param booking, booking is the obj that we are trying to find if it is in the system
     * @return returns the index if the booking is found, if not then it returns -1
     */
    private int find(Booking booking) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].equals(booking)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * doubles the size of the booking array when it is full
     */
    private void grow() {
        Booking[] newBook = new Booking[bookings.length + CAPACITY];
        for(int i = 0; i < size; i++){
            newBook[i] = bookings[i];
        }
        bookings = newBook;
    }

    /**
     * adds a booking to the resevation
     * @param booking booking to be added
     */
    public void add(Booking booking) {
        if (size == bookings.length) grow();
        bookings[size++] = booking;
    }

    /**
     * removes a booking from the reservation
     * @param booking, booking to be removed
     */
    public void remove(Booking booking) {
        int idx = find(booking);
        if (idx == NOT_FOUND) return;
        bookings[idx] = bookings[size - 1];
        bookings[size - 1] = null;
        size--;
    }

    /**
     * cgecks to see if the booking is already in the reservation
     * @param booking , booking to be searched
     * @return true if found and -1 or false if otherwise
     */
    public boolean contains(Booking booking){
        return find(booking) != NOT_FOUND;
    }

    /**
     * True if there exists ANY booking for the given vehicle plate.
     * */
    public boolean hasAnyForPlate(String plate) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].getVehicle().getPlate().equalsIgnoreCase(plate)){
                return true;
            }
        }
        return false;
    }

    /**
     * Find the specific booking by plate + begin + end, returns null if not found.
     */
    public Booking findByPlateDates(String plate, Date begin, Date end) {
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (b.getVehicle().getPlate().equalsIgnoreCase(plate) && b.getBegin().equals(begin) && b.getEnd().equals(end)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Find booking by plate + END date only
     */
    public Booking findByPlateEnd(String plate, Date end) {
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (b.getVehicle().getPlate().equalsIgnoreCase(plate) && b.getEnd().equals(end)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Returns the booking with the earliest END date, null if none.
     */
    public Booking getEarliestEnd() {
        if (size == 0) return null;
        int min = 0;
        for (int i = 1; i < size; i++) {
            if (bookings[i].getEnd().compareTo(bookings[min].getEnd()) < 0){
                min = i;
            }
        }
        return bookings[min];
    }

    /**
     * Vehicle availability, checks to make sure there is no overlap with any existing booking for that vehicle.
     */
    public boolean isVehicleAvailable(Vehicle v, Date begin, Date end) {
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (!b.getVehicle().equals(v)) continue;
            boolean endsBefore = end.compareTo(b.getBegin()) < 0;
            boolean beginsAfter = begin.compareTo(b.getEnd()) > 0;
            if (!(endsBefore || beginsAfter)) return false; // overlap
        }
        return true;
    }

    /**
     * Employee conflict if the employee has a booking that includes the *begin* date.
     */
    public boolean hasEmployeeBeginConflict(Employee emp, Date begin) {
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (b.getEmployee() != emp){
                continue;
            }
            boolean beginsOnOrAfter = begin.compareTo(b.getBegin()) >= 0;
            boolean beginsOnOrBefore = begin.compareTo(b.getEnd()) <= 0;
            if (beginsOnOrAfter && beginsOnOrBefore){
                return true;
            }
        }
        return false;
    }

    /**
     * Print the list of reservations by plate
     */
    public void printByVehicle() {
        if (size == 0) {
            System.out.println("There is no booking record.");
            return;
        }
        // sort by plate, then begin date
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                int cmp = bookings[j].getVehicle().getPlate()
                        .compareTo(bookings[min].getVehicle().getPlate());
                if (cmp == 0) {
                    cmp = bookings[j].getBegin().compareTo(bookings[min].getBegin());
                }
                if (cmp < 0) min = j;
            }
            if (min != i) {
                Booking t = bookings[i]; bookings[i] = bookings[min]; bookings[min] = t;
            }
        }
        System.out.println("*List of reservations ordered by license plate number and beginning date.");
        for (int i = 0; i < size; i++){
            System.out.println(bookings[i]);
        }
        System.out.println("*end of list.");
        System.out.println();
    }
    /**
     * Print the list of reservations by department the employee name.
     */
    public void printByDept() {
        if (size == 0) {
            System.out.println("There is no booking record.");
            return;
        }
        // sort by department display name then employee name
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                int cmp = bookings[j].getEmployee().getDept().toString().compareTo(bookings[min].getEmployee().getDept().toString());
                if (cmp == 0) {
                    cmp = bookings[j].getEmployee().name().compareTo(bookings[min].getEmployee().name());
                }
                if (cmp < 0){
                    min = j;
                }
            }
            if (min != i) {
                Booking t = bookings[i]; bookings[i] = bookings[min]; bookings[min] = t;
            }
        }
        System.out.println("*List of reservations ordered by department and employee.");

        Department current = null;

        for (int i = 0; i < size; i++) {
            Department d = bookings[i].getEmployee().getDept();
            if (current == null || d != current) {
                System.out.println("--" + d + "--");
                current = d;
            }
            System.out.println(bookings[i]);
        }
        System.out.println("*end of list.");
        System.out.println();
    }
}
