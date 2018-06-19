package QDLG.LCB.Demo.SQLiteDemoFirst;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SQLiteDemoFirst extends Activity {
	//数据库适配对象,用于操作数据库
	DBAdapter db=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //创建数据库适配对象
        db=new DBAdapter(this);
        
        //创建数据库
        if (!db.createDB("test.db")){
        	Toast.makeText(SQLiteDemoFirst.this, "创建数据库失败!", Toast.LENGTH_SHORT).show();
        	return;
        }
        
        //显示信息的TextView
        final TextView info=(TextView)findViewById(R.id.info);
        
        //创建表
        ((Button)findViewById(R.id.create)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.createTable())
					info.setText("成功创建表!");
				else
					info.setText("创建表失败!");
			}
		});
        
        //添加数据
        ((Button)findViewById(R.id.insert)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.insert())
					info.setText("成功插入3条数据!");
				else
					info.setText("插入数据失败!");
			}
		});
        
        //查询
        ((Button)findViewById(R.id.query)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				info.setText(db.query());
			}
		});
        
        //修改
        ((Button)findViewById(R.id.update)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.update())
					info.setText("成功修改数据!");
				else
					info.setText("修改数据失败!");
			}
		});
        
        //删除
        ((Button)findViewById(R.id.delete)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.delete())
					info.setText("成功删除数据!");
				else
					info.setText("删除数据失败!");
			}
		});
        
        //删除表
        ((Button)findViewById(R.id.drop)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (db.drop())
					info.setText("成功删除表!");
				else
					info.setText("删除表失败!");
			}
		});
        
    }
    
    //退出程序时关闭数据库
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
		db=null;
	}
}