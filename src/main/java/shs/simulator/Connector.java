package shs.simulator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import shs.common.Tool;

public class Connector {
	
	public static String contactServer(String request){

		final int port = 2001;

		// Get the local address
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost(); 
			//address = InetAddress.getByName("192.168.20.20");
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
			return null;
		}
		finally{
			try {
				socket.close();
				rawAnswerServer.close();
			} catch (Exception e) {
				Tool.logger.error("The server is not available");
				System.exit(-1);
			}
		}
		return answerServer;
	}

}
