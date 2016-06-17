package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import interfaces.IWeightController;

public class WeightController implements IWeightController{

	private String ip;
	private int port;
	private Socket socket;
	private BufferedReader inputStream;
	private DataOutputStream outputStream;

	
	public WeightController(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public void connectToWeight() {
		
		try {
			socket = new Socket(ip, port);

			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public String writeToSocket(String output) {

		try {
			outputStream.writeBytes(output +"\r\n");
			outputStream.flush();
			return inputStream.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return output;
	}

	@Override
	public String readSocket() {
		String input = "";
		
		try {
			input = inputStream.readLine();
		} catch (IOException e) {
			System.out.println("READ FAILED");
			e.printStackTrace();
		}
		
		return input;
	}
	
	public String rm20(String output){
		
		writeToSocket("RM20 8 \"" + output + "\" \" \" \" \"\r\n");

		String input = "";
		
			
			try {
				input = inputStream.readLine();
				System.out.println(input);
				input = inputStream.readLine();
				System.out.println(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		

		if (input.startsWith("RM20 A ")) {
			input = input.substring(8, input.length());
			input = input.replaceAll("[\"]", "");

		}
		if(input.startsWith("ES")){
			input = rm20(output);
		}
		return input;
	}
	
	
	@Override
	public int askForUserID(String output) {
		
		String input = rm20(output);
		
		int intInput = Integer.parseInt(input);
	
		return intInput;
	}

	@Override
	public String sendMessage(String output) {
		
		String input = rm20(output);
		
		return input;
		
	}

	@Override
	public int askForPBID(String output) {
		
		String input = rm20(output);
	
		int intInput = Integer.parseInt(input);
		
		return intInput;
	}

	@Override
	public String checkIfEmpty(String output) {
		String input = rm20(output);
		
		return input;
	}

	@Override
	public String askUserToTaraWeight(String output) {
	
		String input = rm20(output);
		System.out.println(input);
		
		return input;
	}

	@Override
	public int getRBID(String output) {
		
		
		String input = rm20(output);
		
		int intInput = -1;
		intInput = Integer.parseInt(input);
		return intInput;
	}

	@Override
	public String completeWeighing(String output) {
		
		String input = rm20(output);
		
		return input;
	}

	@Override
	public String taraWeight() {
		writeToSocket("T\r\n");
		String input = readSocket();
		input = input.replaceAll("[STkg ]", "");
		if(input.equals("E")){
			input = taraWeight();
		}
		return input;
	}

	@Override
	public String getWeight() {
		String input = writeToSocket("S\r\n");
		//String input = readSocket();
		if(input.startsWith("ES")){
			input = readSocket();
		}
		input = input.replaceAll("[STkg ]", "");
		return input;
	}

	@Override
	public String checkName(String output) {
		String input = rm20(output);
		if(input.equals("RM20 C")){
			input = "wrong name";
		}
		
		return input;
	}


}
