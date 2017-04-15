package implementation;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ArrayList;

public class TicTacToe extends JFrame{
	JPanel p=new JPanel();
	Reinforcement lib = new Reinforcement();

	public TicTacToe(){
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
}