package implementation;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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

		//System.out.println(lib.print());

		if(lib.position[location_button] == null && lib.check_forX() == false && lib.check_forO() == false)
		{
			lib.position[location_button] = "X";
			setIcon(X);
			//lib.pick = ((lib.count > 8) ? true  : false);
			//this.removeActionListener(this);
			lib.count++;
			//TimeUnit.SECONDS.sleep(1);
			if(lib.check_forX())
			{
				System.out.println("YOU WIN");
				for(int i=0;i<9;i++){
					lib.buttons[i].setIcon(null);
				}
				lib.position = new String[9];
				lib.count = 0;
				lib.pick = 1;

			}
			else if(lib.check_forO())
			{
				System.out.println("YOU LOST");
				for(int i=0;i<9;i++){
					lib.buttons[i].setIcon(null);
				}
				lib.position = new String[9];
				lib.count = 0;
				lib.pick = 0;

			}
			else if(lib.check_forO() == false && lib.check_forX() == false && lib.count == 9)
			{
				System.out.println("NO ONE WIN");
				for(int i=0;i<9;i++){
					lib.buttons[i].setIcon(null);
				}
				lib.position = new String[9];
				lib.count = 0;
				lib.pick = 1;
			}

			else if(lib.count <= 8 && lib.count >= lib.pick &&lib.check_forX() == false && lib.check_forO() == false)
			{
				//lib.pick = false;
				Random a = new SecureRandom();
				int select = a.nextInt(9);
				while(lib.position[select] != null)
				{
					select = a.nextInt(9);
				}
				lib.buttons[select].setIcon(O);
				lib.position[select] = "O";
				lib.count++;
		
				if(lib.check_forX())
				{
					System.out.println("YOU WIN");
					for(int i=0;i<9;i++){
						lib.buttons[i].setIcon(null);
					}
					lib.position = new String[9];
					lib.count = 0;
					lib.pick = 1;

				}
				else if(lib.check_forO())
				{
					System.out.println("YOU LOST");
					for(int i=0;i<9;i++){
						lib.buttons[i].setIcon(null);
					}
					lib.position = new String[9];
					lib.count = 0;
					lib.pick = 0;

				}
				else if(lib.check_forO() == false && lib.check_forX() == false && lib.count == 9)
				{
					System.out.println("NO ONE WIN");
					for(int i=0;i<9;i++){
						lib.buttons[i].setIcon(null);
					}
					lib.position = new String[9];
					lib.count = 0;
					lib.pick = 1;
				}
				//System.out.println(lib.print());
			}
		}
	}
}