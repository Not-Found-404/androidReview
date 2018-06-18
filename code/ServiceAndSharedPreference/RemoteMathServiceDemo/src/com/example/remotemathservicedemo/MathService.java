package com.example.remotemathservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


public class MathService extends Service {
	
    //通过使用IMathService.java内部的Stub类实现
	private final IMathService.Stub mBinder = new IMathService.Stub() {
		
	  public long Add(long a, long b) { //逐一实现在IMathService.aidl接口文件定义的函数
		  System.out.println("进行加法运算 " + a + "+" + b + "=" + (a+b));
		  return a + b; 
	  }
	  
	}; 
	
	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("远程绑定：MathService");
		return mBinder;
	}
	
	@Override
	public boolean  onUnbind  (Intent intent){
		System.out.println("取消远程绑定：MathService");
		return false;
	}

}
