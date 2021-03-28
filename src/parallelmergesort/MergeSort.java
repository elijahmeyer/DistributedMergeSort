/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;
import java.rmi.*;
/**
 *
 * @author elija
 */
public interface MergeSort extends Remote {
    public int[] mergeSort(int[] values) throws RemoteException;
    
}
