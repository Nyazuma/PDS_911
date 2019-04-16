package shs.simulator;

import java.util.List;

public class CategoryObject{

	protected String referencedName;
	protected List<String> listObjects;

	public CategoryObject(String referencedName, List<String> listObjects) {

		this.referencedName=referencedName;
		this.listObjects=listObjects;
	}

	public Integer waiting() throws InterruptedException {
		if(listObjects.isEmpty())
			throw new InterruptedException();
		// Delay
		Thread.sleep((long) (6000 + Math.random()*10000));
		// Choice of the sensor
		int id = (int)(Math.random()*listObjects.size());
		String activeSensor = listObjects.get(id);
		System.out.println("A new report was generated by a '" + referencedName + "' sensor (ID " + listObjects.get(id) + ")");
		// Transmission of the call
		return Integer.valueOf(activeSensor);
	}
}
