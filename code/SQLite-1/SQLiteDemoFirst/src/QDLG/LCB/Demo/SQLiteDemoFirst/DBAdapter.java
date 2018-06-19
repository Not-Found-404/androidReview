package QDLG.LCB.Demo.SQLiteDemoFirst;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
	//数据库基本信息
	private static final String DB_NAME="people.db";   //数据库名--打开/创建数据库时需要给出全路径名
	private static final String DB_TABLE="info";  //表名
	private static final int   DB_VERSION=1;	  //数据库版本
	//字段名称
	public static  final  String KEY_ID="id";   
	public static  final  String KEY_NAME="name";
	public static  final  String KEY_AGE="age";
	public static  final  String KEY_HEIGHT="height";
	
	private SQLiteDatabase db;  //数据库对象
	private Context context; //记住由构造函数传入的上下文对象
	
	public DBAdapter(Context context){
		this.context=context;  //记住由构造函数传入的上下文对象
	}
	
	//创建/打开数据库
	public boolean createDB(String name){
		try{
			// 打开/创建 指定文件夹下的数据库,需要相应路径的写权限--私有文件夹下面，/data/data/包名
			db=SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()+"/../"+ DB_NAME, null);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	//创建表
	/*create table info (id integer primary key autoincrement, name text not null, age integer, height float);*/
	public boolean createTable(){
		String createSQL="create table "+DB_TABLE+" ("+KEY_ID+" integer primary key autoincrement,"
			+KEY_NAME+" text not null,"+KEY_AGE+" integer,"+KEY_HEIGHT+" float);";
		try{
			db.execSQL(createSQL); //执行创建语句
		}catch(SQLException e){
			return false;
		}		
		return true;
	}
	
	//添加新数据,插入3条记录
	/*insert into [info] values(null,'Tom',21,1.75); ......*/
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
			//取出本行各列的值--getXXX(colIndex)
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
	/*update [info] set height=1.85 where name=?;    "Lily" */
	public boolean update(){
		String updateSQL="update ["+DB_TABLE+"]  set "+KEY_HEIGHT+
			"=1.85 where "+KEY_NAME+"=?;";  //使用占位符
	try{
		db.execSQL(updateSQL,new String[]{"Lily"}); //执行修改语句，给出占位符对应的数据
		}catch(SQLException e){
			return false;
		}		
	return true;
	}
	
	//删除
	/* delete from [info] where age<=21; */
	public boolean delete(){
		String deleteSQL="delete from ["+DB_TABLE+"] where "
			+KEY_AGE+"<=21;";
	try{
		db.execSQL(deleteSQL); //执行删除语句
		}catch(SQLException e){
		return false;
		}		
	return true;
	}
	
	//删除表
	/* drop table [info]; */
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
			db=null; //无意义的指针型变量要置空
		}
	}
}
