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
import java.util.Scanner;
/**
 *
 * @author elija
 */
public class Client {
    private int[] values;
    
    public Client(int[] values) throws RemoteException {
        this.values = values;
    }
    
    public void connectRemote() throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 9999);
            MergeSort ms = (MergeSort)reg.lookup("hi server");
            int[] outputs = ms.mergeSort(this.values);
            System.out.print("Output: ");
            for (int i = 0; i < outputs.length; i++) {
                System.out.print(outputs[i] + " ");
            }
            System.out.println("");
        }
        catch (NotBoundException|RemoteException e) {
            System.out.println("Client error: " + e);
        }
    }
}
