package QDLG.LCB.Demo.SharedPreferencesDemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SharedPreferencesDemo extends Activity {
		
	//类内部成员变量
	private long number;
	private String name;
	private int age;
	
	//SharedPreference的文件名称和存取模式
	public static final String PREFERENCE_NAME="testpf";
	public static final int PREFERENCE_MODE=Context.MODE_WORLD_READABLE + 
											Context.MODE_WORLD_WRITEABLE;
	//控件变量
	private EditText et_num;
	private EditText et_name;
	private EditText et_age;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //获取控件对象
        et_num=(EditText)findViewById(R.id.number);
        et_name=(EditText)findViewById(R.id.name);
        et_age=(EditText)findViewById(R.id.age);
        
        Button exit=(Button)findViewById(R.id.exit);
        //退出应用程序
        exit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish(); //结束当前应用程序--》退出
			}
		});        
    }

	@Override
	protected void onStart() { //显示界面之前，读取存储的数据，更新控件内容
		// TODO Auto-generated method stub
		super.onStart();
		
		//获取存储的数据
		loadSharedPreference();
		//更新界面
		et_num.setText(number+"");
		et_name.setText(name);
		et_age.setText(age+"");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		//存储数据
		saveSharedPreference();
	}
    
	//读取数据
    private void loadSharedPreference(){
    	
    	//获取SharedPreferences对象
    	SharedPreferences sp=getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	
    	//读取数据
    	number=sp.getLong("Number", 0);
    	name=sp.getString("Name", "");
    	age=sp.getInt("Age", 0);
    }
    
    //保存数据
    private void saveSharedPreference(){    	
    	//获取SharedPreferences对象
    	SharedPreferences sp=getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
    	//获取Editor对象
    	SharedPreferences.Editor editor=sp.edit();
    	
    	//写入数据
    	editor.putLong("Number", Long.parseLong(et_num.getText().toString()));
    	editor.putString("Name", et_name.getText().toString());
    	editor.putInt("Age", Integer.parseInt(et_age.getText().toString()));
    	
    	//提交保存
    	editor.commit();
    }
}