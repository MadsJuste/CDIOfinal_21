package interfaces;

public interface IWeightController {

	public void connectToWeight();
	public String writeToSocket(String output);
	public String readSocket();
	public int askForUserID(String output);
	public int askForPBID(String output);
	public void sendMessage(String output);
	
}
