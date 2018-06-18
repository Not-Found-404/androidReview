package QDLG.LCB.Demo.ThreadDemoHandlerThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ThreadDemoHandlerThread extends Activity {
	
	//线程对象
	HandlerThread ht;
	//Handler对象--分别用于工作线程和UI线程,也可以将Handler类分开写
	MyHandler myHandler,mainHandler;
	
	//如果用同一个Handler类接收主线程和工作线程的消息--必须区分ID,实际使用时推荐定义2个不同的Handler子类来分别接收2种消息
	private static final int MAIN_THREAD=1;
	private static final int WORKER_THREAD=2;	
	
	//线程是否退出标志变量
	private static Boolean toExit;
	private static synchronized Boolean getExitFlag(){ //获取互斥变量的值
		return toExit;
	}
	private static synchronized void setExitFlag(Boolean exit){ //设置互斥变量的值
		toExit=exit;
	}
	
	//按钮对象变量
	private Button btnStart,btnStop;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        //输出主线程的ID
        System.out.println("UI-->Primary thread ID-->"+Thread.currentThread().getId());
        
        //获取按钮对象
        btnStart=(Button)findViewById(R.id.startThread);
        btnStop=(Button)findViewById(R.id.stopThread);
        
        //获取主线程的Handler--不带参数的构造函数在哪个线程里执行，则此Handler就绑定哪个线程的消息循环
        mainHandler=new MyHandler();
        
        //启动线程的按钮的监听器
        btnStart.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//创建工作线程
				ht=new HandlerThread("WorkerThread");
				setExitFlag(false); //设置退出标识
				ht.start(); //启动工作线程
				//通过获取Looper对象建立该线程对应的Handler，并将Handler对象与该消息循环绑定
				myHandler=new MyHandler(ht.getLooper()); 
				
				//向工作线程发送消息
				Message msg=myHandler.obtainMessage(); //获取消息实例
				msg.what=WORKER_THREAD; //设置消息ID
		        Bundle b=new Bundle();  //通过Bundle对象传递数据(字符串类型的键值对)
		        b.putInt("age", 20);
		        b.putString("name", "ZhangSan");
		        msg.setData(b);
		        myHandler.sendMessage(msg);  //发送消息
		        
		        //向工作线程发送Runnable对象----在对应的工作线程中运行Runnable对象
		        myHandler.post(myThread);
			}
		});
        
        //停止线程按钮的监听器
        btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setExitFlag(true); //设置退出标志变量
				
				//退出消息循环就结束了HandlerThread
				//若此处不结束消息循环，则会将之前积累未处理的消息一一处理完毕
				ht.getLooper().quit(); 
			}
		});

    }
    
    //自定义的Handler类--可以处理绑定线程传来的消息
    private class MyHandler extends Handler{
		public MyHandler(){
    	}
    	public MyHandler(Looper looper){
    		super(looper);
    	}
    	//处理当前Handler对象绑定的消息队列中收到的消息
		@Override
		public void handleMessage(Message msg) { 
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			//显示线程的ID---回调函数所在的线程决定于调用者线程
			System.out.println("handleMessage: Thread ID--"+Thread.currentThread().getId());			

			//根据消息ID区分是哪个线程传来的消息
			switch(msg.what){
				case MAIN_THREAD: //主线程传来的消息，此分支在主线程中执行
					int result=msg.arg1+msg.arg2;
					System.out.println("Main-thread Data: "+result);
					break;
				case WORKER_THREAD:  //工作线程传来的消息，此分支在工作线程中执行
					Bundle b=msg.getData();
					int age=b.getInt("age");
					String name=b.getString("name");
					System.out.println("Worker-thread Data: "+name+"--"+age+"--"+msg.arg1);
					break;
			}
		}
    }
    
    //工作线程的运行的主体部分
    Runnable myThread=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub		

			//输出当前线程的ID
			System.out.println("Runnable: Thread ID--"+Thread.currentThread().getId());
	       
			int cnt=0; //计数变量 
			
			//循环处理--主体部分
			while (!getExitFlag()){ 
				
		        try
		        {
		        	Thread.sleep(3000);  //延时3秒,模拟耗时操作
		        }
		        catch(InterruptedException e)
		        {
		        	e.printStackTrace();
		        }

		        //给工作线程（自己 --〉自己）发送消息
		        Message msg=myHandler.obtainMessage();
		        msg.what=WORKER_THREAD;
		        Bundle b=new Bundle();
		        b.putInt("age", 21);
		        b.putString("name", "LiSi");
		        msg.setData(b);
		        msg.arg1=cnt;
		        //消息的发送者/接受者位于同一个线程，消息不会被及时处理，而是累积在消息队列中
		        msg.sendToTarget(); 
		        
		        //给主线程发送消息
			    msg=mainHandler.obtainMessage();
			    msg.what=MAIN_THREAD;
			    msg.arg1=cnt++;
			    msg.arg2=100;
			    msg.sendToTarget(); //主线程会及时处理消息
			}
		}    	
    };
}