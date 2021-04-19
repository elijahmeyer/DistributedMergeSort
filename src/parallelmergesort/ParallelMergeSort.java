/*
 * Elijah Meyer
 * CEG 7370-01
 * Dr. Soon Chung
 * April 21, 2021
 *
 * This file defines the driver program used to implement the parallel merge
 * sort algorithm using Java RMI. This file generates an ArrayList of 100
 * randomly generated values, divides this list into a user-specified number of
 * sub-arrays, assigns each sub-array to a thread, and instructs each thread to
 * pass its sub-array to a running Server process using Java RMI.
 *
 * Note: In order for this program to work, Server.java must already be running.
 */
package parallelmergesort;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParallelMergeSort {

    /**
     * This method generates the data to be sorted, assigns the data to threads
     * that will query the RMI server containing the sorting algorithm,
     * starts the threads, waits for them to finish, and aggregates the sorted
     * data from the threads into one large sorted list.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Generate data: an ArrayList of 100 integers between 0 and 99 
        System.out.println("Running the main app");
        ArrayList<Integer> inputList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            inputList.add((int)(Math.random() *(99)));
        }
        
        // Determine number of threads to split the data between
        int threadCount = 7;
        
        // Calculate the size of each thread's list of data, attempting to split
        // the data as evenly as possible
        int fraction = inputList.size() / threadCount;
        
        // Create an array to store each thread's list of data
        ArrayList<Integer>[] valueLists = new ArrayList[threadCount];
        
        // Assign each thread's list of data the same number of integers except for
        // the last thread
        int maxIndex = 0;
        for (int i = 0; i < threadCount - 1; i++) {
            maxIndex = (i+1) * fraction;
            ArrayList<Integer> valueList = new ArrayList<>(inputList.subList((i * fraction), maxIndex));
            valueLists[i] = valueList;
        }
        
        // Assign the last thread's list the remaining integers. If the total
        // number of integers being sorted does not divide evenly into the
        // specified number of threads, this thread will have the extra integers.
        valueLists[threadCount - 1] = new ArrayList<>(inputList.subList(maxIndex, inputList.size()));
        
        try {
            // Create an array of threads, assign each thread a list of integers,
            // and start each thread. The threads will query the running Server
            // process in parallel.
            ClientRunnable[] threadList = new ClientRunnable[threadCount];
            for (int i = 0; i < threadCount; i++) {
                threadList[i] = new ClientRunnable(valueLists[i]);
                threadList[i].start();
            }
        
            // Wait for each thread to finish
            for (int i = 0; i < threadCount; i++) {
                threadList[i].join();
                System.out.println("Output: " + threadList[i].outputs.toString());
            }
        
            // Create a master list of all the threads' data. Initialize it with
            // one thread's sorted data.
            ArrayList<Integer> finalOutput = threadList[0].outputs;
            
            // Iterate through the remaining threads, adding each of their contents
            // to the master list such that the master list will still be sorted.
            // The algorithm to do this is the same as the algorithm to merge
            // split lists in merge sort.
            for (int i = 1; i < threadList.length; i++) {
                finalOutput = mergeLists(finalOutput, threadList[i].outputs);
            }
            
            // Finally, print the sorted master list to the console and confirm
            // that no data was lost in the process.
            System.out.println("Final Sorted List: " + finalOutput.toString());
            System.out.println("List length: " + finalOutput.size());

        }
        catch (Exception e) {
            System.out.println("Main app exception: " + e);
        }
    }
    
    /**
     * Combines two sorted lists into a sorted list that contains all their
     * elements. This method assumes both lists are sorted in ascending order
     * and returns a list sorted in ascending order.
     * @param first - one sorted list to be combined
     * @param last - another sorted list to be combined
     * @return a sorted list containing all the elements of both first and last
     */
    public static ArrayList<Integer> mergeLists(ArrayList<Integer> first, ArrayList<Integer> last) {
        ArrayList<Integer> sorted = new ArrayList<>();
        
        // Each list is assumed to be in order. The elements in each list must
        // be checked against the elements in the other list.
        while (!first.isEmpty() && !last.isEmpty()) {
            
            // Compare the first element in each list, which is the element
            // with the lowest value.
            int firstNum = first.get(0);
            int lastNum = last.get(0);
            
            // Whichever element comes first in ascending order, remove it from
            // its list and add it to the combined sorted list.
            // Repeat until one list is empty.
            if (firstNum <= lastNum) {
                sorted.add(first.remove(0));
            }
            else {
                sorted.add(last.remove(0));
            }
        }
        
        // After one of the lists has been emptied, add the remaining contents
        // of the other list to the combined list. These elements are already in
        // order, so no further processing is needed.
        if (first.isEmpty()) {
            sorted.addAll(last);
        }
        if (last.isEmpty()) {
            sorted.addAll(first);
        }
        
        // Return the sorted combined list.
        return sorted;
    }
    
}
