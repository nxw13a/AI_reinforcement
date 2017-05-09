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
	
	public XOButton(int location, ManagerList x){
		X=new ImageIcon(this.getClass().getResource("X.png"));
		O=new ImageIcon(this.getClass().getResource("O.png"));
		location_button = location;
		lib = new Reinforcement();
		managerList = x;
		this.addActionListener(this);
	}
	
	private void resetBoard() {
		for(int i=0;i<9;i++){
			lib.buttons[i].setIcon(null);
		}
		indexOfManager = 0;
		lib.reinit();
		managerList.printToFile();
		// managerList.print();

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
		// If the state does not exist
		int index = managerList.doesExist(lib.position, lib.order, lib.count);
		System.out.println("YOU WIN " + managerList.getSize());
		
		if (index == -1) {
			if(managerList.repeat(lib.order,lib.position))
			{
				Manager newManager = new Manager();
				// Add position to list
				newManager.setPosition(lib.position);
				newManager.setScore(lib.score);
				newManager.setOrderWith(lib.order);
				// Push to ManagerList
				managerList.addState(newManager);
			for(int x = 0; x < 9; x++)
				System.out.print(lib.position[x] + " ");
			System.out.println();
			for(int x = 0; x < 9; x++)
				System.out.print(lib.order[x] + " ");
			System.out.println();
			}
		} else {
			
			// System.out.println("lib.count: " + lib.count);
			managerList.getItemAtIndex(index).updateScore(location_button, true);
			managerList.getItemAtIndex(index).updateScore(select, false);
			/*
			for(int x = 0; x < 9; x++)
				System.out.print(managerList.getItemAtIndex(index).score[x] + " ");
			System.out.println();
			for(int x = 0; x < 9; x++)
				System.out.print(managerList.getItemAtIndex(index).position[x] + " ");
			System.out.println();
			*/
		}

		resetBoard();
		lib.position = new String[9];
		lib.order = new int[9];
		lib.count = 0;
		lib.pick = 1;
	}

	private void resetLost(){
		// If the state does not exist

		int index = managerList.doesExist(lib.position, lib.order, lib.count);
		System.out.println("YOU LOST" + managerList.getSize());
		if (index == -1) {
			if(managerList.repeat(lib.order,lib.position))
			{
				Manager newManager = new Manager();
				// Add position to list
				newManager.setPosition(lib.position);
				newManager.setScore(lib.score);
				newManager.setOrderWith(lib.order);
				// Push to ManagerList
				managerList.addState(newManager);
			}
		} else {
			// System.out.println("lib.count: " + lib.count);
			managerList.getItemAtIndex(index).updateScore(select, true);
			/*
			for(int x = 0; x < 9; x++)
				System.out.print(managerList.getItemAtIndex(index).score[x] + " ");
			System.out.println();
			for(int x = 0; x < 9; x++)
				System.out.print(managerList.getItemAtIndex(index).position[x] + " ");
			System.out.println();
			*/
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
							select = managerList.getHighestScore(lib.position, lib.order, lib.count);
							
							if (select == -1) {
								generateRandomMove();
							} else {
								select = managerList.getItemAtIndex(select).getHighestScore();
								if (lib.position[select] != null){
									generateRandomMove();
								}
							}
						}
					}
				}

				lib.buttons[select].setIcon(O);
				lib.position[select] = "O";
				lib.count++;

				lib.order[select] = lib.count;
		
				if(lib.check_forO())
				{
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
