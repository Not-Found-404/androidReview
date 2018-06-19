package com.example.tcpsocketdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	private static final int TCP_SERVER_PORT = 9998; //服务器的TCP端口
	private ServerSocket serverSocket=null; //服务器Socket

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					System.out.println("Server accepting...");
					
					//1、创建服务器Socket接口--此处使用的是服务器的默认ip地址，也可以在构造函数中指定ip地址
					serverSocket = new ServerSocket(TCP_SERVER_PORT);
					//2、监听连接请求--等候--客户端连接成功后，返回专门与客户端通信的Socket对象
         			Socket socket = serverSocket.accept();
					
					System.out.println("Server accepted...");
					
					//3、缓冲方式读取文本
					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));//字节输入流-->字符流-->带缓冲的方式读取文本
					//缓冲方式写入文本
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream()));//字节输出流-->字符流-->带缓冲的方式写文本
					
					System.out.println("Server Read...");
					
//					out.write("test--"); //写入时不等待
//					out.flush();
					
					//缓冲方式读取文本，一次一行--无数据则返回null，不等待--读
					String incomingMsg = in.readLine() + System.getProperty("line.separator"); //读入信息+行分隔符
					
					System.out.println("Server get -- " + incomingMsg);
					
					//要从服务器传输给客户端的信息--写
					String outgoingMsg = "googbye from port " + TCP_SERVER_PORT + 
							System.getProperty("line.separator");
					
					System.out.println("Server writing... "+outgoingMsg);
					
					//准备用BufferWriter发送字符串
					out.write(outgoingMsg);
					//刷新，发送
					out.flush();
					
					//4、关闭套接字
					socket.close();
					
					System.out.println("Server closed...");
					
				} catch (InterruptedIOException e) {//超时错误
					e.printStackTrace();
				} catch (IOException e) {//IO异常
					e.printStackTrace();
				} finally {
					if (serverSocket != null) { //如果创建了ServerSocket，则在此关闭
						try {
							serverSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();		
		
		final EditText et=(EditText)findViewById(R.id.input); //输入要发送数据的EditText
		
		Button btn=(Button)findViewById(R.id.btnTest);
		btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							
							System.out.println("Client connecting...");
							
							//1、创建客户端Socket，同时指出要连接的服务器的名称和端口号--连接服务器
							Socket socket=new Socket("localhost", TCP_SERVER_PORT);
							
							System.out.println("Client connected...");
							
							//2、缓冲方式读取文本
							BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
							//缓冲方式写出文本
							BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							
							//从客户端发送到服务器的字符串
							String outMsg=et.getText().toString()+System.getProperty("line.separator");
							
							System.out.println("Client write... "+outMsg);
							
							//写入，准备发送
							out.write(outMsg);
							//发送
							out.flush();
							
							System.out.println("Client read... ");
							
							//接收服务器发来的数据--无数据则返回null，不等待
							String inMsg=in.readLine()+System.getProperty("line.separator");
							
							System.out.println("Client get...  "+ inMsg);
							
							//3、关闭套接字
							socket.close();
							
							System.out.println("Client closed...");
							
						}catch(UnknownHostException e){
							e.printStackTrace();
						}catch (IOException e){
							e.printStackTrace();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
