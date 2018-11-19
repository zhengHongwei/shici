package com.pamc.api.util;

/**
 * @author zhenghongwei943
 * @date 2018/11/6
 * @description：
 **/
public class BaseResp<T> {
    private T data;
    private String resCode;
    private String msg;

    public BaseResp(T data, String resCode, String msg) {
        this.data = data;
        this.resCode = resCode;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
