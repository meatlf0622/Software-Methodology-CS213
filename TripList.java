package project1;

/**
 * Represenets a circular linked list of trips
 * Able to use insert and print trips by order of end date
 * @author joshuaH, alexG

*/

public class TripList {
    /**
     * reference to last node
     */
    private Node last;

    /**
     * Nodes for linked list, stores trip obj and has reference to next node
     */
    private static class Node {
        /** The trip stored in this node. */
        Trip trip;
        /** The next node in the list. */
        Node next;
        /**
         * Constructs a node that stores a trip.
         * @param t the trip to store
         */
        Node(Trip t){
            this.trip = t;
        }
    }

    /**
     * Adds a new trip to the end of list
     * @param trip new trip to be added
     */
    public void add(Trip trip) {
        Node newNode = new Node(trip);
        if (last == null) {
            last = newNode;
            newNode.next = newNode;
            return;
        }
        newNode.next = last.next;
        last.next = newNode;
        last = newNode;
    }

    /**
     * Prints trips in list order
     */
    public void print() {
        if (last == null) {
            System.out.println("There is no archived trips.");
            return;
        }
        int size = 0;
        Node curNode = last.next;
        do{
            size++; curNode = curNode.next;
        } while (curNode != last.next);

        Trip[] arr = new Trip[size];
        curNode = last.next;
        for(int i = 0; i < size; i++){
            arr[i] = curNode.trip; curNode = curNode.next;
        }

        for (int i = 0; i < size - 1; i++){
            int min = i;
            for (int j = i + 1; j < size; j++){
                if (arr[j].getBooking().getEnd().compareTo(arr[min].getBooking().getEnd()) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                Trip temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }

        System.out.println("*List of completed trips ordered by ending date.");
        for (int i = 0; i < size; i++){
            System.out.println(arr[i]);
        }
        System.out.println("*end of list.");
    }
}
