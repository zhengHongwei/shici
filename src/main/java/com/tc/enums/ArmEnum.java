package com.tc.enums;

/**
 * @author zhenghongwei943
 * @date 2018/12/9
 * @description：兵种类型
 **/
public enum ArmEnum {
    BU_BING(1, "步兵"),
    QI_BING(1, "骑兵"),
    GONG_BING(1, "弓兵"),
    DUN_BING(1, "盾兵"),
    SHU_SHI(1, "术士");

    private int value;
    private String des;

    ArmEnum(int value, String des) {
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