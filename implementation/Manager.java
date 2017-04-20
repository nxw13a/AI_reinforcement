package implementation;

import java.util.*;

public class Manager {

    // Object Attributes
    public int[] order;
    public double[] score;
    public String[] position;

    // Constructor
    public Manager(){
        this.score = new double[9];;
        this.position = new String[9];
        this.order = new int[9];

        for (int i = 0; i < 9; i++) {
            this.order[i] = 0;
            this.score[i] = 0.5;
        }
    }

    public Manager(Manager other){
        this.score = other.score;
        this.position = other.position;
        this.order = other.order;
    }
    
    public void print(){ 
        for (int i = 0; i < 9; i++) {
            System.out.print(order[i] + " ");
        }
        System.out.println(" ");
        for (int i = 0; i < 9; i++) {
            System.out.print(position[i] + " ");
        } 
        System.out.println(" ");
        for (int i = 0; i < 9; i++) {
            System.out.print(score[i] + " ");
        }
        System.out.println("\n");
    }

    /*
        Returns the highest score so that the better 
        move for this state may be played by the bot
    */
    public int getHighestScore() {
        int checker = 0;
        for (int i = 1; i < 9; i++) {
            if (this.score[i] > this.score[checker]) {
                checker = i;
            }
        }
        return checker;
    }

    public void setScore(double[] sc) {
        double [] clonedSc = (double[])sc.clone();
        this.score = clonedSc;
    }

    // sets the position of a state
    public void setPosition(String[] pos) {
        String [] clonedPos = (String[])pos.clone();
        this.position = clonedPos;
    }

    public void setOrderWith(int[] turnNumber) {
        int [] clonedTurn= (int[])turnNumber.clone();
        this.order = clonedTurn;
    }

    // Checks if all possible moves have the same score
    public boolean isEqualScore() {
        double checker = score[0];
        for (int i = 1; i < 9; i++) {
            if (score[i] != checker) {
                return false;
            }
        }
        return true;
    }

    public double getScoreAtIndex(int index) {
        return this.score[index];
    }  

    public boolean compare (String[] pos, int[] currentOrder, int count) {
        boolean flag = true;
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j < 9; j++) {
                if ((currentOrder[j] != i) && (this.order[j] != i)){
                    flag = false;
                } else {
                    flag = true;
                }
                if (flag) {
                    if (pos[j] != this.position[j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}