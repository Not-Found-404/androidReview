package com.example.newhttpurlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	//界面控件
		Button btnTest;
		TextView tvTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnTest=(Button)findViewById(R.id.btn);
		tvTest=(TextView)findViewById(R.id.txt);
		
		btnTest.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			//在新线程中进行网络操作	
            new Thread(){
            	@Override
				public void run() {
				try {
					//发送Post请求到百度服务器
					HttpURLConnectUtils http=new HttpURLConnectUtils();
					String rlt=http.DoHttpPost("http://www.baidu.com");
					
					//输出返回的结果
					System.out.println(rlt);
					
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
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

	//利用HttpURLConnection联系Http服务器
		public class HttpURLConnectUtils {
			
		    private HttpURLConnectUtils() {
		    }

		    //post
		    public String DoHttpPost(String mUrl) throws IOException {

		        URL url = new URL(mUrl);
		        //初始化创建HttpURLConnection实例
		        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        
		        //据说如果出现错误“java.io.FileNotFoundException”，此处两行代码用来欺骗百度--让它以为是浏览器访问
//		        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
//		        httpURLConnection.setRequestProperty("Accept","*/*");

		        //设置当前连接的参数
		        httpURLConnection.setConnectTimeout(5000);//推荐设置网络延时，如果不设置此项，获取响应状态码时可能引起阻塞
		        httpURLConnection.setDoOutput(true); //可读写
		        httpURLConnection.setDoInput(true);
		        httpURLConnection.setUseCaches(false); //不用缓存
		        
		        //设置HttpURLConnection请求头里面的属性
		        //设定传送的内容类型是可序列化的java对象    
		        //(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)  
		        httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
		        
		        //设置请求的方法
		        httpURLConnection.setRequestMethod("POST");//Post请求
		        
		        //创建输出流，此时会隐含的进行connect
		        OutputStream outputStream = httpURLConnection.getOutputStream();
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);   //OutputStream-->ObjectOutputStream
		        
		        String params = new String();
		        //这里简单设置参数，普通字符串 --> application/x-www-form-rulencoded MIME字符串
		        params = "name=" + URLEncoder.encode("李传斌", "GBK");
		        //提交参数
		        objectOutputStream.writeBytes(params);
		        objectOutputStream.flush();
		        objectOutputStream.close();
		        
		        //获取响应的状态码，判断是否请求成功
		        int rltCode = httpURLConnection.getResponseCode();
		        String msg = httpURLConnection.getResponseMessage(); //获取响应状态码的描述,   正常返回"OK"
		        if (rltCode != 200) //判断响应状态是否成功
		        {
		        	System.out.println();
		        	return "Error Code--" + rltCode + ", Error Message--" + msg;
		        }
		        //接收返回值
		        //创建文件流对象, InputStream-->InputStreamReader-->BufferedReader
		        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		        //获取响应数据
		        StringBuilder builder = new StringBuilder();
		        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) { //循环读取所有数据
		            builder.append(s);
		        }
		        
		        return builder.toString();
		    }

		    //get
		    public String DoHttpGet(String mUrl) throws IOException {
		    	
		        URL url = new URL(mUrl);
		        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        httpURLConnection.setConnectTimeout(5000);//推荐设置网络延时
		        httpURLConnection.setRequestMethod("GET");
		        httpURLConnection.connect(); //此处必须显式进行连接

		        //以下同Post
		        //获取响应的状态码，判断是否请求成功
		        int rltCode = httpURLConnection.getResponseCode();
		        String msg = httpURLConnection.getResponseMessage(); //获取响应状态码的描述, "OK"
		        if (rltCode != 200) //判断响应状态是否成功
		        {
		        	System.out.println();
		        	return "Error Code--" + rltCode + ", Error Message--" + msg;
		        }

		        //获取响应内容
		        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		        StringBuilder builder = new StringBuilder();
		        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
		            builder.append(s);
		        }

		        return builder.toString();
		    }
		}
		
}
