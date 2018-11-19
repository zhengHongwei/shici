package com.pamc.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.pamc.api.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：诗行实体
 **/
@Entity
@Table(name="poetry_line")
@TableName("poetry_line")
public class PoetryLine extends IdEntity{
    /**
     * 诗头ID
     */
    @TableField(value="head_id")
    private Long headId;
    /**
     * 序号
     */
    private Integer seq;
    /**
     * 内容
     */
    private String content;
    /**
     * 内容转拼音
     */
    private String pinyin;

    /**
     * 长度
     */
    private Integer size;
    /**
     * 第一个字的拼音
     */
    @TableField(value="first_char_pin_yin")
    private String firstCharPinYin;
    /**
     * 第一个字的音调
     */
    @TableField(value="first_char_pin_yin_tone")
    private Integer firstCharPinYinTone;

    public PoetryLine(){

    }
    public PoetryLine(Long headId, Integer seq, String content, String pinyin,Integer size,String firstCharPinYin,Integer firstCharPinYinTone) {
        this.headId = headId;
        this.seq = seq;
        this.content = content;
        this.pinyin = pinyin;
        this.size = size;
        this.firstCharPinYin = firstCharPinYin;
        this.firstCharPinYinTone = firstCharPinYinTone;
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    @Column(unique = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFirstCharPinYin() {
        return firstCharPinYin;
    }

    public void setFirstCharPinYin(String firstCharPinYin) {
        this.firstCharPinYin = firstCharPinYin;
    }

    public Integer getFirstCharPinYinTone() {
        return firstCharPinYinTone;
    }

    public void setFirstCharPinYinTone(Integer firstCharPinYinTone) {
        this.firstCharPinYinTone = firstCharPinYinTone;
    }
}
