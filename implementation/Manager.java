package implementation;

import java.util.*;

public class Manager {

    // Object Attributes
    private static List<Double> score;
    private static String[] position;

    // Constructor
    public Manager(){
        position = new String[9];
        score = new ArrayList<Double>();

        for (int i = 0; i < 9; i++) {
            score.add(i, 0.5);
        }
    }
    
    /*
        Returns the highest score so that the better 
        move for this state may be played by the bot
    */
    public static int getHighestScore() {
        double checker = 0;
        for (int i = 1; i < 9; i++) {
            if (score.get(i) > score.get(checker)) {
                checker = i;
            }
        }
        return checker;
    }

    /*
        posNeg - Positive or Negative (True / False)
        Score will increase when positive move is made and decrease for a bad move
    */
    public static void setScoreAtIndex(int index, boolean posNeg) {
        double value;
        if (posNeg){
            value = score.get(index) + 0.1;
            score.set(index, value);
        } else {
            value = score.get(index) - 0.1;
            score.set(index, value);
        }
    }

    public static boolean isSubset(String[] pos) {
        return position.contains(pos);
    }

    // gets the position of a state
    public static String[] getPosition() {
        return position;
    }

    // sets the position of a state
    public static void setPosition(String[] pos) {
        position = pos;
    }

    // Checks if all possible moves have the same score
    public static boolean isEqualScore() {
        boolean flag = true; 
        double checker = score.get(0);
        for (int i = 1; i < 9; i++) {
            if (score.get(i) != checker) {
                return false;
            }
        }
        return flag;
    }
}