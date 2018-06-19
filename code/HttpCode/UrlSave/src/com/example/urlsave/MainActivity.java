package com.example.urlsave;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv=(TextView)findViewById(R.id.info);
		final Button btn=(Button)findViewById(R.id.btn);
		
		//防止与版本相关的错误
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		
		btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//放到新线程中进行网络操作
				new Thread(){
					@Override
					public void run() {
						try {
							//要访问的网页的网址--一个免费的天气预报webservice
							URL url=new URL("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl");
							
							//打开Http链接
							URLConnection connect = url.openConnection();
							
							//建立Http链接的输入流对象  InputStream（字节流）-->DataInputStream（基本类型数据流）-->BufferedReader（带缓冲的字符流）
							DataInputStream dis=new DataInputStream(connect.getInputStream());
							//建立带缓冲的文本输入对象
							BufferedReader in = new BufferedReader(new InputStreamReader(dis, "UTF-8"));
							
							//逐行读入网页文本
							String html="";
							String readline=null;
							while ((readline=in.readLine())!= null){
								html+=readline;
							}
							
							System.out.println("HTML page: \r\n" + html);
																					
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							System.out.println(e.toString());
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

}
