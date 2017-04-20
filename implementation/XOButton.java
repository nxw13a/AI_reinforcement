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
	ManagerList managerList;
	int indexOfManager = -1;
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
		managerList = new ManagerList();
		this.addActionListener(this);
	}
	
	private void resetBoard() {
		for(int i=0;i<9;i++){
			lib.buttons[i].setIcon(null);
		}
		indexOfManager = 0;
		lib.reinit();
		managerList.print();
	}

	private void generateRandomMove() {
		Random a = new SecureRandom();
		select = a.nextInt(9);
		while(lib.position[select] != null)
		{
			select = a.nextInt(9);
		}
	}

	private void resetWin(){
		System.out.println("YOU WIN");
		// If the state does not exist
		int index = managerList.doesExist(lib.position, lib.order, lib.count);
		if (index == -1) {
			Manager newManager = new Manager();
		// 	// Add position to list
			newManager.setPosition(lib.position);
			newManager.setScore(lib.score);
			newManager.setOrderWith(lib.order);
		// 	// Push to ManagerList
			// newManager.print();
			managerList.addState(newManager);
		} else {
			managerList.getItemAtIndex(index).setScore(lib.score);
		}

		resetBoard();
		lib.position = new String[9];
		lib.order = new int[9];
		lib.count = 0;
		lib.pick = 1;
	}

	private void resetLost(){
		System.out.println("YOU LOST");
		// If the state does not exist
		int index = managerList.doesExist(lib.position, lib.order, lib.count);
		if (index == -1) {
			Manager newManager = new Manager();
			// Add position to list
			newManager.setPosition(lib.position);
			newManager.setScore(lib.score);
			newManager.setOrderWith(lib.order);
			// Push to ManagerList
			managerList.addState(newManager);
		} else {
			managerList.getItemAtIndex(index).setScore(lib.score);
		}

		resetBoard();
		lib.position = new String[9];
		lib.order = new int[9];
		lib.count = 0;
		lib.pick = 0;
	}

	private void resetDraw(){
		System.out.println("NO ONE WIN");
		resetBoard();
		lib.position = new String[9];
		lib.order = new int[9];
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

			lib.order[location_button] = lib.count;
			// lib.printOrder();
			//TimeUnit.SECONDS.sleep(1);
			if(lib.check_forX())
			{
				if (lib.count >= 2){
					lib.score[lib.count-1] = lib.score[lib.count-1] + 0.1;
					lib.score[lib.count-2] = lib.score[lib.count-2] - 0.1;
				}
				resetWin();

			}
			else if(lib.check_forO() == false && lib.check_forX() == false && lib.count == 9)
			{
				resetDraw();
			}
			else if(lib.count <= 8 && lib.count >= lib.pick &&lib.check_forX() == false && lib.check_forO() == false)
			{
				if (managerList.isEmpty()) {
					generateRandomMove();

				} else {
					
					indexOfManager = managerList.doesExist(lib.position, lib.order, lib.count);
					// @ TODO - IF STATES DON'T EXIST
					if (indexOfManager == -1) {
						generateRandomMove();
					} else { // @ TODO - IF STATE EXISTS
						if (managerList.getItemAtIndex(indexOfManager).isEqualScore()) {
							generateRandomMove();
						} else {
							select = managerList.getItemAtIndex(indexOfManager).getHighestScore();
							if (lib.position[select] != null){
								generateRandomMove();
								// @ TODO - GET NEXT OR EQUAL
								// Manager currentIndex = managerList.getItemAtIndex(indexOfManager);

								// select = 0;
								// for (int i = 1; i< 9; i++) {
								// 	if ((currentIndex.getScoreAtIndex(select) <= currentIndex.getScoreAtIndex(i)) && (currentIndex.getScoreAtIndex(select) <= currentIndex.getScoreAtIndex(select))){
								// 		select = i;
								// 	}
								// }
							}
						}
					}
				}

				lib.buttons[select].setIcon(O);
				lib.position[select] = "O";
				lib.count++;

				lib.order[select] = lib.count;
				// lib.printOrder();
		
				if(lib.check_forO())
				{
					System.out.println(lib.count);
					lib.score[lib.count-1] = lib.score[lib.count-1] + 0.1;
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