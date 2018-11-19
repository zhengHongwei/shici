package com.pamc.api.enums;


/**
 * @author zhenghongwei943
 * @date 2018/11/6
 * @description：公共请求返回码
 **/
public enum ResCodeEnum{
    /**
     *  请求成功返回码
     */
    SUCCESS("200", "成功!"),
   /* *
    * 请求内部系统异常
    */
    ERROR("500", "失败!"),
    /**
     * 没有权限访问
     */
    AUTH_ERROR("5003", "没有访问权限!"),
    /**
     * 参数错误返回码
     */
    PARAM_ERROR("5002", "参数错误!");

    /**
     * 状态码
     */
    private String resCode;
    /**
     * 提示信息
     */
    private String msg;

    ResCodeEnum(String resCode, String msg) {
        this.resCode = resCode;
        this.msg = msg;
    }
    public String getResCode() {
        return resCode;
    }

    public String getMsg() {
        return msg;
    }

}
