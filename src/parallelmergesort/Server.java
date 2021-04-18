/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author elija
 */
public class Server extends UnicastRemoteObject implements MergeSort {
    public Server() throws RemoteException {
        super();
    }
    
    @Override
    public ArrayList<Integer> mergeSort(ArrayList<Integer> values) throws RemoteException {
        System.out.println("mergeSort() method called.");
        ArrayList<Integer> outputs = localMergeSort(values);
        return outputs;
    }
    
    private ArrayList<Integer> localMergeSort(ArrayList<Integer> values) {
        // Base case: if the list to be sorted has 1 or fewer elements,
        // it is already sorted.
        System.out.println("localMergeSort() method called.");
        if (values.size() <= 1) {
            return values;
        }
        
        // Recursion step: split the list in half, then invoke merge sort
        // on both halves. 
        int midpoint = values.size() / 2;
        ArrayList<Integer> firstHalf = new ArrayList<>(values.subList(0, midpoint));
        ArrayList<Integer> lastHalf = new ArrayList<>(values.subList(midpoint, values.size()));
        System.out.println("First half: " + firstHalf.toString());
        System.out.println("Last half: " + lastHalf.toString());
        
        ArrayList<Integer> sortedFirstHalf = localMergeSort(firstHalf);
        ArrayList<Integer> sortedLastHalf = localMergeSort(lastHalf);
        
        // Aggregate the sorted halves into one sorted array.
        ArrayList<Integer> sorted = new ArrayList<>();
        
        while (!sortedFirstHalf.isEmpty() && !sortedLastHalf.isEmpty()) {
            int firstNum = sortedFirstHalf.get(0);
            int lastNum = sortedLastHalf.get(0);
            
            if (firstNum <= lastNum) {
                sorted.add(sortedFirstHalf.remove(0));
            }
            else {
                sorted.add(sortedLastHalf.remove(0));
            }
            System.out.println("sorted: " + sorted.toString());
        }
        
        if (sortedFirstHalf.isEmpty()) {
            sorted.addAll(sortedLastHalf);
        }
        if (sortedLastHalf.isEmpty()) {
            sorted.addAll(sortedFirstHalf);
        }
        return sorted;
    }
    
    public static void main(String[] args) throws RemoteException {
        try {
            // This is the instruction that lets you run from the IDE.
            Registry reg = LocateRegistry.createRegistry(9999);
            reg.rebind("hi server", new Server());
            System.out.println("Server is ready!");
        }
        catch (RemoteException e) {
            System.out.println("Server exception: " + e);
        }
    }
}
