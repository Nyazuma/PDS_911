package shs.simulator;

import java.util.List;

import shs.common.MsgReportMotion;
import shs.common.Tool;

public class CategoryMotion extends CategoryObject{

	public CategoryMotion(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public CategoryMotion() {
		super();
	}

	public void launchAlert(Integer id) {
		MsgReportMotion update = new MsgReportMotion(id);
		Connector.contactServer(Tool.messageToJSON(update));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
	}


}
