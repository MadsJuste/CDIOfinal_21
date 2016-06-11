package test01917;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import connector01917.Connector;
import daoimpl01917.MySQLProduktBatchDAO;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchDAO;
import dto01917.ProduktBatchDTO;

public class TestProduktBatchDAO {
	
	ProduktBatchDAO pbDAO = new MySQLProduktBatchDAO();
	
	@Before
	public void connect()
	{
		try {
			new Connector();
		} catch (Exception e) {
		
		}
	}

	@Test
	public void testGetProduktBatch() throws DALException
	{
		ProduktBatchDTO pbDTO = null;
		List<ProduktBatchDTO> pbList = pbDAO.getProduktBatchList();
		int validId = pbList.get(0).getPbId();
		pbDTO = pbDAO.getProduktBatch(validId);
		
		ProduktBatchDTO actual = pbDTO;
		ProduktBatchDTO expected = pbList.get(0);
		
		boolean theSame = true;
		
		if (actual.getPbId() 	!= expected.getPbId()) 	   	
			theSame = false;
		if (actual.getReceptId() != expected.getReceptId()) 
			theSame = false;
		if (actual.getStatus() != expected.getStatus()) 	
			theSame = false;

		assertTrue(theSame);
		
	}
	
	@Test
	public void testGetProduktBatchList() throws DALException {
		
		List<ProduktBatchDTO> pbList = pbDAO.getProduktBatchList();
		
		assertTrue(pbList.size()>1);
	}
	
	@Test
	public void testCreateProduktBatch() throws DALException {
		List<ProduktBatchDTO> pbList = pbDAO.getProduktBatchList();
		int currentHighestId  = pbList.get(pbList.size()-1).getPbId();
	
		int expected = pbDAO.getProduktBatchList().size()+1;
		pbDAO.createProduktBatch(new ProduktBatchDTO(currentHighestId+1, 1, 1));
		int actual = pbDAO.getProduktBatchList().size();
	
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateProduktBatch() {
		ProduktBatchDTO pbDTO = null;
		int expected = 1;
		try {
			pbDTO = pbDAO.getProduktBatchList().get(0);
			pbDTO.setReceptId(expected);
			pbDAO.updateProduktBatch(pbDTO);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		int actual = pbDTO.getReceptId();
		assertEquals(expected, actual);
	}

}
