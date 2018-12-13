package com.tc.dto.pg.sign;

import java.io.Serializable;

/**
 * @author zhenghongwei943
 * @date 2018/12/11
 * @description：
 **/
public class SignReqDto implements Serializable {
    private String data;
    private String sign;

    public SignReqDto() {
    }

    public SignReqDto(String data, String sign) {
        this.data = data;
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    @Override
    public String toString(){
        return "{" +
                "\"data\":\""+data+"\","+
                "\"sign\":\""+sign+"\""+
                "}";
    }
}
