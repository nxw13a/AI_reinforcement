package implementation;
import java.awt.GridLayout;
import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class TicTacToeTrain extends JFrame{
	JPanel p=new JPanel();
	Reinforcement lib = new Reinforcement();
	int select;
	public static BufferedReader  br = null;
    public static FileReader fr = null;
	ManagerList managerList = new ManagerList();
	public int[] order = new int[9];
    public double[] score = new double[9];
    public String[] position = new String[9];

    //XOButton v = new XOButton();
	public TicTacToeTrain(){
		super("TicTacToe");
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(3,3));
		managerList.load();
		for(int i=0;i<9;i++){
			lib.buttons[i]=new XOButton(i, managerList);
			p.add(lib.buttons[i]);
		}
		add(p);
		setVisible(true);
	}
	public void touchs()
	{
		//int x;
	  	generateRandomMove();
		lib.buttons[select].doClick();
	}
	public void startTrain()
	{
		for (int i = 0;i<5;i++) {
			touchs();
		}
	}
	public void generateRandomMove() {
		Random a = new SecureRandom();
		select = a.nextInt(9);
		while(lib.position[select] != null)
		{
			select = a.nextInt(9);
		}
	}
	public void loadData()
	{
		try {
			File f = new File("data.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLins = "";
            while ((readLins = b.readLine()) != null) {
	            String[] o = readLins.split("\\.");
	            readLins = b.readLine();
	            position = readLins.split("\\.");
	            readLins = b.readLine();
	            String[] s = readLins.split("\\.");
	            check(o,s);
            }


		} catch (IOException e) {
            e.printStackTrace();
        }

		}
	public void check(String[] ab, String[] ba)
	{
	    
       	System.out.println(ab[2]);
		Manager newManager = new Manager();
		// Add position to list
		newManager.setPosition(position);
		newManager.setScore(score);
		newManager.setOrderWith(order);
		// Push to ManagerList
		managerList.addState(newManager);
	}
}
	
