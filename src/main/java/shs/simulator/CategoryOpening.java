package shs.simulator;

import java.util.List;

import shs.common.MsgReportOpening;
import shs.common.Tool;

public class CategoryOpening extends CategoryObject{

	public CategoryOpening(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public CategoryOpening() {
		super();
	}

	@Override
	public void launchAlert(Integer id) {
		MsgReportOpening update = new MsgReportOpening(id);
		Connector.contactServer(Tool.messageToJSON(update));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
	}

}
