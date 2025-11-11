package org.example.project3_v3;

import util.Sort;
import java.text.DecimalFormat;
import util.List;

/**
 * Represenets a circular linked list of trips
 * Able to use insert and print trips by order of end date
 * @author joshuaH, alexG

 */

public class TripList extends List<Trip> {

    /**
     * Prints the list of completed trips ordered by license plate
     * and ending date using PT command
     *
     * @return A string representation of the completed trips.
     */
    public String printTrips() {
        if (this.isEmpty()) {
            return "There is no completed trips.";
        }

        Sort.selectionSort(this, (a, b) -> {
            int c = a.getBooking().getVehicle().getPlate()
                    .compareTo(b.getBooking().getVehicle().getPlate());
            if (c != 0) return c;
            return a.getBooking().getEnd().compareTo(b.getBooking().getEnd());
        });

        StringBuilder sb = new StringBuilder();
        sb.append("*List of completed trips ordered by license plate and ending date.\n");
        for (int i = 0; i < this.size(); i++) {
            Trip t = this.get(i);
            String plate = t.getBooking().getVehicle().getPlate();
            String begin = t.getBooking().getBegin().toString();
            String end   = t.getBooking().getEnd().toString();
            int oldM = t.getMileageOld();
            int newM = t.getMileageNew();
            int used = t.used();
            String drop = t.getBooking().getDropoff().displayName()
                    + (t.hasSurcharge() ? "**" : "");
            sb.append(plate).append(" ").append(begin).append(" ~ ").append(end)
                    .append(" mileage(old): ").append(oldM)
                    .append(" mileage(new): ").append(newM)
                    .append(" mileage(used): ").append(used)
                    .append(" [dropped off: ").append(drop).append("]\n");
        }
        sb.append("*end of list.\n");

        return sb.toString();
    }

    /**
     * Prints the detailed cost report of all archived trips ordered by department,
     * then by license plate and ending date using PC command
     *
     * @return A string representation of the trip charges report.
     */
    public String printCharges() {
        if (this.isEmpty()) {
            return "There is no archived trips for the cost report.";
        }

        Sort.selectionSort(this, (a, b) -> {
            int c = a.getBooking().getEmployee().getDept().toString()
                    .compareTo(b.getBooking().getEmployee().getDept().toString());
            if (c != 0) return c;
            c = a.getBooking().getVehicle().getPlate()
                    .compareTo(b.getBooking().getVehicle().getPlate());
            if (c != 0) return c;
            return a.getBooking().getEnd().compareTo(b.getBooking().getEnd());
        });

        DecimalFormat money = new DecimalFormat("$#,##0.00");
        DecimalFormat moneyBare = new DecimalFormat("#,##0.00");

        StringBuilder sb = new StringBuilder();
        sb.append("*List of charges ordered by department.\n");

        Department cur = null;
        double deptTotal = 0.0;

        for (int i = 0; i < this.size(); i++) {
            Trip t = this.get(i);
            Department d = t.getBooking().getEmployee().getDept();

            if (cur == null || d != cur) {
                if (cur != null) {
                    sb.append("  <*>Department total: $ ").append(moneyBare.format(deptTotal)).append("\n");
                }
                cur = d;
                deptTotal = 0.0;
                sb.append("--").append(cur).append("--\n");
            }

            String plate = t.getBooking().getVehicle().getPlate();
            String begin = t.getBooking().getBegin().toString();
            String end   = t.getBooking().getEnd().toString();
            int oldM = t.getMileageOld();
            int newM = t.getMileageNew();
            int used = t.used();
            String drop = t.getBooking().getDropoff().displayName()
                    + (t.hasSurcharge() ? "**" : "");
            sb.append("\t").append(plate).append(" ").append(begin).append(" ~ ").append(end)
                    .append(" mileage(old): ").append(oldM)
                    .append(" mileage(new): ").append(newM)
                    .append(" mileage(used): ").append(used)
                    .append(" [dropped off: ").append(drop).append("]\n");

            Vehicle v = t.getBooking().getVehicle();
            double charge = v.charge(used);
            double sur = v.surcharge(used, t.hasSurcharge());
            double total = charge + sur;

            String surchargeText = (sur == 0.0) ? "no" : money.format(sur);
            sb.append("\t\t[charge: ").append(money.format(charge))
                    .append("] [surcharge: ").append(surchargeText)
                    .append("] [total charge: $ ").append(moneyBare.format(total)).append("]\n");

            deptTotal += total;
        }

        sb.append("  <*>Department total: $ ").append(new DecimalFormat("#,##0.00").format(deptTotal)).append("\n");
        sb.append("*end of list.\n");

        return sb.toString();
    }
}