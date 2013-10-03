/**
 * 
 */
package com.yeshu.app.ui;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.net.WebRestClient;
import com.yeshu.app.ui.base.BaseActivity;
import com.yeshu.app.utils.LogUtil;

/**
 * 
 * @author yeshu
 * @date 2013-7-7 
 *
 */

public class LoginActivity extends BaseActivity{
	private Button btnLogin;
	private EditText etAccount;
	private EditText etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_login);
		
		init();
	}
	
	private void init(){
		btnLogin = (Button)findViewById(R.id.loginac_btn_login);
		etAccount = (EditText)findViewById(R.id.logingac_et_account);
		etPassword = (EditText)findViewById(R.id.logingac_et_password); 
		
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				login();
			}
		});
	}

	

	private void login(){
		String username = etAccount.getText().toString();
		String pwd = etPassword.getText().toString();
		
		
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("���ڵ�¼...");
		dialog.show();
		Api.getInstance().login(username, pwd, new JsonHttpResponseHandler(){
	
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
//				dialog.dismiss();
				LogUtil.i("yeshu", "error-->" + errorResponse.toString());
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONObject response) {
				LogUtil.i("yeshu", response.toString());
				dialog.dismiss();
				int result;
				try {
					result = response.getInt("result");
					if(result == Api.LOGIN_SUCCESS){			
						Intent it = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(it);
						LoginActivity.this.finish();
					}else{
						Toast.makeText(LoginActivity.this, "��¼ʧ��!", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}	
			}
		});
	}
	
	public void logout(){
		Api.getInstance().logout(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				System.out.println("---->" + response.toString());
				
				WebRestClient.getCookieStore().clear();
				testCookie();
			}
		});
	}
	
	public void testCookie(){
		for(Cookie cookie : WebRestClient.getCookieStore().getCookies()){
			System.out.println("----------xx>" + cookie.toString());
		}
	}
	
	
}

