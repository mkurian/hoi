package com.intuit.txtweb.hoi.platform.goldPrice;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author mkurian
 *
 */
public class CacheUpdateTimer {
	
	static Timer timer;
	static {
		timer = new Timer();
		timer.schedule(new UpdateTask(), 1*60*1000, 5*60*1000);
	}
}


class UpdateTask extends TimerTask{

	@Override
	public void run() {
		int i = 1;
		boolean updated;
		do {
			updated = Cache.upateCache();
			i--;
		}  while (!updated && i > 0);
	}
	
}
