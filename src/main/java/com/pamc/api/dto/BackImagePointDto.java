package com.pamc.api.dto;

import java.io.Serializable;

/**
 * @author zhenghongwei943
 * @date 2018/11/17
 * @description：
 **/
public class BackImagePointDto implements Serializable{
    /**
     *图片名称
     */
    private String fileName;
    /**
     * 绘制诗句起点X坐标
     */
    private int x;
    /**
     * 绘制诗句起点Y坐标
     */
    private int y;

    public BackImagePointDto(String fileName, int x, int y) {
        this.fileName = fileName;
        this.x = x;
        this.y = y;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
