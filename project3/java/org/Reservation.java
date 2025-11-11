package org.example.project3_v3;

import util.List;
import util.Date;
import util.Sort;

/**
 * Represents the collection of the bookings
 * stores the bookings in an array and uses methods add, cancel and print for ease
 * @author alexG, joshuaH
 */

public class Reservation extends List<Booking> {

    /**
     * Checks whether any booking exists for a given vehicle plate.
     * @param plate the license plate string to check
     * @return true if any booking exists for the specified plate, false otherwise
     */
    public boolean hasAnyForPlate(String plate) {
        for (Booking b : this) {
            if (b.getVehicle().getPlate().equalsIgnoreCase(plate)) return true;
        }
        return false;
    }

    /**
     * Finds a booking by its plate number and matching begin and end dates.
     * @param plate the vehicle plate to search for
     * @param begin the booking's beginning date
     * @param end   the booking's ending date
     * @return the matching booking if found, null otherwise
     */

    public Booking findByPlateDates(String plate, Date begin, Date end) {
        for (Booking b : this) {
            if (b.getVehicle().getPlate().equalsIgnoreCase(plate)
                    && b.getBegin().equals(begin)
                    && b.getEnd().equals(end)) return b;
        }
        return null;
    }

    /**
     * Finds a booking by its plate and ending date only.
     * Used during trip returns to identify which booking is being completed.
     * @param plate the license plate string
     * @param end   the ending date to match
     * @return the matching booking object, or null if not found
     */
    public Booking findByPlateEnd(String plate, Date end) {
        for (Booking b : this) {
            if (b.getVehicle().getPlate().equalsIgnoreCase(plate)
                    && b.getEnd().equals(end)){
                return b;
            }
        }
        return null;
    }

    /**
     * Returns the booking with the earliest ending date.
     * @return the booking with the earliest end date, or null if list is empty
     */
    public Booking earliestEnd() {
        if (this.isEmpty()){
            return null;
        }
        int minIdx = 0;
        for (int i = 1; i < this.size(); i++) {
            if (this.get(i).getEnd().compareTo(this.get(minIdx).getEnd()) < 0){
                minIdx = i;
            }
        }
        return this.get(minIdx);
    }

    /**
     * Checks whether a vehicle is available for the requested date ranges
     * Overlapping date windows with an existing booking make the vehicle unavailable.
     * @param vehicle     the vehicle being booked
     * @param begin the begin date
     * @param end   the  end date
     * @return true if no overlap exists , false if  otherwise
     */
    public boolean isVehicleAvailable(Vehicle vehicle, Date begin, Date end) {
        for (Booking b : this) {
            if (!b.getVehicle().equals(vehicle)) continue;
            boolean endsBefore = end.compareTo(b.getBegin()) < 0;
            boolean beginsAfter = begin.compareTo(b.getEnd()) > 0;
            if (!(endsBefore || beginsAfter)) return false;
        }
        return true;
    }

    /**
     * Determines if an employee already has a conflicting booking during the given window.
     * @param emp   the employee making the booking
     * @param begin the proposed start date
     * @param end   the proposed end date
     * @return true if a booking conflict exists, false otherwise
     */
    public boolean hasEmployeeWindowConflict(Employee emp, Date begin, Date end) {
        for (Booking book : this) {
            if (book.getEmployee() != emp) continue;
            boolean endsBefore = end.compareTo(book.getBegin()) < 0;
            boolean beginsAfter = begin.compareTo(book.getEnd()) > 0;
            if (!(endsBefore || beginsAfter)) return true;
        }
        return false;
    }

    /**
     * Prints all bookings ordered by Campus city, Vehicle license plate, Booking begin date
     * Used for the pr command
     *
     * @return A string representation of the sorted reservations.
     */
    public String printByLocationPlateBegin() {
        if (this.isEmpty()) {
            return "There is no booking record.";
        }

        Sort.selectionSort(this, (a, b) -> {
            int c = a.getVehicle().getCampus().city().compareTo(b.getVehicle().getCampus().city());
            if (c != 0) return c;
            c = a.getVehicle().getPlate().compareTo(b.getVehicle().getPlate());
            if (c != 0) return c;
            return a.getBegin().compareTo(b.getBegin());
        });

        StringBuilder sb = new StringBuilder();
        sb.append("*List of reservations ordered by location/license plate/beginning date.\n");
        for (Booking b : this) {
            sb.append(b.toString()).append("\n");
        }
        sb.append("*end of list.\n");

        return sb.toString();
    }

    /**
     * Prints all bookings ordered by department and employee.
     *
     * @return A string representation of the reservations sorted by department.
     */
    public String printByDept() {
        if (this.isEmpty()) {
            return "There is no booking record.";
        }

        Sort.selectionSort(this, (a, b) -> {
            int c = a.getEmployee().getDept().toString().compareTo(b.getEmployee().getDept().toString());
            if (c != 0) return c;
            return a.getEmployee().name().compareTo(b.getEmployee().name());
        });

        StringBuilder sb = new StringBuilder();
        sb.append("*List of reservations ordered by department and employee.\n");
        Department cur = null;
        for (int i = 0; i < this.size(); i++) {
            Booking b = this.get(i);
            if (cur == null || cur != b.getEmployee().getDept()) {
                cur = b.getEmployee().getDept();
                sb.append("--").append(cur).append("--\n");
            }
            sb.append("\t").append(b.toString()).append("\n");
        }
        sb.append("*end of list.\n");

        return sb.toString();
    }
}
