package qdlg.lcb.demo.ThreadDemoJava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThreadDemoJava extends Activity {
	
	//按钮控件变量
	private  Button start,end;
	
	//线程对象
	private  MyThread t;
	
	//线程是否退出标志变量, Thread.interrupt()不能判断终止
	private static Boolean toExit;
	private static synchronized Boolean getExitFlag(){ //获取互斥变量的值
		return toExit;
	}
	private static synchronized void setExitFlag(Boolean exit){ //设置互斥变量的值
		toExit=exit;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取控件对象
        start=(Button)findViewById(R.id.startThread);
        end=(Button)findViewById(R.id.stopThread);

        //创建并开始运行线程
        start.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//退出标志设为假--开始时不退出
				setExitFlag(false);
				
				//创建线程
		        t=new MyThread();
		        t.setName("WorkerThread"); //设置线程名字
		        t.setPriority(Thread.MIN_PRIORITY);  //设置线程为低优先级
		        t.start();   //运行线程
			}
		});
        
        //结束线程的运行
        end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setExitFlag(true); //设置退出标志
			}
		});
        
    }
    
    //线程类
    private class MyThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			int cnt=1;  //循环次数计数器
			
			//线程循环提供服务, 退出标志为真时退出循环
			while (!getExitFlag()){
 	
				//输出循环的运行次数提示
				System.out.println("Thread is running..." + cnt++);
				
				try {
					Thread.sleep(1000); //延时1秒, 模拟大量费时操作
				}
				catch (InterruptedException e) {
						// TODO Auto-generated catch block
				    e.printStackTrace();
				}
			}
			
		}
    	
    }
}