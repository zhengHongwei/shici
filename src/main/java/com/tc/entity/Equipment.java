package com.tc.entity;

/**
 * @author zhenghongwei943
 * @date 2018/12/4
 * @description：装备
 **/
public class Equipment {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String image;
    /**
     * 增加或减少的物攻
     */
    private Integer ma = 0;
    /**
     * 增加或减少的法攻
     */
    private Integer la = 0;
    /**
     * 增加或减少的物抗
     */
    private Integer mr = 0;
    /**
     * 增加或介绍的法抗
     */
    private Integer lr = 0;
    /**
     * 增加或减少的血量
     */
    private Integer blood = 0;
    /**
     * 增加或减少的血量
     */
    private Integer speed = 0;
    /**
     * 最小使用等级
     */
    private Integer minLevel = 1;
    /**
     * 专属使用英雄Id
     */
    private Long heroId;
}
