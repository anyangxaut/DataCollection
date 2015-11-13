package com.xaut.sensor_v1;

import java.util.ArrayList;
import java.util.List;

import com.xaut.fragment.AccelerometerFragment;
import com.xaut.fragment.BatteryFragment;
import com.xaut.fragment.GravityFragment;
import com.xaut.fragment.GyroscopeFragment;
import com.xaut.fragment.LightFragment;
import com.xaut.fragment.LinearAccelerationFragment;
import com.xaut.fragment.LocationFragment;
import com.xaut.fragment.MagneticfieldFragment;
import com.xaut.fragment.MenuFragment;
import com.xaut.fragment.PressureFragment;
import com.xaut.fragment.ProximityFragment;
import com.xaut.fragment.RotationFragment;
import com.xaut.fragment.TemperatureFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class ViewpagerFragmentAdapter extends PagerAdapter {

    private final FragmentManager mFragmentManager;
    
    private FragmentTransaction mTransaction = null;
    
    public static List<Fragment> mFragmentList = new ArrayList<Fragment>();
    
    private final List<String> content = MenuFragment.tabs;
    
    
    //给Viewpager添加Fragment
    public ViewpagerFragmentAdapter(FragmentManager fm) {
    	
        mFragmentManager = fm;
        
        
        for(String s : content){
        	
        	 if(s.equals("Battery")){
        		 
        			mFragmentList.add(new BatteryFragment());
        		 
        	 }else if(s.equals("Location")){
        		 
        		 mFragmentList.add(new LocationFragment());
        		 
        	 }else if(s.equals("Accelerometer")){
        		 
        		 mFragmentList.add(new AccelerometerFragment());
        		 
        	 }else if(s.equals("Gravity")){
        		 
        		 mFragmentList.add(new GravityFragment());
        		 
        	 }else if(s.equals("Rotation")){
        		 
        		 mFragmentList.add(new RotationFragment());
        		 
        	 }else if(s.equals("Linear Acceleration")){
        		 
        		 mFragmentList.add(new LinearAccelerationFragment());
        		 
        	 }else if(s.equals("Gyroscope")){
        		 
        		 mFragmentList.add(new GyroscopeFragment());
        		 
        	 }else if(s.equals("Light")){
        		 
        		 mFragmentList.add(new LightFragment());
        		 
        	 }else if(s.equals("Magnetic field")){
        		 
        		 mFragmentList.add(new MagneticfieldFragment());
        		 
        	 }else if(s.equals("Pressure")){
        		 
        		 mFragmentList.add(new PressureFragment());
        		 
        	 }else if(s.equals("Proximity")){
        		 
        		 mFragmentList.add(new ProximityFragment());
        		 
        	 }else if(s.equals("Temperature")){
        		 
        		 mFragmentList.add(new TemperatureFragment());
        	 }
        }


    }
    
    
    @Override
    public int getCount() {
    	
        return mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
    	
        return ((Fragment) object).getView() == view;
    }

    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	
        if (mTransaction == null) {
        	
            mTransaction = mFragmentManager.beginTransaction();
        }
        
        String name = getTag(position);
        
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        
        if (fragment != null) {
        	
            mTransaction.attach(fragment);
            
        } else {
        	
            fragment = getItem(position);
            
            mTransaction.add(container.getId(), fragment, getTag(position));
        }
        
        return fragment;
    }

    
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	
        if (mTransaction == null) {
        	
            mTransaction = mFragmentManager.beginTransaction();
        }
        
        mTransaction.detach((Fragment) object);
    }
    
    
    
    @Override
    public void finishUpdate(ViewGroup container) {
    	
        if (mTransaction != null) {
        	
            mTransaction.commitAllowingStateLoss();
            
            mTransaction = null;
            
            mFragmentManager.executePendingTransactions();
        }
    }
    
    
    
    public Fragment getItem(int position){
    	
       return  mFragmentList.get(position);
    }
    
    
    public long getItemId(int position) {
    	
        return position;
    }
    
    protected  String getTag(int position){
    	
        return content.get(position);
    }

}
