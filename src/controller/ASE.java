package controller;

import java.io.IOException;

import interfaces.IASE;


public class ASE implements IASE {
	
	DBController dbc = new DBController();
	WeightController wc;
	TUIController tuic = new TUIController();
	String weightChoice, user, rcName;
	int oprID, pbID;
	
	public void run(){
		connectToDatabase();
		chooseWeight();
		chooseUser();
		choosePB();
	}
	@Override
	public void chooseWeight() {
		tuic.printMessage("vælg hvilken vægt du vil bruge.  Tast 1 for vægt. Tast 2 for simulator");
		try {
			weightChoice = tuic.getString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(weightChoice.equals("1")){
			wc = new WeightController("169.254.2.3", 8000);
		}
		
		else if(weightChoice.equals("2")){
			wc = new WeightController("localhost", 8000);
		}
		else {
			tuic.printMessage("Ukendt input");
			chooseWeight();
		}
		
		
	}

	@Override
	public void chooseUser() {
		
		oprID = wc.askForUserID("Indtast operator nummer:");
		user = dbc.checkUserID(oprID);
		if(user.equals("Ukent Bruger")){
			wc.sendMessage(user);
			chooseUser();
		}else{
			wc.sendMessage(user);
		}
		
	}

	@Override
	public void choosePB() {
		pbID = wc.askForPBID("Indtast ProduktBatch nummer");
		rcName = dbc.getRCName(pbID);
		if(rcName.equals("Ukent ProduktBatch")){
			wc.sendMessage(rcName);
			choosePB();
		}
		else {
			wc.sendMessage(rcName);
		}
	}

	@Override
	public void weightProduct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectToDatabase() {
		dbc.connectToDatabase();	
	}

}
