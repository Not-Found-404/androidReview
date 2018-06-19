package QDLG.LCB.Demo.AnimationListenerDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AnimationListenerDemo extends Activity {
	
	//可以动态删除的View控件
	private  ImageView view;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //找到View对象
        view=(ImageView)findViewById(R.id.test);
        
        //找到父View对象, 删除控件只能由父控件来完成
        final ViewGroup parent=(ViewGroup)findViewById(R.id.parent);
        
        //根据xml文件创建动画对象
		final Animation  animation=AnimationUtils.loadAnimation(AnimationListenerDemo.this,R.anim.alpha);
		
		//为动画对象建立监听器
		animation.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) { //开始时触发
				// TODO Auto-generated method stub		
				System.out.println("Start");
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {//重复时触发
				// TODO Auto-generated method stub	
				System.out.println("Repeat");
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {//结束时触发
				// TODO Auto-generated method stub
				
				parent.removeView(view);   //删除控件
				view=null; //已经被删除，置空
				
				System.out.println("End");
			}
		});
        
		//按钮监听器
        ((Button)findViewById(R.id.testBtn)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (view!=null)
					view.startAnimation(animation); //开始播放动画
				else
					Toast.makeText(AnimationListenerDemo.this, "Deleted!!!", Toast.LENGTH_SHORT).show();
			}
		});
    }
}