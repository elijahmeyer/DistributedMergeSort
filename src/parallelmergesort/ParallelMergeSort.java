/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelmergesort;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        ArrayList<Integer> inputList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            inputList.add((int)(Math.random() *(99)));
        }
        
        int threadCount = 7;
        int fraction = inputList.size() / threadCount;
        
        ArrayList<Integer>[] valueLists = new ArrayList[threadCount];
        int maxIndex = 0;
        for (int i = 0; i < threadCount - 1; i++) {
            maxIndex = (i+1) * fraction;
            ArrayList<Integer> valueList = new ArrayList<>(inputList.subList((i * fraction), maxIndex));
            valueLists[i] = valueList;
        }
        valueLists[threadCount - 1] = new ArrayList<>(inputList.subList(maxIndex, inputList.size()));
        
        try {
            ClientRunnable[] threadList = new ClientRunnable[threadCount];
            for (int i = 0; i < threadCount; i++) {
                threadList[i] = new ClientRunnable(valueLists[i]);
                threadList[i].start();
            }
        
            for (int i = 0; i < threadCount; i++) {
                threadList[i].join();
                System.out.println("Output: " + threadList[i].outputs.toString());
            }
        
            ArrayList<Integer> finalOutput = threadList[0].outputs;
            for (int i = 1; i < threadList.length; i++) {
                finalOutput = mergeLists(finalOutput, threadList[i].outputs);
            }
            System.out.println("Final Sorted List: " + finalOutput.toString());
            System.out.println("List length: " + finalOutput.size());

        }
        catch (Exception e) {
            System.out.println("Main app exception: " + e);
        }
    }
    
    public static ArrayList<Integer> mergeLists(ArrayList<Integer> first, ArrayList<Integer> last) {
        ArrayList<Integer> sorted = new ArrayList<>();
        
        while (!first.isEmpty() && !last.isEmpty()) {
            int firstNum = first.get(0);
            int lastNum = last.get(0);
            
            if (firstNum <= lastNum) {
                sorted.add(first.remove(0));
            }
            else {
                sorted.add(last.remove(0));
            }
        }
        
        if (first.isEmpty()) {
            sorted.addAll(last);
        }
        if (last.isEmpty()) {
            sorted.addAll(first);
        }
        return sorted;
    }
    
}
