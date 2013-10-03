/**
 * 
 */
package com.yeshu.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeshu.app.R;
import com.yeshu.app.ui.base.BaseFragment;

/**
 * 
 * @author yeshu
 * @date 2013-9-8 
 *
 */

public class PersonalCenterFargment extends BaseFragment{

	
    public static GuideDispatchFragment newInstance() {
    	GuideDispatchFragment fragment = new GuideDispatchFragment();

        return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_personalcenter, null);
		
		return view;
	}
	
    
	
    
}