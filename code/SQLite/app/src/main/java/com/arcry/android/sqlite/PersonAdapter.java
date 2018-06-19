package com.arcry.android.sqlite;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Arcry on 2018/5/5.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> mPersonList;

    //新建两个内部接口
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    //新建两个私有变量用于保存用户设置的监听器及其set方法
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View personView;
        TextView personId;
        TextView personName;
        TextView personAge;
        TextView personHeight;

        public ViewHolder(View view){
            super(view);
            personView = view;
            personId = (TextView) view.findViewById(R.id.person_id);
            personName = (TextView) view.findViewById(R.id.person_name);
            personAge = (TextView) view.findViewById(R.id.person_age);
            personHeight = (TextView) view.findViewById(R.id.person_height);
        }
    }

    public PersonAdapter(List<Person> personList){
        mPersonList = personList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//返回点击item行
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }

//        holder.personView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Person person = mPersonList.get(position);
//                Toast.makeText(v.getContext(),"you clicked view" + person.getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = mPersonList.get(position);
        holder .personId.setText(String.valueOf(person.getId()));
        holder .personName.setText(person.getName());
        holder .personAge.setText(String.valueOf(person.getAge()));
        holder .personHeight.setText(String.valueOf(person.getHeight()));

    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

}
