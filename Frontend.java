package project1;

import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * front end class, that handles the ui of the project
 * Commands:
 * A (add), D (delete), B(Book), C(cancel), r(return),
 * PF (print fleet), PR (print reservations), PD (print departments),
 * PT (print trips), and Q (quit).
 * @author alexG, joshuaH
 */

public class Frontend {
    /** The fleet of vehicles being managed. */
    private Fleet fleet = new Fleet();
    /** The collection of reservations in the system. */
    private Reservation reservations = new Reservation();
    /** The list of completed trips. */
    private TripList trips = new TripList();

    /**
     * starts the system and uses scanner the process the inputs line by line
     */

    public void run() {
        System.out.println("Vehicle Management System is running.");
        //System.out.println();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line == null){
                break;
            }
            line = line.trim();
            if (line.isEmpty()){
                continue;
            }
            if (line.equals("Q")) {
                System.out.println("Vehicle Management System is terminated.");
                sc.close();
                return;
            }

            char cmd = line.charAt(0);
            switch (cmd) {
                case 'A': handleAdd(line); break;
                case 'D': handleDelete(line); break;
                case 'B': handleBook(line); break;
                case 'C': handleCancel(line); break;
                case 'R': handleReturn(line); break;
                case 'P': handlePrint(line); break;
                case 'Q':
                    System.out.println("Vehicle Management System is terminated.");
                    sc.close();
                    return;
                default:
                    printInvalidCommand(line);
            }
        }
        sc.close();
        System.out.println();
    }

    /**
     * prints invalid command if the comand is not recognized
     * @param line current line in scanner
     */
    private void printInvalidCommand(String line) {
        String tok = line;
        int space = line.indexOf(' ');
        if (space > 0){
            tok = line.substring(0, space);
        }
        System.out.println(tok.toLowerCase() + " - invalid command!");
    }

    /**
     * takes date and turns it into seperate integers
     * @param mmddyyyy date input
     * @return Date obj
     */
    private Date parseDate(String mmddyyyy) {
        String[] p = mmddyyyy.split("/");
        if (p.length != 3) return new Date(-1, -1, -1);
        try {
            int m = Integer.parseInt(p[0]);
            int d = Integer.parseInt(p[1]);
            int y = Integer.parseInt(p[2]);
            return new Date(m, d, y);
        } catch (Exception e) {
            return new Date(-1, -1, -1);
        }
    }

    /**
     * compares current date to another  date
     * @param date, Date obj
     * @return
     */
    private int compareToToday(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, date.getYear());
        c.set(Calendar.MONTH, date.getMonth() - 1);
        c.set(Calendar.DAY_OF_MONTH, date.getDay());
        zeroTime(now); zeroTime(c);
        return c.compareTo(now);
    }

    /**
     * helper method that resets the time of a calander obj , to make sure only day month and year
     * @param cal cal onject used to reset
     */
    private void zeroTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * checks to see if a date is beyond three months from the current date
     * @param begin, date that is being used to check if it is over 3 months
     * @return
     */
    private boolean beyondThreeMonths(Date begin) {
        Calendar lim = Calendar.getInstance();
        zeroTime(lim);
        lim.add(Calendar.MONTH, 3);
        Calendar b = Calendar.getInstance();
        b.set(begin.getYear(), begin.getMonth() - 1, begin.getDay(), 0, 0, 0);
        b.set(Calendar.MILLISECOND, 0);
        return b.after(lim);
    }

    /**
     * calulates the number of days between two dates
     * @param dayOne begin date
     * @param dayTwo end date
     * @return the number of dats inbetween
     */
    private int daysBetweenInclusive(Date dayOne, Date dayTwo) {
        Calendar ca = Calendar.getInstance();
        ca.set(dayOne.getYear(), dayOne.getMonth() - 1, dayOne.getDay(), 0, 0, 0);
        ca.set(Calendar.MILLISECOND, 0);
        Calendar cb = Calendar.getInstance();
        cb.set(dayTwo.getYear(), dayTwo.getMonth() - 1, dayTwo.getDay(), 0, 0, 0);
        cb.set(Calendar.MILLISECOND, 0);
        long diff = (cb.getTimeInMillis() - ca.getTimeInMillis()) / (24L * 60 * 60 * 1000);
        return (int) diff + 1;
    }
    /**
     * handles the add comand
     * makes sure the plate, date, make, and mileage are valid and inserts them into the fleet
     * @param line m the input that is being looked at currently
     */
    private void handleAdd(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        String plate = st.nextToken();

        String dateStr = st.nextToken();
        Date obtained = parseDate(dateStr);
        if (!obtained.isValid()) {
            System.out.println(dateStr + " - invalid calendar date.");
            return;
        }
        // not today or future
        if (compareToToday(obtained) >= 0) {
            System.out.println(dateStr + " - is today or a future date.");
            return;
        }

        String makeStr = st.nextToken();
        Make make;
        try {
            make = Make.valueOf(makeStr.toUpperCase());
        } catch (Exception e) {
            System.out.println(makeStr + " - invalid make.");
            return;
        }

        String mileageTok = st.nextToken();
        int mileage;
        try { mileage = Integer.parseInt(mileageTok); }
        catch (Exception e) { mileage = -1; }
        if (mileage <= 0) {
            System.out.println(mileage + " - invalid mileage.");
            return;
        }

        Vehicle vehicle = new Vehicle(plate, obtained, make, mileage);
        if (!fleet.contains(vehicle)) {
            fleet.add(vehicle);
            System.out.println(vehicle+ " has been added to the fleet.");
        }
    }

    /**
     * handles the delete command
     * removes a vehicle if allowed from the fleet
     * @param line, the input line that is being used for the delete parameters
     */
    private void handleDelete(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        String plate = st.nextToken();

        Vehicle found = fleet.get(plate);
        if (found == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }

        if (reservations.hasAnyForPlate(plate)) {
            System.out.println(plate + " - has existing bookings; cannot be removed.");
            return;
        }
        fleet.remove(found);
        System.out.println(found + " has been removed from the fleet.");
    }

    /**
     * handles the Book command
     * creates a new booking if employee and their vehicle are valid
     * @param line , the input that has the booking parameters
     */
    private void handleBook(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        String beginStr = st.hasMoreTokens() ? st.nextToken() : "";
        String endStr   = st.hasMoreTokens() ? st.nextToken() : "";
        String plate    = st.hasMoreTokens() ? st.nextToken() : "";
        String empStr   = st.hasMoreTokens() ? st.nextToken() : "";

        Date begin = parseDate(beginStr);

        if (!begin.isValid()) {
            System.out.println(beginStr + " - beginning date is not a valid calendar date.");
            return;
        }
        if (compareToToday(begin) < 0) {
            System.out.println(beginStr + " - beginning date is not today or a future date.");
            return;
        }
        if (beyondThreeMonths(begin)) {
            System.out.println(beginStr + " - beginning date beyond 3 months.");
            return;
        }

        Date end = parseDate(endStr);
        if (!end.isValid()) {
            System.out.println(endStr + " - ending date is not a valid calendar date.");
            return;
        }
        if (end.compareTo(begin) < 0) {
            System.out.println(endStr + " - ending date must be equal or after the beginning date " + beginStr);
            return;
        }
        if (daysBetweenInclusive(begin, end) > 7) {
            System.out.println(beginStr + " ~ " + endStr + " - duration more than a week.");
            return;
        }

        Vehicle v = fleet.get(plate);
        if (v == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }

        Employee emp;
        try {
            emp = Employee.valueOf(empStr.toUpperCase());
        } catch (Exception e) {
            System.out.println(empStr + " - not an eligible employee to book.");
            return;
        }

        if (!reservations.isVehicleAvailable(v, begin, end)) {
            System.out.println(plate + " - booking with " + beginStr + " ~ " + endStr + " not available.");
            return;
        }

        if (reservations.hasEmployeeBeginConflict(emp, begin)) {
            System.out.println(emp.name() + " - has an existing booking conflicting with the beginning date " + beginStr);
            return;
        }

        Booking book = new Booking(begin, end, emp, v);
        reservations.add(book);
        System.out.println(book + " booked.");
    }

    /**
     * handles cancel command
     * cancels a booking if it is found
     * @param line, input line of that has cancel parameters
     */
    private void handleCancel(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        String beginStr = st.nextToken();
        String endStr   = st.nextToken();
        String plate    = st.nextToken();

        Date begin = parseDate(beginStr);
        Date end   = parseDate(endStr);

        Booking book = reservations.findByPlateDates(plate, begin, end);
        if (book == null) {
            System.out.println(plate + ":" + beginStr + " ~ " + endStr + " - cannot find the booking.");
            return;
        }
        reservations.remove(book);
        System.out.println(plate + ":" + beginStr + " ~ " + endStr + " has been canceled.");
    }

    /**
     * handles the return command
     * @param line, input line that has the return parameters
     */
    private void handleReturn(String line) {
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        String endStr   = st.nextToken();
        String plate    = st.nextToken();
        String milStr   = st.nextToken();

        Date end = parseDate(endStr);
        Booking book = reservations.findByPlateEnd(plate, end);
        if (book == null) {
            System.out.println(plate + " booked with ending date " + endStr + " - cannot find the booking.");
            return;
        }

        Booking earliest = reservations.getEarliestEnd();
        if (earliest != null && !earliest.getEnd().equals(end)) {
            System.out.println(plate + " booked with ending date " + endStr + " - returning not in order of ending date.");
            return;
        }

        int entered;
        try { entered = Integer.parseInt(milStr); }
        catch (Exception e) { entered = -1; }
        if (entered <= 0) {
            System.out.println(entered + " - invalid mileage.");
            return;
        }

        Vehicle vehicle = book.getVehicle();
        int current = vehicle.getMileage();
        if (entered <= current) {
            System.out.println("Invalid mileage - current mileage: " + current + " entered mileage: " + entered);
            return;
        }

        Trip trip = new Trip(book, current, entered);
        vehicle.setMileage(entered);
        reservations.remove(book);
        trips.add(trip);
        System.out.println("Trip completed: " + trip);
    }

    /**
     * handles the print command
     * uses PF, PR, PD, PT as inputs
     * @param line , input of line that selects which print command to use
     */
    private void handlePrint(String line) {
        if (line.equals("PF")) {
            fleet.printByMake();
        } else if (line.equals("PR")) {
            reservations.printByVehicle();
        } else if (line.equals("PD")) {
            reservations.printByDept();
        } else if (line.equals("PT")) {
            trips.print();
        } else {
            printInvalidCommand(line);
        }
    }
}
