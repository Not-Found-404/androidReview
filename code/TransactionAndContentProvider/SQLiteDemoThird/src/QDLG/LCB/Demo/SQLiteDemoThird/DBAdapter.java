package QDLG.LCB.Demo.SQLiteDemoThird;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {
	//数据库基本信息
	private static final String DB_NAME="people.db";   //数据库名
	private static final String DB_TABLE="info";  //表名
	private static final int   DB_VERSION=1;	  //数据库版本
	//字段名称
	public static  final  String KEY_ID="id";   
	public static  final  String KEY_NAME="name";
	public static  final  String KEY_AGE="age";
	public static  final  String KEY_HEIGHT="height";
	
	private SQLiteDatabase db=null;  //数据库对象
	private Context context; //记住由构造函数传入的上下文对象
	
	//数据库辅助操作对象
	private MyDBOpenHelper helper;
	
	public DBAdapter(Context context){
		this.context=context;  //记住由构造函数传入的上下文对象
		helper=new MyDBOpenHelper(context, DB_NAME, null, DB_VERSION); //若在此处更改数据库版本,则会出发升级动作
	}
	
	//创建数据库
	public boolean createDB(){
		try{
			//打开/创建指定文件夹下的数据库,需要相应路径的写权限
			db=helper.getWritableDatabase();
		}catch(SQLiteException e){
			return false;
		}
		return true;
	}
	
	//创建表
	public boolean createTable(){		
		return true;
	}
	
	//添加新数据,插入3条记录
	public boolean insert(){
		String insertSQL="insert into ["+DB_TABLE+"]  values(null,'Tom',21,1.75);";
		try{
			db.execSQL(insertSQL); //执行语句
			insertSQL="insert into ["+DB_TABLE+"]  values(null,'Jack',22,1.80);";
			db.execSQL(insertSQL);
			insertSQL="insert into ["+DB_TABLE+"]  values(null,'Lily',20,1.70);";
			db.execSQL(insertSQL);
		}catch(SQLException e){
			return false;
		}		
		return true;
	}
	
	//查询数据
	public String query(){
		String rlt=""; //结果字符串
		
		//查询---可以使用占位符--SQL中的?对应后面字符串数组中的字符串,此处不用,第二个参数设置为null
		Cursor cursor=null;
		cursor=db.rawQuery("select * from ["+DB_TABLE+"]", null); //如果要查询的表不存在则会产生异常
		if (cursor.getCount()==0) //查询结果为空则返回
			return "查询结果为空!";
		while (cursor.moveToNext()){ //循环取出游标中的查询结果
			//取出本行各列的值
			int id=cursor.getInt(0);
			String name=cursor.getString(1);
			int age=cursor.getInt(2);
			float height=cursor.getFloat(3);
			//写到结果字符串中
			rlt+=id+"\t"+name+"\t"+age+"\t"+height+"\n";
		}; //继续取下一行
		
		cursor.close(); //关闭游标
		
		return rlt;
	}
	
	//修改
	public boolean update(){
		String updateSQL="update ["+DB_TABLE+"]  set "+KEY_HEIGHT+
			"=1.85 where "+KEY_NAME+"=\"Lily\";";
//		String updateSQL="update ["+DB_TABLE+"]  set "+KEY_HEIGHT+
//			"=1.85 where "+KEY_NAME+"=?;";  //使用占位符
	try{
		db.execSQL(updateSQL); //执行修改语句
//		db.execSQL(updateSQL,new String[]{"Lily"}); //给出占位符对应的数据
		}catch(SQLException e){
			return false;
		}		
	return true;
	}
	
	//删除
	public boolean delete(){
		String deleteSQL="delete from ["+DB_TABLE+"] ;";
	try{
		db.execSQL(deleteSQL); //执行删除语句
		}catch(SQLException e){
		return false;
		}		
	return true;
	}
	
	//删除表
	public boolean drop(){
		String dropSQL="drop table ["+DB_TABLE+"] ;";
		try{
			db.execSQL(dropSQL); //执行删除表语句
			}catch(SQLException e){
				return false;
			}
			
		return true;
	}
	
	//关闭数据库对象
	public void close(){
		if (db!=null){
			db.close();
			db=null;
		}
	}
	
	//完成事务
	public  void execTransaction(boolean rollback){
		db.beginTransaction();  //开始事务
		update();
		delete(); //全部删除数据
		if (!rollback) //模拟检测条件，如果不回滚则设置事务完成,在结束事务时会正确提交
			db.setTransactionSuccessful(); //如不执行此项,则在结束事务时会回滚事务
		db.endTransaction(); //结束事务
	}
}
