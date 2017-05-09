package implementation;

import java.util.*;
import java.io.*;

public class Manager {

    // Object Attributes
    public int[] order;
    public double[] score;
    public String[] position;
    public static BufferedWriter writer = null;
    public static FileWriter fw = null;

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

    public void updateScore(int index, boolean increase) {
        if (this.score[index] != 0) {
            if (increase)
                this.score[index] += 0.01;
            else
                this.score[index] -= 0.01;
        }
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

    public double getHighestScoreByOrder(int turn) {

        double checker = -1;

        for (int i = 0; i < 9; i++) {
            if (this.order[i] == turn) {
                checker = this.score[i];
                break;
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

    public boolean getPossibleStates (String[] pos, int[] currentOrder, int turn) {
        boolean flag = false;
        for (int i = 1; i <= turn; i++) {
            for (int j = 0; j < 9; j++) {
                if ((currentOrder[j] == i) && (this.order[j] == i)){
                    flag = true;
                    break;
                } else {
                    flag = false;
                }   
            }
            if (!flag) {break;}
        }
        return flag;
    }

    public boolean compare (String[] pos, int[] currentOrder, int turn) {
        boolean flag = true;
        for (int i = 1; i <= turn; i++) {
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

    public void printToFile(){ 

        File varTmpDir = new File("data.txt");
        boolean exists = varTmpDir.exists();

        String tmp1="";
        for (int x=0;x<9;x++) 
        {
            
            tmp1+=order[x]+ " ";
        }
        String tmp2="";
        for (int x=0;x<9;x++) 
        {
            
            tmp2+=position[x]+ " ";
        }

        try {

            if (!exists) {
                varTmpDir.createNewFile();
            }

            fw = new FileWriter(varTmpDir.getAbsoluteFile(), true);
            writer = new BufferedWriter(fw);

            File f = new File("data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            String first="";
            String second="";
            String third="";
            boolean d=true;
            while ((first = b.readLine()) != null) {
                second=b.readLine();
                third=b.readLine();
                if(tmp1.equals(first) && tmp2.equals(second))
                {
                    d=false;
                    break;
                }
            }
            if(d)
            {
                for (int i = 0; i < 9; i++) {
                    writer.write(order[i] + " ");
                }
                writer.write("\n");
                for (int i = 0; i < 9; i++) {
                    writer.write(position[i] + " ");
                } 
                writer.write("\n");
                for (int i = 0; i < 9; i++) {
                    writer.write(score[i] + " ");
                }
                writer.write("\n\n");
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (writer != null)
                    writer.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }   
    }
}