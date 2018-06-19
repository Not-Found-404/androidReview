package com.arcry.android.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private List<Person> personList = new ArrayList<>();

    //更新的条件where，用字符串数组指定占位符的值
    private String[] update_where = new String[3];

    //设置变量指定数据库更新哪一条数据
    int update_id = 0;
    //设置变量指定UI更新哪一条数据item
    int update_id_ui = 0;


    EditText nameET;
    EditText ageET;
    EditText heightET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_btn = (Button) findViewById(R.id.add_btn);
        final Button update_btn = (Button) findViewById(R.id.update_btn);
        Button delete_btn = (Button) findViewById(R.id.delete_btn);
        Button retrieve_btn = (Button) findViewById(R.id.retrieve_btn);

        RecyclerView myRV = (RecyclerView)findViewById(R.id.person_recycler);

        nameET = (EditText) findViewById(R.id.name) ;
        ageET = (EditText) findViewById(R.id.age) ;
        heightET = (EditText) findViewById(R.id.height) ;

//        final String nameStr = nameET.getText().toString();
//        final String ageStr = ageET.getText().toString();
//        final String heightStr = heightET.getText().toString();

        dbHelper = new MyDatabaseHelper(this,"Person.db",null,1);
        dbHelper.getWritableDatabase();

        retrieve();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRV.setLayoutManager(layoutManager);
        final PersonAdapter adapter = new PersonAdapter(personList);
        myRV.setAdapter(adapter);

        //点击recyclerview item 事件
        adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Person person = personList.get(position);

                nameET.setText(person.getName());
                ageET.setText(String.valueOf(person.getAge()));
                heightET.setText(String.valueOf(person.getHeight()));
                update_id = person.getId();
                update_id_ui = position;
                System.out.println("-----" + update_id);
                update_where[0] = person.getName();
                update_where[1] = String.valueOf(person.getAge());
                update_where[2] = String.valueOf(person.getHeight());
                System.out.println("-0-"+update_where[0]);
                System.out.println("-1-"+update_where[1]);
                System.out.println("-2-"+update_where[2]);
                Toast.makeText(MainActivity.this,"click " +
                        person.getId(),Toast.LENGTH_LONG).show();
            }
        });



        //添加数据到数据库
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据库插入数据
                addPerson();
                //UI更新
                Person person = new Person(selectMaxId(),
                        nameET.getText().toString(),
                        Integer.parseInt(ageET.getText().toString()),
                        Double.parseDouble(heightET.getText().toString()));
                personList.add(person);
                adapter.notifyItemInserted(personList.size());
            }
        });

        //查找数据
        retrieve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按照年龄查找数据
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Person",null,null,
                        null, null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        if(Integer.valueOf(ageET.getText().toString()) ==
                                cursor.getInt(cursor.getColumnIndex("age"))){
                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            int age = cursor.getInt(cursor.getColumnIndex("age"));
                            double height = cursor.getFloat(cursor.getColumnIndex("height"));
                            Person person = new Person(id,name,age,height);
                            Toast.makeText(MainActivity.this,
                                    id+":姓名："+name+",年龄："+age+"岁,身高"+height+"cm",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();

//                //查询全部
//                retrieve();
//                adapter.notifyDataSetChanged();
            }
        });

        //更新数据
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更新数据库
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name",nameET.getText().toString());
                values.put("age",Integer.parseInt(ageET.getText().toString()));
                values.put("height",Double.parseDouble(heightET.getText().toString()));

                db.update("Person",values,"id = ? ",
                        new String[]{String.valueOf(update_id)});
                //更新UI
                //System.out.println("+++" + personList.get(update_id).getId());
                personList.get(update_id_ui).setName(nameET.getText().toString());
                personList.get(update_id_ui).setAge(Integer.parseInt(ageET.getText().toString()));
                personList.get(update_id_ui).setHeight(Double.parseDouble(heightET.getText().toString()));
                adapter.notifyItemChanged(update_id_ui);
            }
        });

        //删除一条数据
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据库删除一行数据
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Person","id = ?",
                        new String[]{String.valueOf(update_id)});
                //界面删除一行数据
                System.out.println("size---"+personList.size());
                personList.remove(update_id_ui);
                adapter.notifyItemRemoved(update_id_ui);
            }
        });

    }

    private void initPerson(){

            Person zhangsan = new Person(1,"zhangsan",23,173);
            personList.add(zhangsan);
            Person lisi = new Person(2,"lisi",24,174);
            personList.add(lisi);
            Person wangwu = new Person(3,"wangwu",25,175);
            personList.add(wangwu);
    }

    //查找数据库
    public void retrieve(){
        personList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Person",null,null,
                null, null,null,null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                double height = cursor.getFloat(cursor.getColumnIndex("height"));
                Person person = new Person(id,name,age,height);
                personList.add(person);
                Log.d("MainActivity","id:" + age);
                Log.d("MainActivity","name:" + name);
                Log.d("MainActivity","age:" + age);
                Log.d("MainActivity","height:" + height);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    //向数据库中插入一条记录
    public void addPerson(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",nameET.getText().toString());
        values.put("age",Integer.parseInt(ageET.getText().toString()));
        values.put("height",Float.valueOf(heightET.getText().toString()));
        db.insert("Person",null,values);
        values.clear();
    }

    //获取数据库中id最大的值
    public int selectMaxId(){
        int id = 1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Person",null,null,
                null, null,null,null);
        if(cursor.moveToLast())
        {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            // 这个id就是最大值
        }
        return  id;
    }
}
