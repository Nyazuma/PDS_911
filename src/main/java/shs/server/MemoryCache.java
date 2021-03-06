package shs.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache implements Runnable {


	private static Map<Integer, List<List<Object>>> cache = new ConcurrentHashMap<Integer, List<List<Object>>>();

	final protected static Integer occurenciesNecessary = 3;
	final protected static Integer delayTime = 3*60*1000; // max delay which defines that two events are linked
	final protected static Integer frequenceClearTime = 15*60*1000;
	
	public static boolean addCacheData(Integer id) {
		return addCacheData(id, null);
	}
	
	public static boolean addCacheData(Integer id, Float value) {
		if(cache.containsKey(id)){
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(new Timestamp(System.currentTimeMillis()));
			line.add(value);
			cache.get(id).add(line);
			// We will count all the events which occurs
			int occurencies = 0;
			Timestamp limitTime = new Timestamp(System.currentTimeMillis()-delayTime);
			for(List<Object> cursor : cache.get(id)) {
				if(((Timestamp) cursor.get(0)).after(limitTime)) {
					occurencies++;
				}
			}
			// to avoid the launch of alerts already sent, we had changed occurencies>=ocurrenciesNecessary
			if(occurencies==occurenciesNecessary) {
				System.out.println("#Monitoring report : " + occurencies + " reports were received from the sensor ID n°" + id 
						+ ", lets test the value!");
				return true;
			}
			else {
				if(occurencies<occurenciesNecessary) {
					System.out.println("#Monitoring report : " + occurencies + " reports were received from the sensor ID n°" + id 
							+ ", need " + (occurenciesNecessary-occurencies) + " more!");
				}
				else {
					System.out.println("#Monitoring report : " + occurencies + " reports were received from the sensor ID n°" + id 
							+ ", the test was already done in the last 5 minutes");
				}
				return false;
			}
		}
		else {
			System.out.println("#Monitoring report : 1 report was received from the sensor ID n°" + id 
					+ ", need 2 more!");
			List<Object> lineSensors = new ArrayList<Object>();
			List<List<Object>> infoSensors = new ArrayList<List<Object>>();
			lineSensors.add(new Timestamp(System.currentTimeMillis()));
			lineSensors.add(value);
			infoSensors.add(lineSensors);
			cache.put(id, infoSensors);
			return false;
		}
	}
	
	public static Float getAverageValue(Integer id) {
		Float average = 0.0f;
		int occurencies = 0;
		if(cache.containsKey(id)){
			List<List<Object>> reportsSensor = cache.get(id);
			Timestamp limitTime = new Timestamp(System.currentTimeMillis()-delayTime);
			for(List<Object> cursor : reportsSensor) {
				if(((Timestamp) cursor.get(0)).after(limitTime) && cursor.get(1)!=null) {
					average+= (Float) cursor.get(1);
					occurencies++;
				}
			}
		}
		if(occurencies==0)
			return null;
		else {
			average = average / occurencies;
			System.out.println("#Monitoring report : the average value of the last reports is " + average + " for the sensor ID n°" + id);
			return average;
		}			
	}

	public static void clearOldData() {
		Timestamp limitDate = new Timestamp(System.currentTimeMillis()-delayTime);
		Iterator<Integer> iteratorID = cache.keySet().iterator();
		while(iteratorID.hasNext()){
			Integer id = iteratorID.next();
			ListIterator<List<Object>> iteratorTimestamp = cache.get(id).listIterator();
			while(iteratorTimestamp.hasNext()) {
				Timestamp time = (Timestamp) iteratorTimestamp.next().get(0);
				if(time.before(limitDate)) {
					iteratorTimestamp.remove();
				}
			}
			if(cache.get(id).isEmpty())
				iteratorID.remove();
		}
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(frequenceClearTime);
				clearOldData();
			}catch(InterruptedException e) {
				break;
			}
		}
	}
	
}
