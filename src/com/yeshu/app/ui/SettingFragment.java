/**
 * 
 */
package com.yeshu.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.base.BaseFragment;

/**
 * 
 * @author yeshu
 * @date 2013-9-8 
 *
 */

public class SettingFragment extends BaseFragment{
	private Button btnLogout;
	
    public static GuideDispatchFragment newInstance() {
    	GuideDispatchFragment fragment = new GuideDispatchFragment();

        return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_setting, null);
		btnLogout = (Button)view.findViewById(R.id.setting_btn_logout);
		
		btnLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//×¢ÏúÍË³ö³ÌÐò
				Api.getInstance().logout(null);
				getActivity().finish();  
                System.exit(0); 
			}
		});
		return view;
	}
    
	
    
}