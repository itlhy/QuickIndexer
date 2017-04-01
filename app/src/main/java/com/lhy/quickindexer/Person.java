package com.lhy.quickindexer;

import android.support.annotation.NonNull;

/**
 * 创 建 人: 路好营
 * 创建日期: 2017/3/29 15:37
 * 添加备注:
 */
public class Person implements Comparable<Person> {
    private String name;
    private String pinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Person(String name) {
        this.name = name;
        this.pinyin = PinyinUtil.getPinyin(name);//根据名字,拿到拼音
    }

    @Override
    public int compareTo(@NonNull Person o) {
        return this.pinyin.compareTo(o.getPinyin());
    }
}
