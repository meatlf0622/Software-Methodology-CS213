package org.example.project3_v3;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import util.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Controller class for the JavaFX GUI (view.fxml).
 * Handles all user interactions and updates the view.
 * Interface with the Model classes (Fleet,TripList,Reservation).
 * @author alexG, joshuaH
 */
public class Controller {

    //  Constants
    /**
     * The number of parts expected in a date string (m/d/y)
     */
    private static final int EXPECTED_DATE_PARTS = 3;

    /**
     *  Holds all vehicle objects.
     */
    private Fleet fleet = new Fleet();
    /**
     * Holds all active booking objects.
     * */
    private Reservation reservations = new Reservation();

    /**
     * Holds all completed trip objects.
     */
    private TripList trips = new TripList();

    // --- FXML Components: Book/Return Tab ---
    @FXML
    private TextField bookEmployee;
    @FXML
    private TextField bookPlate;
    @FXML
    private TextField bookStartDate;
    @FXML
    private TextField bookEndDate;
    @FXML
    private TextField bookDropoff;
    @FXML
    private TextField returnPlate;
    @FXML
    private TextField returnEndDate;
    @FXML
    private TextField returnMileage;

    //  --- FXML Components: Fleet/Trips Tab ---
    @FXML
    private ToggleGroup reportGroup; // Connects the RadioButtons
    @FXML
    private RadioButton radioReservationsByPlate;
    @FXML
    private RadioButton radioReservationsByDept;
    @FXML
    private RadioButton radioTrips;
    @FXML
    private RadioButton radioCharges;

    //  --- FXML Components: Output ---
    @FXML
    private TextArea outputArea;


    // Helper & Validation Methods

    /**
     * Appends text to the output area, handling exceptions.
     * Primary way to communicate with user
     *
     * @param message The message to display in the TextArea.
     */
    private void outputGuest(String message) {
        // This ensures that a crash will not happen if outputArea is null
        if (outputArea != null) {
            outputArea.setText(message);
        } else {
            // For debugging purpose if FXML fails to inject
            System.out.println("GUI Error: " + message);
        }
    }

    /**
     * Parses and validates an employee string from a TextField
     *
     * @param name The employee's name from the TextField.
     * @return The matching Employee enum.
     * @throws IllegalArgumentException if the employee name is invalid.
     */
    private Employee parseEmployee(String name) throws IllegalArgumentException {
        try {
            return Employee.valueOf(name.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid employee name: " + name);
        }
    }

    /**
     * Parses and validates a vehicle license plate from a TextField
     *
     * @param plate The plate from the TextField.
     * @return The matching Vehicle object from the fleet.
     * @throws IllegalArgumentException if the plate is invalid or not in the fleet.
     */
    private Vehicle parseVehicle(String plate) throws IllegalArgumentException {
        if (plate == null || plate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty.");
        }
        Vehicle v = fleet.getByPlate(plate.trim());
        if (v == null) {
            throw new IllegalArgumentException("Vehicle not found in fleet: " + plate);
        }
        return v;
    }

    /**
     * Parses and validates a date string from a TextField
     *
     * @param dateStr The date string (m/d/y) from the TextField.
     * @return A valid Date object.
     * @throws IllegalArgumentException if the date format is invalid or not a real date.
     */
    private Date parseDate(String dateStr) throws IllegalArgumentException {
        try {
            String[] parts = dateStr.split("/");
            if (parts.length != EXPECTED_DATE_PARTS) {
                throw new IllegalArgumentException("Invalid date format. Use m/d/y");
            }


            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            Date date = new Date(month, day, year);
            if (!date.isValid()) {
                throw new IllegalArgumentException("Invalid date: " + dateStr);
            }
            return date;
        } catch (Exception e) {
            throw new IllegalArgumentException("Date must contain numeric values");
        }
    }

    /**
     * Parses and validates a campus string from a TextField
     *
     * @param campusStr The campus name from the TextField.
     * @return The matching Campus enum.
     * @throws IllegalArgumentException if the campus name is invalid.
     */
    private Campus parseCampus(String campusStr) throws IllegalArgumentException {
        Campus campus = Campus.fromText(campusStr);
        if (campus == null) {
            throw new IllegalArgumentException("Invalid campus: " + campusStr);
        }
        return campus;
    }

    /**
     * Parses and validates a mileage string from a TextField
     *
     * @param mileageStr The mileage from the TextField.
     * @return A valid, positive integer mileage.
     * @throws IllegalArgumentException if mileage is not a positive number.
     */
    private int parseMileage(String mileageStr) throws IllegalArgumentException {
        try {
            int mileage = Integer.parseInt(mileageStr.trim());
            if (mileage < 0) {
                throw new IllegalArgumentException("Mileage cannot be negative.");
            }
            return mileage;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid mileage. Must be a number.");
        }
    }

    /**
     * Runs all business logic checks for a new booking.
     *
     * @param emp    The Employee making the booking.
     * @param vehicle The Vehicle to be booked.
     * @param start  The desired start date.
     * @param end    The desired end date.
     * @throws IllegalArgumentException if any business rule is violated.
     */
    private void validateBooking(Employee emp, Vehicle vehicle, Date start, Date end) throws IllegalArgumentException {
        // Check 1: Date logic
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start date must be before or same as end date.");
        }

        // Check 2: Vehicle availability
        if (!reservations.isVehicleAvailable(vehicle, start, end)) {
            throw new IllegalArgumentException("Vehicle is not available for the selected dates.");
        }

        // Check 3: Employee booking conflict
        if (reservations.hasEmployeeWindowConflict(emp, start, end)) {
            throw new IllegalArgumentException("Employee already has a booking during this time.");
        }
    }

