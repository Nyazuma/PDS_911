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
			Integer hygroValue = (int) (Math.random()*5)+95;
			MsgReportHygro update = new MsgReportHygro(id, hygroValue);
			Connector.contactServer(Tool.messageToJSON(update));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}