package shs.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache implements Runnable {


	private static Map<Integer, List<Timestamp>> cache = new ConcurrentHashMap<Integer, List<Timestamp>>();
	// TODO : could be useful to put these numbers in the config file
	final protected static Integer occurenciesNecessary = 3;
	final protected static Integer delayTime = 5*60*1000; // max delay which defined that two events are linked

	public static boolean addCacheData(Integer id) {
		if(cache.containsKey(id)){
			cache.get(id).add(new Timestamp(System.currentTimeMillis()));
			// We will count all the events which occurs
			int occurencies = 0;
			Timestamp limitTime = new Timestamp(System.currentTimeMillis()-delayTime);
			for(Timestamp time : cache.get(id)) {
				if(time.after(limitTime)) {
					occurencies++;
				}
			}
			if(occurencies>=occurenciesNecessary)
				return true;
			else
				return false;
		}
		else {
			List<Timestamp> list = new ArrayList<Timestamp>();
			list.add(new Timestamp(System.currentTimeMillis()));
			cache.put(id, list);
			return false;
		}
	}

	public static void clearOldData() {
		// We will remove all the timestamp which are older than 24h
		Timestamp limitDate = new Timestamp(System.currentTimeMillis()-100);
		Iterator<Integer> iteratorID = cache.keySet().iterator();
		while(iteratorID.hasNext()){
			Integer id = iteratorID.next();
			ListIterator<Timestamp> iteratorTimestamp = cache.get(id).listIterator();
			while(iteratorTimestamp.hasNext()) {
				Timestamp time = iteratorTimestamp.next();
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
				//We clear the old data each hours;
				Thread.sleep(60*60*1000);
				clearOldData();
			}catch(InterruptedException e) {
				break;
			}
		}
	}
}
