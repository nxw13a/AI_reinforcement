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