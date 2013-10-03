/**
 * 
 */
package com.yeshu.app.ui;

import java.util.ArrayList;

import com.yeshu.app.ui.bean.GuideInfo;
import com.yeshu.app.ui.bean.TourGroupInfo;

/**
 * 
 * @author yeshu
 * @date 2013-9-21 
 *
 */

public class DataManager {
	private static DataManager instance = new DataManager();
	
	private ArrayList<TourGroupInfo> mGruopList = null;
	private ArrayList<GuideInfo> mGuideList = null;
	
	private DataManager(){
		
	}
	
	public static DataManager getInstance(){
		return instance;
	}
	
	public ArrayList<TourGroupInfo> getTourGroupList(){
		return mGruopList;
	}
	
	public ArrayList<GuideInfo> getGuideList(){
		return mGuideList;
	}
	
}

