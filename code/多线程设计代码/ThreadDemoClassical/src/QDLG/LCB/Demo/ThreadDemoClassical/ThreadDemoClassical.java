package QDLG.LCB.Demo.ThreadDemoClassical;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThreadDemoClassical extends Activity {
	
	//自定义的工作线程对象
	MyThread myThread;
	//自定义的工作线程的Handler--每个Handler对象处理各自不同的消息
	Handler mChildThreadHandler, testChildHandler;
	
	//UI线程接收的消息类型
	private static final int UI_UPDATE_ID1=1;
	private static final int UI_UPDATE_ID2=2;
	//工作线程接收的消息类型
	private static final int  SERVER_DATA1=1;
	private static final int SERVER_DATA2=2;
	
	//界面控件对象
	private EditText info;
	
	//UI线程的Handler
	Handler mainThreadHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	switch (msg.what){ //根据消息类型处理
	    	case UI_UPDATE_ID1:
	    		String data=msg.getData().getString("result");        //从消息中获取数据
	    		updateUI(data);     //在此方法中进行UI更新操作,参数为需要更新的数据
	    		break;
	    	case UI_UPDATE_ID2:
	    		break;
	    	}
	    }
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取界面控件对象
        info=(EditText)findViewById(R.id.info);
        Button start=(Button)findViewById(R.id.btnStart);
        Button request=(Button)findViewById(R.id.btnRequest);
        Button stop=(Button)findViewById(R.id.btnStop);
        
        //开始线程按钮的监听器
        start.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			    //创建线程--- 但是没有启动
		        myThread=new MyThread();
				//设置线程的属性
				myThread.setName("WorkerThread"); //线程名称
				myThread.setPriority(Thread.MIN_PRIORITY);  //工作线程低优先级运行
				//启动线程
				myThread.start();
				
				Toast.makeText(ThreadDemoClassical.this, "线程已经启动!", Toast.LENGTH_SHORT).show();
			}
		});
        
        //请求数据处理按钮的监听器
        request.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//通过向工作线程发送消息来调用工作线程完成数据处理--第一个Handler
		        Message msg = mChildThreadHandler.obtainMessage();
		        msg.what=SERVER_DATA1; //消息类型
	            Bundle b = new Bundle();
	            b.putString("info", info.getText().toString() + "-1-");
	            msg.setData(b);  //消息中携带要处理的数据
		        mChildThreadHandler.sendMessage(msg);
		        
		        //通过向工作线程发送消息来调用工作线程完成数据处理--第二个Handler
		        msg = testChildHandler.obtainMessage();
		        msg.what=SERVER_DATA1; //消息类型
	            Bundle b2 = new Bundle();
	            b2.putString("info", info.getText().toString() + "-2-");
	            msg.setData(b2);  //消息中携带要处理的数据
		        testChildHandler.sendMessage(msg);
			}
		});
        
        //停止线程按钮的监听器
        stop.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//停止消息循环, 也就停止了线程
				mChildThreadHandler.getLooper().quit();		
			}
		});
        
    }
    
    //自定义的线程类
    private class MyThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			InitWorkThread(); //此调用初始化线程工作环境
        	
        	Looper.prepare(); //准备消息循环--创建了一个Looper对象
        	
        	//此处创建工作线程的Handler，会自动绑定上面建立的消息循环
        	mChildThreadHandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					
					//存放线程数据的变量
					String data;
					
					switch (msg.what){ //根据消息类型分别处理
					case SERVER_DATA1:
						data=msg.getData().getString("info"); //从消息中获取数据
					
			            data=data+data;
			            
			            //通过消息向UI线程返回结果
			            Message m = mainThreadHandler.obtainMessage();
			            m.what=UI_UPDATE_ID1; //消息ID
			            Bundle b = new Bundle();
			            b.putString("result", data);  
			            m.setData(b);    // 向消息中添加结果数据
			            mainThreadHandler.sendMessage(m);    // 向主线程发送消息，更新UI
						break;
					case SERVER_DATA2:
						break;
					}					
				}        		
        	};
        	
        	testChildHandler=new Handler(){//当前自定义线程的第二个Handler
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					
					String data=msg.getData().getString("info"); //从消息中获取数据
					data="test--"+data;
		            doStuff();         // 该方法中执行耗时操作,处理UI线程传来的数据
		            
					//通过消息向UI线程返回结果
		            Message m = mainThreadHandler.obtainMessage();
		            m.what=UI_UPDATE_ID1; //消息ID
		            Bundle b = new Bundle();
		            b.putString("result", data);  
		            m.setData(b);    // 向消息中添加结果数据
		            mainThreadHandler.sendMessage(m);    // 向主线程发送消息，更新UI
				}        		
        	};
            
        	//初始化, 准备消息循环, 创建Handler对象后  -->  进入工作线程的消息循环
            Looper.loop();
		}
    	
    }
    
    //工作线程中的初始化操作
    private void InitWorkThread(){  
    	try {
			Thread.sleep(100);  //延时0.1秒，模拟线程运行环境的初始化操作
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //在工作线程中的数据处理操作
    private void doStuff(){  
    	try {
			Thread.sleep(3000); //延时3秒, 模拟耗时操作
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //UI线程中更新界面
    private void updateUI(String data){    	
    	info.setText(data);
    	System.out.println(data);
    }
}