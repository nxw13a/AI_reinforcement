package implementation;
import java.awt.GridLayout;
import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class TicTacToe extends JFrame{
	JPanel p=new JPanel();
	Reinforcement lib = new Reinforcement();
	ManagerList managerList = new ManagerList();
	public TicTacToe(){
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
	
}