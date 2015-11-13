package com.xaut.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xaut.entity.Rotation;
import com.xaut.sensor_v1.R;
import com.xaut.util.httpUtil;

public class RotationFragment extends Fragment implements SensorEventListener{

	private SensorManager sensor = null;

	private Sensor rotation = null;

	private TextView rotshow = null;

	private View view = null;

	private LinearLayout layout = null;

	private ImageButton data = null;

	private ImageButton tendency = null;

	private ImageButton info = null;
	
	private ImageButton sample = null;

	private int flag = 0;

	private DecimalFormat df = new DecimalFormat("0.00");

	private GraphicalView graphview;

	private String title1 = null, title2 = null, title3 = null;

	private XYMultipleSeriesRenderer mRenderer;

	private XYMultipleSeriesDataset mDataset;

	private XYSeries seriesOne, seriesTwo, seriesThree;

	private ArrayList<Rotation> list = new ArrayList<Rotation>();

	private int count = 0, temp = 0, xtag = 1;

	private final int NUM = 10;
	
	private int inittag = -1;
	
	private TextView show = null;
	
	private Button start = null, end = null;
	
    private String szImei = null;
	
	private int datanumber = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

//		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.rotation_layout, null);

		// 从系统服务中获取传感器管理器
		sensor = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		
		// 获取旋转矢量传感器实例
		rotation = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

		// if(!TextUtils.isEmpty(text)) {//text为空或者为""，都会返回真
		//
		// textView.setText(text);
		// }
		//Relativelayout.setBackgroundResource(R.drawable.leaf);

		layout = (LinearLayout) view.findViewById(R.id.layout);

		data = (ImageButton) view.findViewById(R.id.data);

		tendency = (ImageButton) view.findViewById(R.id.tendency);

		info = (ImageButton) view.findViewById(R.id.info);
		
		sample = (ImageButton)view.findViewById(R.id.sample);
		
		LayoutInflater inflater1 = LayoutInflater.from(getActivity());

		View dataLayout = inflater1.inflate(R.layout.data_layout, null);

		layout.addView(dataLayout);

		rotshow = (TextView) view.findViewById(R.id.show);

		if (rotation != null) {
			
			rotshow.setText("名称：" + rotation.getName()
					+ "\n\n版本：" + rotation.getVersion()
					+ "\n\n供应商：" + rotation.getVendor()
		        	+ "\n\n功率：" + rotation.getPower()
					+ "\n\n分辨率："
					+ rotation.getResolution()
					+ "\n\n最大范围："
					+ rotation.getMaximumRange() + "\n");

			       

		} else {
			
			rotshow.setText("\n\n      此手机上不存在该类型的传感器设备。");
		}

		data.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				layout.removeAllViews();

				LayoutInflater inflater = LayoutInflater.from(getActivity());

				View dataLayout = inflater.inflate(R.layout.data_layout, null);

				layout.addView(dataLayout);

				rotshow = (TextView) view.findViewById(R.id.show);

