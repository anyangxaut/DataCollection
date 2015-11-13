package com.xaut.sensor_v1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;
import com.xaut.fragment.MenuFragment;

//MainActivity继承了SlidingActivity，可以实现slidemenu
public class MainActivity extends SlidingActivity {
	
	@SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // 解决网络问题，android4.0之前无需添加
     		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
     				.detectDiskReads().detectDiskWrites().detectNetwork() // or
     																		// .detectAll()
     																		// for
     																		// all
     																		// detectable
     																		// problems
     				.penaltyLog().build());
     		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
     				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
     				.penaltyLog().penaltyDeath().build());
        
        //设置页面标题
        setTitle("Sensor-V1");
        
        //默认为Fragment模式
        setContentView(R.layout.frame_content);
        
     //setBehindContentView()方法会把view放置在SlidingMenu的后面(设置SlidingMenu中的布局文件)
        setBehindContentView(R.layout.frame_menu);
        
        
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        
        MenuFragment menuFragment = new MenuFragment();
        
        fragmentTransaction.replace(R.id.menu, menuFragment);
        
//        fragmentTransaction.replace(R.id.content, new ContentFragment(getString(R.string.welcome)),"Welcome");
        
        fragmentTransaction.commit();

        initSlidingMenu();
    }
    
    private void initSlidingMenu() {

        //自定义SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        
        sm.setShadowWidth(50);//设置阴影的宽度 
        
        sm.setShadowDrawable(R.drawable.shadow);//设置阴影图片
        
        sm.setBehindOffset(80);//SlidingMenu划出时主页面显示的剩余宽度
        
        sm.setFadeDegree(0.35f);//SlidingMenu滑动时的渐变程度 
        
        //设置SlidingMenu的几种手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动可以打开SlidingMenu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，在屏幕边缘滑动可以打开SlidingMenu
        //TOUCHMODE_NONE 不能通过手势打开
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //使ActionBar左上方的Icon或者Logo可以点击，这样在onOptionsItemSelected里面才可以监听到R.id.home
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    

    //设置ActionBar中的Item选项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    
    //处理ActionBar点击事件
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        switch (item.getItemId()) {
        
        case android.R.id.home:{
        	
        	//动态判断自动关闭或开启SlidingMenu 
            toggle();
            return true;
            
        }
        case R.id.contact:{
        	
        	final Intent email = new Intent(
					android.content.Intent.ACTION_SENDTO);
        	
			String uriText;
			
			try {
				
				uriText = "mailto:anyangxaut@gmail.com" + "?subject="
						+ URLEncoder.encode("SensorDataCollection Demos Feedback","UTF-8");
				
				email.setData(Uri.parse(uriText));
				
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				startActivity(email);
				
			} catch (Exception e) {
				
				Toast.makeText(this, R.string.no_email, Toast.LENGTH_SHORT)
						.show();
			}
			break;
        	
        }
        case R.id.about:{
        	
        	new AlertDialog.Builder(this).setTitle(R.string.about)
			.setMessage(Html.fromHtml(getString(R.string.about_msg)))
			.show();
	        break;
        	
        }
        case R.id.help:{
        	
        	Uri uriUrl = Uri.parse("http://github.com/anyangxaut");
    		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl); 
    		this.startActivity(launchBrowser);
    		break;
        	
        }	
        case R.id.exit:{
        	
        	AlertDialog.Builder dialog = new Builder(this);
        	dialog.setMessage("确认退出吗？");
        	dialog.setTitle("提示");
        	dialog.setPositiveButton("确认", new OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				
					finish();
				}
        		
        	});
        	
        dialog.setNegativeButton("取消", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
        	
        	
        });
        	
        	dialog.create().show();
    		break;
        	
        }	
        }
        return super.onOptionsItemSelected(item);
    }
}
