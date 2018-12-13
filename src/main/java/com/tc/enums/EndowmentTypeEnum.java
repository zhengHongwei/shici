package com.tc.enums;

/**
 * @author zhenghongwei943
 * @date 2018/12/9
 * @description：资质
 **/
public enum EndowmentTypeEnum {
    WEEK(2, "裨将"),
    strong(4, "上将"),
    VIOLET(8, "虎将"),
    ORANGE(16, "神将");

    private int value;
    private String des;

    EndowmentTypeEnum(int value, String des) {
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