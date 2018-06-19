package com.example.udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private static final int MAX_UDP_DATAGRAM_LEN = 1024;
	private static final int UDP_SERVER_PORT = 9999;
		
	//用于接收数据报的字节数组
	private byte[] Msg;
	//数据报
	private DatagramPacket dp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//创建接收数据报的数组
				Msg=new byte[MAX_UDP_DATAGRAM_LEN];
				//创建数据报对象
				dp=new DatagramPacket(Msg, Msg.length);
				
				DatagramSocket ds=null;
				try{
					//创建一个服务器端的UDP Socket--需要指明端口（IP地址使用本机默认IP地址），也可以在此指明ip地址
					ds=new DatagramSocket(UDP_SERVER_PORT);
					
					System.out.println("Ready to receive...");
					//等待接收UDP数据报--无数据则阻塞
					ds.receive(dp);
					
					//从数据包中取出收到的数据--substring(0,dp.getLength())能够去除多余的无用符号
					System.out.println("Received---"  + new String(dp.getData()).substring(0,dp.getLength()));
					
				}catch(SocketException e){
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if (ds!=null){
						ds.close();
						System.out.println("Close Server socket.");
					}
				}
			}
		}.start();
		
		final EditText et=(EditText)findViewById(R.id.input);
		Button btnTest=(Button)findViewById(R.id.btnTest);
		btnTest.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						DatagramSocket ds=null;
						try{
							//创建客户端的UDP Socket--一般不需要指定端口
							ds=new DatagramSocket();
							
							//初始化一个服务器地址对象
							InetAddress serverAddr=InetAddress.getByName("localhost");
							
							DatagramPacket dp;
							String info=et.getText().toString();
							//创建数据报对象--指出要发送的数据、数据长度、服务器地址 和 端口
							dp=new DatagramPacket(info.getBytes(), info.length(), serverAddr, UDP_SERVER_PORT);
							
							System.out.println("Send---"+info);
							//发送数据报
							ds.send(dp);
							
						}catch(SocketException e){
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							if (ds!=null)
							{
								ds.close();
								System.out.println("Close Client socket.");
							}
						}
					}					
				}.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
