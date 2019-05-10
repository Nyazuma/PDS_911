package shs.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.List;

import shs.common.Message;
import shs.common.MsgDeleteObject;
import shs.common.MessageType;
import shs.common.MsgAddObject;
import shs.common.MsgBooleanResult;
import shs.common.MsgChangeAlert;
import shs.common.MsgConnection;
import shs.common.MsgNumberObjectAdded;
import shs.common.MsgNumberObjectAlert;
import shs.common.MsgNumberObjectDeleted;
import shs.common.MsgNumberObjectFetch;
import shs.common.MsgNumberObjectUpdated;
import shs.common.MsgDeleteEmplacement;
import shs.common.MsgIntResult;
import shs.common.MsgListResult;
import shs.common.MsgUpdateEmplacement;
import shs.common.MsgUpdateObject;
import shs.common.Tool;

public class GuiController {

	// JFrame of the application
	protected Gui gui;

	public GuiController() {
		this.gui=new Gui(this);
	}

	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
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
	
	/**
	 * Used to add a location to a sensor
	 * @param ID_Capteur
	 * @param ID_Emplacement
	 * @return
	 */
	public boolean updateEmplacementObject(String ID_Capteur, String ID_Emplacement) {
		MsgUpdateEmplacement updateEmplacement = new MsgUpdateEmplacement(ID_Capteur, ID_Emplacement);
		String output = Tool.messageToJSON(updateEmplacement); 
		String answer; 
		try {
			answer = contactServer(output);
			if(answer!= null) {
				MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer);
				return result.getStatus();
			}
			return false;	
		}catch (ConnectException e) {
			return false;
		}
	}
	
	/**
	 * Used to delete the location of an object
	 * @param ID_Capteur
	 * @return
	 */
	public boolean deleteEmplacementObject(String ID_Capteur) {
		MsgDeleteEmplacement deleteEmplacement = new MsgDeleteEmplacement(ID_Capteur);
		String output = Tool.messageToJSON(deleteEmplacement); 
		String answer; 
		try {
			answer = contactServer(output);
			if(answer!= null) {
				MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer);
				return result.getStatus();
			}
			return false;	
		}catch (ConnectException e) {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
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
	
	public int nbObjectAdded(String dateFrom, String dateTo) {
		MsgNumberObjectAdded nbObjectAdded = new MsgNumberObjectAdded (dateFrom, dateTo);
		return readInt(nbObjectAdded);
	}
	
	public int nbObjectDeleted(String dateFrom, String dateTo) {
		MsgNumberObjectDeleted nbObjectDeleted = new MsgNumberObjectDeleted (dateFrom, dateTo);
		return readInt(nbObjectDeleted);
	}
	
	public int nbObjectUpdated(String dateFrom, String dateTo) {
		MsgNumberObjectUpdated nbObjectUpdated = new MsgNumberObjectUpdated (dateFrom, dateTo);
		return readInt(nbObjectUpdated);
	}
	
	public int nbObjectAlert(String dateFrom, String dateTo) {
		MsgNumberObjectAlert nbObjectAlert = new MsgNumberObjectAlert (dateFrom, dateTo);
		return readInt(nbObjectAlert);
	}
	
	public int nbObjectFetch(String captorType, String captorState, String captorPlace, String captorFloor, String captorResidence) {
		MsgNumberObjectFetch nbObjectFetch = new MsgNumberObjectFetch (captorType, captorState, captorPlace, captorFloor, captorResidence);
		return readInt(nbObjectFetch);
	}
	
	
	
	public int nbObjectNonConfigured() {
		Message nbObjectNonConfigured = new Message(MessageType.NUMBEROBJECTNONCONFIGURED);
		String output = Tool.messageToJSON(nbObjectNonConfigured);
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

	/**
	 * 
	 * @param rowUpdate
	 * @return
	 */
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
	
//	public boolean updateNonConfig(List<String> rowUpdate) {
//		MsgUpdateObjectNonConfig update =  new MsgUpdateObjectNonConfig(rowUpdate);  
//		String output = Tool.messageToJSON(update);
//		String answer; 
//		try {
//			answer = contactServer(output); 
//			if(answer != null) {
//				MsgBooleanResult result = (MsgBooleanResult)Tool.jsonToMessage(answer); 
//				return result.getStatus(); 
//			}
//		}catch (ConnectException e) {
//			return false; 
//		}
//		return false; 
//	}
//	
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @param detectorType
	 * @return
	 */
	public List<List<String>> addObject(String detectorType) {
		MsgAddObject addObject = new MsgAddObject(detectorType, addresseMac());
		return readGeneric(addObject);
	}
	
	/**
	 * 
	 * @param idObject
	 * @return
	 */
	public List<List<String>> delete(String idObject) {
		MsgDeleteObject delete = new MsgDeleteObject(idObject);  
		return readGeneric(delete);
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	public List<List<String>> EmplacementFull(){
		return readGeneric(new Message(MessageType.LISTEMPLACEMENTS)); 
	}
	
	public List<List<String>> listSensors(){
		return readGeneric(new Message(MessageType.LISTSENSORSDETAILS)); 
	}


	/**
	 * Read stage and RDC emplacement
	 * @return
	 */
	public String[] readEmplacements() {
		List<List<String>> listEmplacements = readGeneric(new Message(MessageType.LISTEMPLACEMENTS));
		int i =0;
		String str = "";
		for(List<String> line : listEmplacements) {
			if (!str.contains(line.get(1))) {
				i++;
				str = str+","+line.get(1);
			}
			
		}
		String[] tabEmplacements = new String[i];
		i=0;
		str = "";
		for(List<String> line : listEmplacements) {
			if (!str.contains(line.get(1))) {
			tabEmplacements[i]=line.get(1);
			i++;
			str = str+","+line.get(1);
			}
		}
		
		return tabEmplacements;
	}

	/**
	 * 
	 * @return
	 */
	public String[] readEtages() {
		List<List<String>> listEtages = readGeneric(new Message(MessageType.LISTETAGES));
		String[] tabEtages = new String[listEtages.size()];
		int i =0;
		for(List<String> line : listEtages) {
			tabEtages[i]=line.get(2);
			i++;
		}
		return tabEtages;
	}
	
	/**
	 * 
	 * @return
	 */
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


	/**
	 * 
	 * @return
	 */
	public List<List<String>> readObjects() {
		return readGeneric(new Message(MessageType.LISTOBJECTS));
	}

	/**
	 * 
	 * @return
	 */
	public List<List<String>> listCapteurs(){
		return readGeneric(new Message(MessageType.LISTCAPTEURS));
	}

	/**
	 * 
	 * @return
	 */
	public List<List<String>> listNotifications(){
		return readGeneric(new Message(MessageType.MONITORING));
	}

	
	public List<List<String>> listNonConfigured(){
		return readGeneric(new Message(MessageType.LISTNONCONFIGURED));
	}
	/**
	 * 
	 * @param id
	 * @param status
	 */
	public void changeAlert(Integer id, Boolean status){
		MsgChangeAlert msg = new MsgChangeAlert(id, status);
		String output = Tool.messageToJSON(msg);
		try {
			contactServer(output);
		}
		catch (Throwable e) {}
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * To be called when we expect the message to return a List<List<String>>
	 * @param message
	 * @return
	 */
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
	
	// To be called when we expect the message to return a int
	public int readInt(Message message) {
		String output = Tool.messageToJSON(message);
		String answer; 
		try { 
			answer = contactServer(output);
			if(answer!= null) {
				MsgIntResult result = (MsgIntResult)Tool.jsonToMessage(answer);
				return result.getNumber();
			}
		}
		catch (ConnectException e) {
			return 0;
		}
		return 0;
	}


	// TODO config file
	/**
	 * 
	 * @param request
	 * @return
	 * @throws ConnectException
	 */
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
