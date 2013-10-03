package com.yeshu.app.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.yeshu.app.R;
import com.yeshu.app.ui.adapter.SideMenuAdapter;
import com.yeshu.app.ui.base.BaseSherlockFragmentActivity;

public class MainActivity extends BaseSherlockFragmentActivity {
	private long m_exitTime = 0;
	private OnActivityResultListener listener;
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
		
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSideMenu();
        showGuideDepartment();
    }  
    


    
    class TabsAdapter extends FragmentStatePagerAdapter  implements IconPagerAdapter{
    	private ArrayList<TabFragment> mTabs = new ArrayList<TabFragment>();
    	
    	public void addTab(TabFragment f){
    		mTabs.add(f);
    	}
    	
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabs.get(position).getTabTitle();
		}


		public TabsAdapter(FragmentManager fm) {
			super(fm);
		}


		@Override
		public Fragment getItem(int arg0) {
			return mTabs.get(arg0).getFragment();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public int getIconResId(int index) {
			if(0 == index){
				return R.drawable.icon_dispatch;
			}else if(1 == index){
				return R.drawable.icon_user;
			}else{
				return R.drawable.icon_adduser;
			}
		}
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(null != listener){
			listener.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
    public void setOnActivityResultListener(OnActivityResultListener _listener){
    	this.listener = _listener;
    }
    
    
    @Override 
	public boolean dispatchKeyEvent(KeyEvent event) { 
		int keyCode = event.getKeyCode();
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if((System.currentTimeMillis() - m_exitTime) > 2000) { //System.currentTimeMillis()无论何时调用，肯定大于2000 
             Toast.makeText(this, "再按一次退出程序",Toast.LENGTH_SHORT).show();                                  
             m_exitTime = System.currentTimeMillis();  
            } else {  
                finish();  
                System.exit(0);  
            }  
            return true;  
        }
	    return super.dispatchKeyEvent(event); 
	} 
    

    private void initSideMenu(){
    	ArrayList<SideMenuItem> sideMenus = new ArrayList<SideMenuItem>();
    	sideMenus.add(new SideMenuItem("导游部", R.drawable.ic_menu_guidedepartment));
    	sideMenus.add(new SideMenuItem("个人中心", R.drawable.ic_menu_personal));
    	sideMenus.add(new SideMenuItem("设置", R.drawable.ic_menu_setting));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Add header news title
        ViewGroup header_news = (ViewGroup)LayoutInflater.from(this).inflate(R.layout.sidemenu_header, mDrawerList, false);
        mDrawerList.addHeaderView(header_news, null, false);
        
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(new SideMenuAdapter(sideMenus, this));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        
        
        mTitle = mDrawerTitle = getTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            	getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    
        
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            mDrawerLayout.closeDrawer(mDrawerList);
            SideMenuItem item = (SideMenuItem) mDrawerList.getItemAtPosition(position);
        	setTitle(item.getName());
        	if(1 == position){
        		showGuideDepartment();
        	}else if(2 == position){
        		showPersonalCenter();
        	}else if(3 == position){
        		showSetting();
        	}
        }
    }
    

    private void showGuideDepartment(){
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addTab(new TabFragment(new GuideDispatchFragment(), "导游排团"));
        adapter.addTab(new TabFragment(new GuideManagermentFragment(), "导游管理"));
        adapter.addTab(new TabFragment(new AddGuideFragment(), "增加导游"));
          
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setVisibility(View.VISIBLE);
        //调用它tab标签栏才会刷新UI
        indicator.notifyDataSetChanged();
        indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(2 == arg0){
					hideRefreshMenu();
				}else{
					showRefreshMenu();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    private void showPersonalCenter(){
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addTab(new TabFragment(new PersonalCenterFargment(), "个人中心"));
          
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setVisibility(View.GONE);
        
        //调用它tab标签栏才会刷新UI
        indicator.notifyDataSetChanged();
    }
    
    private void showSetting(){
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addTab(new TabFragment(new SettingFragment(), "设置"));
          
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setVisibility(View.GONE);
        
        
        //调用它tab标签栏才会刷新UI
        indicator.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("refresh")
//            .setIcon(R.drawable.actionbar_refresh)
//            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    	getSupportMenuInflater().inflate(R.menu.menu_mainac, menu);
        return true;
    }
    
    
    private boolean showRefresh = true;
    public void showRefreshMenu(){
    	showRefresh = true;
    	invalidateOptionsMenu();
    }
    
    public void hideRefreshMenu(){
    	showRefresh = false;
    	invalidateOptionsMenu();
    }
    

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem refreshMenu = menu.findItem(R.id.menu_refresh);
		
		//这里用不到actionbar上的menu，就把你们都隐藏了吧。不想删掉
		//refreshMenu.setVisible(showRefresh);
		refreshMenu.setVisible(false);
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	/**
    	 * 好像是abs这里有bug，不能自动处理sidemenu的展开收缩，所以这里就自己实现
    	 */
    	if (item.getItemId() == android.R.id.home) {

	    	if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	    		mDrawerLayout.closeDrawer(mDrawerList);
	    	} else {
	    	mDrawerLayout.openDrawer(mDrawerList);
	    		}
    	}
    	
    	if(item.getTitle().equals("refresh")){
    		Toast.makeText(getApplicationContext(), "11111", Toast.LENGTH_SHORT).show();
    	}
    	
    	return super.onOptionsItemSelected(item);
   }

    
    
}
