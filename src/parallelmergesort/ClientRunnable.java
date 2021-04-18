/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author elija
 */
public class ClientRunnable extends Thread {
    private ArrayList<Integer> inputs;
    public ArrayList<Integer> outputs;
    
    public ClientRunnable(ArrayList<Integer> values) {
        this.inputs = values;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("" + Thread.currentThread().getId() + " has started.");
            Client c = new Client(this.inputs);
            c.connectRemote();
            this.outputs = c.sortedValues;
            System.out.println("" + Thread.currentThread().getId() + " has finished.");
        }
        catch (RemoteException e) {
            System.out.println("Runnable exception: " + e);
        }
    }
}
