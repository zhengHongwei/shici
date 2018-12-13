package com.tc.enums;

/**
 * @author zhenghongwei943
 * @date 2018/12/9
 * @description：英雄类型
 **/
public enum TypeEnum {
    warrior(1, "武将"),
    master(2, "军师");

    private int value;
    private String des;

    TypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public int getValue() {
        return this.value;
    }

    public String getDes() {
        return this.des;
    }
}