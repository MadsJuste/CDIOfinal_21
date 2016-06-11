package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.*;
import daointerfaces01917.DALException;
import daointerfaces01917.ReceptDAO;
import dto01917.ReceptDTO;

public class MySQLReceptDAO implements ReceptDAO {
	
	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM recept WHERE recept_id = " + receptId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	    	if (!rs.first()) throw new DALException("Recepten " + receptId + " findes ikke");
	    	return new ReceptDTO (rs.getInt("recept_id"), rs.getString("recept_navn"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		
		List<ReceptDTO> list = new ArrayList<ReceptDTO>();
			ResultSet rs = null;
			Connector connector = new Connector();
			try {
				rs = connector.doQuery("SELECT * FROM recept");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		
		return list;
	}

	@Override
	public void createRecept(ReceptDTO recept) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"INSERT INTO recept(recept_id, recept_navn) VALUES " +
					"(" + recept.getReceptId() + ", '" + recept.getReceptId() + "')"
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
	}

	@Override
	public void updateRecept(ReceptDTO recept) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"UPDATE recept SET recept_navn = '" + recept.getReceptNavn() + "' WHERE recept_id = " +
					recept.getReceptId()
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
	}

}
