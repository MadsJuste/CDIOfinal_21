package boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import interfaces.ITUI;

public class TUI implements ITUI {
	private BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	
	
	@Override
	public String getString() throws IOException {
		return userInput.readLine();
	}

	@Override
	public int stringToInt() {
		int inputInt = 0;
		try {
			inputInt = Integer.parseInt(userInput.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputInt;
	}

	@Override
	public void printMessage(String message) {
		System.out.println(message);
		
	}

}
