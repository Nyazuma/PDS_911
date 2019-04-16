package shs.simulator;

import java.util.List;

import shs.common.MsgReportTemperature;
import shs.common.Tool;

public class CategoryTemperature extends CategoryObject implements Runnable {

	public CategoryTemperature(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void run() {

		try {
			while(true) {
				float temperature = (float) (14.0 + Math.random()*10.0);
				MsgReportTemperature update = new MsgReportTemperature(waiting(), temperature);
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}
