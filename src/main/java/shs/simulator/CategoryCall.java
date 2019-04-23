package shs.simulator;

import java.util.List;

import shs.common.MsgReportCall;
import shs.common.Tool;

public class CategoryCall extends CategoryObject implements Runnable {

	public CategoryCall(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void run() {
		
		try {
			while(true) {
				MsgReportCall update = new MsgReportCall((waiting()));
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			System.out.println("There is no '" + referencedName + "' available");
		}
	}

}
