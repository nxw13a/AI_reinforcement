package implementation;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.security.SecureRandom;

public class XOButton extends JButton implements ActionListener{
	ImageIcon X,O;
	byte value=0;
	int count = 0;
	int location_button;
	Reinforcement lib;

	/*
	0:nothing
	1:X
	2:O
	*/
	
	public XOButton(int location){
		X=new ImageIcon(this.getClass().getResource("X.png"));
		O=new ImageIcon(this.getClass().getResource("O.png"));
		location_button = location;
		lib = new Reinforcement();
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){

		if(lib.check_forX())
		{
			System.out.println("YOU WIN");
		}
		if(lib.check_forO())
		{
			System.out.println("YOU LOST");
		}
		if(lib.position[location_button] == null && lib.check_forX() == false && lib.check_forO() == false)
		{
			//System.out.println(location_button);
			lib.position[location_button] = "X";
			setIcon(X);
			this.removeActionListener(this);
			lib.count++;
			if(lib.check_forX())
			{
				System.out.println("YOU WIN");
			}
			if(lib.check_forO())
			{
				System.out.println("YOU LOST");
			}
			if(lib.count <= 8 && lib.check_forX() == false && lib.check_forO() == false)
			{
				Random a = new SecureRandom();
				int select = a.nextInt(9);
				while(lib.position[select] != null)
				{
					select = a.nextInt(9);
				}
				lib.buttons[select].setIcon(O);
				lib.position[select] = "O";
				lib.count++;
				//System.out.println(lib.print());
			}
		}
		else if(lib.position[location_button] != null && lib.check_forX() == false && lib.check_forO() == false)
		{
			this.removeActionListener(this);
		}
	}
}