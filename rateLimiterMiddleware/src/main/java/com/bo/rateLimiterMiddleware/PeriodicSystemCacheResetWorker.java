package com.bo.rateLimiterMiddleware;

import com.bo.service.cache.UserApiLimitterDataLru;

public class PeriodicSystemCacheResetWorker {


	static void startPeriodicSystemCacheWorker() {
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						resetCache();
						Thread.sleep(30000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(runnable1);
		thread.start();
	}


	private static  void resetCache() {
		UserApiLimitterDataLru.clearQueue();
		UserApiLimitterDataLru.clearMap();
		System.out.println("Resetting the System catch");
	}
}



