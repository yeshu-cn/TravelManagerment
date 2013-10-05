/**
 * 
 */
package com.yeshu.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.base.BaseActivity;
import com.yeshu.app.ui.bean.GuideInfo;
import com.yeshu.app.ui.bean.TourGroupInfo;
import com.yeshu.app.utils.LogUtil;

/**
 * 
 * @author yeshu
 * @date 2013-7-20 
 *
 */

public class SelectGuideActivity extends BaseActivity{
	private TextView tvSelectedGuides;
	private ListView lvGuides;
	private MyAdapter mAdapter;
	private Button btnCancel;
	private Button btnOk;
	private String guides = "";	//已选择的导游
	
	private ArrayList<GuideInfo> selectedGuides = new ArrayList<GuideInfo>();
	private TourGroupInfo groupInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_selectguide);
		
		Bundle bundle = getIntent().getExtras();
		groupInfo = (TourGroupInfo) bundle.getSerializable("tourgroup");
		
		init();
		initData();
	}

	private void init(){
		lvGuides = (ListView)findViewById(R.id.selectguideac_list);
		tvSelectedGuides = (TextView)findViewById(R.id.selectguideac_tv_selectedguides);
		btnCancel = (Button)findViewById(R.id.selectguideac_btn_cancel);
		btnOk = (Button)findViewById(R.id.selectguideac_btn_ok);
		
		
		mAdapter = new MyAdapter(this);
		lvGuides.setAdapter(mAdapter);
		
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SelectGuideActivity.this.finish();
			}
		});
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				for(GuideInfo guide : selectedGuides){
					guides = guides + guide.getNickname() + " ";
				}
				
				AlertDialog.Builder builder  = new AlertDialog.Builder(SelectGuideActivity.this);
				builder.setTitle(getString(R.string.selectguide_selectedguides));
				builder.setMessage(guides);
				builder.setPositiveButton(getString(R.string.selectguide_ok), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						submitDispatchGuideInfo();
					}
				});
				
				builder.setNegativeButton(getString(R.string.selectguide_cancel), new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SelectGuideActivity.this.finish();
					}
				});
				
				builder.create().show();

			}
		});
	}
	
	private void initData(){
    	//get guide list data
    	Api.getInstance().getGuideList(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray response) {
				LogUtil.i("yeshu", "-->" + response.toString());
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
	
	private class MyAdapter extends BaseAdapter{
    	private Context ctx;
    	private ArrayList<GuideInfo> guideList = new ArrayList<GuideInfo>();
    	private LayoutInflater inflater;
    	
    	public void updateData(ArrayList<GuideInfo> _list){
    		if(null != _list){
    			this.guideList.addAll(_list);
    			this.notifyDataSetChanged();
    		}
    	}
    	
		public MyAdapter(Context ctx) {
			super();
			this.ctx = ctx;
			inflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return guideList.size();
		}

		@Override
		public Object getItem(int position) {
			return guideList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			ViewHolder holder;
			
			if(null == contentView){
				contentView = inflater.inflate(R.layout.selectguideac_lvitem_guide, null);
				holder = new ViewHolder(contentView);
				contentView.setTag(holder);
				
			}else{
				holder = (ViewHolder) contentView.getTag();
			}
			
			
			final GuideInfo info = guideList.get(position);
			holder.tvName.setText(info.getNickname());
			
			
			holder.cbSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton btn, boolean check) {
					if(check){
						selectedGuides.add(info);
					}else{
						selectedGuides.remove(info);
					}

				}
			});
			
			return contentView;
		}
		
		private class ViewHolder{
			TextView tvName;
			CheckBox cbSelect;
			
			public ViewHolder(View contentView) {
				tvName = (TextView)contentView.findViewById(R.id.selectedguide_item_guidename);
				cbSelect = (CheckBox)contentView.findViewById(R.id.selectguide_item_cb);
			}
		}
		
		
    }
	
	

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	/**
	 * 确认提交、排团导游
	 */
	private void submitDispatchGuideInfo(){
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
						Toast.makeText(SelectGuideActivity.this, getString(R.string.selectguide_dispatch_success), Toast.LENGTH_SHORT).show();
						groupInfo.setGuides(guides);
						Intent data = new Intent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("tourgroup", groupInfo);
						data.putExtras(bundle);
						setResult(0, data);
						SelectGuideActivity.this.finish();
						
					}else{
						//dispatch guide failed
						Toast.makeText(SelectGuideActivity.this, getString(R.string.selectguide_dispatch_failed), Toast.LENGTH_SHORT).show();
						SelectGuideActivity.this.finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
	
	
	
	
	
}

