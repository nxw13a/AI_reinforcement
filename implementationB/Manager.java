package implementationB;

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Manager {

    private double[][] score;
    private String[][] matrix;
    public static BufferedWriter writer = null;
    public static FileWriter fw = null;

    // Constructor
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

    protected void setScoreOnPossibleMoves(ArrayList<Integer> posY, ArrayList<Integer> posX) {
        int counter = 0;    
        while ((counter < posX.size()) && (counter < posY.size())) {
            this.score[posY.get(counter)][posX.get(counter)] = 0.5;
            counter++;
        }
    }

    protected void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    protected double[][] stateDoesExist(String[][] mat) throws IOException{
        // @TODO - OPEN AND PARSE DATA FILE
        String temp = "";
        double[][] hc = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp += "\"";
                temp += mat[i][j];
                temp += "\" ";
            }
        }

        try {

            File f = new File("implementationB/data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                if (temp.equals(readLine)) {
                    // Get score and return score matrix
                    System.out.println("Found .... .. . .. . . . \n\n");
                    hc = getScore(b.readLine());
                    return hc;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hc;
    }

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
                        hcIndex.add(0, i);
                        hcIndex.add(1, j);
                    }
                    return hcIndex;
                }
            }
        }

        return hcIndex;
    }

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

    protected void printToFile(){ 

        File varTmpDir = new File("implementationB/data.txt");
        boolean exists = varTmpDir.exists();

        try {

            if (!exists) {
                varTmpDir.createNewFile();
            }

            fw = new FileWriter(varTmpDir.getAbsoluteFile(), true);
            writer = new BufferedWriter(fw);

            String[] dataToPrint = transformData();

            for (int i = 0; i < 2; i++) {
                writer.write(dataToPrint[i]+"\n");
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

    private String[] transformData() {
        String temp[] = new String[2];
        Arrays.fill(temp, "");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[0] += "\"";
                temp[0] += this.matrix[i][j];
                temp[0] += "\" "; 
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[1] += "\"";
                temp[1] += this.score[i][j];
                temp[1] += "\" "; 
            }
        }

        return temp;
    }
}