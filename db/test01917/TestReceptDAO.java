package test01917;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import connector01917.Connector;
import daoimpl01917.MySQLReceptDAO;
import daointerfaces01917.DALException;
import daointerfaces01917.ReceptDAO;
import dto01917.ReceptDTO;

public class TestReceptDAO {

	ReceptDAO re = new MySQLReceptDAO();
	
	@Before
	public void connect()
	{
		try {
			new Connector();
		} catch (Exception e) { 
			System.out.println("Catch");
		}
	}

	@Test
	public void testGetRecept() throws DALException{

		ReceptDTO testRec = null;
		List<ReceptDTO> recList = re.getReceptList();
		int ID = recList.get(0).getReceptId();
		testRec = re.getRecept(ID);
		
		ReceptDTO actual = testRec;
		ReceptDTO expected = recList.get(0);

		boolean areSame = true;

		if(actual.getReceptId() != expected.getReceptId()) 				areSame = false;
		if(!actual.getReceptNavn().equals(expected.getReceptNavn())) 	areSame = false;

		assertTrue(areSame);
	}
	
	@Test
	public void testGetReceptList() throws DALException{
		
		List<ReceptDTO> recList = re.getReceptList();
		
		assertTrue(recList.size()>1);
	}

	@Test
	public void testCreateRecept() throws DALException{
		
		List<ReceptDTO> recList = re.getReceptList();
		int highID = recList.get(recList.size()-1).getReceptId();
		
		int expected = re.getReceptList().size()+1;
		re.createRecept(new ReceptDTO(highID+1, "Spiral"));
		int actual = re.getReceptList().size();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateRecept()throws DALException{
		
		ReceptDTO rec = null;
		String expected = "ostManden";
		
		rec = re.getReceptList().get(0);
		rec.setReceptNavn(expected);
		re.updateRecept(rec);
		
		String actual = rec.getReceptNavn();
		
		assertEquals(expected, actual);
	}

}
