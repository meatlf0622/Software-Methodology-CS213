package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * List from scratch
 * @param <E> the type of elements stored in this list
 * @author joshuaH, alexG
 */
public class List<E> implements Iterable<E> {

    /** Number of additional slots list can go up when list grows. */
    private static final int GROW_BY = 4;

    /**  array that stores list elements. */
    private E[] objects;

    /** Current number of elements in the list. */
    private int size;

    /**
     * Constructs an empty list with initial capacity
     */
    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[GROW_BY];
        size = 0;
    }
    /**
     * Finds the index of a given element within the list.
     * @param element the element to search for
     * @return the index of the element, or -1  if not found
     */
    private int find(E element) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(element)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Increases the capacity of the internal array by growby
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] n = (E[]) new Object[objects.length + GROW_BY];
        for (int i = 0; i < size; i++){
            n[i] = objects[i];
        }
        objects = n;
    }

    /**
     * Checks whether the specified element exists in this list.
     * @param element the element to search for
     * @return true if the element exists; false otherwise
     */
    public boolean contains(E element) {
        return find(element) != -1;
    }

    /**
     * Adds a new element to the end of the list adding storage if needed
     * @param e the element to add
     */
    public void add(E e) {
        if (size == objects.length) grow();
        objects[size++] = e;
    }

    /**
     * Removes the first occurrence of the given element from the list.
     * @param element the element to remove
     */
    public void remove(E element) {
        int idx = find(element);
        if (idx == -1) return;
        objects[idx] = objects[size - 1];
        objects[size - 1] = null;
        size--;
    }

    /**
     * Checks if the list has no elements.
     * @return true if the list is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements currently stored.
     * @return the current size of the list
     */
    public int size() { return size; }

    /**
     * Returns the element at the specified inde
     * @param index the zero-based index
     * @return the element at the given index
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public E get(int index) { return objects[index]; }

    /**
     * Replaces the element at the specified index with the given value.
     * @param index the zero-based index to modify
     * @param element     the new element to store
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void set(int index, E element) { objects[index] = element; }

    /**
     * Returns the index of the specified element.
     * @param element the element to search for
     * @return the index of the element, or -1 if not found
     */
    public int indexOf(E element) { return find(element); }

    /**
     * Returns an iterator over elements of this list.
     * @return an iterator for this list
     */
    @Override
    public Iterator<E> iterator() { return new ListIterator(); }

    /**
     * Inner class implementing iterator for list
     */
    private class ListIterator implements Iterator<E> {
        /** Current position of the iterator. */
        int current = 0;
        /**
         * Checks if there are more elements to iterate.
         * @return true if additional elements exist
         */
        public boolean hasNext() { return current < size; }

        /**
         * Returns the next element in iteration order.
         * @return the next element
         * @throws NoSuchElementException if no more elements exist
         */
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return objects[current++];
        }
    }
}
