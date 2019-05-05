package shs.simulator;

import java.util.List;

import shs.common.MsgReportSmoke;
import shs.common.Tool;

public class CategorySmoke extends CategoryObject{

	public CategorySmoke(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public CategorySmoke() {
		super();
	}

	@Override
	public void launchAlert(Integer id) {
		for(int i=0; i<3; i++) {
			int smokeValue = (int) (50 + Math.random()*50);
			MsgReportSmoke update = new MsgReportSmoke(id, smokeValue);
			Connector.contactServer(Tool.messageToJSON(update));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}
