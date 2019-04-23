package shs.simulator;

import java.util.List;

import shs.common.MsgReportRFID;
import shs.common.Tool;

public class CategoryRFID extends CategoryObject implements Runnable {

	public CategoryRFID(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void run() {
		
		try {
			while(true) {
				MsgReportRFID update = new MsgReportRFID(waiting());
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			System.out.println("There is no '" + referencedName + "' available");
		}


	}

}
