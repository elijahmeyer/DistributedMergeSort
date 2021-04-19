/*
 * Elijah Meyer
 * CEG 7370-01
 * Dr. Soon Chung
 * April 21, 2021
 *
 * This file defines the Remote Interface for the mergeSort() function. This function
 * accepts an ArrayList of integers from a client, and returns an ArrayList containing
 * the integers sorted in ascending order.
 */
package parallelmergesort;
import java.rmi.*;
import java.util.ArrayList;

public interface MergeSort extends Remote {
    // Return a list containing the input values sorted in ascending order
    public ArrayList<Integer> mergeSort(ArrayList<Integer> values) throws RemoteException;
}
