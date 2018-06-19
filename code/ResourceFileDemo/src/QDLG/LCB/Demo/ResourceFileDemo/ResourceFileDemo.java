package QDLG.LCB.Demo.ResourceFileDemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResourceFileDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //显示结果的TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //获取资源管理的实例
		final Resources resources=getResources();
        
        //原始文件操作
        Button raw=(Button)findViewById(R.id.rawBtn);
        raw.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				InputStream input=null;
				input=resources.openRawResource(R.raw.test); //获取指定资源文件的输入流对象
				try { 
					//读取文件内容,操作类似于FileInputStream
					byte[] reader=new byte[input.available()]; //必须用try块包围
					String out="";
					while (input.read(reader)!=-1){
						out+=new String(reader,"GBK");
						//第二个参数为编码,必须与raw文件的属性中的编码相符,否则乱码
					}
					
					//显示文件内容
					info.setText(out);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{ //关闭输入流对象
					if (input!=null){
						try {
							input.close(); //必须用try块包围
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
        
        //xml文件操作
        Button xml=(Button)findViewById(R.id.xmlBtn);
        xml.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//获取XML解析器
				XmlPullParser parser=resources.getXml(R.xml.people);
				
				String msg=""; //显示字符串
				String people=null; //People节点名字
				//元素的属性值
				String name=null; 
				String age=null;
				String height=null;
				// 从xml文件分析出的元素的名称和值
				String attrName;
				String attrValue;
				
				try {
					int result=parser.next();   //读取一个节点
					
					while (result!=XmlPullParser.END_DOCUMENT){ //一直扫描到文件末尾
						
						people=parser.getName(); //获取当前节点的名字
						
						//处理一个元素----一行,
						//START_TAG, 开始处理一行时解析如下内容,
						if (people!=null && 	people.equals("person")  && 
								result==XmlPullParser.START_TAG){ //如果此处不加上限制条件,则解析完一行时也会触发该事件
							
							int count=parser.getAttributeCount(); //获取属性个数
							
							for (int i=0; i<count; i++){ //按序号获取当前节点的每个属性值
								
								//取出当前节点的名字和值
								attrName=parser.getAttributeName(i);
								attrValue=parser.getAttributeValue(i);
								
								//获取属性值-->不同的属性值赋值给不同的变量
								if ((attrName!=null) && attrName.equals("name"))
									name=attrValue;
								else if ((attrName!=null) && attrName.equals("age"))
									age=attrValue;
								else if ((attrName!=null) && attrName.equals("height"))
									height=attrValue;
							}
							//形成要显示的字符串
							msg+="姓名: "+name+", 年龄: "+age+", 身高: "+height+"\n";
						}
						result=parser.next();
					}
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//显示内容
				info.setText(msg);
			}
		});
    }
}