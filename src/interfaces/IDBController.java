package interfaces;

public interface IDBController {

	public void connectToDatabase();
	public String getOperatorName(int input);
	public String checkUserID(int input);
	public String getRCName(int input);
	public void setPBStatus(int input, int status);
	public void updatePB(int input);
	public int getRAAIDFromRCK(int input, int raavareNummer);
	public int getRAAIDFromRAAB(int input);
	public String getRAAName(int input);
	public double getTolerance(int RCID,int RAAID);
	public double getNomNetto(int RCID, int RAAID);
	public int getNumberOfIngre(int input);
	public int getRCID(int input);
	public void writeTaraAtPBK(int input);
	
}
