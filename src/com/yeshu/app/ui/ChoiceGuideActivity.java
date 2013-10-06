package com.yeshu.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.adapter.ChoiceGuideAdapter;
import com.yeshu.app.ui.base.BaseSherlockActivity;
import com.yeshu.app.ui.bean.GuideInfo;
import com.yeshu.app.ui.bean.TourGroupInfo;

public class ChoiceGuideActivity extends BaseSherlockActivity implements
		OnItemClickListener {
	
	private ListView mListView;	
	private ChoiceGuideAdapter mAdapter;
	private TourGroupInfo groupInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		groupInfo = (TourGroupInfo) bundle.getSerializable("tourgroup");
		
		setContentView(R.layout.activity_choiceguide);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mListView = (ListView)findViewById(R.id.choiceguideac_listview);
		mAdapter = new ChoiceGuideAdapter(savedInstanceState, this);
		mAdapter.setAdapterView(mListView);
		mAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//				Toast.makeText(ChoiceGuideActivity.this, "Item click: " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
			}
		});
		
		
		initData();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mAdapter.save(outState);
	}

	private void initData(){
		//get guide list data
    	Api.getInstance().getGuideList(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray response) {
				try {
					ArrayList<GuideInfo> guideList = getGuideListFromResponse(response);
					mAdapter.updateData(guideList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
    	});
	}
	
	private ArrayList<GuideInfo> getGuideListFromResponse(JSONArray response) throws JSONException{
    	if(null == response){
    		return null;
    	}
    	
    	ArrayList<GuideInfo> list = new ArrayList<GuideInfo>();
    	JSONObject jObject;
    	for(int i=0; i<response.length(); i++){
    		jObject = response.getJSONObject(i);
    		int id = jObject.getInt("id");
    		String name = jObject.getString("name");
    		String phone = jObject.getString("phone");
    		String nickname = jObject.getString("nickname");
    		
    		list.add(new GuideInfo(id, name, phone, nickname));
    	}
	
    	return list;
    }
	
	
	@Override
	public void onItemClick(android.widget.AdapterView<?> adapterView,
			View view, int position, long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
        	break;
        }
        return false;
    }
	
	/**
	 * 确认提交、排团导游
	 */
	public void submitDispatchGuideInfo(final String guides){
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.show();
		
		Api.getInstance().submitDispatchInfo(groupInfo.getId(), guides, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				dialog.dismiss();
				
				int result;
				try {
					result = response.getInt("result");
					if(Api.DISPATCHGUIDE_SUCCESS == result){
						//dispatch guide success
						Toast.makeText(ChoiceGuideActivity.this, getString(R.string.selectguide_dispatch_success), Toast.LENGTH_SHORT).show();
						groupInfo.setGuides(guides);
						Intent data = new Intent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("tourgroup", groupInfo);
						data.putExtras(bundle);
						setResult(0, data);
						ChoiceGuideActivity.this.finish();
						
					}else{
						//dispatch guide failed
						Toast.makeText(ChoiceGuideActivity.this, getString(R.string.selectguide_dispatch_failed), Toast.LENGTH_SHORT).show();
						ChoiceGuideActivity.this.finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}

}
