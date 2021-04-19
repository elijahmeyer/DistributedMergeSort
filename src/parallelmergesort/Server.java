/*
 * Elijah Meyer
 * CEG 7370-01
 * Dr. Soon Chung
 * April 21, 2021
 *
 * This file defines the Server class and its data fields and methods. This file
 * must be run as a separate process for the program to function as intended.
 * The Server class acts as a Java RMI server, listening for remote invocations
 * of the mergeSort() method and handling them in parallel. It implements the
 * MergeSort interface.
 */
package parallelmergesort;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Server extends UnicastRemoteObject implements MergeSort {
    public Server() throws RemoteException {
        super();
    }
    
    /*
    * Receives an ArrayList of integers from a client, sorts the integers, and returns
    * the sorted ArrayList to the client. Because the merge sort algorithm is recursive,
    * this method serves as a wrapper for a method that actually performs the sorting,
    * as this method will need to be called multiple times.
    * This way, the Server will not return some intermediate result to the client
    * instead of the full sorted list.
    * This method overrides the mergeSort() method in the MergeSort interface.
    * @param values - the list to be sorted
    * @return a list containing all of the values of the input list, but sorted
    * in ascending order
    */
    @Override
    public ArrayList<Integer> mergeSort(ArrayList<Integer> values) throws RemoteException {
        System.out.println("mergeSort() method called.");
        
        // Call the recursive merge sort method and return its output
        ArrayList<Integer> outputs = localMergeSort(values);
        return outputs;
    }
    
    /*
    * Sorts an ArrayList of integers into ascending order. This is a recursive
    * function. It splits the given ArrayList in half, calls itself on both halves,
    * and aggregates both sorted halves into one sorted list.
    * @param values - the list of values to be sorted
    * @return the given list sorted into ascending order
    */
    private ArrayList<Integer> localMergeSort(ArrayList<Integer> values) {
        // Base case: if the list to be sorted has 1 or fewer elements,
        // it is already sorted
        System.out.println("localMergeSort() method called.");
        if (values.size() <= 1) {
            return values;
        }
        
        // Recursion step: split the list in half, then invoke localMergeSort()
        // on both halves 
        int midpoint = values.size() / 2;
        ArrayList<Integer> firstHalf = new ArrayList<>(values.subList(0, midpoint));
        ArrayList<Integer> lastHalf = new ArrayList<>(values.subList(midpoint, values.size()));
        System.out.println("First half: " + firstHalf.toString());
        System.out.println("Last half: " + lastHalf.toString());
        
        ArrayList<Integer> sortedFirstHalf = localMergeSort(firstHalf);
        ArrayList<Integer> sortedLastHalf = localMergeSort(lastHalf);
        
        // Aggregate the sorted halves into one sorted array
        ArrayList<Integer> sorted = new ArrayList<>();
        
        // Each list is assumed to be in order. The elements in each list must
        // be checked against the elements in the other list.
        while (!sortedFirstHalf.isEmpty() && !sortedLastHalf.isEmpty()) {
            
            // Compare the first element in each list, which is the element
            // with the lowest value.
            int firstNum = sortedFirstHalf.get(0);
            int lastNum = sortedLastHalf.get(0);
            
            // Whichever element comes first in ascending order, remove it from
            // its list and add it to the combined sorted list.
            // Repeat until one list is empty.
            if (firstNum <= lastNum) {
                sorted.add(sortedFirstHalf.remove(0));
            }
            else {
                sorted.add(sortedLastHalf.remove(0));
            }
            System.out.println("sorted: " + sorted.toString());
        }
        
        // After one of the lists has been emptied, add the remaining contents
        // of the other list to the combined list. These elements are already in
        // order, so no further processing is needed.
        if (sortedFirstHalf.isEmpty()) {
            sorted.addAll(sortedLastHalf);
        }
        if (sortedLastHalf.isEmpty()) {
            sorted.addAll(sortedFirstHalf);
        }
        
        // Return the combined sorted list
        return sorted;
    }
    
    /*
    * This method creates an instance of the Server class and creates a remote
    * object registry for the instance so that clients can communicate with it.
    * @param args - the command line arguments
    */
    public static void main(String[] args) throws RemoteException {
        try {
            // Create registry at port 9999. Doing this in the source code
            // instead of in the command line allows this code to be run from an
            // IDE as well as the command line.
            Registry reg = LocateRegistry.createRegistry(9999);
            
            // Add a Server instance into the registry.
            reg.rebind("mergeSort server", new Server());
            System.out.println("Server is ready!");
        }
        catch (RemoteException e) {
            System.out.println("Server exception: " + e);
        }
    }
}
