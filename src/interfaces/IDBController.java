package interfaces;

public interface IDBController {

	public void connectToDatabase();
	public String getOperatorName(int input);
	public String checkUserID(int input);
	public String getRCName(int input);
}
