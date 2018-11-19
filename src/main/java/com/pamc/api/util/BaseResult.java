package com.pamc.api.util;

import com.pamc.api.enums.ResCodeEnum;

/**
 * @author zhenghongwei943
 * @date 2018/11/6
 * @description：
 **/
public class BaseResult {

    public static <T> BaseResp<T> success(T data){
        BaseResp baseResp = new BaseResp<T>(data, ResCodeEnum.SUCCESS.getResCode(), ResCodeEnum.SUCCESS.getMsg());
        return baseResp;
    }
    public static <T> BaseResp<T> error(T data){
        BaseResp baseResp = new BaseResp<T>(data, ResCodeEnum.ERROR.getResCode(), ResCodeEnum.ERROR.getMsg());
        return baseResp;
    }
    public static <T> BaseResp<T> paramError(String msg){
        BaseResp baseResp = new BaseResp<T>(null, ResCodeEnum.PARAM_ERROR.getResCode(), msg);
        return baseResp;
    }
}
