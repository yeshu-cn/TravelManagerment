/**
 * 
 */
package com.yeshu.app.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeshu.app.R;
import com.yeshu.app.ui.SideMenuItem;

/**
 * 
 * @author yeshu
 * @date 2013-9-7 
 *
 */

public class SideMenuAdapter extends BaseAdapter{
	private ArrayList<SideMenuItem> list;
	private LayoutInflater inflater;
	
	
	
	public SideMenuAdapter(ArrayList<SideMenuItem> list, Context context) {
		super();
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(null == convertView){
			convertView = inflater.inflate(R.layout.item_sidemenu, null);
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.sidemenu_name);
			viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.sidemenu_icon);
			convertView.setTag(viewHolder);
		}
		
		viewHolder = (ViewHolder) convertView.getTag();
		SideMenuItem item = (SideMenuItem) getItem(position);
		viewHolder.tvName.setText(item.getName());
		viewHolder.ivIcon.setBackgroundResource(item.getIcon());
		
		
		return convertView;
	}
	
	
	private class ViewHolder{
		TextView tvName;
		ImageView ivIcon;
	}

}

