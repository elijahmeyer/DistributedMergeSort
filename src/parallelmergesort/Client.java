/*
 * Elijah Meyer
 * CEG 7370-01
 * Dr. Soon Chung
 * April 21, 2021
 *
 * This file defines the Client class and its data fields and methods. The Client
 * class performs Java Remote Method Invocation on a running instance of the
 * Server class. It receives a list of integer values, passes them to the Server
 * for processing by calling its mergeSort() method, and saves the result the 
 * Server returns.
 *
 * Note: In order for this program to work, Server.java must already be running.
 */
package parallelmergesort;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private ArrayList<Integer> values;
    public ArrayList<Integer> sortedValues;
    
    public Client(ArrayList<Integer> values) throws RemoteException {
        this.values = values;
    }
    
    /*
    * Sorts the list of integers passed to it on creation by calling the Server's
    * mergeSort() remote method. Stores the sorted list.
    */
    public void connectRemote() throws RemoteException {
        try {
            // Search the local remote object registry at port 9999
            Registry reg = LocateRegistry.getRegistry("localhost", 9999);
            
            // Look for a server implementing the MergeSort remote interface
            MergeSort ms = (MergeSort)reg.lookup("mergeSort server");
            
            // Sort the values list using the mergeSort() remote method invocation
            ArrayList<Integer> outputs = ms.mergeSort(this.values);
            this.sortedValues = outputs;
        }
        catch (NotBoundException|RemoteException e) {
            System.out.println("Client error: " + e);
        }
    }
}
