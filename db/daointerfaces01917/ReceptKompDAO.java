package daointerfaces01917;

import java.util.List;

import dto01917.rkDTO;

public interface ReceptKompDAO {
	rkDTO getReceptKomp(int receptId, int raavareId) throws DALException;
	List<rkDTO> getReceptKompList(int receptId) throws DALException;
	List<rkDTO> getReceptKompList() throws DALException;
		void createReceptKomp(rkDTO receptkomponent) throws DALException;
	void updateReceptKomp(rkDTO receptkomponent) throws DALException;
}
