package com.xaut.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.slidingmenu.lib.SlidingMenu;
import com.xaut.sensor_v1.MainActivity;
import com.xaut.sensor_v1.R;
import com.xaut.sensor_v1.ViewpagerFragmentAdapter;

public class MenuFragment extends PreferenceFragment implements OnPreferenceClickListener{
	
    private int index = -1;
    private ViewPager mViewPager = null;
//    private FrameLayout mFrameLayout = null;
    private MainActivity   mActivity = null;
    
    private SensorManager sensor = null;
	private List<Sensor> list = null;
    public static List<String> tabs = new ArrayList<String>();
    private int flag = 2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //控制页面重建，和Fragment生命周期相关
        setRetainInstance(true);
        
        mActivity = (MainActivity)getActivity();
        
        mViewPager = (ViewPager)mActivity.findViewById(R.id.viewpager);
        
//        mFrameLayout = (FrameLayout)mActivity.findViewById(R.id.content);
        
        //添加preference布局文件
        addPreferencesFromResource(R.xml.menu);
        
        
    	//从系统服务中获取传感器管理器
		sensor = (SensorManager)mActivity.getSystemService(Context.SENSOR_SERVICE);
		
		//从传感器管理器中获取所有传感器的列表
		list = sensor.getSensorList(Sensor.TYPE_ALL);
        
        PreferenceScreen ps = (PreferenceScreen) getPreferenceManager().findPreference("preferencescreen");
        
        tabs.add("Battery");
        
        //绑定监听器
        findPreference("0").setOnPreferenceClickListener(this);
                
        tabs.add("Location");
        
        findPreference("1").setOnPreferenceClickListener(this);
       
