package com.tc.abs;

import com.tc.entity.Hero;
import com.tc.enums.ArmEnum;

/**
 * @author zhenghongwei943
 * @date 2018/12/8
 * @description：
 **/
public abstract class AbstractHero {

    /**
     * 等级
     */
    public Integer level = 1;

    /**
     * 总经验值
     */
    public Integer totalExp = 0;
    /**
     * 当前拥有的经验值
     */
    public Integer currentExp = 0;

    /**
     * 兵种
     */
    public ArmEnum arm;
    /**
     * 物攻值
     */
    public Integer ma;
    /**
     * 法攻值
     */
    public Integer la;
    /**
     *物抗值
     */
    public Integer mr;
    /**
     * 法抗值
     */
    public Integer lr;
    /**
     *血量
     */
    public Integer blood = 80;
    /**
     *速度
     */
    public Integer speed = 5;

    /**
     * 升级
     */
    public abstract void heroUpgrade(Integer exp,boolean flag);

    /**
     * 攻击
     * @param foe
     * @return
     */
    public abstract Hero attack(Hero foe);

}
