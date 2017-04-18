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
        if (managerList.size() == 0) {
            return true;
        } else
            return false;
    }

    public static void addState(Manager pos) {
        managerList.add(pos);
    }

    private static Manager getItemAtIndex(int index) {
        return managerList.get(index);
    }

    public static int doesExist(String[] pos) {
        if (getSize() == 0) {
            return -1;
        } else {
            for (int i = 0; i < getSize(); i++) {
                if (pos == getItemAtIndex(i).getPosition()) {
                    return i;
                }
            }
            return -1;
        }

    }

}