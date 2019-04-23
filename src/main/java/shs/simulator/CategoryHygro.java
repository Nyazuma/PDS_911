package shs.simulator;

import java.util.List;

import shs.common.MsgReportHygro;
import shs.common.Tool;

public class CategoryHygro extends CategoryObject implements Runnable {

	public CategoryHygro(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void run() {

		try {
			while(true) {
				Integer hygroValue = (int) (Math.random()*100);
				MsgReportHygro update = new MsgReportHygro(waiting(), hygroValue);
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			System.out.println("There is no '" + referencedName + "' available");
		}

	}
}