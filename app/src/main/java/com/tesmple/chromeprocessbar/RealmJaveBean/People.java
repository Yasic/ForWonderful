package com.tesmple.chromeprocessbar.RealmJaveBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ESIR on 2016/1/2.
 */
public class People extends RealmObject {
    /**
     * 姓名
     */
    @PrimaryKey
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    public People(){

    }

    public People(String name){
        this.setName(name);
    }

    public People(String name, String sex){
        this.setName(name);
        this.setSex(sex);
    }

    public People(String name, String sex, String age){
        this.setName(name);
        this.setSex(sex);
        this.setAge(age);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }
}
