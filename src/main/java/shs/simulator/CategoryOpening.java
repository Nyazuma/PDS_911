package shs.simulator;

import java.util.List;

import shs.common.MsgReportOpening;
import shs.common.Tool;

public class CategoryOpening extends CategoryObject implements Runnable {

	public CategoryOpening(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}
	
	public void run() {

		try {
			while(true) {
				MsgReportOpening update = new MsgReportOpening(waiting());
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
