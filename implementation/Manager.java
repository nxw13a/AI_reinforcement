package implementation;

import java.util.*;

public class Manager {

    // Object Attributes
    private static int[] order;
    private static List<Double> score;
    private static String[] position;

    // Constructor
    public Manager(){
        position = new String[9];
        score = new ArrayList<Double>();
        order = new int[9];

        for (int i = 0; i < 9; i++) {
            order[i] = 0;
            score.add(i, 0.5);
        }
    }
    
    /*
        Returns the highest score so that the better 
        move for this state may be played by the bot
    */
    public static int getHighestScore() {
        int checker = 0;
        for (int i = 1; i < 9; i++) {
            if (score.get(i) > score.get(checker)) {
                checker = i;
            }
        }
        System.out.println("score: " + score);
        System.out.println("High: " + score.get(checker));
        return checker;
    }

    public static void setOrderWith(int[] turnNumber) {
        order = turnNumber;
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
            System.out.println("plus: " + score);
        } else {
            value = score.get(index) - 0.1;
            score.set(index, value);
            System.out.println("minus: " + score);
        }
    }
    public static void setScore(List<Double> sc) {
        score = sc;
    }

    // sets the position of a state
    public static void setPosition(String[] pos) {
        position = pos;
    }

    // Checks if all possible moves have the same score
    public static boolean isEqualScore() {
        double checker = score.get(0);
        for (int i = 1; i < 9; i++) {
            if (score.get(i) != checker) {
                return false;
            }
        }
        return true;
    }

    public static double getScoreAtIndex(int index) {
        return score.get(index);
    }  

    public static boolean compare (String[] pos, int[] currentOrder, int count) {
        boolean flag = true;
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j < 9; j++) {
                if ((currentOrder[j] != i) && (order[j] != i)){
                    flag = false;
                } else {
                    flag = true;
                }
                if (flag) {
                    if (pos[j] != position[j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}