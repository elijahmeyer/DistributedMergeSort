/*
 * Elijah Meyer
 * CEG 7370-01
 * Dr. Soon Chung
 * April 21, 2021
 *
 * This file defines the ClientRunnable class and its data fields and methods.
 * The ClientRunnable class is a wrapper for the Client class that allows a new
 * instance of the Client class to be instantiated, be assigned an ArrayList of 
 * integers, and invoke the mergeSort() remote method in its own thread of
 * execution. This allows multiple Client instances to communicate with a
 * running Server process in parallel.
 * Note: In order for this program to work, Server.java must already be running.
 */
package parallelmergesort;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientRunnable extends Thread {
    private ArrayList<Integer> inputs;
    public ArrayList<Integer> outputs;
    
    public ClientRunnable(ArrayList<Integer> values) {
        this.inputs = values;
    }
    
    /*
    * Creates a new Client instance, assigns it a list of values, and instructs it
    * to call the remote mergeSort() function to sort the values. Saves the sorted
    * list.
    */
    @Override
    public void run() {
        try {
            System.out.println("" + Thread.currentThread().getId() + " has started.");
            
            // Create new Client instance and invoke the mergeSort() method
            Client c = new Client(this.inputs);
            c.connectRemote();
            
            // Save the sorted list for future reference
            this.outputs = c.sortedValues;
            System.out.println("" + Thread.currentThread().getId() + " has finished.");
        }
        catch (RemoteException e) {
            System.out.println("Runnable exception: " + e);
        }
    }
}
