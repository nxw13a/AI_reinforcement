package implementation;

import java.util.*;
import java.io.*;

public class ManagerList {

    // Object Attributes
    public static ArrayList<Manager> managerList = new ArrayList<Manager>();
    private static ArrayList<Integer> possibleStates = new ArrayList<Integer>();

    public static int getSize() {
        return managerList.size();
    }

    public static boolean isEmpty() {

        boolean flag = ((managerList.size() == 0) ? true : false);
        return flag;
    }

    public static void addState(Manager pos) {
        Manager temp = new Manager(pos);
        managerList.add(temp);
    }

    public static Manager getItemAtIndex(int index) {
        return managerList.get(index);
    }

    private void getPossibleStates (String[] pos, int[] currentOrder, int turn) {
        if (!possibleStates.isEmpty()){
            possibleStates.clear();
        }

        // Check for errors here
        for (int i = 0; i < getSize(); i++) {
            if (getItemAtIndex(i).getPossibleStates(pos, currentOrder, turn)) {
                possibleStates.add(i);
                // getItemAtIndex(i).print();
            }
        }

        // System.out.println("possibleStates: " + possibleStates);
        // System.out.println("\ngetSize: " + possibleStates.size());
    }

    public int getHighestScore(String[] pos, int[] currentOrder, int turn) {
        getPossibleStates(pos, currentOrder, turn);

        if (possibleStates.isEmpty()){
            // System.out.println("isEmpty is returned");
            return -1;
        }

        if (possibleStates.size() == 1) {
            // System.out.println("size == 1 is returned / " + possibleStates.get(0));
            return possibleStates.get(0);
        }

        int managerWithHighestScore = 0;
        // Gets the first state from all the possible moves
        double currManager = getItemAtIndex(possibleStates.get(0)).getHighestScoreByOrder(turn+1);

        for (int i = 1; i < possibleStates.size(); i++) {
            double nextManager = getItemAtIndex(possibleStates.get(i)).getHighestScoreByOrder(turn+1);
            // System.out.println("currManager: " + currManager);
            // System.out.println("nextManager: " + nextManager);
            if ((currManager < nextManager) && nextManager != -1) {
                currManager = nextManager;
                managerWithHighestScore = i;
            }
        }

        // System.out.println("managerWithHighestScore: " + managerWithHighestScore);
        return managerWithHighestScore;
    }

    public static int doesExist(String[] pos, int[] currentOrder, int turn) {
        for (int i = 0; i < getSize(); i++) {
            if (getItemAtIndex(i).compare(pos ,currentOrder, turn)) {
                return i;
            }
        }
        return -1;
    }

    public static void print(){ 
        for (int i = 0; i < getSize(); i++) {
            getItemAtIndex(i).print();
        }
        System.out.println(" ");
    }

    public static void printToFile(){ 
        for (int i = 0; i < getSize(); i++) {
            getItemAtIndex(i).printToFile();
        }
    }

    
}