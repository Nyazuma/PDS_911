package shs.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import shs.common.Tool;
import shs.common.Message;
import shs.common.MessageType;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgConnection;
import shs.common.MsgIntResult;

public class GuiController {

	// JFrame of the application
	protected Gui gui;

	public GuiController() {
		// Get a dedicated connection from the pool to the object
		this.gui=new Gui(this);
	}

	public boolean connection(String username, String password) {
		MsgConnection connection = new MsgConnection(username, password);
		String output = Tool.messageToJSON(connection);
		String answer = contactServer(output);
		if(answer!= null) {
			MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer);
			return result.getStatus();
		}
		return false;	
	}

	public int nbObject() {
		Message nbObject = new Message(MessageType.NUMBEROBJECT);
		String output = Tool.messageToJSON(nbObject);
		String answer = contactServer(output);
		if(answer!= null) {
			MsgIntResult result = (MsgIntResult)Tool.jsonToMessage(answer);
			return result.getNumber();
		}
		return -1;
	}

	public boolean addObject(String detectorType) {
		MsgAddObject addObject = new MsgAddObject(detectorType);
		String output = Tool.messageToJSON(addObject);
		String answer = contactServer(output);
		if(answer!= null) {
			MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer);
			return result.getStatus();
		}
		return false;
	}



	// TODO Work in progress, is it the right place?
	private String contactServer(String request) {

		final int port = 2001;
		
		// Get the local address
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		Socket socket=null;
		DataOutputStream requestServer=null;
		DataInputStream rawAnswerServer=null;
		String answerServer = "";
		
		try {
			socket=new Socket(address, port);
			// We send the request
			requestServer= new DataOutputStream(socket.getOutputStream());
			requestServer.writeUTF(request);
			requestServer.flush();
			// We get the answer
			rawAnswerServer= new DataInputStream(socket.getInputStream());
			answerServer = rawAnswerServer.readUTF();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				socket.close();
				rawAnswerServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return answerServer;
	}

	
	/**
	 * Getter of the Gui
	 */
	public Gui getGui() {
		return gui;
	}
}
