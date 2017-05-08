package implementation;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.security.SecureRandom;
import java.io.*;

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
	public void loadData()
	{
		File varTmpDir = new File("data.txt");
        boolean exists = varTmpDir.exists();
        File filed = varTmpDir.getAbsoluteFile();
        try {
        	if (!exists) {
        	        varTmpDir.createNewFile();
        	 }

            fr = new FileReader(filed);
            br = new BufferedReader(fr);
            String position;
            String tmp;
            String order;
            String score;
            br = new BufferedReader(new FileReader(filed));
            position = br.readLine();
          	position = position.substring(16,34);
          	for
          	System.out.println(position);
            /*sCur = br.readLine();
            sCur = br.readLine();


			while ((sCur = br.readLine()) != null) {
			}*/

			} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
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
		System.out.println("YOU WIN");
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
			// System.out.println("lib.count: " + lib.count);
			managerList.getItemAtIndex(index).updateScore(location_button, true);
			managerList.getItemAtIndex(index).updateScore(select, false);
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
			// System.out.println("lib.count: " + lib.count);
			managerList.getItemAtIndex(index).updateScore(select, true);
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