package shs.simulator;

import java.util.List;

import shs.common.MsgReportRFID;
import shs.common.Tool;

public class CategoryRFID extends CategoryObject{

	public CategoryRFID(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}
	
	public CategoryRFID() {
		super();
	}
	
	public void launchAlert(Integer id) {	
		MsgReportRFID update = new MsgReportRFID(id);
		Connector.contactServer(Tool.messageToJSON(update));
	}

}
