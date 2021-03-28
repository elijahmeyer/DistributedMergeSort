/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;
import java.rmi.RemoteException;

/**
 *
 * @author elija
 */
public class ParallelMergeSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Running the main app");
        int[] inputs = {8, 6, 7, 5, 3, 0, 9};
        
        try {
            Client c = new Client(inputs);
            c.connectRemote();
        }
        catch (RemoteException e) {
            System.out.println("Main app exception: " + e);
        }
    }
    
}
