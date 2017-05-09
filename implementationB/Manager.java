package implementationB;

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.*;
import java.nio.charset.*;

public class Manager {

    ////////////////////////////////////////////////////////////
    //                    Object variables                    //
    ////////////////////////////////////////////////////////////

    private double[][] score;
    private String[][] matrix;
    public static BufferedWriter writer = null;
    public static FileWriter fw = null;
    private ArrayList<String[][]> currThreeStates = new ArrayList<String[][]>();
    private Queue<String> q = new LinkedList<String>();

    ////////////////////////////////////////////////////////////
    //                    Object constructor                  //
    ////////////////////////////////////////////////////////////
    
    public Manager() {
        this.score = new double[8][8];
        this.matrix = new String[8][8];

        this.matrix[0][1] = "R1";
        this.matrix[0][3] = "R2";
        this.matrix[0][5] = "R3";
        this.matrix[0][7] = "R4";
        this.matrix[1][0] = "R5";
        this.matrix[1][2] = "R6";
        this.matrix[1][4] = "R7";
        this.matrix[1][6] = "R8";

        this.matrix[7][0] = "B1";
        this.matrix[7][2] = "B2";
        this.matrix[7][4] = "B3";
        this.matrix[7][6] = "B4";
        this.matrix[6][1] = "B5";
        this.matrix[6][3] = "B6";
        this.matrix[6][5] = "B7";
        this.matrix[6][7] = "B8";

        // Place points into every cell of the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.score[i][j] = 0;
            }            
        }
    }

    ////////////////////////////////////////////////////////////
    //                        Setters                         //
    ////////////////////////////////////////////////////////////

    // Save the score of the current state
    protected void setScoreOnPossibleMoves(ArrayList<Integer> posY, ArrayList<Integer> posX) {
        int counter = 0;    
        while ((counter < posX.size()) && (counter < posY.size())) {
            this.score[posY.get(counter)][posX.get(counter)] = 0.5;
            counter++;
        }
    }

    // Save the current state of the board
    protected void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    protected void setQueueOrder(Queue<String> queue) {
        this.q = queue;
    }

    protected void cacheCurrentState(String[][] mat) {

        String[][] tempCopy = cloneArray(mat);

        if (this.currThreeStates.size() == 3){ 
            
            this.currThreeStates.remove(0);
            this.currThreeStates.trimToSize();
            this.currThreeStates.add(tempCopy);
        } else {
            this.currThreeStates.add(tempCopy);
        }

        
    }

    ////////////////////////////////////////////////////////////
    //                        Getters                         //
    ////////////////////////////////////////////////////////////

    // Find the highest score 
    protected ArrayList<Integer> getHighScore(double[][] score) {
        ArrayList<Integer> hcIndex = null;
        double temp = score[0][0];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (temp < score[i][j]) {
                    if (hcIndex == null){
                        hcIndex = new ArrayList<Integer>();
                        hcIndex.add(i);
                        hcIndex.add(j);
                    } else {
                        hcIndex.set(0, i);
                        hcIndex.set(1, j);
                    }
                }
            }
        }
        return hcIndex;
        
    }

    // Converts the raw string of the score to an array of doubles 
    private double[][] getScore(String rawScore) {
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(rawScore);
        
        int row = 0, column = 0;
        double[][] tempHolder = new double[8][8];
        while (m.find()) {
            tempHolder[row][column] = Double.parseDouble(m.group(1).replace("\"", ""));

            if (column >= 7) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }

        return tempHolder;

        // Print temporary holder
        // for (int i = 0; i < 8; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         System.out.print(tempHolder[i][j]);
        //     }
        // }


    }

    ////////////////////////////////////////////////////////////
    //                     Main Functions                     //
    ////////////////////////////////////////////////////////////

    // learn func: the index of the score being updated for an existing matrix/mat
    // learnState: pos or neg; points increases and decreases accordingly
    protected void learn(int[] index, String[][] mat, Queue<String> que, String learnState){
        try {
            // for(Object item : que){
            //     System.out.println("item.toString(): " + item.toString());
            // }

            double[][] temp = stateDoesExist(mat, que);
            

            String oldScore, newScore;
            if (temp != null){
                System.out.println("Found something..");
                oldScore = transformSc(temp);

                if (learnState.equals("pos")) {
                    System.out.println("Learnt something positive..");
                    System.out.println("Before " + temp[index[0]][index[1]]);
                    if (temp[index[0]][index[1]] != 0.0)
                        temp[index[0]][index[1]] += 0.01;
                    else
                        temp[index[0]][index[1]] += 0.51;
                    System.out.println("After " + temp[index[0]][index[1]]);
                    newScore = transformSc(temp);
                    setVariable(getLineNumber(mat, que), newScore);
                } 

                else if (learnState.equals("neg")) {
                    System.out.println("Learnt something negative..");
                    // Gets the second element of the arraylist
                    System.out.println("Before " + temp[index[0]][index[1]]);
                    temp[index[0]][index[1]] -= 0.01;
                    System.out.println("After " + temp[index[0]][index[1]]);
                    newScore = transformSc(temp);
                    setVariable(getLineNumber(mat, que), newScore);
                }
                
            } else {
                temp = stateDoesExist(mat);
                if (temp == null) {
                    System.out.println("Found nothing..");
                } else {
                    System.out.println("Found something in temp..");
                }
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if the state exist in the file
    protected double[][] stateDoesExist(String[][] mat, Queue<String> queue) throws IOException{
        // @TODO - OPEN AND PARSE DATA FILE
        String tempMat = "", tempOrder = "";

        double[][] hc = null;

        tempMat = transformMat(mat);
        tempOrder = transformQ(queue);
        // System.out.println("\n" + tempOrder);

        try {

            File f = new File("implementationB/data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                String currOrder = readLine;
                String currSpace = b.readLine();
                String currScore = b.readLine();

                if (tempMat.equals(currSpace) && tempOrder.equals(currOrder)) {
                    // Get score and return score matrix
                    hc = getScore(currScore);
                    return hc;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hc;
    }

    protected double[][] stateDoesExist(String[][] mat) throws IOException{
        // @TODO - OPEN AND PARSE DATA FILE
        String temp = "";

        double[][] hc = null;

        temp = transformMat(mat);

        try {

            File f = new File("implementationB/data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                String currOrder = readLine;
                String currSpace = b.readLine();
                String currScore = b.readLine();

                if (temp.equals(currSpace)) {
                    // Get score and return score matrix
                    hc = getScore(currScore);
                    return hc;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hc;
    }

    // Print state, score, and order to file
    protected void printToFile(){ 

        File varTmpDir = new File("implementationB/data.txt");
        boolean exists = varTmpDir.exists();

        try {

            if (!exists) {
                varTmpDir.createNewFile();
            }

            fw = new FileWriter(varTmpDir.getAbsoluteFile(), true);
            writer = new BufferedWriter(fw);

            writer.write(transformData("Q")+"\n");
            writer.write(transformData("mat")+"\n");
            writer.write(transformData("sc")+"\n");

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

    ////////////////////////////////////////////////////////////
    //    Transformation function: turns data into String     //
    ////////////////////////////////////////////////////////////

    private String transformData(String option) {
        String temp = "";

        if (option.equals("mat")) {
            temp = transformMat(this.matrix);  
        } else if (option.equals("sc")) {
            temp = transformSc(this.score);          
        } else {
            temp = transformQ(this.q);
        }

        return temp;
    }

    private String transformSc(double[][] sc) {
        String temp = "";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp += "\"";
                temp += sc[i][j];
                temp += "\" "; 
            }
        }            

        return temp;
    }

    private String transformMat(String[][] mat) {
        String temp = "";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp += "\"";
                temp += mat[i][j];
                temp += "\" "; 
            }
        }            

        return temp;
    }

    private String transformQ(Queue q) {
        String temp = "";

        for(Object item : q){
            temp += "\"";
            temp += item.toString();    
            temp += "\" "; 
        }

        return temp;
    }

    /**
     * Clones the provided array
     * 
     * @param src
     * @return a new clone of the provided array
     */
    public static String[][] cloneArray(String[][] src) {
        int length = src.length;
        String[][] target = new String[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public static void setVariable(int lineNumber, String data) throws IOException {
        Path path = Paths.get("implementationB/data.txt");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber-1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    protected int getLineNumber(String[][] mat, Queue<String> queue) throws IOException{
        // @TODO - OPEN AND PARSE DATA FILE
        String tempMat = "", tempOrder = "";
        int currLine = 0;

        double[][] hc = null;

        tempMat = transformMat(mat);
        tempOrder = transformQ(queue);

        try {

            File f = new File("implementationB/data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                String currOrder = readLine;
                String currSpace = b.readLine();
                String currScore = b.readLine();

                currLine += 3;
                if (tempMat.equals(currSpace) && tempOrder.equals(currOrder)) {
                    return currLine;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return currLine;
    }
}