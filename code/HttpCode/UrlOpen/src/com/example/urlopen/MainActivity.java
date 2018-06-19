package com.example.urlopen;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//直接利用浏览器打开网址
		((Button)findViewById(R.id.btnTest)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri=Uri.parse("http://inews.ifeng.com/");
				Intent intent=new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
			}
		});
		
		//利用WebView控件打开网址
		final WebView wv=(WebView)findViewById(R.id.webview);
		((Button)findViewById(R.id.btnTest2)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				wv.loadUrl("http://inews.ifeng.com/"); //手机版凤凰网新闻
				wv.setWebViewClient(new WebViewClient(){ //修正网页显示问题
					@Override
					public boolean shouldOverrideUrlLoading(WebView view,
							String url) {
						
						view.loadUrl(url);
						return true;
						
					}
				});
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
