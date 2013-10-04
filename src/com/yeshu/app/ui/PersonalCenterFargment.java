/**
 * 
 */
package com.yeshu.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeshu.app.R;
import com.yeshu.app.User;
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
		labelUserName.setText(getString(R.string.personalinfo_username, user.getUsername()));
		lableRoleName.setText(getString(R.string.personalinfo_rolename, user.getRolename()));
		lablePhone.setText(getString(R.string.personalinfo_phone, user.getPhone()));
		
		return view;
	}
	
    
	
    
}