package shs.simulator;

import java.util.ArrayList;
import java.util.List;

import shs.common.Message;
import shs.common.MessageType;
import shs.common.MsgListResult;
import shs.common.Tool;

public class Simulation {

	public Simulation() {
		System.out.println("911 SHS Simulation > launched!");
		init();
	}
	
	private void init() {
		//We get the referentiel of objects
		Message requestReferentielList = new Message(MessageType.LISTREFERENTIELS);
		String answerReferentiel = Connector.contactServer(Tool.messageToJSON(requestReferentielList));
		List<List<String>> listReferentiels = ((MsgListResult) Tool.jsonToMessage(answerReferentiel)).getListResult();
		System.out.println("The referentiel sensor list was loaded from the server!");
		
		//We get a list of current sensors
		Message requestCaptorsList = new Message(MessageType.LISTOBJECTS);
		String answerCaptors = Connector.contactServer(Tool.messageToJSON(requestCaptorsList));
		List<List<String>> listObjects = ((MsgListResult) Tool.jsonToMessage(answerCaptors)).getListResult();
		System.out.println("The sensor list was loaded from the server!");
		
		//We aggregate objects by their referentiel types
		List<List<String>> sortedCaptors = new ArrayList<List<String>>();
		//Init
		for(int i=0; i<listReferentiels.size();i++)
			sortedCaptors.add(new ArrayList<String>());
		int cursor;
		for(List<String> captor : listObjects) {
			cursor = 0;
			for(List<String> reference : listReferentiels) {
				if(captor.get(1).trim().equalsIgnoreCase(reference.get(0))) {
					//We add its id
					sortedCaptors.get(cursor).add(captor.get(0));
					continue;
				}
				cursor++;
			}
		}
		
		
		CategoryRFID categoryRFID = new CategoryRFID(listReferentiels.get(0).get(0), sortedCaptors.get(0), 5);
		Thread threadRFID = new Thread(categoryRFID);
		threadRFID.start();
		
		CategoryCall categoryCall = new CategoryCall(listReferentiels.get(1).get(0), sortedCaptors.get(0), 2);
		Thread threadCall = new Thread(categoryCall);
		threadCall.start();
	}
	
}
