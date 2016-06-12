package controller;

import java.io.IOException;

import interfaces.IASE;


public class ASE implements IASE {
	
	DBController dbc = new DBController();
	WeightController wc;
	TUIController tuic = new TUIController();
	String weightChoice= "", user="", rcName="", currentTara="", OK="", raaName="";
	int oprID = 0, pbID = 0,raaID = 0, rcID = 0, raaBID = 0, numberOfIngre = 0, ingreNumber = 0;
	double tolerance = 0, nomNetto = 0, currentWeight  = 0, posTole = 0, negTole = 0,TARA = 0, finalWeight = 0;
	
	public void run(){
		connectToDatabase();
		chooseWeight();
		runProduction(0);
	}
	
	public void runProduction(int ingreValue){
		ingreNumber = ingreValue;
		chooseUser();
		choosePB();
		for(ingreNumber = 0; ingreNumber < numberOfIngre; ingreNumber++){
			weightProduct(ingreNumber);
		}
		endProduction();
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
			wc.connectToWeight();
		}
		
		else if(weightChoice.equals("2")){
			wc = new WeightController("localhost", 8000);
			wc.connectToWeight();
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
			rcID = dbc.getRCID(pbID);
			numberOfIngre = dbc.getNumberOfIngre(rcID);
		}
	}

	@Override
	public void weightProduct(int ingreNumber) {
		
		OK = wc.askUserToTaraWeight("sæt beholder på vægten og tryk OK");
		currentTara = wc.taraWeight();
		TARA = Double.parseDouble(currentTara);
		//skal added en måde at updater ProduktBatchKomponenten... tara skal addes
		
		raaID = dbc.getRAAIDFromRCK(rcID, ingreNumber);
		raaName = dbc.getRAAName(raaID);
		raaBID = wc.getRBID("Indtast RaavareBatch nummer på raavare "+ raaName);
		//ikke sikker på hvad jeg skal gøre med rbID.
		
		weightCheck();
		if(ingreNumber == 0){
		dbc.setPBStatus(pbID, 1);
		dbc.updatePB(pbID);
		}
	}
	
	@Override
	public void weightCheck() {
		OK = wc.completeWeighing("Afvej den ønskede mængde og tryk OK");
		tolerance = dbc.getTolerance(rcID, raaID);
		nomNetto = dbc.getNomNetto(rcID, raaID);
		currentWeight = Double.parseDouble(wc.getWeight());
		tuic.printMessage(currentWeight + " " + nomNetto + " " + tolerance);
		posTole = (currentWeight/100)*tolerance+nomNetto;
		negTole = nomNetto-(currentWeight/100)*tolerance;
		if(currentWeight > posTole){
			wc.sendMessage("for meget af raavare");
			weightCheck();
		}else if(currentWeight < negTole){
			wc.sendMessage("for lidt af raavare");
			weightCheck();
		}else if(posTole > currentWeight && currentWeight > negTole){
				wc.checkIfEmpty("fjern beholder + produkt");
				finalWeight = Double.parseDouble(wc.getWeight());
				if(finalWeight != -TARA){
					wc.sendMessage("Fejl i brutto check, start forfra");
					runProduction(ingreNumber);
				}
			}
		
	}

	@Override
	public void connectToDatabase() {
		dbc.connectToDatabase();	
	}
	@Override
	public void endProduction() {
		dbc.setPBStatus(pbID, 2);
		dbc.updatePB(pbID);
	}
	

}
