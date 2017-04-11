package implementation;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class TicTacToe extends JFrame{
	JPanel p=new JPanel();
	XOButton buttons[]=new XOButton[9];

	public TicTacToe(){
		super("TicTacToe");
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(new GridLayout(3,3));
		for(int i=0;i<9;i++){
			buttons[i]=new XOButton();
			p.add(buttons[i]);
		}
		add(p);
		
		setVisible(true);
	}
}