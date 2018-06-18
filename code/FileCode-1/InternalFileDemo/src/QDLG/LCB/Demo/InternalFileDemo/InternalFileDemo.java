package QDLG.LCB.Demo.InternalFileDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class InternalFileDemo extends Activity {
	
	//定义对象变量
	private TextView info;
	private EditText readText,writeText;
	private CheckBox append;
	private Button readBtn,writeBtn;
	
	//要读写的文件名
	private String fileName="DemoFile.txt";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取控件对象
        info=(TextView)findViewById(R.id.info);
        readText=(EditText)findViewById(R.id.read_content);
        writeText=(EditText)findViewById(R.id.write_content);
        append=(CheckBox)findViewById(R.id.append);
        readBtn=(Button)findViewById(R.id.read);
        writeBtn=(Button)findViewById(R.id.write);
        
        //写文件操作
        writeBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//为写文件打开的输出流对象
				FileOutputStream fos=null;
				try {  //可能产生例外,此处必须用Try块包围
					if (append.isChecked())				
						fos=openFileOutput(fileName, Context.MODE_APPEND);
					else
						fos=openFileOutput(fileName, Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					//写入输出流
					fos.write(writeText.getText().toString().getBytes());
					
					//写入文件
					fos.flush();
					fos.close();
					
					info.setText("文件写入成功，写入长度："+writeText.getText().toString().length()); //提示
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        //读文件操作
        readBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//为读入文件内容而设置的输入流对象
				FileInputStream fis=null;
				
				try {
					fis=openFileInput(fileName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//存放读入文件内容的数组
				byte[] readBytes=null;
				try {
					readBytes = new byte[fis.available()]; //根据文件大小创建数组
					while(fis.read(readBytes) != -1){  //读入文件内容直至文件末尾，读入的数据以字节数组大小为参考；也可以加上2个参数表明读入内容的块大小
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				//显示文件内容
				readText.setText(new String(readBytes)); //字节数组--〉字符串
				info.setText("文件读取成功，文件长度："+readBytes.length);				
			}
		});
        
    }
}