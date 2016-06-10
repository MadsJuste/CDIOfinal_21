package interfaces;

public interface IWeightController {

	public void connectToWeight();
	public String writeToSocket(String message);
	public String readSocket();
	
}
