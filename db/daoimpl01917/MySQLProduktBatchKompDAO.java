package daoimpl01917;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchKompDAO;
import dto01917.ProduktBatchKompDTO;

public class MySQLProduktBatchKompDAO implements ProduktBatchKompDAO {

	
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId)	throws DALException {
		
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery(("SELECT * FROM produktbatchkomponent  WHERE pb_id =" + pbId + "AND rb_Id = " +  rbId));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	    	if (!rs.first()) throw new DALException("Operatoeren " + pbId + " findes ikke");
	    	return new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
	    }
	    catch (SQLException e) {throw new DALException(e); }	
		
	}

	
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId)
			throws DALException {
		
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id= " + pbId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
		
	
	}


	public List<ProduktBatchKompDTO> getProduktBatchKompList()
			throws DALException {
		
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM produktbatchkomponent");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
		
	}

	
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent)
			throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("INSERT INTO produktbatchkomponent (opr_id, netto, tara, rb_id, pb_id) VALUES" + 
				"(" + produktbatchkomponent.getOprId() + ", " + produktbatchkomponent.getNetto() + ", " + produktbatchkomponent.getTara() + ", " + produktbatchkomponent.getRbId() + ", " + produktbatchkomponent.getPbId()+ ")" 
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
		
	
		
	}

	
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent)
			throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"UPDATE produktbatchkomponent SET opr_id = '" + produktbatchkomponent.getOprId() + "', netto = '" + produktbatchkomponent.getNetto() + "', tara = '" + produktbatchkomponent.getTara() + "' " + "WHERE rb_id = " + produktbatchkomponent.getRbId() +
					"' " + "WHERE pb_id = " + produktbatchkomponent.getPbId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		} 
			
		
		
	}

}
