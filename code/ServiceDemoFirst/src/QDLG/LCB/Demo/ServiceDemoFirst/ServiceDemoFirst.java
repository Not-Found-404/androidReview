package QDLG.LCB.Demo.ServiceDemoFirst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceDemoFirst extends Activity {
	
	//����Serviceʹ�õ�Intent����
	private  Intent intent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ȡ��ť����
        Button btnStartService=(Button)findViewById(R.id.startService);
        Button btnStopService=(Button)findViewById(R.id.stopService);
        
        //����Intent����������ʽ����Service
        intent=new Intent("QDLG.LCB.Demo.ServiceDemoFirst.RandomService");
        
        //��������İ�ť�ļ�����
        btnStartService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startService(intent);
			}
		});
        
        //ֹͣ����ļ�����
        btnStopService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sopService(intent);t
			}
		});
        
    }
}