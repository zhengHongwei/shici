package com.tc.dto.pg.sign;

import java.io.Serializable;

/**
 * @author zhenghongwei943
 * @date 2018/12/11
 * @description：
 **/
public class PackageReqDto implements Serializable{
    private HeadReqDto head;
    private Object body;

    public PackageReqDto() {
    }

    public PackageReqDto(HeadReqDto head, Object body) {
        this.head = head;
        this.body = body;
    }

    public HeadReqDto getHead() {
        return head;
    }

    public void setHead(HeadReqDto head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
