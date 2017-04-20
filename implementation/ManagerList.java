package implementation;

import java.util.*;
import java.io.*;

public class ManagerList {

    // Object Attributes
    public static ArrayList<Manager> managerList = new ArrayList<Manager>();

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

    public static int doesExist(String[] pos, int[] currentOrder, int count) {
        for (int i = 0; i < getSize(); i++) {
            if (getItemAtIndex(i).compare(pos ,currentOrder, count)) {
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