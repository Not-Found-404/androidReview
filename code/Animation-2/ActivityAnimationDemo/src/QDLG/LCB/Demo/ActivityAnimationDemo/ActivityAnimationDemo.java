package QDLG.LCB.Demo.ActivityAnimationDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityAnimationDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //切换Activity
        ((Button)findViewById(R.id.btn)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//利用显式Intent调用来打开另一个Activity
				Intent intent=new Intent();
				intent.setClass(ActivityAnimationDemo.this, SecondActivity.class);
				startActivity(intent);
				
				//此语句必须放在startActivity的后面,分别给出2个Activity消失和显现的动画效果
				overridePendingTransition(R.anim.enter,R.anim.out);
			}
		});
    }
}