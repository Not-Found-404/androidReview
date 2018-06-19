package QDLG.LCB.Demo.DrawDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class CustomView extends View{
	
	//画矩形和圆的画笔和颜色
	Paint paint1;
	int color1;
	//画Path1的画笔和颜色
	Paint paint2;
	int color2;
	//画Path2的画笔和颜色
	Paint paint3;
	int color3;
	
	//组合图形
	Path path1,path2;
	
	//需要在程序中绘制的图形
	Bitmap bitmap;

	//如果不用在布局文件中定义View,则实现此构造函数就足够了---在此处准备好绘图使用的所有对象和数据
	public CustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//设置白底
		setBackgroundColor(Color.WHITE);
		
		//初始化画笔和颜色;
		color1=Color.BLUE;
		paint1=new Paint();
		paint1.setStyle(Style.FILL);
		paint1.setStrokeWidth(5);    //实芯画笔的宽度无效
		paint1.setColor(color1);
		paint1.setAlpha(255);
		
		color2=Color.argb(255, 200, 200, 0);
		paint2=new Paint();
		paint2.setStyle(Style.STROKE); //线条画笔可以设置宽度
		paint2.setStrokeWidth(5);
		paint2.setColor(color2);

		color3=getResources().getColor(R.color.mycolor); //从资源文件中获取颜色值
		paint3=new Paint();
		paint3.setStyle(Style.STROKE);
		paint3.setStrokeWidth(3);
		paint3.setColor(color3);
		paint3.setTextSize(70);
		

		//建立组合图形对象，画三角形
		path1=new Path();
		path1.moveTo(80, 90); //画图起始位置
		path1.lineTo(10, 180);
		path1.lineTo(140, 180);
		path1.lineTo(80, 90);
		
		//建立组合图形对象，画椭圆和线段
		path2=new Path();
		RectF rec=new RectF();
		rec.set(150, 90, 200, 180);
		path2.addOval(rec, Direction.CW); 
		path2.moveTo(150, 135);
		path2.lineTo(250, 135);
		Shader mShader=new LinearGradient(150, 90, 200, 180,    //渐变色范围
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},   //色值 
                null, //定义每个颜色处于的渐变相对位置,如果为null表示所有的颜色按顺序均匀的分布
                Shader.TileMode.REPEAT); //颜色变化的方式
		paint3.setShader(mShader); //设置渐变色,此处设置后,前面的setColor就无效了
		
		//从资源中获取图形
		bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.test)).getBitmap();
	}

	//所有的绘图操作均在此进行
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//画出矩形和圆形
		canvas.drawRect(10, 10, 150, 80, paint1); //左上角坐标，右下角坐标
		canvas.drawCircle(200, 40, 30, paint1); //圆心坐标，半径
		
		
		//画出组合图象和文本
		canvas.drawPath(path1, paint2);
		
		paint1.setTextSize(18); //设置画笔的字体大小
		canvas.drawTextOnPath("1234567890123456789012345", path1, 10, 10, paint1);//Path上写字
		
		canvas.drawPath(path2, paint3); //利用渐变色绘制Path2--椭圆+线段
		
		
		//画出文本部分--渐变色写文本
		canvas.drawText("测试文本", 20, 270, paint3);
		
		//画图形部分
		paint3.setAlpha(120);  //改变图片的透明度, 0--完全透明, 255--完全不透明
		//画原始图形
		canvas.drawBitmap(bitmap, 40, 300, paint3);
		
		//缩放/裁剪图形
		Matrix matrix=new Matrix();  //图片缩放的矩阵
		matrix.postScale((float)0.8,(float)0.8);  //图片缩小到0.8倍
//		matrix.postScale((float)1.1,(float)1.1);  //图片放大到1.1倍
		
		//根据给定的矩阵大小放缩图像--Filter改善图像质量
		//sourceBitmap: 产生子位图的源位图；
		//x int: 子位图第一个像素在源位图的X坐标
		//y int: 子位图第一个像素在源位图的y坐标
		//width int: 子位图每一行的像素个数
		//height int:子位图的行数
		//m Matrix: 对像素值进行变换的可选矩阵
		//filter boolean: 如果为true，源图要被过滤
		Bitmap bp=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		//取出图片的一部分
		//Bitmap bp=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/2, bitmap.getHeight()/2); 
		
		//显示缩放后的图形
		canvas.drawBitmap(bp, 190, 300, paint3); //在指定的坐标处绘制图形
	}

}
