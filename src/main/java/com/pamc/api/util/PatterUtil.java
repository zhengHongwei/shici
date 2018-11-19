package com.pamc.api.util;

/**
 * @author zhenghongwei943
 * @date 2018/11/19
 * @description：正则表达式工具类
 * 该类处理一些通用的正则判断
 **/
public class PatterUtil {
    /**
     * 判断输入的是不是2-4个汉字
     * @param name
     * @return
     */
    public static boolean isChinaseName(String name) {
       return name.matches("[\u4e00-\u9fa5]{2,4}");
    }

}
