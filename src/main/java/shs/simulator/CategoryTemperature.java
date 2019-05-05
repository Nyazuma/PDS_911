package shs.simulator;

import java.util.List;

import shs.common.MsgReportTemperature;
import shs.common.Tool;

public class CategoryTemperature extends CategoryObject{

	public CategoryTemperature(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}
	
	public CategoryTemperature() {
		super();
	}

	public void launchAlert(Integer id) {
		for(int i=0; i<3; i++) {
			float temperature = (float) (10.0 + Math.random()*4.0);
			MsgReportTemperature update = new MsgReportTemperature(id, temperature);
			Connector.contactServer(Tool.messageToJSON(update));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}
