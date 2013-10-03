package com.yeshu.app.net;
import org.json.*;

import com.loopj.android.http.*;

class WebRestClientUsage {
	public static final String API_LOGIN = "login.php";
	public static final String API_GET_GUIDE_INFO = "query_guide_info.php";
	public static final String API_UPDATE_GUIDE_INFO = "";
	public static final String API_DELETE_GUIDE = "";
	public static final String API_DISPATCH_GUIDE = "";
	
	
	
	
    public void getPublicTimeline() {
        WebRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                try {
                    JSONObject firstEvent = (JSONObject)timeline.get(0);
                    String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    System.out.println(tweetText);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    public void login(String account, String password){
    	WebRestClient.get(API_LOGIN, null, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(response);
			}
    		
    	});
    }
    
    
    
}