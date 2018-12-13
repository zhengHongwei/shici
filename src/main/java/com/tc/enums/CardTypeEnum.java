package com.tc.enums;

/**
 * @author zhenghongwei943
 * @date 2018/12/9
 * @description：卡片类型
 **/
public enum CardTypeEnum {
    GREEN(1, "绿卡"),
    BLUE(2, "蓝卡"),
    VIOLET(3, "紫卡"),
    ORANGE(4, "橙卡");

    private int value;
    private String des;

    CardTypeEnum(int value, String des) {
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