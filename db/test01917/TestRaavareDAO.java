package test01917;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import connector01917.Connector;
import daoimpl01917.MySQLRaavareDAO;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import dto01917.RaavareDTO;

public class TestRaavareDAO {

	RaavareDAO rDAO = new MySQLRaavareDAO();
	
	@Before
	public void connect()
	{
		try {
			new Connector();
		} catch (Exception e) {
		
		}
	}

	@Test
	public void testGetRaavare() throws DALException {
	

		RaavareDTO testVare = null;
		List<RaavareDTO> raavareList = rDAO.getRaavareList();
		int ID = raavareList.get(0).getRaavareID();
		testVare = rDAO.getRaavare(ID);

		RaavareDTO actual = testVare;
		RaavareDTO expected = raavareList.get(0);

		boolean sameElements = true;
		
		if (actual.getRaavareID() 	!= expected.getRaavareID()) 	   
			sameElements = false;
		if (!actual.getRaavareNavn().equals(expected.getRaavareNavn())) 
			sameElements = false;
		if (!actual.getLeverandoer().equals(expected.getLeverandoer())) 
			sameElements = false;

		assertTrue(sameElements);

	}

	@Test
	public void testGetRaavareList() throws DALException{

		List<RaavareDTO> list = rDAO.getRaavareList();
		
		assertTrue(list.size()>1);
	} 
	
	@Test
	public void TestCreateRaavare() throws DALException{
	
		List<RaavareDTO> list = rDAO.getRaavareList();
		int currentHighestID  = list.get(list.size()-1).getRaavareID();
		
		int expected = rDAO.getRaavareList().size()+1;
		rDAO.createRaavare(new RaavareDTO(currentHighestID+1, "Ananas", "omraade"));
		int actual =  rDAO.getRaavareList().size();
		
		

		assertEquals(expected, actual);

	}
	
	@Test
	public void testUpdateRaaVare(){
		RaavareDTO dto = null;
		String expected = "kebabhuset";
		try {
			dto = rDAO.getRaavareList().get(0);
			dto.setLeverandoer(expected);
			rDAO.updateRaavare(dto);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String actual = dto.getLeverandoer();
		assertEquals(expected, actual);
		
	}
}
