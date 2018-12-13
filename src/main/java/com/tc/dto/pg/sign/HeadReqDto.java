package com.tc.dto.pg.sign;

import java.io.Serializable;

/**
 * @author zhenghongwei943
 * @date 2018/12/11
 * @description：
 **/
public class HeadReqDto implements Serializable {
    private String channelCode;
    private String channelName;
    private String versionCode;

    public HeadReqDto() {
    }

    public HeadReqDto(String channelCode, String channelName, String versionCode) {
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.versionCode = versionCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
