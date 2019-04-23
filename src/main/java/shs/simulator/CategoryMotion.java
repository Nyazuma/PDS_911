package shs.simulator;

import java.util.List;

import shs.common.MsgReportMotion;
import shs.common.Tool;

public class CategoryMotion extends CategoryObject implements Runnable{

	public CategoryMotion(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void run() {
		
		try {
			while(true) {
				MsgReportMotion update = new MsgReportMotion(waiting());
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			System.out.println("There is no '" + referencedName + "' available");
		}
	}
	

}
