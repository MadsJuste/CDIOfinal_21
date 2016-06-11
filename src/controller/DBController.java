package controller;

import connector01917.Connector;
import daoimpl01917.*;
import daointerfaces01917.DALException;
import interfaces.IDBController;

public class DBController implements IDBController {
	Connector connect;
	public MySQLOperatoerDAO ODAO;
	public MySQLProduktBatchDAO PBDAO;
	public MySQLProduktBatchKompDAO PBKDAO;
	public MySQLReceptDAO RCDAO;
	public MySQLReceptKompDAO RCKDAO;
	public MySQLRaavareBatchDAO RAABDAO;
	public MySQLRaavareDAO RAADAO;
	
	@Override
	public void connectToDatabase() {
		connect = new Connector();
		
		ODAO = new  MySQLOperatoerDAO();
		PBDAO = new MySQLProduktBatchDAO();
		PBKDAO = new  MySQLProduktBatchKompDAO();
		RCDAO = new MySQLReceptDAO();
		RCKDAO = new  MySQLReceptKompDAO();
		RAABDAO = new MySQLRaavareBatchDAO();
		RAADAO = new MySQLRaavareDAO();
		
	}

	@Override
	public String getOperatorName(int input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkUserID(int input) {
		String user = "";
		try {
			if(input != ODAO.getOperatoer(input).getOprId()){
			return "Ukent Bruger";
			}
			else {
				user = ODAO.getOperatoer(input).getOprNavn();
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return user;
	}

	@Override
	public String getRCName(int input) {
		String rcName = "";
		int rcID;
		try {
			if(input != PBDAO.getProduktBatch(input).getPbId()){
			return "Ukent ProduktBatch";
			} 
			else {
				rcID = PBDAO.getProduktBatch(input).getReceptId();
				rcName = RCDAO.getRecept(rcID).getReceptNavn();
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcName;
	}
	
	
	
}
