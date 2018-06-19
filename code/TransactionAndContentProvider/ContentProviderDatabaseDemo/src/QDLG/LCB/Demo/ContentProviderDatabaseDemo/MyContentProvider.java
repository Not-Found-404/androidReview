package QDLG.LCB.Demo.ContentProviderDatabaseDemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

public class MyContentProvider extends ContentProvider{//自定义的ContentProvider类
	//要操作的SQLite数据库对象
	private SQLiteDatabase db;
	//操作数据库的辅助对象
	private MyDBOpenHelper helper;
	
	//在此作初始化工作，准备底层数据操作
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context=getContext(); //获取上下文对象
		helper=new MyDBOpenHelper(context, MyDBOpenHelper.DB_NAME, null, MyDBOpenHelper.DB_VERSION);
		try{
			db=helper.getWritableDatabase(); //打开/创建数据库
		}catch(SQLiteException e){
			return false;
		}
		
		return true; //正确初始化,返回
	}
	
	//创建用于检查URI的UriMatcher对象,UriMatcher.NO_MATCH表示匹配失败时返回-1
	private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
	static{
		//这里的UriMatcher对象可以解析匹配2种类型的URI
		//前2个参数给出要匹配的URI, 第3个参数给出匹配成功后的返回值
		uriMatcher.addURI(ConstValue.AUTHORITY, ConstValue.PATH_MULTIPLE, ConstValue.MULTIPLE_PEOPLE);
		uriMatcher.addURI(ConstValue.AUTHORITY, ConstValue.PATH_SINGLE, ConstValue.SINGLE_PEOPLE);
	}
	
	//获取ContentProvider提供的指定URI的数据的MIME类型
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch(uriMatcher.match(uri)){ //根据匹配结果返回MIME类型
		case ConstValue.MULTIPLE_PEOPLE:
			return ConstValue.MIME_MULTIPLE;
		case ConstValue.SINGLE_PEOPLE:
			return ConstValue.MIME_SINGLE;
			default:
				throw new IllegalArgumentException("无法识别的URI: "+uri); 
		}
	}

	//插入数据(仅能插入一条记录) 
	//若要插入多条记录,ContentResolver.bultInsert(CONTENT_URI, arrayValues)会多次调用此方法
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub		
		//在数据库中插入数据, 存在多个表时,首先需要根据uri的值确定要操作的表
		long id=db.insert(MyDBOpenHelper.DB_TABLE, null, values);
		if (id>0){ //插入成功时返回该行的_id值
			Uri newUri=ContentUris.withAppendedId(ConstValue.CONTENT_URI_, id); //生成新加行的URI
			getContext().getContentResolver().notifyChange(newUri, null); //通知内容更改
			return newUri; //返回新加行的URI
		}
		//插入失败--id<=0
		throw new SQLiteException("插入失败!--"+uri);
	}

	//删除操作
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count=0; //删除的行数
		
		switch (uriMatcher.match(uri)){ //根据URI的类型选择删除方式
		case ConstValue.MULTIPLE_PEOPLE:
			count=db.delete(MyDBOpenHelper.DB_TABLE, selection, selectionArgs);
			break;
		case ConstValue.SINGLE_PEOPLE:
			String id=uri.getPathSegments().get(1);//获取URI最后的ID值
			count=db.delete(MyDBOpenHelper.DB_TABLE, MyDBOpenHelper.KEY_ID+
					"="+id, null); //删除指定ID的一条记录
			break;
			default:
				throw new IllegalArgumentException("不支持的URI: "+uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null); //通知内容更改
		return count;
	}

	//修改操作
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count; //修改的行数
		
		switch (uriMatcher.match(uri)){ //根据URI的类型选择修改方式
		case ConstValue.MULTIPLE_PEOPLE:
			count=db.update(MyDBOpenHelper.DB_TABLE, values, selection, selectionArgs);
			break;
		case ConstValue.SINGLE_PEOPLE:
			String id=uri.getPathSegments().get(1);//获取URI最后的ID值
			count=db.update(MyDBOpenHelper.DB_TABLE, values, MyDBOpenHelper.KEY_ID+
					"="+id, null); //修改指定ID的一条记录
			break;
			default:
				throw new IllegalArgumentException("不支持的URI: "+uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null); //通知内容更改
		return count;
	}
	
	//查询操作
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor cursor=null;

		switch (uriMatcher.match(uri)){
		case ConstValue.MULTIPLE_PEOPLE:
			cursor=db.query(MyDBOpenHelper.DB_TABLE, projection, 
					selection, selectionArgs, null, null, sortOrder);
			break;
		case ConstValue.SINGLE_PEOPLE:
			cursor=db.query(MyDBOpenHelper.DB_TABLE, projection, 
					MyDBOpenHelper.KEY_ID+"="+uri.getPathSegments().get(1), null, 
					null, null, sortOrder);
			break;
			default:
				break;
		}
		
		if (cursor!=null)  //注册数据改变的监听器, 以便数据改变后及时通知Cursor--setNotificationUri内部是调用registerContentObserver
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

}
