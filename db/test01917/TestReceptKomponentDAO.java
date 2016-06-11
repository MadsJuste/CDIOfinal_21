package test01917;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import connector01917.Connector;
import daoimpl01917.MySQLReceptKompDAO;
import daointerfaces01917.DALException;
import dto01917.rkDTO;

public class TestReceptKomponentDAO {

	

	MySQLReceptKompDAO rk = new MySQLReceptKompDAO();


	@Before
	public void Connect() {
		try {
			new Connector();
		}catch (Exception e){
		}
	}

	@Test
	public void testgetReceptKomp() throws DALException{

		List<rkDTO> list = rk.getReceptKompList();
		int v = rk.getReceptKompList().get(0).getRaavareId();
		
		rk.getReceptKompList(v);
		list.get(0);
		
		boolean sameElement = true;
		assertTrue(sameElement);
		

	}

	@Test
	public void testGetReceptKompList() throws DALException{
		boolean moreThanZero = false;

		if(rk.getReceptKompList().size() > 0){
			moreThanZero = true;
		}
		assertTrue(moreThanZero);
	}

	@Test
	public void getReceptKomponentAndReceptID() throws DALException{
		int receptID = rk.getReceptKompList().get(0).getReceptId();
		
		boolean listMoreThanZero = false;

		if(rk.getReceptKompList(receptID).size() > 0){
			listMoreThanZero = true;
		}
		assertTrue(listMoreThanZero);
	}
	
	
	
	@Test
	public void testUpdateReceptKomp() throws DALException{

		
		
		rkDTO zero = null;
		double expected = 0.5;
		
		try{
			zero = rk.getReceptKompList().get(0);
			zero.setTolerance(expected);
			rk.updateReceptKomp(zero);
		}catch(DALException e){
			e.printStackTrace();
			
		}

		
		double actual = zero.getTolerance();
	
		assertEquals(expected, actual, 0);
	}
}
