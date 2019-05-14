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
		float temperature = DataConfigSimu.getMIN_TEMPERATURE_VALUE() + 
				(float)(Math.random()*(DataConfigSimu.getMAX_TEMPERATURE_VALUE()-DataConfigSimu.getMIN_TEMPERATURE_VALUE()));
		MsgReportTemperature update = new MsgReportTemperature(id, temperature);
		Connector.contactServer(Tool.messageToJSON(update));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
	}

}
