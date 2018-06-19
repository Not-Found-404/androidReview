package qdlg.lcb.demo.LocationServiceDemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationServiceDemo extends Activity {
	
	//系统服务对象
	private LocationManager locationManager;
	
	//定位的位置
	private Location position;
	
	//定位方式
	String provider=LocationManager.GPS_PROVIDER;
		
	//是否正在定位--GPS是否正在工作?
	boolean isLocating;
	
	//按钮对象--开始,停止
	private Button locate;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isLocating=false;
        locate=(Button)findViewById(R.id.locate);
        locate.setText("开始定位");
        
        //获取位置服务管理对象
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager==null){
        	Toast.makeText(this, "获取系统服务失败!", Toast.LENGTH_SHORT).show();
        	return;
        }
        
        //如果没有打开GPS,则转至GPS设置页面
        if (!locationManager.isProviderEnabled(provider)){
        	(new AlertDialog.Builder(this))
        	.setTitle("提示")
        	.setMessage("需要启用相应的定位设备")
        	.setPositiveButton("确定", new OnClickListener() {	//点击确定后打开设置对话框		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//打开设置页面
		        	Intent intent = new Intent();
		            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            try 
		            {
		                startActivity(intent);
		            } catch(ActivityNotFoundException ex) 
		            {
		                // The Android SDK doc says that the location settings activity
		                // may not be found. In that case show the general settings.                
		                // General settings activity
		                intent.setAction(Settings.ACTION_SETTINGS);
		                try {
		                       startActivity(intent);
		                } 
		                catch (Exception e) {
		                }
		            }
		        }

			})
        	.create().show();
		}
        //获取定位信息
        position=locationManager.getLastKnownLocation(provider);
        
        //显示位置信息
        ShowLocationInfo(position);
        
        locate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isLocating){ //如果正在定位
					locationManager.removeUpdates(listener);//停止定位
					locate.setText("开始定位");
					isLocating=false;
				}
				else{
					//设置位置改变的监听器
			        //时间大于2秒,距离大于10米时通知改变
					locationManager.requestLocationUpdates(provider, 2000, 10, listener);
					locate.setText("停止定位");
					isLocating=true;
				}
				
			}
		});
        
    }
    
    //显示位置信息
    private void ShowLocationInfo(Location position){
    	String s="";
    	
    	if (position==null){
    		((TextView)findViewById(R.id.info)).setText("没有可用的位置信息!");
    		return;    		
    	}
    	
    	//获取位置信息
    	s+="经度: ";
    	s+=position.getLongitude()+"\n";
    	s+="纬度: ";
    	s+=position.getLatitude()+"\n";
    	s+="高度: ";
    	s+=position.getAltitude()+"\n";
    	s+="加速度: ";
    	s+=position.getAccuracy()+"\n";
    	s+="方向: ";
    	s+=position.getBearing();
    	
    	//解析经纬度对应的地址信息
    	List<Address> address=null;
    	Geocoder gc=new Geocoder(LocationServiceDemo.this,Locale.CHINA);
    	try {
			address=gc.getFromLocation(position.getLatitude(), position.getLongitude(), 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String t="";
		if (address!=null && address.size()>0){
			for (int i=0; i<address.size(); i++){
				t+=address.get(i).toString()+"\n";
			}
		}
		else
			t="没有解析出地址...\n";
    	
    	//显示位置信息
    	((TextView)findViewById(R.id.info)).setText(s+"\n"+t);
    }
    
    //位置改变的监听器--追踪移动
    private LocationListener listener=new LocationListener() {		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			ShowLocationInfo(null);
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			ShowLocationInfo(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			ShowLocationInfo(location);
		}
	};

	@Override
	protected void onDestroy() { //关闭Activity时停止接收GPS定位信息
		// TODO Auto-generated method stub
		super.onDestroy();
		if(isLocating){ //如果正在定位
			locationManager.removeUpdates(listener);//停止定位
			isLocating=false;
		}
	}
	
	
}