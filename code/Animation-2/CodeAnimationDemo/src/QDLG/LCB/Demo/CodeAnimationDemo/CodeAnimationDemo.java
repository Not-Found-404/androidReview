package QDLG.LCB.Demo.CodeAnimationDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class CodeAnimationDemo extends Activity {
	
	//动画对象
	private ImageView view;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view=(ImageView)findViewById(R.id.test);
        
        Button btn=(Button)findViewById(R.id.testBtn);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				//透明度动画
				//AlphaAnimation(float fromAlpha, float toAlpha) 
				//第一个参数fromAlpha为 动画开始时候透明度
				//第二个参数toAlpha为 动画结束时候透明度
//				AlphaAnimation animation=new AlphaAnimation(1.0f,0.1f);
//				//设置动画显示时间
//				animation.setDuration(3000); 
//				animation.setFillAfter(true);
				
				//移动动画
				//TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) 
				//第一个参数fromXDelta为动画起始时 X坐标上的移动位置
				//第二个参数toXDelta为动画结束时 X坐标上的移动位置
				//第三个参数fromYDelta为动画起始时Y坐标上的移动位置
				//第四个参数toYDelta为动画结束时Y坐标上的移动位置
//				TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
//						Animation.RELATIVE_TO_PARENT, 0.5f,
//						Animation.RELATIVE_TO_PARENT, 0,
//						Animation.RELATIVE_TO_PARENT, 0);
//				//设置动画显示时间
//				animation.setDuration(3000); 
//				animation.setFillAfter(true);
				
				//旋转动画
				//RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
				//第一个参数fromDegrees为动画起始时的旋转角度
				//第二个参数toDegrees为动画旋转到的角度
				//第三个参数pivotXType为动画在X轴相对位置类型  
				//第四个参数pivotXValue为动画相对于X坐标的开始位置
				//第五个参数pivotXType为动画在Y轴相对位置类型
				//第六个参数pivotYValue为动画相对于Y坐标的开始位置
//				RotateAnimation animation=new RotateAnimation(0.0f, +360.0f,
//			               Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
//				//设置动画显示时间
//				animation.setDuration(3000); 
				
				//缩放动画
				//ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
				//第一个参数fromX为动画起始时 X坐标上的伸缩尺寸    
				//第二个参数toX为动画结束时 X坐标上的伸缩尺寸     
				//第三个参数fromY为动画起始时Y坐标上的伸缩尺寸    
				//第四个参数toY为动画结束时Y坐标上的伸缩尺寸
				/*说明:
                	以上四种属性值    
                	0.0表示收缩到没有 
                	1.0表示正常无伸缩     
                	值小于1.0表示收缩  
                	值大于1.0表示放大*/
				//第五个参数pivotXType为动画在X轴相对位置类型
				//第六个参数pivotXValue为动画相对于X坐标的开始位置
				//第七个参数pivotXType为动画在Y轴相对位置类型
				//第八个参数pivotYValue为动画相对于Y坐标的开始位置
//				ScaleAnimation animation=new ScaleAnimation(0f, 1.0f, 0f, 1.0f,
//			             Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF, 0);
//				//设置动画显示时间
//				animation.setDuration(3000); 
				
				//组合动画
				//建立存放组合动画对象的集合,true表示所有动画对象共享一个加速器
				AnimationSet animation=new AnimationSet(true);
				//透明度动画
				AlphaAnimation anim1=new AlphaAnimation(1, 0);
				anim1.setDuration(1000);
				anim1.setRepeatCount(2);
				animation.addAnimation(anim1);
				//自转动画
				RotateAnimation anim2=new RotateAnimation(0.0f, +360.0f,
			               Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				anim2.setDuration(1000);
				anim2.setRepeatCount(2);
				animation.addAnimation(anim2);
				//旋转动画
				RotateAnimation anim3=new RotateAnimation(0.0f, +360.0f,
			               Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT, 0);
				anim3.setDuration(3000);
				animation.addAnimation(anim3);

				
				//设置加速模式
				Interpolator i=new DecelerateInterpolator();
				animation.setInterpolator(i);
				//启动显示动画
				view.startAnimation(animation);
			}
		});
    }
}