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
/**
 *
 * @author elija
 */
public class Server extends UnicastRemoteObject implements MergeSort {
    public Server() throws RemoteException {
        super();
    }
    
    @Override
    public int[] mergeSort(int[] values) throws RemoteException {
        System.out.println("mergeSort() method called.");
        int[] outputs = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            outputs[i] = values[values.length - (i + 1)];
        }
        return outputs;
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