				flag = 1;
			}

		});

		tendency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				layout.removeAllViews();

				flag = 2;

				initView();
			}

		});

		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				layout.removeAllViews();

				flag = 3;

				LayoutInflater inflater = LayoutInflater.from(getActivity());

				View dataLayout = inflater.inflate(R.layout.data_layout, null);

				layout.addView(dataLayout);

				rotshow = (TextView) view.findViewById(R.id.show);

				if (rotation != null) {
					
					rotshow.setText("名称：" + rotation.getName()
							+ "\n\n版本：" + rotation.getVersion()
							+ "\n\n供应商：" + rotation.getVendor()
				        	+ "\n\n功率：" + rotation.getPower()
							+ "\n\n分辨率："
							+ rotation.getResolution()
							+ "\n\n最大范围："
							+ rotation.getMaximumRange() + "\n");

					       

				} else {
					
					rotshow.setText("\n\n      此手机上不存在该类型的传感器设备。");
				}
				
				
			}

		});
		
		
		sample.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				layout.removeAllViews();

				LayoutInflater inflater = LayoutInflater.from(getActivity());

				View sampleLayout = inflater.inflate(R.layout.sample_layout, null);

				layout.addView(sampleLayout);
				
				show = (TextView)view.findViewById(R.id.show);

				start = (Button)view.findViewById(R.id.start);
				
				end = (Button)view.findViewById(R.id.end);

				TelephonyManager TelephonyMgr = (TelephonyManager)getActivity().getSystemService("phone");

				szImei = TelephonyMgr.getDeviceId();
				
				end.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						show.setText("旋转矢量传感器采样结束:\n\n\n目前采样数据"
								+ datanumber + "条");
						
						datanumber = 0;
						
						flag = 5;
						
						end.setEnabled(false);
						
						start.setEnabled(true);
							
					}
					
					
				});

				start.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

					    show.setText("旋转矢量传感器正在采样中:\n\n\n目前采样数据"
										+ datanumber + "条");
							
						flag = 4;
							
						start.setEnabled(false);
							
						end.setEnabled(true);
					}

				});

				
				
			}

		});

		return view;
	}
	

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onDetach() {

		super.onDetach();
	}

	@Override
	public void onPause() {

		super.onPause();

		sensor.unregisterListener(this);
	}

	@Override
	public void onResume() {

		super.onResume();

		sensor.registerListener(this, rotation, 500000);
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		double x = event.values[0];

		double y = event.values[1];

		double z = event.values[2];

		if (flag == 1) {

			rotshow.setText("旋转矢量传感器数据：\n\nx轴：" + Double.parseDouble(df.format(x))
					+ "\n\ny轴：" + Double.parseDouble(df.format(y)) 
					+ "\n\nz轴：" + Double.parseDouble(df.format(z))
					+ "\n");

		} else if (flag == 2) {

			update(x, y, z);

		} else if (flag == 3) {

		}else if (flag == 4) {
			
			//判断是否有网络连接
//			if(NetworkUtil.isNetworkConnected(getActivity())){
				
				sample(x, y, z);
				
//			}else{
//				//網絡設置界面
//				NetworkUtil.setNetworkMethod(getActivity());
//			}	
			
		}
	}
	
	
    private String sample(double x, double y, double z){
		
		datanumber++;

		show.setText("重力传感器正在采样中:\n\n\n目前采样数据" + datanumber + "条");

		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		NameValuePair pair = new BasicNameValuePair("szImei", szImei);
		NameValuePair pair1 = new BasicNameValuePair("X", String.valueOf(x));
		NameValuePair pair2 = new BasicNameValuePair("Y", String.valueOf(y));
		NameValuePair pair3 = new BasicNameValuePair("Z", String.valueOf(z));

		pairList.add(pair);
		pairList.add(pair1);
		pairList.add(pair2);
		pairList.add(pair3);

		String url = httpUtil.BASE_URL + "/RotationServlet";
		String information = httpUtil.queryStringForPost(url, pairList);

		return information;
	}

	private void update(double x, double y, double z) {

		list.add(new Rotation(x, y, z));

		mDataset.removeSeries(seriesOne);
		mDataset.removeSeries(seriesTwo);
		mDataset.removeSeries(seriesThree);

		seriesOne.clear();
		seriesTwo.clear();
		seriesThree.clear();

		count = 1;
		temp++;
		xtag = temp + 1;

		for (int i = temp; i < NUM + temp; i++) {

			x = list.get(i).getX();
			seriesOne.add(count, Double.parseDouble(df.format(x)));
			y = list.get(i).getY();
			seriesTwo.add(count, Double.parseDouble(df.format(y)));
			z = list.get(i).getZ();
			seriesThree.add(count, Double.parseDouble(df.format(z)));

			mRenderer.addXTextLabel(count, "" + xtag);

			count++;
			xtag++;
		}

		mDataset.addSeries(seriesOne);
		mDataset.addSeries(seriesTwo);
		mDataset.addSeries(seriesThree);

		// 视图更新，没有这一步，曲线不会呈现动态
		// 如果在非UI主线程中，需要调用postInvalidate()，具体参考api
		graphview.invalidate();
	}

	private void getdata() {

		// 初始化数据10条
		for(int i =0; i < NUM; i++){
			
			list.add(new Rotation(0, 0, 0));
		}

	}

	private void initView() {
			
		if(inittag == -1){
			
			inittag = 1;
			
			getdata(); // 初始化数据集	
		}
			

		title1 = "X轴数据变化图";
		title2 = "Y轴数据变化图";
		title3 = "Z轴数据变化图";

		mDataset = new XYMultipleSeriesDataset();

		// t1,t2图表数据信息
		seriesOne = new XYSeries(title1);
		seriesTwo = new XYSeries(title2);
		seriesThree = new XYSeries(title3);

		mRenderer = new XYMultipleSeriesRenderer();
		// 设置图表的X轴的当前方向
		mRenderer
				.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
		mRenderer.setXTitle("数据量(条)");// 设置为X轴的标题
		mRenderer.setYTitle("数据(m/s^2)");// 设置y轴的标题
		mRenderer.setAxisTitleTextSize(32);// 设置轴标题文本大小
		// mRenderer.setChartTitle("加速度传感器数据变化图");// 设置图表标题
		// mRenderer.setChartTitleTextSize(30);// 设置图表标题文字的大小
		mRenderer.setLabelsTextSize(25);// 设置标签的文字大小
		mRenderer.setLegendTextSize(30);// 设置图例文本大小
		mRenderer.setPointSize(7f);// 设置点的大小
		// mRenderer.setYAxisMin(0);// 设置y轴最小值是0
		// mRenderer.setYAxisMax(1.5);
		mRenderer.setAxesColor(Color.BLACK);// 设置坐标轴的颜色
		mRenderer.setGridColor(Color.GRAY);// 设置网格线的颜色
		mRenderer.setLabelsColor(Color.BLACK);// 设置轴标题的颜色
		mRenderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		mRenderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		mRenderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		mRenderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		mRenderer.setShowGrid(true);// 显示网格
		mRenderer.setPanEnabled(false, false);// 设置视图能否拖动
		mRenderer.setZoomButtonsVisible(false);// 设置放大缩小按钮是否可见
		mRenderer.setApplyBackgroundColor(true);// 设置是否显示背景色
		mRenderer.setBackgroundColor(Color.argb(0, 220, 228, 234));// 设置背景色
		mRenderer.setMarginsColor(Color.argb(0, 220, 228, 234));// 设置报表周边颜色
		mRenderer.setMargins(new int[] { 90, 60, 80, 30 });// 设置视图位置 (上/左/下/右)
		mRenderer.setXLabels(0);// 设置x轴显示格式

		count = 1;

		for (int i = 0; i < NUM; i++) {

			double x = list.get(i).getX();

			seriesOne.add(count, Double.parseDouble(df.format(x)));

			double y = list.get(i).getY();

			seriesTwo.add(count, Double.parseDouble(df.format(y)));

			double z = list.get(i).getZ();

			seriesThree.add(count, Double.parseDouble(df.format(z)));

			mRenderer.addXTextLabel(count, "" + xtag);

			count++;
			xtag++;
		}

		mDataset.addSeries(seriesOne);
		mDataset.addSeries(seriesTwo);
		mDataset.addSeries(seriesThree);

		XYSeriesRenderer rOne = new XYSeriesRenderer();// (类似于一条线对象)
		rOne.setColor(Color.BLUE);// 设置颜色
		rOne.setPointStyle(PointStyle.CIRCLE);// 设置点的样式
		rOne.setFillPoints(true);// 填充点（显示的点是空心还是实心）
		rOne.setDisplayChartValues(true);// 将点的值显示出来
		rOne.setChartValuesSpacing(10);// 显示的点的值与图的距离
		rOne.setChartValuesTextSize(20);// 点的值的文字大小
		// r.setFillBelowLine(true);//是否填充折线图的下方
		// r.setFillBelowLineColor(Color.GREEN);//填充的颜色，如果不设置就默认与线的颜色一致
		rOne.setLineWidth(3);// 设置线宽
		mRenderer.addSeriesRenderer(rOne);

		XYSeriesRenderer rTwo = new XYSeriesRenderer();// (类似于一条线对象)
		rTwo.setColor(Color.RED);// 设置颜色
		rTwo.setPointStyle(PointStyle.CIRCLE);// 设置点的样式
		rTwo.setFillPoints(true);// 填充点（显示的点是空心还是实心）
		rTwo.setDisplayChartValues(true);// 将点的值显示出来
		rTwo.setChartValuesSpacing(10);// 显示的点的值与图的距离
		rTwo.setChartValuesTextSize(20);// 点的值的文字大小
		// rTwo.setFillBelowLine(true);//是否填充折线图的下方
		// rTwo.setFillBelowLineColor(Color.GREEN);//填充的颜色，如果不设置就默认与线的颜色一致
		rTwo.setLineWidth(3);// 设置线宽
		mRenderer.addSeriesRenderer(rTwo);

		XYSeriesRenderer rThree = new XYSeriesRenderer();// (类似于一条线对象)
		rThree.setColor(Color.DKGRAY);// 设置颜色
		rThree.setPointStyle(PointStyle.CIRCLE);// 设置点的样式
		rThree.setFillPoints(true);// 填充点（显示的点是空心还是实心）
		rThree.setDisplayChartValues(true);// 将点的值显示出来
		rThree.setChartValuesSpacing(10);// 显示的点的值与图的距离
		rThree.setChartValuesTextSize(20);// 点的值的文字大小
		// rTwo.setFillBelowLine(true);//是否填充折线图的下方
		// rTwo.setFillBelowLineColor(Color.GREEN);//填充的颜色，如果不设置就默认与线的颜色一致
		rThree.setLineWidth(3);// 设置线宽
		mRenderer.addSeriesRenderer(rThree);

		// 生成图表
		graphview = ChartFactory.getLineChartView(getActivity(), mDataset,
				mRenderer);
		// 将图表添加到布局中去
		layout.addView(graphview);

	}
	
	
}
