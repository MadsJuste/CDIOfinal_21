package boundary;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import interfaces.ITUI;

public class TUI implements ITUI {
	private BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public String getString(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int stringToInt(String input) {
		//int inputInt = Integer.parseInt(input.readLine());
		return 0;
	}

	@Override
	public void printMessage(String message) {
		System.out.println(message);
		
	}

}
