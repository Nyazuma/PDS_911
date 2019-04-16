package shs.simulator;

import java.util.ArrayList;
import java.util.List;

import shs.common.Message;
import shs.common.MsgAddObject;
import shs.common.MsgListResult;
import shs.common.Tool;

public class CategoryObject {
	
	protected String referencedName;
	protected List<String> listObjects;
	
	public CategoryObject(String referencedName, List<String> listObjects, int askedNumberSensors) {
		
		System.out.println("Loading of " + askedNumberSensors + " '" + referencedName + "' sensors");
		this.referencedName=referencedName;
		
		if(askedNumberSensors<=listObjects.size()) {
			this.listObjects=listObjects.subList(0, askedNumberSensors);
		}
		else {
			// We have to add more sensors to reach the number asked
			System.out.println("Not enough '" + referencedName + "' sensors in the database : " 
					 + (askedNumberSensors -listObjects.size()) + " will be added");
			Message addObject = new MsgAddObject(referencedName);
			String answer = null;
			for(int i=0; i< (askedNumberSensors -listObjects.size()); i++) {
				answer = Connector.contactServer(Tool.messageToJSON(addObject));
			}
			// Once all are added, we have to get their ids
			List<List<String>> listAllObjects = ((MsgListResult) Tool.jsonToMessage(answer)).getListResult();
			listObjects = new ArrayList<String>();
			for(List<String> object : listAllObjects) {
				if(object.get(1).trim().equals(referencedName)) {
					listObjects.add(object.get(0));
					if(listObjects.size()>=askedNumberSensors)
						break;
				}
			}
		}
		System.out.println("The loading of '" + referencedName + "' sensors is done!");
	}


}
