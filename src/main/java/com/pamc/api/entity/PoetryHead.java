package com.pamc.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.pamc.api.util.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：诗头信息
 **/
@Entity
@Table(name="poetry_head")
@TableName("poetry_head")
public class PoetryHead extends IdEntity{
    /**
     * 作者
     */
    @TableField(value="author")
    private String author;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 链接
     */
    private String href;
    /**
     * 朝代
     */
    private String dynasty;

    public PoetryHead() {

    }
    public PoetryHead(String author, String title, String type, String href,String dynasty) {
        this.author = author;
        this.title = title;
        this.type = type;
        this.href = href;
        this.dynasty = dynasty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }
}
