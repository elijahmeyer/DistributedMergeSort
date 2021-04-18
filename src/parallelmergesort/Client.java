/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author elija
 */
public class Client {
    private ArrayList<Integer> values;
    public ArrayList<Integer> sortedValues;
    
    public Client(ArrayList<Integer> values) throws RemoteException {
        this.values = values;
    }
    
    public void connectRemote() throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 9999);
            MergeSort ms = (MergeSort)reg.lookup("hi server");
            ArrayList<Integer> outputs = ms.mergeSort(this.values);
            this.sortedValues = outputs;
        }
        catch (NotBoundException|RemoteException e) {
            System.out.println("Client error: " + e);
        }
    }
}
