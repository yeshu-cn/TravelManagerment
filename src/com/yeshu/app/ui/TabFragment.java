/**
 * 
 */
package com.yeshu.app.ui;

import android.support.v4.app.Fragment;

/**
 * 
 * @author yeshu
 * @date 2013-7-16 
 *
 */

public class TabFragment {
	private Fragment fragment;
	private String tabTitle;
	
	
	
	
	public TabFragment(Fragment fragment, String tabTitle) {
		super();
		this.fragment = fragment;
		this.tabTitle = tabTitle;
	}
	
	
	public Fragment getFragment() {
		return fragment;
	}
	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	
	
	
	

}

