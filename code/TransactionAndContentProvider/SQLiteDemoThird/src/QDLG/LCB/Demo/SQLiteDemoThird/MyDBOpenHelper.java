package QDLG.LCB.Demo.SQLiteDemoThird;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MyDBOpenHelper extends  SQLiteOpenHelper{
	//数据库基本信息
	private static final String DB_NAME="people.db";   //数据库名
	private static final String DB_TABLE="info";  //表名
	private static final int   DB_VERSION=1;	  //数据库版本
	//字段名称
	public static  final  String KEY_ID="id";   
	public static  final  String KEY_NAME="name";
	public static  final  String KEY_AGE="age";
	public static  final  String KEY_HEIGHT="height";
	
	//必须重写的构造函数
	public MyDBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	//必须重写的创建数据库处理
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		String createSQL="create table "+DB_TABLE+" ("+KEY_ID+" integer primary key autoincrement,"
		+KEY_NAME+" text not null,"+KEY_AGE+" integer,"+KEY_HEIGHT+" float);";
		arg0.execSQL(createSQL); //执行创建语句
	}

	//必须重写的升级数据库处理
	//三个参数: 数据库对象,旧版本号,新版本号
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS "+DB_TABLE); //一般情况下不能直接删除数据,先保存...
        onCreate(arg0);
	}

	//可选的方法---每次成功打开数据库后首先被执行
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}
}
