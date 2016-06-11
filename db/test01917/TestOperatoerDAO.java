package test01917;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import connector01917.Connector;
import daoimpl01917.MySQLOperatoerDAO;
import daointerfaces01917.DALException;
import daointerfaces01917.OperatoerDAO;
import dto01917.OperatoerDTO;


public class TestOperatoerDAO {
	
	OperatoerDAO opDAO  = new MySQLOperatoerDAO();
	
	@Before
	public void connect()
	{
		try {
			new Connector();
		} catch (Exception e) {
		
		}
	}
	
	@Test
	public void TestGetOperatoer() throws DALException {
		OperatoerDTO testOperatoer = null;
		List<OperatoerDTO> operatoerList = opDAO.getOperatoerList();
		int ID = operatoerList.get(0).getOprId();
		testOperatoer = opDAO.getOperatoer(ID);

		OperatoerDTO actual = testOperatoer;
		OperatoerDTO expected = operatoerList.get(0);

		boolean theSame = true;
		
		if (actual.getOprId() 	!= expected.getOprId()) 	   		
			theSame = false;
		if (!actual.getOprNavn().equals(expected.getOprNavn())) 	
			theSame = false;
		if (!actual.getCpr().equals(expected.getCpr())) 			
			theSame = false;
		if (!actual.getIni().equals(expected.getIni())) 			
			theSame = false;
		if (!actual.getPassword().equals(expected.getPassword())) 	
			theSame = false;

		assertTrue(theSame);
	}

	@Test
	public void getOperatoerList() throws DALException {
	
		List<OperatoerDTO> list = opDAO.getOperatoerList();
		
		assertTrue(list.size()>1);
		
	}
	
	@Test
	public void createOperatoer() throws DALException{
		List<OperatoerDTO> list = opDAO.getOperatoerList();
		int currentHighestID  = list.get(list.size()-1).getOprId();
		
		int expected = opDAO.getOperatoerList().size()+1;
		opDAO.createOperatoer(new OperatoerDTO(currentHighestID+1, "Kim Larsen", "KL", "090554-1911", "Ss123sS"));
		int actual =  opDAO.getOperatoerList().size();
		
		

		assertEquals(expected, actual);
	}
	
	@Test
	public void updateOperatoer() throws DALException {
		OperatoerDTO opDTO = null;
		String expected = "Kim Larsen";
		
		try {
			opDTO = opDAO.getOperatoerList().get(0);
			opDTO.setOprNavn(expected);
			opDAO.updateOperatoer(opDTO);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DALException(e);
		}
		String actual = opDTO.getOprNavn();
		assertEquals(expected, actual);
	}

}
