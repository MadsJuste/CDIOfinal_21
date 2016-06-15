package controller;

import java.io.IOException;

import interfaces.IASE;
import interfaces.IDBController;
import interfaces.ITUIController;
import interfaces.IWeightController;

public class ASE implements IASE {
	
	private IDBController dbc = new DBController();
	private IWeightController wc;
	private ITUIController tuic = new TUIController();
	
	private String weightChoice= "", user="", rcName="", currentTara="", OK="", raaName="";
	private int oprID = 0, pbID = 0,raaID = 0, rcID = 0, raaBID = 0, numberOfIngre = 0, ingreNumber = 0;
	private double tolerance = 0, nomNetto = 0, currentWeight  = 0, posTole = 0, negTole = 0,TARA = 0, finalWeight = 0;
	
	public void run(){
		connectToDatabase();
		chooseWeight();
		runProduction();
	}
	
	public void runProduction(){
		chooseUser();
		choosePB();
		for(ingreNumber = 0; ingreNumber < numberOfIngre; ingreNumber++){
			System.out.println(ingreNumber + "Ingre Number");
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
		System.out.println(user);
		if(user.equals("Ukent Bruger")){
			wc.sendMessage(user);
			chooseUser();
		}else{
			String check = wc.checkName(user);
			if(check.equals("wrong name")){
				chooseUser();
			}
		
		}
		
	}

	@Override
	public void choosePB() {
		pbID = wc.askForPBID("Indtast pb nummer");
		rcName = dbc.getRCName(pbID);
		if(rcName.equals("Ukent ProduktBatch")){
			choosePB();
		}
		else {
			wc.sendMessage(rcName);
			rcID = dbc.getRCID(pbID);
			numberOfIngre = dbc.getNumberOfIngre(rcID);
		}
	}

	@Override
	public void weightProduct(int ingreNumber) {
		wc.checkIfEmpty("tom vaegten");
		String check = wc.getWeight();
		System.out.println(check);
		if(!check.equals("0.000")){
			weightProduct(ingreNumber);
		}
		if(ingreNumber == 0){
			dbc.setPBStatus(pbID, 1);
			dbc.updatePB(pbID);
		}
	
		wc.taraWeight();
		
		
		OK = wc.askUserToTaraWeight("saet beholder, tryk OK");
		//gemmer vægt i PBK
		currentTara = wc.getWeight();
		TARA = Double.parseDouble(currentTara);
		dbc.writeTaraAtPBK(pbID, TARA);
		
		//TARA vægt
		currentTara = wc.taraWeight();
		TARA = Double.parseDouble(currentTara);
		System.out.println(TARA + " tara");
				
		raaID = dbc.getRAAIDFromRCK(rcID, ingreNumber);
		System.out.println("raaID = " +raaID);
		raaName = dbc.getRAAName(raaID);
		System.out.println("raaName = " + raaName);
		raaBID = wc.getRBID("RaaB nr paa "+ raaName);
		//ikke sikker på hvad jeg skal gøre med rbID.
		
		weightCheck();
		
		
	}
	
	@Override
	public void weightCheck() {
		OK = wc.completeWeighing("Afvej " + raaName + " og tryk OK");
		tolerance = dbc.getTolerance(rcID, raaID);
		nomNetto = dbc.getNomNetto(rcID, raaID);
		currentWeight = Double.parseDouble(wc.getWeight());
		tuic.printMessage(currentWeight + " " + nomNetto + " " + tolerance);
		posTole = nomNetto*tolerance+nomNetto;
		negTole = nomNetto-nomNetto*tolerance;
		System.out.println("posTole " + posTole);
		System.out.println("negTole " + negTole);
		if(currentWeight > posTole){
			wc.sendMessage("for meget af raavare");
			weightCheck();
		}else if(currentWeight < negTole){
			wc.sendMessage("for lidt af raavare");
			weightCheck();
		}else if(posTole >= currentWeight && currentWeight >= negTole){
				wc.checkIfEmpty("fjern beholder + produkt");
				finalWeight = Double.parseDouble(wc.getWeight());
				dbc.writeWeightToPBK(pbID, currentWeight);
				wc.taraWeight();

				if(finalWeight != -TARA){
					wc.sendMessage("Fejl i brutto check, start forfra");
					weightProduct(ingreNumber);
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
		runProduction();
	}
	

}
