package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareBatchDAO;
import dto01917.RaavareBatchDTO;

public class MySQLRaavareBatchDAO implements RaavareBatchDAO {

	@SuppressWarnings("null")
	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
					connector.doQuery("SELECT * FROM raavarebatch WHERE raavare_id = " + rbId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	    	if (!rs.first()) throw new DALException("raavareBatch " + rbId + " findes ikke");
	    	return new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM raavarebatch");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add((RaavareBatchDTO) new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId)
			throws DALException {
		
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM raavare_id WHERE raavare_id = '" + raavareId + "' ");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch)
			throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"INSERT INTO raavarebatch (rb_id, raavare_id, maengde) VALUES " +
					"(" + raavarebatch.getRbId() + ", " + raavarebatch.getRaavareId() + ", " + raavarebatch.getMaengde() + ")"
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
		
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch)
			throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"UPDATE raavarebatch SET  rb_id = '" + raavarebatch.getRbId() + "', raavare_id =  '" + 
							raavarebatch.getRaavareId() +"' " + "WHERE maengde = " + raavarebatch.getMaengde());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
		
	}

}
