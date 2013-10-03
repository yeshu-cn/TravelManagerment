/**
 * 
 */
package com.yeshu.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeshu.app.R;
import com.yeshu.app.ui.base.BaseFragment;
import com.yeshu.app.utils.LogUtil;

/**
 * 
 * @author yeshu
 * @date 2013-7-27 
 *
 */

public class AddGuideFragment extends BaseFragment{
	private final static String TAG = "AddGuideFragment";
	
	public static AddGuideFragment newInstance() {
    	AddGuideFragment fragment = new AddGuideFragment();

        return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onCreateView");
		
		View view = inflater.inflate(R.layout.fragment_addguide, null);
		
		init(view);
		
		return view;
	}
	
/********ÉúÃüÖÜÆÚ²âÊÔ--------------------------->********************/
	
	@Override
	public void onAttach(Activity activity) {
		LogUtil.i(TAG, "--->onAttach");
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onCreate");
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		LogUtil.i(TAG, "--->onStart");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		LogUtil.i(TAG, "--->onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogUtil.i(TAG, "--->onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogUtil.i(TAG, "--->onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		LogUtil.i(TAG, "--->onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		LogUtil.i(TAG, "--->onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		LogUtil.i(TAG, "--->onDetach");
		super.onDetach();
	}
	/*****<------------------------------------------********************/
	
	
	private void init(View view){

    }
}