		for(Sensor sm :list){
			
			 switch (sm.getType()){
			 
			 case Sensor.TYPE_ACCELEROMETER :{
				 
				 if(!tabs.contains("Accelerometer")){
					 
					 Preference preference = new Preference(mActivity);
				        
				     preference.setTitle("Accelerometer");
				        
				     preference.setKey("" + flag);
				     
				     preference.setIcon(R.drawable.sensor);
				        
				     ps.addItemFromInflater(preference);
				        
				     findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				     tabs.add("Accelerometer");
				     
				     flag++;
				     
					 
				 }
				 	
				 break;
			 }
			 case Sensor.TYPE_GRAVITY :{
				 
				 if(!tabs.contains("Gravity")){
					 
					  Preference preference1 = new Preference(mActivity);
				        
				      preference1.setTitle("Gravity");
				        
				      preference1.setKey("" + flag);
				      
				      preference1.setIcon(R.drawable.sensor);
				       
				      ps.addItemFromInflater(preference1);
				        
				      findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				      tabs.add("Gravity");
		
				      flag++;
				 }
					
				 break;
			 }
			 case Sensor.TYPE_ROTATION_VECTOR :{
				 
				 if(!tabs.contains("Rotation")){
					 
					 Preference preference2 = new Preference(mActivity);
				        
				     preference2.setTitle("Rotation");
				        
				     preference2.setKey("" + flag);
				     
				     preference2.setIcon(R.drawable.sensor);
				        
				     ps.addItemFromInflater(preference2);
				      
				     findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				     tabs.add("Rotation");
				     
				     flag++;
				 }    
					
				 break;
			 }
			 case Sensor.TYPE_LINEAR_ACCELERATION :{
				 
				 if(!tabs.contains("Linear Acceleration")){
					 
					    Preference preference3 = new Preference(mActivity);
				        
				        preference3.setTitle("Linear Acceleration");
				        
				        preference3.setKey("" + flag);
				        
				        preference3.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference3);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Linear Acceleration");	
				        
				        flag++;
				 }
				 
				
				 break;
			 }
			 case Sensor.TYPE_GYROSCOPE :{
				 
				 if(!tabs.contains("Gyroscope")){
					 
					    Preference preference4 = new Preference(mActivity);
				        
				        preference4.setTitle("Gyroscope");
				        
				        preference4.setKey("" + flag);
				        
				        preference4.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference4);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Gyroscope");		
					 
				        flag++;
				 }
				 
				
				 break;
			 }
			 case Sensor.TYPE_LIGHT :{
				 
				 if(!tabs.contains("Light")){
					 
					    Preference preference5 = new Preference(mActivity);
				        
				        preference5.setTitle("Light");
				        
				        preference5.setKey("" + flag);
				        
				        preference5.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference5);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Light");		
					 
				        flag++;
				 }
				 
			
				 break;
			 }
			 case Sensor.TYPE_MAGNETIC_FIELD :{
				 
				 if(!tabs.contains("Magnetic field")){
					 
					    Preference preference6 = new Preference(mActivity);
				        
				        preference6.setTitle("Magnetic field");
				        
				        preference6.setKey("" + flag);
				        
				        preference6.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference6);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Magnetic field");	
					 
				        flag++;
				 }
				 
		
				 break;
			 }
			 case Sensor.TYPE_PRESSURE :{
				 
				 if(!tabs.contains("Pressure")){
					 
					    Preference preference7 = new Preference(mActivity);
				        
				        preference7.setTitle("Pressure");
				        
				        preference7.setKey("" + flag);
				        
				        preference7.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference7);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Pressure");		
					 
				        flag++;
				 }
				 
				 
				 break;
			 }
			 case Sensor.TYPE_PROXIMITY :{
				 
				 if(!tabs.contains("Proximity")){
					 
					    Preference preference8 = new Preference(mActivity);
				        
				        preference8.setTitle("Proximity");
				        
				        preference8.setKey("" + flag);
				        
				        preference8.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference8);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Proximity");		
					 
				        flag++;
				 }
				 
				
				 break;
			 }
			 case Sensor.TYPE_AMBIENT_TEMPERATURE :{
				 
				 if(!tabs.contains("Temperature")){
					 
					    Preference preference9 = new Preference(mActivity);
				        
				        preference9.setTitle("Temperature");
				        
				        preference9.setKey("" + flag);
				        
				        preference9.setIcon(R.drawable.sensor);
				        
				        ps.addItemFromInflater(preference9);
				        
				        findPreference("" + flag).setOnPreferenceClickListener(this);
				        
				        tabs.add("Temperature");
					 
				        flag++;
				 }
				 
				 
				 break;
			 }
			 default :{
				 
					break;
				}
			 }
			 
		
		}
           
        
      //显示ViewPage
      mViewPager.setVisibility(View.VISIBLE);
      
      ViewpagerFragmentAdapter viewpagerFragmentAdapter = new ViewpagerFragmentAdapter(mActivity.getFragmentManager());
      
      mViewPager.setOffscreenPageLimit(5);
      
      mViewPager.setAdapter(viewpagerFragmentAdapter);
      
      mViewPager.setOnPageChangeListener(onPageChangeListener);
      
      //设置导航栏模式--Tabs
      ActionBar actionBar = mActivity.getActionBar();
      
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      
      //移除原来的Tabs，新加Tabs
      actionBar.removeAllTabs();
      
      for(String s : tabs){
    	  
    	  actionBar.addTab(actionBar.newTab()
                  .setText(s)
                  .setTabListener(tabListener));
    	  
      }
      
    }
    

    //处理点击事件
    @Override
    public boolean onPreferenceClick(Preference preference) {
    	
        String key = preference.getKey();
        
        int keytemp = Integer.parseInt(key);
        
        switch(keytemp){
        
        case 0:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 1:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        } 
        case 2:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 3:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 4:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 5:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 6:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 7:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 8:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 9:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 10:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        case 11:{
        	
        	ShowFragment(keytemp);
        	
        	break;
        }
        default :{
        	
			break;
		}
        
        }
        
