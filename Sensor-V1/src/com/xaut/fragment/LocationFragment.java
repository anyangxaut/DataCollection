package com.xaut.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.xaut.sensor_v1.R;
import com.xaut.util.BMapUtil;

public class LocationFragment extends Fragment{

    private View view = null;
    // 定位
 	public BDLocationListener myListener = new MyLocationListener();
 	private LocationClient mLocClient = null;
 	private boolean mIsStart = false;
 	private ArrayList<BDLocation> locationinfo = new ArrayList<BDLocation>();
 	/**
 	 * MapView 是地图主控件
 	 */
 	private MapView mMapView = null;
 	/**
 	 * 用MapController完成地图控制
 	 */
 	private MapController mMapController = null;
 	private MyOverlay mOverlay = null;
 	private PopupOverlay pop = null;
 	private TextView popupText = null;
 	private View viewCache = null;
 	private View popupInfo = null;
 	private View popupLeft = null;
 	private View popupRight = null;
 	private Button button = null;
 	private MapView.LayoutParams layoutParam = null;
 	private OverlayItem mCurItem = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		setRetainInstance(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		LocationRegister app = (LocationRegister)getActivity().getApplication();
		
		if (app.mBMapManager == null) {
			
			app.mBMapManager = new BMapManager(getActivity());
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(LocationRegister.strKey,
					
					new LocationRegister.MyGeneralListener());
		}
		/**
		 * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		 */
		view = inflater.inflate(R.layout.location_layout, null);
		
		mMapView = (MapView)view.findViewById(R.id.bmapView);
		/**
		 * 获取地图控制器
		 */
		mMapController = mMapView.getController();
		/**
		 * 设置地图是否响应点击事件 .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(14);
		/**
		 * 显示内置缩放控件
		 */
		mMapView.setBuiltInZoomControls(true);

		mLocClient = new LocationClient(getActivity()); // 声明LocationClient类
		
		mLocClient.setAK(LocationRegister.strKey);
		
		mLocClient.registerLocationListener(myListener); // 注册监听函数
		
		setLocationOption();

		if (!mIsStart) {

			mLocClient.start();
			if (mLocClient != null && mLocClient.isStarted()) {
				mLocClient.requestLocation(); // 发起定位请求。请求过程是异步的，定位结果在监听函数onReceiveLocation中获取。
				mIsStart = true;
			} else
				Log.d("LocSDK3", "locClient is null or not started");

		}	
		
		return view;
	}
	
	
	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setAddrType("all"); // 返回的定位结果包含地址信息
		option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		option.setPriority(LocationClientOption.GpsFirst); // 设置Gps优先
		option.disableCache(true); // 禁止启用缓存定位
		// option.setPoiNumber(10); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		mLocClient.setLocOption(option);
	}

	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中 BDLocationListener接口有2个方法需要实现：
	 * 1.接收异步返回的定位结果，参数是BDLocation类型参数。 2.接收异步返回的POI查询结果，参数是BDLocation类型参数。
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			initOverlay(location);

			/**
			 * 设定地图中心点
			 */
			GeoPoint p = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			mMapController.setCenter(p);

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public void initOverlay(BDLocation location) {
		/**
		 * 创建自定义overlay
		 */
		mOverlay = new MyOverlay(getResources().getDrawable(
				R.drawable.icon_gcoding), mMapView);
		/**
		 * 准备overlay 数据
		 */
		

			GeoPoint p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			OverlayItem item1 = new OverlayItem(p1, "覆盖物1", "");
			/**
			 * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
			 */
			item1.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
			
			//清楚原来的pop点，重新绘制pop点
			
			mOverlay.removeAll();
			
			if (pop != null){
				
                pop.hidePop();
        	}
        
        	mMapView.getOverlays().clear();
        	
			/**
			 * 将item 添加到overlay中 注意： 同一个itme只能add一次
			 */
			mOverlay.addItem(item1);


		/**
		 * 将overlay 添加至MapView中
		 */
		mMapView.getOverlays().add(mOverlay);

		/**
		 * 刷新地图
		 */
		mMapView.refresh();

		/**
		 * 向地图添加自定义View.
		 */

		viewCache = getActivity().getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		popupLeft = (View) viewCache.findViewById(R.id.popleft);
		popupRight = (View) viewCache.findViewById(R.id.popright);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);

		button = new Button(getActivity());
		
		button.setBackgroundResource(R.drawable.popup);

		/**
		 * 创建一个popupoverlay
		 */
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				if (index == 0) {
					// 更新item位置
					pop.hidePop();
					GeoPoint p = new GeoPoint(mCurItem.getPoint()
							.getLatitudeE6() + 5000, mCurItem.getPoint()
							.getLongitudeE6() + 5000);
					mCurItem.setGeoPoint(p);
					mOverlay.updateItem(mCurItem);
					mMapView.refresh();
				} else if (index == 2) {
					// 更新图标
					mCurItem.setMarker(getResources().getDrawable(
							R.drawable.nav_turn_via_1));
					mOverlay.updateItem(mCurItem);
					mMapView.refresh();
				}
			}
		};
		pop = new PopupOverlay(mMapView, popListener);

	}

	@Override
	public void onPause() {
		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mMapView.onPause();
		super.onPause();
		if (mLocClient != null) {
			mLocClient.stop();
		}
		mIsStart = false;
	}

	
	@Override
	public void onResume() {
		/**
		 * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		mMapView.destroy();
		super.onDestroy();
	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@SuppressWarnings("rawtypes")
	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			OverlayItem item = getItem(index);
			mCurItem = item;
			if (index == 3) {
				button.setText("这是一个系统控件");

				BDLocation temp = locationinfo.get(locationinfo.size() - 1);

				GeoPoint pt = new GeoPoint((int) (temp.getLatitude() * 1E6),
						(int) (temp.getLongitude() * 1E6));
				// 创建布局参数
				layoutParam = new MapView.LayoutParams(
				// 控件宽,继承自ViewGroup.LayoutParams
						MapView.LayoutParams.WRAP_CONTENT,
						// 控件高,继承自ViewGroup.LayoutParams
						MapView.LayoutParams.WRAP_CONTENT,
						// 使控件固定在某个地理位置
						pt, 0, -32,
						// 控件对齐方式
						MapView.LayoutParams.BOTTOM_CENTER);
				// 添加View到MapView中
				mMapView.addView(button, layoutParam);
			} else {
				popupText.setText(getItem(index).getTitle());
				Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupLeft),
						BMapUtil.getBitmapFromView(popupInfo),
						BMapUtil.getBitmapFromView(popupRight) };
				pop.showPopup(bitMaps, item.getPoint(), 32);
			}
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (pop != null) {
				pop.hidePop();
				mMapView.removeView(button);
			}
			return false;
		}

	}
	
}
