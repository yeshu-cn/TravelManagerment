/**
 * 
 */
package com.yeshu.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.User;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.base.BaseFragment;

/**
 * 
 * @author yeshu
 * @date 2013-9-8 
 *
 */

public class PersonalCenterFargment extends BaseFragment{
	private TextView labelUserName;
	private TextView lableRoleName;
	private TextView lablePhone;

	
    public static GuideDispatchFragment newInstance() {
    	GuideDispatchFragment fragment = new GuideDispatchFragment();

        return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_personalcenter, null);
		
		labelUserName = (TextView)view.findViewById(R.id.personalinfo_tv_username);
		lableRoleName = (TextView)view.findViewById(R.id.personalinfo_tv_rolename);
		lablePhone = (TextView)view.findViewById(R.id.personalinfo_tv_phone);
		
		User user = User.getInstance();
		if(null != user.getInstance().getUsername()){
			labelUserName.setText(getString(R.string.personalinfo_username, user.getUsername()));
			lableRoleName.setText(getString(R.string.personalinfo_rolename, user.getRolename()));
			lablePhone.setText(getString(R.string.personalinfo_phone, user.getPhone()));
		}else{
			getUserInfo();
		}	
		return view;
	}
	
    private void getUserInfo(){
    	Api.getInstance().getUserInfo(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				try {
					User user = User.getInstance();
					//保存用户信息进入全局变量
					user.setUsername(response.getString("nickname"));
					user.setPhone(response.getString("phone"));
					user.setRolename(response.getString("rolename"));
					//设置显示
					labelUserName.setText(getString(R.string.personalinfo_username, user.getUsername()));
					lableRoleName.setText(getString(R.string.personalinfo_rolename, user.getRolename()));
					lablePhone.setText(getString(R.string.personalinfo_phone, user.getPhone()));
				} catch (JSONException e) {
					e.printStackTrace();
				}			
			}
    		
    	});
    }
	
    
}