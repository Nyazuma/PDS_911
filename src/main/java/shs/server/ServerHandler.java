package shs.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import shs.common.Tool;

public class ServerHandler implements Runnable{

	protected Socket socket;
	protected Controller controller;

	public ServerHandler(Socket clientSocket, Controller controller) {
		this.socket = clientSocket;
		this.controller = controller;
	}

	public void run() {

		// Get the JSON format from the Byte request
		String requestJSON = requestDecrypted();
		System.out.println("Just received the request : " + requestJSON);

		// Call to the controller
		String answer = controller.treatmentRequest(requestJSON);
		
		// Send the answer
		System.out.println("I'm sending the answer : " + answer);
		sendAnswer(answer);

		// The end, we close the socket
		try {
			socket.close();
			controller.closeController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String requestDecrypted() {
		
		DataInputStream rawInput=null;
		String decodedInput = "";

		// Instantiation
		try {
			rawInput = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			Tool.logger.info("ServerService : socket problem");
		}

		// Reading phase
		try {
			decodedInput += rawInput.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return decodedInput;

	}

	private void sendAnswer(String answer) {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(answer);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

