/**
 * 
 */
package com.yeshu.app.net;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @author yeshu
 * @date 2013-9-2 
 *
 */

public class Api {
	private static Api instance = new Api();
	
	//api
    private static final String API_LOGIN = "login";
    private static final String API_LOGOUT = "logout";
    private static final String API_GETGUIDElIST = "getGuideList";
    private static final String API_GETTOURGROUPLIST = "getTourGroupList";
    private static final String API_DISPATCHGUIDE = "dispatchGuide";
    private static final String API_GETUSERINFO = "getUserInfo";
    
    //登录返回状态
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_FAILED = 1;
	//导游排团返回状态
	public static final int DISPATCHGUIDE_SUCCESS = 0;
	public static final int DISPATCHGUIDE_FAILED = 1;
	
	private Api(){
		
	}
	
	public static Api getInstance(){
		return instance;
	}
	
	public void login(String username, String password, JsonHttpResponseHandler reponseHandler){
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);
		WebRestClient.post(API_LOGIN, params, reponseHandler);
	}
	
	public void logout(JsonHttpResponseHandler reponseHandler){
		WebRestClient.post(API_LOGOUT, null, reponseHandler);
		WebRestClient.getCookieStore().clear();
	}
	
	
	/**
	 * 获取团队信息
	 * @param pageIndex	获取第几页的导游信息、默认一页显示20条数据
	 * @param reponseHandler
	 */
	public void getTourGroupList(int pageIndex, JsonHttpResponseHandler reponseHandler){
		RequestParams params = new RequestParams();
		params.put("pageIndex", pageIndex + "");
		WebRestClient.post(API_GETTOURGROUPLIST, params, reponseHandler);
	}
	
	
	public void getGuideList(JsonHttpResponseHandler reponseHandler){
		WebRestClient.post(API_GETGUIDElIST, null, reponseHandler);
	}
	
	/**
	 * 提交排团信息
	 */
	public void submitDispatchInfo(int groupId, String guides, JsonHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.put("id", groupId+"");
		params.put("guides", guides);
		WebRestClient.post(API_DISPATCHGUIDE, params, responseHandler);
	}
	
	public void getUserInfo(JsonHttpResponseHandler responseHandler){
		WebRestClient.post(API_GETUSERINFO, null, responseHandler);
	}
	
	
}

