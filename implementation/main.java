package implementation;

import java.util.*;
import java.lang.Thread;
class Main{
	public static void main(String[] args)
	{

		System.out.println("Welcome: Train Agent (0) or Play Agianst (1) : ");
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		int iterations = 5;
		if(i == 1)
		{
			TicTacToe run = new TicTacToe();	
		}
		else if(i == 0)
		{
			TicTacToeTrain tr = new TicTacToeTrain();
			long t= System.currentTimeMillis();
			long end = t+300000;
			while(System.currentTimeMillis() < end) {
			  	tr.startTrain();
			}
			
		}
		else
		{
			System.out.println("You failed to type a correct letter...Fail..Goodbye");
			return;
		}

	}
}