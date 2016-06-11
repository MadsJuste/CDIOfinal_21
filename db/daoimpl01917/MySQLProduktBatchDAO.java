package daoimpl01917;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchDAO;
import dto01917.ProduktBatchDTO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO {

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		Connector connector = new Connector();
		ResultSet rs = null;
		try {
			rs = connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new DALException(e);
		}
	    try {
	    	if (!rs.first()) throw new DALException("Produktbatch " + pbId + " findes ikke");
	    	return new ProduktBatchDTO (rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("status"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	
	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"INSERT INTO produktbatch(pb_id, recept_id, status) VALUES " +
					"(" + produktbatch.getPbId() + ", '" + produktbatch.getReceptId() + "', '" + produktbatch.getStatus() + "')"
				);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new DALException(e);
		}
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"UPDATE produktbatch SET status = '" + produktbatch.getStatus() + "', recept_id =  '" + produktbatch.getReceptId() + "' WHERE pb_id = " + produktbatch.getPbId()
			);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new DALException(e);
		}
	}
	
	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM produktbatch");
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchDTO(rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("status")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

}
