package org.example.project3_v3;

import util.Date;
import util.Sort;
import util.List;

import java.util.Scanner;

/**
 * Represents a fleet of vehicles
 * Strores the vehicles in an array so we can use the add, remove, and search operations easily
 * @author alexG, joshuaH
 */

public class Fleet extends List<Vehicle> {

    /**
     * Searches the fleet for a vehicle with the given license plate.
     * @param plate the license plate string to search for
     * @return the vehicle object if found, or null not found
     */
    public Vehicle getByPlate(String plate) {
        for (Vehicle v : this) {
            if (v.getPlate().equalsIgnoreCase(plate)){
                return v;
            }
        }
        return null;
    }

    /**
     * Checks whether a vehicle with the specified license plate exists in the fleet.
     * @param plate the license plate to check
     * @return true if a matching vehicle exists
     */
    public boolean hasPlate(String plate) {
        return getByPlate(plate) != null;
    }

    /**
     * Loads vehicles into the fleet from a provided text file.
     * @param sc  object used to read the vehicle file
     * @return the number of vehicles successfully loaded
     */
    public int load(Scanner sc) {
        int added = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] tok = line.split("\\s+");
            if (tok.length < 5) continue;

            String plate = tok[0];
            String dateStr = tok[1];
            String makeStr = tok[2];
            String mileageStr = tok[3];
            String campusStr = tok[4];

            String err = Vehicle.validatePlate(plate);
            if (err != null) continue;

            String[] md = dateStr.split("/");
            Date obtained = new Date(
                    Integer.parseInt(md[0]),
                    Integer.parseInt(md[1]),
                    Integer.parseInt(md[2])
            );
            Make make = Make.fromText(makeStr);
            int mileage = Integer.parseInt(mileageStr);
            Campus campus = Campus.fromText(campusStr);

            if (this.getByPlate(plate) != null) continue;
            Vehicle v = Vehicle.fromPlateAndBasics(plate, obtained, make, mileage, campus);
            if (v == null) continue;

            this.add(v);
            added++;
        }
        return added;
    }

    /**
     * Prints all vehicles in the fleet sorted by campus city, then make,then obtained date. Used by the PF command
     * If the fleet is empty prints no vehicle in fleet
     *
     * @return A string representation of the sorted fleet list.
     */
    public String printByLocationMakeDate() {
        if (this.isEmpty()) {
            return "There is no vehicle in the fleet.";
        }

        Sort.selectionSort(this, (a, b) -> {
            int c = a.getCampus().city().compareTo(b.getCampus().city());
            if (c != 0) return c;
            c = a.getMake().toString().compareTo(b.getMake().toString());
            if (c != 0) return c;
            return a.getObtained().compareTo(b.getObtained());
        });

        StringBuilder sb = new StringBuilder();
        sb.append("*List of vehicles in the fleet, ordered by location/make/date obtained.\n");
        for (Vehicle v : this) {
            sb.append(v.toString()).append("\n");
        }
        sb.append("*end of list.\n");

        return sb.toString();
    }
}
