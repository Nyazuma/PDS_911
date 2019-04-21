package shs.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import shs.common.Message;
import shs.common.MsgDeleteObject;
import shs.common.MessageType;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgChangeAlert;
import shs.common.MsgConnection;
import shs.common.MsgIntResult;
import shs.common.MsgListResult;
import shs.common.MsgUpdateObject;
import shs.common.Tool;

public class GuiController {

	// JFrame of the application
	protected Gui gui;

	public GuiController() {
		this.gui=new Gui(this);
	}

	public boolean ping() {
		Message ping = new Message(MessageType.PING);
		String output = Tool.messageToJSON(ping);
		try {
			String answer = contactServer(output);
		}
		catch (Throwable e) {
			return false;
		}

		return true;
	}

	public boolean connection(String username, String password) {
		MsgConnection connection = new MsgConnection(username, password);
		String output = Tool.messageToJSON(connection);
		String answer;
		try {
			answer = contactServer(output);
			if(answer!= null) {
				MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer);
				return result.getStatus();
			}
			return false;	
		}
		catch (ConnectException e) {
			return false;
		}
	}

	public int nbObject() {
		Message nbObject = new Message(MessageType.NUMBEROBJECT);
		String output = Tool.messageToJSON(nbObject);
		String answer;
		try {
			answer = contactServer(output);
			if(answer!= null) {
				MsgIntResult result = (MsgIntResult)Tool.jsonToMessage(answer);
				return result.getNumber();
			}
		}
		catch (ConnectException e) {
			return -1;
		}
		return -1;
	}

	public boolean update(List<String> rowUpdate) {
		MsgUpdateObject update =  new MsgUpdateObject(rowUpdate);  
		String output = Tool.messageToJSON(update);
		String answer; 
		try {
			answer = contactServer(output); 
			if(answer != null) {
				MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer); 
				return result.getStatus(); 
			}
		}catch (ConnectException e) {
			return false; 
		}
		return false; 
	}
	public String addresseMac() {
		String mac = "";
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for(int y=0;y<5;y++) {
			for(int x=0;x<2;x++)
			{
				int i = (int)Math.floor(Math.random() * 36); 
				mac += chars.charAt(i);
			}
			if(y!=4)
				mac+=":";
		}
		System.out.println(mac);
		return mac;



	}
	public List<List<String>> addObject(String detectorType) {
		MsgAddObject addObject = new MsgAddObject(detectorType, addresseMac());
		return readGeneric(addObject);
	}

	public List<List<String>> delete(String idObject) {
		MsgDeleteObject delete = new MsgDeleteObject(idObject);  
		return readGeneric(delete);
	}

	public String[] readResidences() {
		List<List<String>> listResidences = readGeneric(new Message(MessageType.LISTRESIDENCES));
		String[] tabResidences = new String[listResidences.size()];
		int i =0;
		for(List<String> line : listResidences) {
			tabResidences[i]=line.get(1);
			i++;
		}
		return tabResidences;
	}
	
	public List<List<String>> EmplacementFull(){
		return readGeneric(new Message(MessageType.LISTEMPLACEMENTS)); 
	}


	// Pour etage et rdc emplacements 
	public String[] readEmplacements() {
		List<List<String>> listEmplacements = readGeneric(new Message(MessageType.LISTEMPLACEMENTS));
		String[] tabEmplacements = new String[listEmplacements.size()];
		int i =0;
		for(List<String> line : listEmplacements) {
			tabEmplacements[i]=line.get(1);
			i++;
		}
		return tabEmplacements;
	}




	public String[] readEtages() {
		List<List<String>> listEtages = readGeneric(new Message(MessageType.LISTETAGES));
		String[] tabEtages = new String[listEtages.size()];
		int i =0;
		for(List<String> line : listEtages) {
			tabEtages[i]=line.get(1);
			i++;
		}
		return tabEtages;
	}
	
	public String[] readEtageImage() {
		List<List<String>> listImage = readGeneric(new Message(MessageType.LISTETAGES)); 
		String[] tabImage = new String[listImage.size()];
		int i =0; 
		for(List<String>line : listImage) {
			tabImage[i]=line.get(1); 
			i++;
		}
		return tabImage;  
	}

	//	public String[] readZones() {
	//		List<List<String>> listZones = readGeneric(new Message(MessageType.LISTZONES));
	//		String[] tabZones = new String[listZones.size()];
	//		int i =0;
	//		for(List<String> line : listZones) {
	//			tabZones[i]=line.get(0);
	//			i++;
	//		}
	//		return tabZones;
	//	}
	//
	//	public String[] readPieces() {
	//		List<List<String>> listPieces = readGeneric(new Message(MessageType.LISTPIECES));
	//		String[] tabPieces = new String[listPieces.size()];
	//		int i =0;
	//		for(List<String> line : listPieces) {
	//			tabPieces[i]=line.get(0);
	//			i++;
	//		}
	//		return tabPieces;
	//	}
	//


	public List<List<String>> readObjects() {
		return readGeneric(new Message(MessageType.LISTOBJECTS));
	}
	
	public List<List<String>> listCapteurs(){
		return readGeneric(new Message(MessageType.LISTCAPTEURS));
	}
	
	public List<List<String>> listNotifications(){
		return readGeneric(new Message(MessageType.MONITORING));
	}
	
	public void changeAlert(Integer id, Boolean status){
		MsgChangeAlert msg = new MsgChangeAlert(id, status);
		String output = Tool.messageToJSON(msg);
		try {
			contactServer(output);
		}
		catch (Throwable e) {}
	}

	public String[] readReferentiels() {
		List<List<String>> listReferentiels = readGeneric(new Message(MessageType.LISTREFERENTIELS));
		String[] tabReferentiels = new String[listReferentiels.size()];
		int i =0;
		for(List<String> line : listReferentiels) {
			tabReferentiels[i]=line.get(0);
			i++;
		}
		return tabReferentiels;
	}

	// To be called when we expect the message to return a List<List<String>>
	public List<List<String>> readGeneric(Message message) {
		String output = Tool.messageToJSON(message);
		String answer; 
		try { 
			answer = contactServer(output);
			if(answer!= null) {
				MsgListResult result = (MsgListResult)Tool.jsonToMessage(answer);
				return result.getListResult();
			}
		}
		catch (ConnectException e) {
			return null;
		}
		return null;
	}


	// TODO config file
	private String contactServer(String request) throws ConnectException {

		final int port = 2001;

		// Get the local address
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost(); //+ mettre dans config 192.168.20.20 et garder le même port
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
			//TODO : put a timer before awfull exception
			rawAnswerServer= new DataInputStream(socket.getInputStream());
			answerServer = rawAnswerServer.readUTF();
		}
		catch(IOException e){}
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
