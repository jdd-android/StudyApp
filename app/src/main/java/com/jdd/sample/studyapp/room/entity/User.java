package com.jdd.sample.studyapp.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author lc. 2018-03-09 16:59
 * @since 1.0.0
 *
 * Entity 定义数据库表
 */

@Entity
public class User {

    /** PrimaryKey 设置该字段为主键 */
    @PrimaryKey(autoGenerate = true)
    private int uid;

    /** ColumnInfo 设置该字段对应数据表列的信息 */
    @ColumnInfo(name = "user_age")
    private int age;

    @ColumnInfo(name = "user_name")
    private String name;

//    public User() {
//    }
//
//    public User(int age, String name) {
//        this.age = age;
//        this.name = name;
//    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[uid=" + uid + " name=" + name + " age=" + age + "]";
    }
}
