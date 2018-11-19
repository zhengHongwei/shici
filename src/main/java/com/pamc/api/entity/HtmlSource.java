package com.pamc.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.pamc.api.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：爬取的页面内容
 **/
@Entity
@Table(name = "html_source")
@TableName("html_source")
public class HtmlSource extends IdEntity {
    /**
     * 诗头ID
     */
    @TableField(value = "html")
    private String html;
    /**
     * 序号
     */
    private String href;

    public HtmlSource() {


    }

    public HtmlSource(String html, String href) {
        this.html = html;
        this.href = href;
    }

    @Column(columnDefinition = "mediumtext")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
