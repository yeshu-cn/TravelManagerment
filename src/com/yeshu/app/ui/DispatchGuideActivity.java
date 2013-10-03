/**
 * 
 */
package com.yeshu.app.ui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yeshu.app.R;
import com.yeshu.app.ui.base.BaseActivity;
import com.yeshu.app.ui.bean.GuideInfo;

/**
 * 
 * @author yeshu
 * @date 2013-7-18 
 *
 */

public class DispatchGuideActivity extends BaseActivity{
	private GridView gridView;
	private MyAdapter mAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispatch);
		
		init();
		initData();
	}
	
	private void init(){
		gridView = (GridView)findViewById(R.id.dispatchac_gridview);
		mAdapter = new MyAdapter(this);
		gridView.setAdapter(mAdapter);
	}
	
	private void initData(){

	}
	
	private class MyAdapter extends BaseAdapter{
    	private Context ctx;
    	private ArrayList<GuideInfo> tourgroupList = new ArrayList<GuideInfo>();
    	private LayoutInflater inflater;
    	
    	public void updateData(ArrayList<GuideInfo> _list){
    		if(null != _list){
    			this.tourgroupList.addAll(_list);
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
			return tourgroupList.size();
		}

		@Override
		public Object getItem(int position) {
			return tourgroupList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			ViewHolder holder;
			
			if(null == contentView){
				contentView = inflater.inflate(R.layout.gridview_item_guide, null);
				holder = new ViewHolder(contentView);
				contentView.setTag(holder);
				
			}else{
				holder = (ViewHolder) contentView.getTag();
			}
			
			
			GuideInfo info = tourgroupList.get(position);
			holder.tvName.setText(info.getName());
			
			return contentView;
		}
		
		
		private class ViewHolder{
			TextView tvName;
			
			
			public ViewHolder(View contentView) {
				tvName = (TextView)contentView.findViewById(R.id.gv_guide_name);
			}
			
		}
		
		
    }

}

