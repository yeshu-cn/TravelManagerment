package com.yeshu.app.net;
// Static wrapper library around AsyncHttpClient

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class WebRestClient {
    private static final String BASE_URL = "http://192.168.1.102/sae/1/Index.php/Admin/API/";
//	private static final String BASE_URL = "http://jxgzl.sinaapp.com/index.php/Admin/API/";
    
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    private static PersistentCookieStore myCookieStore;
    
    private static WebRestClient instance = new WebRestClient(); 
    private WebRestClient(){
    	
    }
    
    public static WebRestClient getInstance(){
    	return instance;
    }

    public static AsyncHttpClient getClient(){
    	return client;
    }
    
    public static PersistentCookieStore getCookieStore(){
    	return myCookieStore;
    }
    
    /**
     * ��ʼ�����ô洢cookie
     * @param ctx
     */
    public static void init(Context ctx){
    	myCookieStore = new PersistentCookieStore(ctx);
    	client.setCookieStore(myCookieStore);
    }
    
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    
    
}