package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ReceptKompDAO;
import dto01917.rkDTO;

public class MySQLReceptKompDAO implements ReceptKompDAO {

	@Override
	public rkDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id  =" + receptId + "AND raavare_id = " + raavareId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (!rs.first()) throw new DALException("Receptkomponenten " + receptId + " findes ikke");
			return new rkDTO (rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
		}
		catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<rkDTO> getReceptKompList(int receptId) throws DALException {

		List<rkDTO> list = new ArrayList<rkDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM receptkomponent");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new rkDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<rkDTO> getReceptKompList() throws DALException {

		List<rkDTO> list = new ArrayList<rkDTO>();
		ResultSet rs = null;
		Connector connector = new Connector();
		try {
			rs = connector.doQuery("SELECT * FROM receptkomponent");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			while (rs.next()) 
			{
				list.add(new rkDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createReceptKomp(rkDTO receptkomponent) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate(
					"INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES" +
							"(" + receptkomponent.getReceptId() + ", '" + receptkomponent.getRaavareId() + "', '" + receptkomponent.getNomNetto() + "', '" + 
							receptkomponent.getTolerance() + ")"
					);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}

	}

	@Override
	public void updateReceptKomp(rkDTO receptkomponent) throws DALException {
		Connector connector = new Connector();	
		try {
				connector.doUpdate(
						"UPDATE receptkomponent SET recept_id = '" + receptkomponent.getReceptId() + "', raavare_id = '" + receptkomponent.getRaavareId() + 
						"', nom_netto = '" + receptkomponent.getNomNetto() + "', tolerance = '" + receptkomponent.getTolerance() + "' WHERE recept_id = '" +
						receptkomponent.getReceptId() + "' AND raavare_id = '" + receptkomponent.getRaavareId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DALException(e);
			} 

	}

}
