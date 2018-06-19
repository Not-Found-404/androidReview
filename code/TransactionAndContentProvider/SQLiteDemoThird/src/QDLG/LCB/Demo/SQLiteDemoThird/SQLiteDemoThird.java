package QDLG.LCB.Demo.SQLiteDemoThird;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteDemoThird extends Activity {
	
	//数据库操作对象
	private DBAdapter db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //显示信息用的TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //回滚设置
        final CheckBox rollback=(CheckBox)findViewById(R.id.rollback);
        
        //创建数据库适配对象
        db=new DBAdapter(SQLiteDemoThird.this);
        //创建数据库
        if (!db.createDB()){
        	Toast.makeText(SQLiteDemoThird.this, "创建数据库失败!", Toast.LENGTH_SHORT).show();
        	return;
        }
        db.insert(); //预先插入三条数据
        
        //查询按钮处理
        ((Button)findViewById(R.id.query)).setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				info.setText(db.query()); //显示查询结果
				Toast.makeText(SQLiteDemoThird.this, "查询操作完成!", Toast.LENGTH_SHORT).show();
			}
		});
        
        //提交事务的按钮处理
        ((Button)findViewById(R.id.commit)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//提交事务，同时设置是否回滚标识
				db.execTransaction(rollback.isChecked());
				Toast.makeText(SQLiteDemoThird.this, "事务操作完成!", Toast.LENGTH_SHORT).show();
			}
		});
    }
}