package QDLG.LCB.Demo.MyViewDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyView extends View{

	//画文字的笔
	private Paint mTextPaint; 
	//划线的画笔
	private Paint cPaint;
	
	//要显示的字符串
	private String mStr;
	
	//是否显示划线?
	private boolean showLine;
	//划线的终点
	private int endX,endY;

	public MyView(Context context){
		super(context);
	}
	
	//必须实现此构造函数才能在布局文件和代码中正常使用此View
	public MyView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		// TODO Auto-generated constructor stub

		//对于我们自定义的类中，我们需要使用一个名为obtainStyledAttributes的方法来获取布局文件中的属性定义。
		//第二个参数是在属性文件中定义的属性类型定义名称
        TypedArray params = context.obtainStyledAttributes(attrs,R.styleable.TestView);
        
        //得到自定义控件的属性值, getResourceId的参数由系统根据属性文件中的定义自动生成   
        //属性名称的引用----R.styleable.属性类型定义名称_属性名称
        int backgroudId = params.getResourceId(R.styleable.TestView_imgBackground, 0);   
        if (backgroudId != 0)   
            setBackgroundResource(backgroudId);   //设置View的背景图片
        //设置写字用的画笔的颜色和字体大小
        mTextPaint=new Paint(); //创建画笔对象
        int textColor = params.getColor(R.styleable.TestView_textColor,0XFFFFFF);   
        mTextPaint.setColor(textColor); //设置画笔的颜色
        float textSize = params.getDimension(R.styleable.TestView_textSize, 36);   
        mTextPaint.setTextSize(textSize);  //设置画笔使用的字体
        
        //不显示划线
        showLine=false;
        endX=0;
        endY=0;
        
        //初始显示的字符串
        mStr="测试文本!";
        
        //初始化划线的笔
		 cPaint = new Paint();//构造Paint实例
	     cPaint.setColor(Color.BLUE);//使用蓝色绘图
	     cPaint.setStrokeWidth(3); //设置画笔的粗度
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
	     //"画"文本
	     canvas.drawText(mStr, 50, 290, mTextPaint);
	     
	     //画线
	     if (showLine) //确定要不要画线
	    	 canvas.drawLine(0, 0, endX, endY, cPaint);
	     
	     //"画"文本
	     canvas.drawText(mStr, 50, 350, mTextPaint);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//获取触摸位置
		int x=(int)event.getX();
		int y=(int)event.getY();
		
		int action=event.getAction();
		switch (action){ //分类触摸动作，通过控制显示标识和划线终点来控制划线动作
		case MotionEvent.ACTION_DOWN:
			showLine=true;
			endX=x;
			endY=y;
			break;
		case MotionEvent.ACTION_UP: //清除画线
			showLine=false;
			endX=0;
			endY=0;
			break;
		case MotionEvent.ACTION_MOVE: //持续画线
			showLine=true;
			endX=x;
			endY=y;
			break;
		}
		
		//必须重绘才能更新View
		invalidate();
		
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		//如果这里用上面的return super.onTouchEvent(event); 
		//调用父类的方法来得到返回值返回 ，
		//这样是有问题的, 因为调用父类的onTouchEvent方法可能会返回false
		//这样一来会无法响应触摸移动事件和触摸抬起事件，因为Android触摸事件的内部机制是返回fase的话，这个事件就会“消失”，而且接收不到下一次事件。
		return true;
	}

	//设置要显示的文本
	public void setText(String info){
		mStr=info;
		invalidate(); //设置完新文本后必须重绘
	}
	//获得正在显示的文本
	public String getText(){
		return mStr;
	}
}
