package com.arcry.android.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Arcry on 2018/4/17.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREAT_PERSON = "create table Person (" +
            "id integer primary key autoincrement, " +
            "name text, " +
            "age integer, " +
            "height real)";

    private Context mContext;

    //context，name数据库名，
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_PERSON);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
