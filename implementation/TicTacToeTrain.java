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

	public TicTacToeTrain(){
		super("TicTacToe");
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(3,3));
		for(int i=0;i<9;i++){
			lib.buttons[i]=new XOButton(i);
			p.add(lib.buttons[i]);
		}
		add(p);
		setVisible(true);
	}
	public void touch()
	{
		int x=9;
		generateRandomMove();
		lib.buttons[select].doClick();
	}
	private void generateRandomMove() {
		Random a = new SecureRandom();
		select = a.nextInt(9);
		while(lib.position[select] != null)
		{
				select = a.nextInt(9);
		}
	}
	public void startTrain()
	{
		for (int i = 0;i<5;i++) {
			if (lib.check_forX() == true || lib.check_forO() == true) {
				break;
			}
			touch();
		}
	}
}
	