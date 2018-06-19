package com.arcry.android.sqlite;

/**
 * Created by Arcry on 2018/5/5.
 */

public class Person {
    private int id;
    private String name;
    private int age;
    private double height;

    public Person(int id,String name,int age,double height){
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        //System.out.println("---"+id+"---"+name+"---"+age+"---"+height);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
