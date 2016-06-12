package interfaces;

public interface IWeightController {

	public void connectToWeight();
	public String writeToSocket(String output);
	public String readSocket();
	public int askForUserID(String output);
	public int askForPBID(String output);
	public void sendMessage(String output);
	public String checkIfEmpty(String output);
	public String askUserToTaraWeight(String output);
	public int getRBID(String output);
	public String completeWeighing(String output);
	public String taraWeight();
	public String getWeight();
	
}
