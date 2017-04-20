package implementation;

import java.util.*;

class Reinforcement{

	public static String[] position = new String[9];
	public static int[] order = new int[9];
	public static double[] score = new double[9];
	// public static ArrayList<Double> score;
	public static XOButton buttons[] = new XOButton[9];
	public static int count = 0;
	public static int pick = 1;

	public Reinforcement(){
		for (int i = 0; i < 9; i++) {
            score[i] = 0.5;
        }
	}

	public static void reinit() {
		// score.clear();
		for (int i = 0; i < 9; i++) {
            score[i] = 0.5;
        }
	}

	public static String print() {
		String hold = "";
		for(int x = 0; x < 9; x++)
		{
			if(position[x] == null)
				hold+="_";
			else
				hold += position[x];
		}
		return hold;
	}

	public static boolean check_forX()
	{
		if(position[0] == "X" && position[1] == "X" && position[2] == "X")
				return true;
		if(position[3] == "X" && position[4] == "X" && position[5] == "X")
				return true;
		if(position[6] == "X" && position[7] == "X" && position[8] == "X")
				return true;
		if(position[0] == "X" && position[3] == "X" && position[6] == "X")
				return true;
		if(position[1] == "X" && position[4] == "X" && position[7] == "X")
				return true;
		if(position[2] == "X" && position[5] == "X" && position[8] == "X")
				return true;
		if(position[0] == "X" && position[4] == "X" && position[8] == "X")
				return true;
		if(position[2] == "X" && position[4] == "X" && position[6] == "X")
				return true;
		return false;			
	}
	public static boolean check_forO()
	{
		if(position[0] == "O" && position[1] == "O" && position[2] == "O")
				return true;
		if(position[3] == "O" && position[4] == "O" && position[5] == "O")
				return true;
		if(position[6] == "O" && position[7] == "O" && position[8] == "O")
				return true;
		if(position[0] == "O" && position[3] == "O" && position[6] == "O")
				return true;
		if(position[1] == "O" && position[4] == "O" && position[7] == "O")
				return true;
		if(position[2] == "O" && position[5] == "O" && position[8] == "O")
				return true;
		if(position[0] == "O" && position[4] == "O" && position[8] == "O")
				return true;
		if(position[2] == "O" && position[4] == "O" && position[6] == "O")
				return true;
		return false;		
	}
}
