/**
 * 
 */
package com.yeshu.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yeshu.app.net.WebRestClient;

import android.app.Application;
import android.os.Handler;

/**
 * 
 * @author yeshu
 * @date 2013-7-7 
 *
 */

public class MyApplication extends Application{
	private Handler mHandler = new Handler();
	private ExecutorService mThreadpool = Executors.newSingleThreadExecutor();
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		WebRestClient.init(getApplicationContext());
			
	}

	/**
	 * UI线程中执行
	 * @param runnable
	 */
	public void runOnUIThread(Runnable runnable){
		if(null != runnable){
			mHandler.post(runnable);	
		}
	}

	public void runOnUIThreadDelayed(Runnable runnable, long delayMillis){
		if(null != runnable){
			mHandler.postDelayed(runnable, delayMillis);
		}
	}
	
	
	/**
	 * 后台线程中执行
	 * @param runnable
	 */
	public void runOnBackgroundThread(Runnable runnable){
		if(null != runnable){
			mThreadpool.submit(runnable);
		}
	}
	
	
	
	
}

