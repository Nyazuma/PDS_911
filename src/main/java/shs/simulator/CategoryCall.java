package shs.simulator;

import java.util.List;

import shs.common.MsgReportCall;
import shs.common.Tool;

public class CategoryCall extends CategoryObject{

	public CategoryCall(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}
	
	public CategoryCall() {
		super();
	}

	public void launchAlert(Integer id) {
		MsgReportCall update = new MsgReportCall(id);
		Connector.contactServer(Tool.messageToJSON(update));
	}


}
