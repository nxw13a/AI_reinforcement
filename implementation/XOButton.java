package implementation;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.security.SecureRandom;

public class XOButton extends JButton implements ActionListener{
	ImageIcon X,O;
	byte value=0;
	int count = 0;
	int location_button;
	Reinforcement lib;
	ManagerList managerList = new ManagerList();
	int indexOfManager = 0;
	int select;
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
	
	private void resetBoard() {
		for(int i=0;i<9;i++){
			lib.buttons[i].setIcon(null);
		}
		indexOfManager = 0;
	}

	private void resetWin(){
		System.out.println("YOU WIN");
		resetBoard();
		lib.position = new String[9];
		lib.count = 0;
		lib.pick = 1;
	}

	private void resetLost(){
		System.out.println("YOU LOST");
		resetBoard();
		lib.position = new String[9];
		lib.count = 0;
		lib.pick = 0;
	}

	private void resetDraw(){
		System.out.println("NO ONE WIN");
		resetBoard();
		lib.position = new String[9];
		lib.count = 0;
		lib.pick = 1;
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
				if (managerList.doesExist(lib.position) != -1) {
					// @TODO - UPDATE SCORE
					managerList.getItemAtIndex(indexOfManager).setScoreAtIndex(select, false);
				} else {
					Manager newManager = new Manager();
					// @TODO - Add position to list
					newManager.setPosition(lib.position);
					newManager.setScoreAtIndex(location_button, false);
					// @TODO - Push to ManagerList
					managerList.addState(newManager);
					
				}				
				resetWin();

			}
			else if(lib.check_forO())
			{
				// If the state is found in the manager
				if (managerList.doesExist(lib.position) != -1) {
					// @TODO - UPDATE SCORE
					managerList.getItemAtIndex(indexOfManager).setScoreAtIndex(select, true);
				} else {
					Manager newManager = new Manager();
					// @TODO - Add position to list
					newManager.setPosition(lib.position);
					newManager.setScoreAtIndex(location_button, true);
					// @TODO - Push to ManagerList
					managerList.addState(newManager);
				}				
				resetLost();

			}
			else if(lib.check_forO() == false && lib.check_forX() == false && lib.count == 9)
			{
				resetDraw();
			}
			else if(lib.count <= 8 && lib.count >= lib.pick &&lib.check_forX() == false && lib.check_forO() == false)
			{

				if (managerList.isEmpty()) {
					//lib.pick = false;
					Random a = new SecureRandom();
					select = a.nextInt(9);
					while(lib.position[select] != null)
					{
						select = a.nextInt(9);
					}	
				} else {
					for (int i = 0; i < managerList.getSize(); i++) {
						if (managerList.getItemAtIndex(i).isSubset(lib.position)) {
							indexOfManager = i;
							break;
						}
					}

					if (managerList.getItemAtIndex(indexOfManager).isEqualScore()) {
						Random a = new SecureRandom();
						select = a.nextInt(9);
						while(lib.position[select] != null)
						{
							select = a.nextInt(9);
						}	
					} else {
						select = managerList.getItemAtIndex(indexOfManager).getHighestScore();
						if (lib.position[select] != null){
							Random a = new SecureRandom();
							select = a.nextInt(9);
							while(lib.position[select] != null){
								select = a.nextInt(9);
							}	
						}
					}
				}

				lib.buttons[select].setIcon(O);
				lib.position[select] = "O";
				lib.count++;
		
				if(lib.check_forX())
				{
					// If the state is found in the manager
					if (managerList.doesExist(lib.position) != -1) {
						// @TODO - UPDATE SCORE
						managerList.getItemAtIndex(indexOfManager).setScoreAtIndex(select, false);
					} else {
						Manager newManager = new Manager();
						// @TODO - Add position to list
						newManager.setPosition(lib.position);
						// @TODO - Push to ManagerList
						managerList.addState(newManager);
					}
					resetWin();
				}
				else if(lib.check_forO())
				{
					// If the state is found in the manager
					if (managerList.doesExist(lib.position) != -1) {
						// @TODO - UPDATE SCORE
						managerList.getItemAtIndex(indexOfManager).setScoreAtIndex(select, true);
					} else {
						Manager newManager = new Manager();
						// @TODO - Add position to list
						newManager.setPosition(lib.position);
						// @TODO - Push to ManagerList
						managerList.addState(newManager);
					}
					resetLost();

				}
				else if(lib.check_forO() == false && lib.check_forX() == false && lib.count == 9)
				{
					resetDraw();
				}
			}
		}
	}
}