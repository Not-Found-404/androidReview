package QDLG.LCB.Demo.ServiceDemoFirst;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


//???????????Service??
public class RandomService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("Service--OnCreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("Service--OnDestroy");
	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("Service--onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		System.out.println("Service--OnStart");
		
		long r=Math.round(Math.random()*100); //?????????
		Toast.makeText(this, "?????: "+r, Toast.LENGTH_SHORT).show();
		System.out.println("?????: "+r);
	
		//??????????????, ????????,Why?
		while (true){		
			r=Math.round(Math.random()*100);
			Toast.makeText(this, "?????: "+r, Toast.LENGTH_SHORT).show();		
			System.out.println("?????: "+r);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
