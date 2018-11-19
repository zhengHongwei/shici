package com.pamc.api.util;

import sun.awt.Symbol;

/**
 * @author zhenghongwei943
 * @date 2018/11/7
 * @description：redis工具类
 **/
public class RedisUtil {

    /**
     * 字符链接符
     */
    private static final String symbol = "::";

    /**
     * 构建redis key
     * @param params
     * @return
     */
    public static String buildKey(String ... params){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i< params.length;i++){
            if(i != params.length-1){
                stringBuffer.append(params[i]).append(symbol);
            }else{
                stringBuffer.append(params[i]);
            }
        }
        return stringBuffer.toString();
    }
}
