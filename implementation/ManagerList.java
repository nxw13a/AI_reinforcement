package implementation;

import java.util.*;
import java.io.*;

public class ManagerList {

    // Object Attributes
    public static ArrayList<Manager> managerList = new ArrayList<Manager>();
    private static ArrayList<Integer> possibleStates = new ArrayList<Integer>();
        public static BufferedWriter writer = null;
    public static FileWriter fw = null;

    public void load(){
        
        String path = "data.txt";
        File varTmpDir = new File(path);
        boolean exists = varTmpDir.exists();
        if(exists)
        {
            try {
                OpenFile(path);
                varTmpDir.delete();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
    private boolean sameOrder(int[] orderN, int[] orderO)
    {
        for(int x = 0; x < 9; x++)
        {
            if(orderN[x] != orderO[x])
            {
                return false;
            }
        }
        return true;
    }
        private boolean samePos(String[] orderN, String[] orderO)
    {
        for(int x = 0; x < 9; x++)
        {
            if(orderN[x] != orderO[x])
            {
                return false;
            }
        }
        return true;
    }

    public boolean repeat(int[] orderN, String[] posN)
    {
        for(int x = 0; x < managerList.size(); x++)
        {
            if(sameOrder(orderN,managerList.get(x).order) && samePos(posN,managerList.get(x).position))
            {
                return false;
            }
        }
        return true;
    }
    private void read_in(String[] line, int size)
    {
        for(int t = 0; t < size; t+=4)
        {
            ArrayList<Integer> list_order = new ArrayList<Integer>();
            ArrayList<String> list_position = new ArrayList<String>();
            ArrayList<Double> list_score = new ArrayList<Double>();
            int[] order = new int[9];
            double[] score = new double[9];
            String[] position = new String[9];
            for(int x = 0; x < line[t].length();x++)
            {
                if(line[0].charAt(x) != ' ')
                {
                    list_order.add(Integer.parseInt(line[t].charAt(x) + ""));
                }
            }
            String[] splited = line[t+1].split(" ");
            for(int x = 0; x < list_order.size();x++)
            {
                if(splited[x] == "null")
                    list_position.add(null);
                else
                    list_position.add(splited[x]);
            }
            String[] splited2 = line[t+2].split(" ");
            for(int x = 0; x < list_order.size();x++)
            {
                list_score.add(Double.parseDouble(splited2[x]));
            }
            for(int x = 0; x < list_order.size();x++)
            {
                order[x] = list_order.get(x);
                position[x] = list_position.get(x);
                score[x] = list_score.get(x);
            }
            Manager newManager = new Manager();
            newManager.setPosition(position);
            newManager.setScore(score);
            newManager.setOrderWith(order);
            addState(newManager);
        }

        //System.out.println(list_order);
        //System.out.println(list_position);
        //System.out.println(list_score);
    }
    private int readLines(String path) throws IOException {
        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aline;
        int numberOfLines = 0;
        while ( ( aline = bf.readLine()) != null) {
            numberOfLines++;
        }
        bf.close();

        return numberOfLines;
    }
    private void OpenFile(String path) throws IOException
    {
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);

        int numberOfLines = readLines(path);
        String[] textData = new String[numberOfLines];

        int i;
        for(i = 0; i < numberOfLines; i++) {
             textData[i] = textReader.readLine();
        }
        read_in(textData,numberOfLines);
        textReader.close();
    }
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

         public void printToFile(){ 

        File varTmpDir = new File("data.txt");
        boolean exists = varTmpDir.exists();

  

        try {

            if (!exists) {
                varTmpDir.createNewFile();
            }

            fw = new FileWriter(varTmpDir.getAbsoluteFile(), false);
            writer = new BufferedWriter(fw);

            File f = new File("data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            String first="";
            String second="";
            String third="";
            boolean d=true;

            if(d)
            {
                for(int x = 0; x < managerList.size(); x++)
                {
                    for (int i = 0; i < 9; i++) {
                        writer.write(managerList.get(x).order[i] + " ");
                    }
                    writer.write("\n");
                    for (int i = 0; i < 9; i++) {
                        writer.write(managerList.get(x).position[i] + " ");
                    } 
                    writer.write("\n");
                    for (int i = 0; i < 9; i++) {
                        writer.write(managerList.get(x).score[i] + " ");

                    }
                    writer.write("\n\n");
                  }
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