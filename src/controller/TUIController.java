package controller;

import java.io.IOException;

import boundary.TUI;
import interfaces.ITUIController;

public class TUIController implements ITUIController {
	TUI tui = new TUI();
	
	@Override
	public void printMessage(String input) {
		tui.printMessage(input);
		
	}

	@Override
	public String getString() throws IOException {
		String temp = "";
		
		temp = tui.getString();
		
		return temp;
	}

}