    /**
     * Clears the booking form fields after a successful booking
     */
    private void clearBookingFields() {
        bookEmployee.clear();
        bookPlate.clear();
        bookStartDate.clear();
        bookEndDate.clear();
        bookDropoff.clear();
    }

    /**
     * Clears the return form fields after a successful return.
     */
    private void clearReturnFields() {
        returnPlate.clear();
        returnEndDate.clear();
        returnMileage.clear();
    }

    // -- Public FXML Event Handlers ---

    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        outputArea.setText("Welcome! Please load a fleet file to begin.\n");
    }

    //  Event Handler Methods

    /**
     * Handles the "Load Fleet File" button click.
     * Opens a FileChooser and loads the selected vehicle file into the fleet.
     */
    @FXML
    private void handleLoadFleet() {
        // Required FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Fleet File");
        // Set extension filter
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                int count = fleet.load(scanner);
                outputGuest(count + " vehicles loaded successfully from " + file.getName() + "\n");
            } catch (FileNotFoundException e) {
                outputGuest("Error: File not found. " + e.getMessage() + "\n");
            } catch (Exception e) {
                outputGuest("Error loading fleet file: " + e.getMessage() + "\n");
            }
        } else {
            outputGuest("File selection cancelled.\n");
        }
    }

    /**
     * Handles the "Make Booking" button click.
     * Validates input and creates a new reservation.
     */
    @FXML
    private void handleMakeBooking() {
        try {
            Employee emp = parseEmployee(bookEmployee.getText());
            Vehicle vehicle = parseVehicle(bookPlate.getText());
            Date startDate = parseDate(bookStartDate.getText());
            Date endDate = parseDate(bookEndDate.getText());
            Campus dropoff = parseCampus(bookDropoff.getText());

            validateBooking(emp, vehicle, startDate, endDate);

            Booking newBooking = new Booking(startDate, endDate, emp, vehicle, dropoff);
            reservations.add(newBooking);

            outputGuest("Booking successful!\n" + newBooking.toString() + "\n");
            clearBookingFields();

        } catch (Exception e) {
            outputGuest("Booking Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles the "Return Vehicle" button click.
     * Validates input and processes a vehicle return, creating a Trip.
     */
    @FXML
    private void handleReturnVehicle() {
        try {
            String plate = returnPlate.getText();
            Date endDate = parseDate(returnEndDate.getText());
            int endMileage = parseMileage(returnMileage.getText());

            Vehicle vehicle = fleet.getByPlate(plate);
            if (vehicle == null) {
                throw new IllegalArgumentException("Vehicle not found: " + plate);
            }

            Booking booking = reservations.findByPlateEnd(plate, endDate);
            if (booking == null) {
                throw new IllegalArgumentException("No matching reservation found for " + plate + " ending on " + endDate);
            }

            if (endMileage < vehicle.getMileage()) {
                throw new IllegalArgumentException("End mileage (" + endMileage + ") cannot be less than start mileage (" + vehicle.getMileage() + ").");
            }

            boolean hasSurcharge = vehicle.getCampus() != booking.getDropoff();
            int startMileage = vehicle.getMileage();

            Trip completedTrip = new Trip(booking, startMileage, endMileage, hasSurcharge);
            trips.add(completedTrip);

            vehicle.setMileage(endMileage);
            vehicle.setCampus(booking.getDropoff());

            reservations.remove(booking);

            outputArea.setText("Return successful!\n" + completedTrip.toString() + "\n");
            clearReturnFields();

        } catch (Exception e) {
            outputGuest("Return Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles the "Show All Fleet Vehicles" button click.
     * Calls the refactored print method from Fleet.
     */
    @FXML
    private void handleShowFleet() {
        try {
            String fleetReport = fleet.printByLocationMakeDate();
            outputGuest(fleetReport);
        } catch (Exception e) {
            outputGuest("Error generating fleet report: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles the "Show Selected Report" button click.
     * Checks which RadioButton is selected and calls the appropriate
     * refactored print method from Reservation or TripList.
     * The method handles all report generation from the second tab
     */
    @FXML
    private void handleShowReport() {
        try {
            String report;
            if (radioReservationsByPlate.isSelected()) {
                report = reservations.printByLocationPlateBegin();
            } else if (radioReservationsByDept.isSelected()) {
                report = reservations.printByDept();
            } else if (radioTrips.isSelected()) {
                report = trips.printTrips();
            } else if (radioCharges.isSelected()) {
                report = trips.printCharges();
            } else {
                report = "Please select a report type.";
            }
            outputGuest(report);
        } catch (Exception e) {
            outputGuest("Error generating report: " + e.getMessage() + "\n");
        }
    }
}
