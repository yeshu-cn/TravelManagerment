package com.yeshu.app.ui.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.manuelpeinado.multichoiceadapter.MultiChoiceBaseAdapter;
import com.yeshu.app.R;
import com.yeshu.app.ui.ChoiceGuideActivity;
import com.yeshu.app.ui.bean.GuideInfo;

public class ChoiceGuideAdapter extends MultiChoiceBaseAdapter{
	private ArrayList<GuideInfo> guideList = new ArrayList<GuideInfo>();
	private LayoutInflater inflater;
	private Context ctx;
	
	public ChoiceGuideAdapter(Bundle savedInstanceState, Context context) {
		super(savedInstanceState);
		inflater = LayoutInflater.from(context);
		this.ctx = context;
	}



	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.action_choiceguide, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		if (item.getItemId() == R.id.menu_cancel) {
			discardSelectedItems();
			return true;
		}
		
		if(item.getItemId() == R.id.menu_ok){
			//选择了确认，则弹出确认信息，提交排团的请求
			String guides = "";
			for(Long position : this.getCheckedItems()){
				guides = guides + guideList.get(position.intValue()).getNickname() + " ";
			}
			
			showConfirmInfoDialog(guides);
			return true;
		}
	
		return false;
	}
	
	private void showConfirmInfoDialog(final String guides){
		AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
		builder.setTitle(getContext().getResources().getString(R.string.selectguide_selectedguides));
		builder.setMessage(guides);
		builder.setPositiveButton(getContext().getResources().getString(R.string.selectguide_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//提交排团请求
				((ChoiceGuideActivity)ctx).submitDispatchGuideInfo(guides);
			}
		});
		
		builder.setNegativeButton(getContext().getResources().getString(R.string.selectguide_cancel), new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//取消
			}
		});
		
		builder.create().show();
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
	protected View getViewImpl(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;	
		if(null == convertView){
			convertView = inflater.inflate(R.layout.listitem_choiceguide, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		final GuideInfo info = guideList.get(position);
		holder.tvName.setText(info.getNickname());
			
		return convertView;
	}

	
	private class ViewHolder{
		TextView tvName;
		
		public ViewHolder(View contentView) {
			tvName = (TextView)contentView.findViewById(R.id.tvGuideName);
		}
	}
	
	private void discardSelectedItems() {
		// http://stackoverflow.com/a/4950905/244576
		List<Long> positions = new ArrayList<Long>(getCheckedItemCount());
		Collections.sort(positions, Collections.reverseOrder());
		for (long position : positions) {
			guideList.remove((int) position);
		}
		finishActionMode();
	}


	public void updateData(ArrayList<GuideInfo> list) {
		if(null != list){
			this.guideList = list;
			this.notifyDataSetChanged();
		}
	}
	
	
	
}
