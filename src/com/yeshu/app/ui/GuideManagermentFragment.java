/**
 * 
 */
package com.yeshu.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.base.BaseFragment;
import com.yeshu.app.ui.bean.GuideInfo;
import com.yeshu.app.utils.LogUtil;

/**
 * 
 * @author yeshu
 * @date 2013-7-16
 * 
 */

public class GuideManagermentFragment extends BaseFragment {
	private final static String TAG = "GuideManagermentFragment";
	private ListView listview;
	private MyAdapter mAdapter;
	private PopupWindow popupWindow;
	private GuideInfo clickGuide;

	public static GuideManagermentFragment newInstance() {
		GuideManagermentFragment fragment = new GuideManagermentFragment();

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onCreateView");
		View view = inflater.inflate(R.layout.fragment_guidemanagerment, null);

		init(view);
		initData();

		return view;
	}
	
	
	/********生命周期测试--------------------------->********************/
	
	@Override
	public void onAttach(Activity activity) {
		LogUtil.i(TAG, "--->onAttach");
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onCreate");
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtil.i(TAG, "--->onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		LogUtil.i(TAG, "--->onStart");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		LogUtil.i(TAG, "--->onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogUtil.i(TAG, "--->onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogUtil.i(TAG, "--->onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		LogUtil.i(TAG, "--->onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		LogUtil.i(TAG, "--->onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		LogUtil.i(TAG, "--->onDetach");
		super.onDetach();
	}
	/*****<------------------------------------------********************/
	
	

	private void init(View view) {
		listview = (ListView) view.findViewById(R.id.gmanagerment_lv);

		mAdapter = new MyAdapter(getActivity());
		//为了让最后一个popupWindow显示全，在底部加个空白的view
		TextView footerView = new TextView(getActivity());
		footerView.setHeight(100);
		listview.addFooterView(footerView);
		listview.setAdapter(mAdapter);
	}

	private void initData() {
		// 获取导游列表
		Api.getInstance().getGuideList(new JsonHttpResponseHandler() {

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

	private ArrayList<GuideInfo> getGuideListFromResponse(JSONArray response)
			throws JSONException {
		if (null == response) {
			return null;
		}

		ArrayList<GuideInfo> list = new ArrayList<GuideInfo>();
		JSONObject jObject;
		for (int i = 0; i < response.length(); i++) {
			jObject = response.getJSONObject(i);
			int id = jObject.getInt("id");
			String name = jObject.getString("name");
			String phone = jObject.getString("phone");

			list.add(new GuideInfo(id, name, phone));
		}

		return list;
	}

	private class MyAdapter extends BaseAdapter {
		private Context ctx;
		private ArrayList<GuideInfo> guideList = new ArrayList<GuideInfo>();
		private LayoutInflater inflater;

		public void updateData(ArrayList<GuideInfo> _list) {
			if (null != _list) {
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

			if (null == contentView) {
				contentView = inflater.inflate(R.layout.item_guide, null);
				holder = new ViewHolder(contentView);
				contentView.setTag(holder);

			} else {
				holder = (ViewHolder) contentView.getTag();
			}

			final GuideInfo info = guideList.get(position);
			holder.tvName.setText(info.getName());
			holder.tvPhone.setText(info.getPhone());
			holder.ivEdit.setVisibility(View.GONE);

			contentView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showPopupMenu(v, info);
				}
			});
			return contentView;
		}

		private class ViewHolder {
			TextView tvName;
			TextView tvPhone;
			ImageView ivEdit;

			public ViewHolder(View contentView) {
				tvName = (TextView) contentView.findViewById(R.id.guide_name);
				tvPhone = (TextView) contentView.findViewById(R.id.guide_phone);
				ivEdit = (ImageView) contentView.findViewById(R.id.guide_edit);
			}
		}

	}

	private void initPopupWindow() {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.guidelist_popupmenu, null, false);

		Button btnCall = (Button) view.findViewById(R.id.popmenu_btn_call);
		Button btnEdit = (Button) view.findViewById(R.id.popmenu_btn_edit);
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				
				if(null != clickGuide){
					//打电话
					Uri uri = Uri.parse("tel:"+clickGuide.getPhone());
					Intent intent = new Intent(Intent.ACTION_DIAL, uri);
					startActivity(intent);
				}
			}
		});
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				
				if(null != clickGuide){
					Toast.makeText(getActivity(), "暂不开通编辑功能", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		popupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));// 点击窗口外消失
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);// 点击窗口外消失,需要设置背景、焦点、touchable、update
		popupWindow.update();
	}

	private void showPopupMenu(View view, GuideInfo info) {
		if (null == popupWindow) {
			initPopupWindow();
		}
		
		if(popupWindow.isShowing()){
			popupWindow.dismiss();
		}
		
		clickGuide = info;
		popupWindow.showAsDropDown(view, 0, 0);
	}

}
