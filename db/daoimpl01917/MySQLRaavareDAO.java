package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import dto01917.RaavareDTO;

public class MySQLRaavareDAO implements RaavareDAO {

	public RaavareDTO getRaavare(int raavareId) throws DALException {
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM raavare WHERE raavare_id = " + raavareId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	    	if (!rs.first()) throw new DALException("Raavare med ID " + raavareId + " findes ikke");
	    	return new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> list = new ArrayList<RaavareDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM raavare");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	public void createRaavare(RaavareDTO raavare) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES " +
					"(" + raavare.getRaavareID() + ", '" + raavare.getRaavareNavn() + "', '" + raavare.getLeverandoer() + "')"
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
		
	}

	public void updateRaavare(RaavareDTO raavare) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"UPDATE raavare SET raavare_navn = '" + raavare.getRaavareNavn() + "', leverandoer =  '" + raavare.getLeverandoer() +"' " + 
					"WHERE raavare_id = " + raavare.getRaavareID()
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
	}

}
