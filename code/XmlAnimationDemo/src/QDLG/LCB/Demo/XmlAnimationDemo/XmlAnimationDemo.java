package QDLG.LCB.Demo.XmlAnimationDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class XmlAnimationDemo extends Activity {
	
	//测试用的动画对象--一张图片
	private ImageView test;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        
        test=(ImageView)findViewById(R.id.test);
        //设置ImageView的位置,从(0,0)移至(50,50)
//        test.setPadding(test.getLeft()+50, test.getTop()+50,
//        		test.getRight(), test.getBottom());
        
        //找出按钮并设置监听器
        Button btn=(Button)findViewById(R.id.testBtn);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				
				//根据xml文件创建动画对象
				Animation  animation=AnimationUtils.loadAnimation(XmlAnimationDemo.this,R.anim.scale);
				animation.setFillAfter(true);  //保持最后的动画结果
				//启动显示动画
				test.startAnimation(animation);
			}
		});
    }
}