//        if("1".equals(key)) {
//        	
//            //如果目前处于点击要显示的页面，则保持该页面不变(if the content view is that we need to show . show directly)
//            if(index == 0) {
//            	
//            	//动态判断自动关闭或开启SlidingMenu 
//                ((MainActivity)getActivity()).getSlidingMenu().toggle();
//                
//                return true;
//            }
//            
//            //如果不是将要显示的页面，则替换当前页面(otherwise , replace the content view via a new Content fragment)
//            index = 0;
//            
//            //显示ContentFragment
//            mFrameLayout.setVisibility(View.VISIBLE);
//            
//            //完全隐藏ViewPager
//            mViewPager.setVisibility(View.GONE);
//            
//            //设置导航栏模式--标准
//            getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//            
//            //显示新的ContentFragment
//            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
//            
//            ContentFragment contentFragment = (ContentFragment)fragmentManager.findFragmentByTag("A");
//            
//            fragmentManager.beginTransaction()
//            .replace(R.id.content, contentFragment == null ? new ContentFragment("Menu:Fragment#First"):contentFragment ,"A")
//            .commit();
//            
//        }else if("2".equals(key)) {
//        	
//            if(index == 1) {
//            	
//                ((MainActivity)getActivity()).getSlidingMenu().toggle();
//                
//                 return true;
//            }
//            
//            index = 1;
//            
//            //完全隐藏ContentFragment
//            mFrameLayout.setVisibility(View.GONE);
//            
//            //显示ViewPager
//            mViewPager.setVisibility(View.VISIBLE);
//            
//            ViewpagerFragmentAdapter viewpagerFragmentAdapter = new ViewpagerFragmentAdapter(mActivity.getFragmentManager());
//            
//            mViewPager.setOffscreenPageLimit(5);
//            
//            mViewPager.setAdapter(viewpagerFragmentAdapter);
//            
//            mViewPager.setOnPageChangeListener(onPageChangeListener);
//            
//            //设置导航栏模式--Tabs
//            ActionBar actionBar = mActivity.getActionBar();
//            
//            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//            
//            //移除原来的Tabs，新加Tabs
//            actionBar.removeAllTabs();
//            
//            actionBar.addTab(actionBar.newTab()
//                    .setText("First Tab")
//                    .setTabListener(tabListener));
//
//            actionBar.addTab(actionBar.newTab()
//                    .setText("Second Tab")
//                    .setTabListener(tabListener));
//            
//            actionBar.addTab(actionBar.newTab()
//                    .setText("Third Tab")
//                    .setTabListener(tabListener));
//            
//        }else if("3".equals(key)) {
//
//            if(index == 2) {
//            	
//                ((MainActivity)getActivity()).getSlidingMenu().toggle();
//                
//                return true;
//            }
//            
//            index = 2;
//            
//            mFrameLayout.setVisibility(View.VISIBLE);
//            
//            mViewPager.setVisibility(View.GONE);
//            
//            getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//            
//            FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
//            
//            ContentFragment contentFragment = (ContentFragment)fragmentManager.findFragmentByTag("N");
//            
//            fragmentManager.beginTransaction()
//            .replace(R.id.content, contentFragment == null ? new ContentFragment("This is N Menu"):contentFragment,"N")
//            .commit();
//        }
        
        //显示Slidingmenu
        ((MainActivity)getActivity()).getSlidingMenu().toggle();
        
        return false;
    }
    
    
    private boolean  ShowFragment(int fragmentindex){
    	
      //如果目前处于点击要显示的页面，则保持该页面不变(if the content view is that we need to show . show directly)
      if(index == fragmentindex) {
      	
      	//动态判断自动关闭或开启SlidingMenu 
          ((MainActivity)getActivity()).getSlidingMenu().toggle();
          
          return true;
      }
      
      //如果不是将要显示的页面，则替换当前页面(otherwise , replace the content view via a new Content fragment)
      index = fragmentindex;
      
      Log.d("debug", "" + fragmentindex);
      
      getActivity().getActionBar().setSelectedNavigationItem(fragmentindex);
      
//      FragmentManager fragmentManager = ((MainActivity)getActivity()).getFragmentManager();
//      
//      ContentFragment fragment = ViewpagerFragmentAdapter.mFragmentList.get(fragmentindex);
      
      
//      fragmentManager.beginTransaction().add(mActivity.findViewById(R.id.viewpager).getId(), fragment, tabs.get(fragmentindex));
   
  
      
      return false;
    	
    }
    
    //获得SlidingMenu实例
    private SlidingMenu getSlidingMenu() {
    	
        return mActivity.getSlidingMenu();
    }
    
    
    //ViewPager页面切换时触发--可以在这里改变tab的聚焦情况
    ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
    	
        @Override
        public void onPageSelected(int position) {
        	
            getActivity().getActionBar().setSelectedNavigationItem(position);
            
            switch (position) {
            
                case 0:
                	
                	////允许通过滑动屏幕任何地方打开SlidingMenu
                    getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    
                    break;
                    
                default:
                	
                	//允许通过滑动屏幕边缘打开SlidingMenu
                    getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                    
                    break;
            }
        }

    };
    
    
    
    //设置ActionBar的监听器
    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
    	
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        	
            if (mViewPager.getCurrentItem() != tab.getPosition())
            	
                mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    };
}
