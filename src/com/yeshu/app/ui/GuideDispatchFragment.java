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
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yeshu.app.R;
import com.yeshu.app.net.Api;
import com.yeshu.app.ui.base.BaseFragment;
import com.yeshu.app.ui.bean.TourGroupInfo;
import com.yeshu.app.utils.LogUtil;

/**
 * 
 * @author yeshu
 * @date 2013-7-16
 * 
 */

public class GuideDispatchFragment extends BaseFragment {
	private final static String TAG = "GuideDispatchFragment";
	private ListView listview;
	private MyAdapter mAdapter;
	private ArrayList<TourGroupInfo> tourGroupDatas;
	private PullToRefreshListView mPullToRefreshListView;

	private RelativeLayout loadingView;
	private TextView loadingViewLable;
	private ImageView loadingViewIc;
	
	private final int FIRST_PAGE = 1;//默认显示第一页的数据
	private int CURRENT_PAGE = 1;	//当前加载到第几页的数据

	public static GuideDispatchFragment newInstance() {
		GuideDispatchFragment fragment = new GuideDispatchFragment();

		return fragment;
	}

	/*****************test fragment lifecycle*********---------------------->****/
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		LogUtil.i(TAG, "--->onCreateView");
		
		View view = inflater.inflate(R.layout.fragment_guidedispatch, null);

		init(view);
		initData();		
		return view;
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

	/******<-------------------**********/
	
	
	



	private void init(View view) {
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.gdispatch_lv);
		listview = mPullToRefreshListView.getRefreshableView();
		loadingView = (RelativeLayout) view.findViewById(R.id.ll_loadingview);
		loadingViewLable = (TextView) view.findViewById(R.id.loadingview_lable);
		loadingViewIc = (ImageView) view.findViewById(R.id.loadingview_ic);

		mAdapter = new MyAdapter(getActivity());
		listview.setAdapter(mAdapter);

