package QDLG.LCB.Demo.ExternalFileDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalFileDemo extends Activity {
	
	//控件对象变量
	private EditText input;
	private Button createFolder,createFile,showFolder,writeFile,copyFile,renameFile,readFile,delFile,delFolder;
	private TextView info;
	
	private String folderName; //文件夹名称
	private String fileName; //文件名
	private File file;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取控件对象
        input=(EditText)findViewById(R.id.input);
        createFolder=(Button)findViewById(R.id.createDir);
        createFile=(Button)findViewById(R.id.createFile);
        showFolder=(Button)findViewById(R.id.showDir);
        writeFile=(Button)findViewById(R.id.writeFile);
        copyFile=(Button)findViewById(R.id.copyFile);
        renameFile=(Button)findViewById(R.id.renFile);
        readFile=(Button)findViewById(R.id.readFile);
        delFile=(Button)findViewById(R.id.deleteFile);
        delFolder=(Button)findViewById(R.id.deleteFolder);
        info=(TextView)findViewById(R.id.output);
        
        //判断是否安装了SD卡
        if (!(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))){
        	Toast.makeText(ExternalFileDemo.this, "没有安装SD卡，不能进行文件操作！", Toast.LENGTH_SHORT).show();
        	//没有SD卡,禁用所有按钮
        	createFolder.setEnabled(false);
        	createFile.setEnabled(false);
        	showFolder.setEnabled(false);
        	writeFile.setEnabled(false);
        	copyFile.setEnabled(false);
        	renameFile.setEnabled(false);
        	readFile.setEnabled(false);
        	delFile.setEnabled(false);
        	delFolder.setEnabled(false);
        	return;
        };
        
        //建立文件夹--按指定名字在SD卡上创建一个文件夹，然后在该文件夹下创建ex子文件夹
        createFolder.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//文件夹的名字取自文本框内容
				folderName=Environment.getExternalStorageDirectory()+"/"+input.getText().toString();
				File dir=new File(folderName);
				if (dir.mkdir()) //必须保证有权限,并且没有同名文件夹
					Toast.makeText(ExternalFileDemo.this, "文件夹创建成功!", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ExternalFileDemo.this, "文件夹创建失败!", Toast.LENGTH_SHORT).show();
				//在新建的文件夹下面再建一个子文件夹ex，必须逐级创建
				File dir2=new File(folderName+"/ex/");
				dir2.mkdir();
				}
			});    	
    	
        //创建文件
    	createFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//读写另一个程序的私有文件
//				try {
//					Context c=createPackageContext("QDLG.LCB.Demo.InternalFileDemo", CONTEXT_IGNORE_SECURITY);
//					File dir=c.getFilesDir();
//					String fn=dir.getPath()+"/DemoFile.txt";
//					File privateFile=new File(fn);
//					FileInputStream fis=new FileInputStream(privateFile);
//					byte[] str=new byte[fis.available()];
//					fis.read(str);
//					String info=new String(str,"GBK");
//					Toast.makeText(ExternalFileDemo.this, info, Toast.LENGTH_SHORT).show();
//					FileOutputStream fos=new FileOutputStream(privateFile);
//					fos.write((info+"-ok?").getBytes());
//					fos.flush();
//					fos.close();
//				} catch (NameNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				//文件的名字来自于EditText
				fileName=folderName+"/"+input.getText().toString();
				file=new File(fileName);
				try {
					if (file.createNewFile())  //创建文件
						Toast.makeText(ExternalFileDemo.this, "文件创建成功!", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(ExternalFileDemo.this, "文件创建失败!", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	
        //查看文件夹
    	showFolder.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//查看前面建立的文件夹
				File dir=new File(folderName);
				
				File[] list=dir.listFiles();  //查看文件夹内容，获取文件和子文件夹列表
				String s="";
				for (int i=0; i<list.length; i++){
					if (list[i].isDirectory()) //区分目录和文件
						s+=list[i].getName()+"--Directory\n";
					else
						s+=list[i].getName()+"\n";
					
				info.setText(s);
				}
			}
		});
    	
        //写入文件
    	writeFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				FileOutputStream fos=null;
				
				try {
					fos=new FileOutputStream(file);  //建立输出流对象
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					fos.write(input.getText().toString().getBytes()); //将EditText内容写入文件
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(ExternalFileDemo.this, "写入文件失败!", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				Toast.makeText(ExternalFileDemo.this, "写入文件成功!", Toast.LENGTH_SHORT).show();
			}
		});
    	
        //复制文件
    	copyFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//定义目标文件对象，原文件名后面加上“-copy”
				File copy=new File(fileName.substring(0, fileName.length()-4)+"-copy.txt");
				try {
					copy.createNewFile(); //创建目标文件
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FileOutputStream fos=null;
				FileInputStream fis=null;
				
				try { //创建输入/输出流对象
					fos=new FileOutputStream(copy);
					fis=new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				
				byte[] buff; //缓冲区
				try {
					buff = new byte[fis.available()];
					while (fis.read(buff)!=-1){  //读原文件
					}
					fos.write(buff); //写入目标文件
					fos.flush();
					fos.close();
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Toast.makeText(ExternalFileDemo.this, "复制文件成功!", Toast.LENGTH_SHORT).show();
			}
		});
    	
        //文件改名
    	renameFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//文件名后面加上“2”
				File newFile=new File(fileName.substring(0, fileName.length()-4)+"2.txt");
				if (file.renameTo(newFile)){
					Toast.makeText(ExternalFileDemo.this, "文件改名成功!", Toast.LENGTH_SHORT).show();
					file=newFile;
				}					
				else
					Toast.makeText(ExternalFileDemo.this, "文件改名失败!", Toast.LENGTH_SHORT).show();
			}
		});
    	
        //读文件
    	readFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileInputStream fis=null;
				try {
					fis=new FileInputStream(file); //创建输入流对象
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					byte[] buff=new byte[fis.available()]; //创建缓冲区
					while (fis.read(buff)!=-1){ //读入文件内容
					}
					fis.close();
					info.setText(new String(buff)); //显示文件内容
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	
        //删除文件
    	delFile.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//删除复制产生的文件
				File copy=new File(fileName.substring(0, fileName.length()-4)+"-copy.txt");
				
				if (copy.delete()) //删除文件
					Toast.makeText(ExternalFileDemo.this, "文件删除成功!", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ExternalFileDemo.this, "文件删除失败!", Toast.LENGTH_SHORT).show();
			}
		});
    	
        //删除文件夹
    	delFolder.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//删除前面建立的ex子文件夹
				File dir=new File(folderName+"/ex/");
				
				if (dir.exists() && dir.delete())  //要删除的文件夹必须为空
					Toast.makeText(ExternalFileDemo.this, "文件夹删除成功!", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ExternalFileDemo.this, "文件夹删除失败!", Toast.LENGTH_SHORT).show();
				
				dir=new File(folderName);
				dir.delete();
			}
		});
    }
}