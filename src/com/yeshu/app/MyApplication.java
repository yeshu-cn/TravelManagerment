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
	 * 主线程中运行
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
	 * 线程池中控制启动后台线程
	 * @param runnable
	 */
	public void runOnBackgroundThread(Runnable runnable){
		if(null != runnable){
			mThreadpool.submit(runnable);
		}
	}
	
	
	
	
}

