package QDLG.LCB.Demo.SharedPreferencesDemoSecond;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class SharedPreferencesDemoSecond extends Activity {
	
	//SharedPreference的包名
	public static final String PREFERENCE_PACKAGE="QDLG.LCB.Demo.SharedPreferencesDemo";
	//SharedPreference的文件名称和存取模式
	public static final String PREFERENCE_NAME="testpf";
	public static final int PREFERENCE_MODE=Context.MODE_WORLD_READABLE + 
															Context.MODE_WORLD_WRITEABLE;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取指定的运行环境
        Context c=null;
        try {
			c=this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//通过包运行环境获取SharedPreferences对象
		SharedPreferences sp=c.getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	//读取数据
    	long number=sp.getLong("Number", 0);
    	String name=sp.getString("Name", "");
    	int age=sp.getInt("Age", 0);

    	//显示结果
    	TextView info=(TextView)findViewById(R.id.info);
    	String s="学号: "+number +"\n姓名: "+name+"\n年龄: "+age;
    	info.setText(s);
    	
    	//试图修改SharedPreferences的内容，低版本总是失败，4.0版以上可能成功！
    	SharedPreferences.Editor editor=sp.edit();
    	editor.putString("Name", "bbbbb"); 
    	editor.commit();
    }
}