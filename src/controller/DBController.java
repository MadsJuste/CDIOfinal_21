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

	@Override
	public void setPBStatus(int input, int status) {

		try {
			PBDAO.getProduktBatch(input).setStatus(status);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updatePB(int input) {
		try {
			PBDAO.updateProduktBatch(PBDAO.getProduktBatch(input));
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getRAAIDFromRCK(int input, int raavareNummer) {
		
		int raaid = -1;
		try {
			raaid = RCKDAO.getReceptKompList(input).get(raavareNummer).getRaavareId();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return raaid;
	}

	@Override
	public int getRAAIDFromRAAB(int input) {
		int raaid = -1;
		try{
			raaid = RAABDAO.getRaavareBatch(input).getRaavareId();
		}catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return raaid;
	}

	@Override
	public String getRAAName(int input) {
		String name = "";
		try {
			name = RAADAO.getRaavare(input).getRaavareNavn();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	@Override
	public double getTolerance(int RCID, int RAAID) {
		
		double tole = -1;
		try {
			tole = RCKDAO.getReceptKomp(RCID, RAAID).getTolerance();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tole;
	}

	@Override
	public double getNomNetto(int RCID, int RAAID) {
		
		double nomNet = -1;
		try {
			nomNet = RCKDAO.getReceptKomp(RCID, RAAID).getNomNetto();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomNet;
	}
	
	
	
}
