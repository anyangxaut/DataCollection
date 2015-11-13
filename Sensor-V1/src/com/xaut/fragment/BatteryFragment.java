package com.xaut.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xaut.sensor_v1.R;

public class BatteryFragment extends Fragment{
	
	private View view = null;
	
	private TextView battery = null;
	
	private BatteryReceiver batteryReceiver;
	
	private IntentFilter batteryFilter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.battery_layout, null);
	
		battery = (TextView)view.findViewById(R.id.batteryinfo);
		
		batteryReceiver = new BatteryReceiver();
		
        batteryFilter = new IntentFilter();
        
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);   
		
		return view;
	}
	
	
	private class BatteryReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
        	
            int level = arg1.getIntExtra("level", 0);
            
            int temperature = arg1.getIntExtra("temperature", 0);
            
            int voltage = arg1.getIntExtra("voltage", 0);  
            
            int status = arg1.getIntExtra("status", 0);  
            
            int health = arg1.getIntExtra("health", 1); 
            
            String statusString = "未知状态";
            
            String healthString = "未知状态"; 
            
            switch (status) {  
            
            case BatteryManager.BATTERY_STATUS_UNKNOWN:  
            	
                statusString = "未知状态";  
                
                break;  
                
            case BatteryManager.BATTERY_STATUS_CHARGING:  
            	
                statusString = "充电状态";  
                
                break;  
                
            case BatteryManager.BATTERY_STATUS_DISCHARGING:  
            	
                statusString = "放电状态";  
                
                break;  
                
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:  
            	
                statusString = "未充满";  
                
                break;  
                
            case BatteryManager.BATTERY_STATUS_FULL:  
            	
                statusString = "充满电";  
                
                break;  
        }  
            
            
            switch (health) {  
            
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:  
            	
                healthString = "未知状态";  
                
                break;  
                
            case BatteryManager.BATTERY_HEALTH_GOOD:  
            	
                healthString = "状态良好";  
                
                break;  
                
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:  
            	
                healthString = "电池过热";  
                
                break;  
                
            case BatteryManager.BATTERY_HEALTH_DEAD:  
            	
                healthString = "电量过低";  
                
                break;  
                
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:  
            	
                healthString = "电压过高";  
                
                break;  
                
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE: 
            	
                healthString = "未知故障";  
                
                break;  
        }  
            
            battery.setText("电量："  +  level + "%"
            		+ "\n\n温度：" + temperature + "℃"
            		+ "\n\n电压：" + voltage + "mV"
            		+ "\n\n状态：" + statusString 
            		+ "\n\n健康：" + healthString);
            
        }
        
    }
	
	
	public void onResume() {  
		
	        super.onResume();  
	        
	        getActivity().registerReceiver(batteryReceiver, batteryFilter);
	      }  
	
	
	@Override
	public void onPause() {

		super.onPause();

		getActivity().unregisterReceiver(batteryReceiver);
	}
    

}
