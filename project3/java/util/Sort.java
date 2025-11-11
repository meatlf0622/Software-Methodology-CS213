package util;

/**
 * Provides static sorting utilities for util.list collections.
 * @author joshuaH, alexG
 */
public final class Sort {

    /** Prevents instantiation of utility class. */
    private Sort() {}

    /**
     * Functional interface for comparing two elements of type E
     * @param <E> the type of objects compared
     */
    public interface Comparator<E> {
        /**
         * Compares two elements for order.
         */
        int compare(E a, E b);
    }

    /**
     * Sorts the providedList in ascending order using the
     * selection sort algorithm and the specified comparator.
     * @param <E>  the element type of the list
     * @param list the list to sort
     * @param cmp  the comparator  defining sort order
     */
    public static <E> void selectionSort(List<E> list, Comparator<E> cmp) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (cmp.compare(list.get(j), list.get(min)) < 0) min = j;
            }
            if (min != i) {
                E tmp = list.get(i);
                list.set(i, list.get(min));
                list.set(min, tmp);
            }
        }
    }
}
