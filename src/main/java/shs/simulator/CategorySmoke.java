package shs.simulator;

import java.util.List;

import shs.common.MsgReportCall;
import shs.common.MsgReportSmoke;
import shs.common.Tool;

public class CategorySmoke extends CategoryObject implements Runnable{

	public CategorySmoke(String referencedName, List<String> listObjects) {
		super(referencedName, listObjects);
	}

	public void randomFire() {
		// Choice of the sensor
		int id = (int)(Math.random()*listObjects.size());
		specificID(Integer.parseInt(listObjects.get(id)));
	}

	public void specificID(int id) {
		// Choice of the sensor
		if(!listObjects.contains(Integer.toString(id)))
			throw new IllegalArgumentException();
		// We create a smoke report (a value >=50)
		for(int i=0; i<3; i++) {
			int level = (int) (50 + Math.random()*50);
			MsgReportSmoke update = new MsgReportSmoke(id, level);
			Connector.contactServer(Tool.messageToJSON(update));
			try {
				Thread.sleep((long) (5000 + Math.random()*5000));
			} catch (InterruptedException e) {}
		}
	}

	public void run() {

		try {
			while(true) {
				int smokeValue = (int) (50 + Math.random()*50);
				MsgReportSmoke update = new MsgReportSmoke(waiting(), smokeValue);
				Connector.contactServer(Tool.messageToJSON(update));
			}
		} catch (InterruptedException e) {
			System.out.println("There is no '" + referencedName + "' available");
		}
	}

}
