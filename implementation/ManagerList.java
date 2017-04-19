package implementation;

import java.util.*;

public class ManagerList {

    // Object Attributes
    private static List<Manager> managerList;

    public ManagerList() {
        managerList = new ArrayList<Manager>();
    }

    public static int getSize() {
        return managerList.size();
    }

    public static boolean isEmpty() {

        boolean flag = ((managerList.size() == 0) ? true : false);
        return flag;
    }

    public static void addState(Manager pos) {
        managerList.add(pos);
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

    // public static String stringBuild(String[] pos) {
    //     String temp = "";
    //     for (int i = 1; i < 9; i++) {
    //         temp += pos[i];
    //     }
    //     return temp;
    // }

    // public static int isSubset(String[] pos) {
    //     for (int i = 0; i < getSize(); i++) {
    //         System.out.println("getItemAtIndex(i).getPosition(): " + getItemAtIndex(i).getPosition());
    //         System.out.println("stringBuild(pos): " + stringBuild(pos));
    //         if (getItemAtIndex(i).getPosition().contains(stringBuild(pos))){
    //             System.out.println("true");
    //             return i;
    //         }
    //     }
    //     return -1;
    // }
}