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
			return "Ukent Bruger";
		}	
		return user;
	}

	@Override
	public String getRCName(int input) {
		System.out.println("input " + input);
		String rcName = "";
		int rcID;
		try {
			if(input != PBDAO.getProduktBatch(input).getPbId()){
				throw new DALException("PBDAO");
			} 
			else {
				rcID = PBDAO.getProduktBatchList().get(input).getReceptId();
				System.out.println(rcID + " = rcID");
				rcName = RCDAO.getReceptList().get(rcID-1).getReceptNavn();
				System.out.println(rcName);
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Ukent ProduktBatch";
		}
		return rcName;
	}

	@Override
	public void setPBStatus(int input, int status) {

		try {
			PBDAO.getProduktBatchList().get(input).setStatus(status);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updatePB(int input) {
		try {
			
			PBDAO.updateProduktBatch(PBDAO.getProduktBatchList().get(input));
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getRAAIDFromRCK(int input, int raavareNummer) {
		
		int raaid = -1;
		try {
			raaid = RCKDAO.getReceptKompList(input-1).get(raavareNummer).getRaavareId();
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
			raaid =RAABDAO.getRaavareBatchList().get(input-1).getRaavareId();

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
			name = RAADAO.getRaavareList().get(input-1).getRaavareNavn();
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
			tole = RCKDAO.getReceptKompList(RCID).get(RAAID).getTolerance();
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
			nomNet = RCKDAO.getReceptKompList(RCID).get(RAAID).getNomNetto();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomNet;
	}

	@Override
	public int getNumberOfIngre(int input) {
		int numberOfIngre = -1;
		try {
			numberOfIngre = RCKDAO.getReceptKompList(input).size();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfIngre;
	}

	@Override
	public int getRCID(int input) {
		int rcID =-1;
		try {
			rcID = PBDAO.getProduktBatchList().get(input).getReceptId();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rcID;
	}

	@Override
	public void writeTaraAtPBK(int input, double tara) {
		try {
			PBKDAO.getProduktBatchKompList().get(input).setTara(tara);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void writeWeightToPBK(int PBK, double weight) {
		try {
			PBKDAO.getProduktBatchKompList().get(PBK).setNetto(weight);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
