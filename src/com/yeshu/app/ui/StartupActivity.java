/**
 * 
 */
package com.yeshu.app.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.yeshu.app.MyApplication;
import com.yeshu.app.R;
import com.yeshu.app.net.WebRestClient;
import com.yeshu.app.ui.base.BaseActivity;

/**
 * 
 * @author yeshu
 * @date 2013-9-7 
 *
 */

public class StartupActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_startup);
		
		PackageInfo pinfo;
		try {
			pinfo = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			TextView tv = (TextView)this.findViewById(R.id.splashVerText);
			tv.setText("v" + pinfo.versionName + " Beta");
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}

		//UI线程中执行跳转
		((MyApplication)getApplication()).runOnUIThreadDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(checkIsLogin()){
					goToMainActivity();
				}else{
					goToLoginActivity();
				}
			}
		}, 1000*1);

		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 检查是否登录过
	 * @return	登录过返回true
	 * 			未登录过返回false
	 */
	private boolean checkIsLogin(){
		return !WebRestClient.getCookieStore().getCookies().isEmpty();
	}

	private void goToMainActivity(){
		Intent it = new Intent(this, MainActivity.class);
		startActivity(it);
		this.finish();
	}
	
	private void goToLoginActivity(){
		Intent it = new Intent(this, LoginActivity.class);
		startActivity(it);
		this.finish();
	}
	
}