		mPullToRefreshListView.setMode(Mode.BOTH);

		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				//向下拉的时候刷新数据
				refreshLatestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				//向上拉的时候加载下一页的数据
				loadNextPageData();
			}
		});

		((MainActivity) getActivity())
				.setOnActivityResultListener(new OnActivityResultListener() {

					@Override
					public void onActivityResult(int requestCode,
							int resultCode, Intent data) {
						if (null == data) {
							return;
						}

						Bundle bundle = data.getExtras();
						if (null != bundle) {
							TourGroupInfo info = (TourGroupInfo) bundle
									.getSerializable("tourgroup");
							updateTourGroupsInfo(info);
						}
					}
				});

	}

	private void initData() {
		if(null == tourGroupDatas){
			//加载数据
			loadData();
		}else{
			//直接显示
			mAdapter.addData(tourGroupDatas);
		}
	}
	
	/**
	 * 重新获取刷新显示最新的数据
	 */
	private void refreshLatestData(){
		CURRENT_PAGE = FIRST_PAGE;
		Api.getInstance().getTourGroupList(FIRST_PAGE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray response) {
				try {
					tourGroupDatas = getTourGroupsFromResponse(response);
					mAdapter.refreshData(tourGroupDatas);
					
					//关闭刷新的动画
					mPullToRefreshListView.onRefreshComplete();
					Toast.makeText(getActivity(), getString(R.string.dispatch_refreshdata_over), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 获取第一页的数据
	 */
	private void loadData(){
		showLoadingUI();
		
		Api.getInstance().getTourGroupList(CURRENT_PAGE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray response) {
				hideLoadingUI();
				// LogUtil.i("yeshu", "-->" + response.toString());
				try {
					tourGroupDatas = getTourGroupsFromResponse(response);
					mAdapter.addData(tourGroupDatas);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				hideLoadingUI();
				Toast.makeText(getActivity(), getString(R.string.dispatch_getdata_failed), Toast.LENGTH_SHORT).show();
			}
			
			
		});
	}
	
	/**
	 * 加载显示下一页的数据
	 */
	private void loadNextPageData(){
		CURRENT_PAGE++;
		Api.getInstance().getTourGroupList(CURRENT_PAGE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray response) {
				try {
					tourGroupDatas = getTourGroupsFromResponse(response);
					if(tourGroupDatas.isEmpty()){
						//获取的下一页数据为空，已是最后的数据，所以得把current_page回复-1；
						CURRENT_PAGE--;
						Toast.makeText(getActivity(), getString(R.string.dispatch_dataisover), Toast.LENGTH_SHORT).show();
					}else{
						mAdapter.addData(tourGroupDatas);
					}
										
					//关闭加载动画
					mPullToRefreshListView.onRefreshComplete();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ArrayList<TourGroupInfo> getTourGroupsFromResponse(
			JSONArray response) throws JSONException {
		if (null == response) {
			return null;
		}

		ArrayList<TourGroupInfo> list = new ArrayList<TourGroupInfo>();

		for (int i = 0; i < response.length(); i++) {
			JSONObject jObject = response.getJSONObject(i);
			int id = jObject.getInt("id");
			String members = jObject.getString("members");
			int shop = jObject.getInt("shop");
			String senddate = jObject.getString("senddate");
			String recvdate = jObject.getString("recvdate");
			String routetitle = jObject.getString("routetitle");
			String routedays = jObject.getString("routedays");
			String route = jObject.getString("route");
			String offices = jObject.getString("offices");
			String type = jObject.getString("type");
			String remark = jObject.getString("remark");
			String jidiao = jObject.getString("jidiao");
			String guides = jObject.getString("guides");
			int isShop = jObject.getInt("isshop");

			list.add(new TourGroupInfo(id, members, shop, isShop, senddate,
					recvdate, routetitle, routedays, route, offices, type,
					remark, jidiao, guides));
		}

		return list;
	}

	private void showLoadingUI() {
		loadingViewIc.setImageResource(R.anim.anim_loadingview);
		AnimationDrawable animationDrawable = (AnimationDrawable) loadingViewIc
				.getDrawable();
		animationDrawable.start();
		loadingViewLable.setText(getString(R.string.dispatch_loading_data));
		loadingView.setVisibility(View.VISIBLE);
		listview.setVisibility(View.GONE);
	}

	private void hideLoadingUI() {
		loadingView.setVisibility(View.GONE);
		listview.setVisibility(View.VISIBLE);
	}

	private void refresh() {

	}

	private class MyAdapter extends BaseAdapter {
		private Context ctx;
		private ArrayList<TourGroupInfo> tourgroupList = new ArrayList<TourGroupInfo>();
		private LayoutInflater inflater;

		/**
		 * 添加显示数据
		 * @param _list
		 */
		public void addData(ArrayList<TourGroupInfo> _list) {
			if (null != _list) {
				this.tourgroupList.addAll(_list);
				this.notifyDataSetChanged();
			}
		}
		
		/**
		 * 重新加载刷新显示数据
		 * @param _list
		 */
		public void refreshData(ArrayList<TourGroupInfo> _list) {
			if (null != _list) {
				this.tourgroupList.clear();
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

			if (null == contentView) {
				contentView = inflater.inflate(R.layout.item_tourgroup, null);
				holder = new ViewHolder(contentView);
				contentView.setTag(holder);

			} else {
				holder = (ViewHolder) contentView.getTag();
			}

			final TourGroupInfo info = tourgroupList.get(position);
			holder.tvGuidename.setText(info.getGuides());
			holder.tvIndex.setText(info.getId() + "");
			holder.tvIsshop.setText(info.getShop() + "");
			holder.tvLeavedate.setTag(info.getSenddate());
			holder.tvLine.setText(info.getRoute());
			holder.tvNumbers.setText(info.getMembers());
			holder.tvRecvdate.setText(info.getRecvdate());
			holder.tvSumbiter.setText(info.getOffices());
			holder.tvType.setText(info.getType());

			contentView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent it = new Intent(ctx, SelectGuideActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("tourgroup", info);
					it.putExtras(bundle);
					((FragmentActivity) ctx).startActivityForResult(it, 0);
				}
			});

			return contentView;
		}

		private class ViewHolder {
			TextView tvIndex;
			TextView tvType;
			TextView tvLine;
			TextView tvSumbiter;
			TextView tvNumbers;
			TextView tvRecvdate;
			TextView tvLeavedate;
			TextView tvGuidename;
			TextView tvIsshop;

			public ViewHolder(View contentView) {
				tvIndex = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_index);
				tvType = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_type);
				tvLine = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_line);
				tvSumbiter = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_sumbiter);
				tvNumbers = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_number);
				tvRecvdate = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_recvdate);
				tvLeavedate = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_leavedate);
				tvGuidename = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_guides);
				tvIsshop = (TextView) contentView
						.findViewById(R.id.tourgroup_tv_isshop);
			}
		}

	}

	/*************** card�����Ѱ��ŵĵ��ε������� ******************/

	private void updateTourGroupsInfo(TourGroupInfo info) {
		TourGroupInfo tourGroupInfo;
		for (int i = 0; i < tourGroupDatas.size(); i++) {
			tourGroupInfo = tourGroupDatas.get(i);
			if (info.getId() == tourGroupInfo.getId()) {
				tourGroupInfo.setGuides(info.getGuides());
				mAdapter.notifyDataSetChanged();
				return;
			}
		}
	}

	// @Override
	// public void onPrepareOptionsMenu(Menu menu) {
	// Toast.makeText(getActivity(), " xx"+menu.getItem(0).getTitle(),
	// Toast.LENGTH_SHORT).show();
	// super.onPrepareOptionsMenu(menu);
	// }
	//
	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	// menu.add("refresh2")
	// .setIcon(R.drawable.actionbar_refresh)
	// .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
	// MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	//
	// super.onCreateOptionsMenu(menu, inflater);
	// }

}
