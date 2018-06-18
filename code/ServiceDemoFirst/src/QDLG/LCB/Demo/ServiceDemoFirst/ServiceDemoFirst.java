package QDLG.LCB.Demo.ServiceDemoFirst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceDemoFirst extends Activity {
	
	//启动Service使用的Intent对象
	private  Intent intent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取按钮对象
        Button btnStartService=(Button)findViewById(R.id.startService);
        Button btnStopService=(Button)findViewById(R.id.stopService);
        
        //建立Intent对象，用于隐式启动Service
        intent=new Intent("QDLG.LCB.Demo.ServiceDemoFirst.RandomService");
        
        //开启服务的按钮的监听器
        btnStartService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startService(intent);
			}
		});
        
        //停止服务的监听器
        btnStopService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopService(intent);
			}
		});
        
    }
}