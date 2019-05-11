package shs.simulator;

import java.util.List;

import shs.common.MsgReportHygro;
import shs.common.Tool;

public class CategoryHygro extends CategoryObject{

	public CategoryHygro(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}
	
	public CategoryHygro() {
		super();
	}

	public void launchAlert(Integer id) {
		for(int i=0; i<3; i++) {
			Integer hygroValue = DataConfigSimu.getMIN_HYGRO_VALUE() + 
					(int) (Math.random()*(DataConfigSimu.getMAX_HYGRO_VALUE()-DataConfigSimu.getMIN_HYGRO_VALUE()));
			MsgReportHygro update = new MsgReportHygro(id, hygroValue);
			Connector.contactServer(Tool.messageToJSON(update));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}