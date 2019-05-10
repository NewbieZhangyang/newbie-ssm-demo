package com.newbie.consistentHash;

import org.junit.Test;

import java.util.UUID;

/**
 * 用户
 */
public class Person {
    private String idno;    //身份证号
    private String name;
    private int age;

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
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


}
