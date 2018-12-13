package com.tc.entity;

import com.tc.abs.AbstractHero;
import com.tc.enums.ArmEnum;
import com.tc.enums.CardTypeEnum;
import com.tc.enums.EndowmentTypeEnum;
import com.tc.enums.TypeEnum;

/**
 * @author zhenghongwei943
 * @date 2018/12/4
 * @description：英雄
 **/
public class Hero extends AbstractHero {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 卡片类型
     */
    private CardTypeEnum cardType = CardTypeEnum.GREEN;
    /**
     * 英雄类型
     */
    private TypeEnum type = TypeEnum.warrior;
    /**
     * 资质
     */
    private EndowmentTypeEnum endowment = EndowmentTypeEnum.WEEK;
    /**
     * 主图
     */
    private String maxImage;
    /**
     * 小图
     */
    private String minImage;

    public Hero() {
    }

    /**
     * 构造参数
     * @param id
     * @param name 名字
     * @param level 级别
     * @param cardType 卡片类型
     * @param type 英雄类型
     * @param endowment 资质
     * @param arm 兵种类型
     * @param maxImage 大图
     * @param minImage 小图
     */
    public Hero(Long id, String name, Integer level,
                CardTypeEnum cardType, TypeEnum type,
                EndowmentTypeEnum endowment,ArmEnum arm,
                String maxImage,String minImage) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.cardType = cardType;
        this.type = type;
        this.endowment = endowment;
        this.arm = arm;
        this.maxImage = maxImage;
        this.minImage = minImage;
        init(level, cardType.getValue(), type.getValue(), endowment.getValue());
    }


    /**
     * 根据等级初始化英雄攻击、防御、血量、速度参数
     * @param level
     * @param cardType
     * @param type
     * @param endowment
     */
    public void init(Integer level, int cardType, int type, int endowment) {
        //经验初始化
        int ce = 0;
        for (int i = 0; i < level - 1; i++) {
            ce = ce + (int) (50 * Math.pow(1.5, level - 2));
        }
        //攻击初始化
        if (TypeEnum.warrior.getValue() == type) {
            this.ma = 13;
            this.la = 0;
            this.mr = 6;
            this.lr = 4;
            this.speed = 5;
            this.blood = 80;
        }else if(TypeEnum.master.getValue() == type){
            this.ma = 5;
            this.la = 13;
            this.mr = 3;
            this.lr = 3;
            this.speed = 5;
            this.blood = 60;
        }
        this.currentExp = ce;
        this.totalExp = ce;
        this.heroUpgrade(level,true);
    }


    public void setTotalExp(Integer totalExp) {
        this.totalExp = totalExp;
    }

    @Override
    public void  heroUpgrade(Integer exp, boolean flag) {
        int radio = 0;
        if (flag) {
            radio = exp;
        } else {
             int nowExp = this.currentExp + exp;
             while (true){
                 int nextLevel = this.level+1;
                 int nextExp = 50 * (int)(Math.pow(1.5,nextLevel-2));
             }

            //while ((exp + this.currentExp)> (this.level-1)*50 * ()(Math.pow(1.5d,this.level-1)) ){}
        }
        this.level = this.level + radio;
        if (this.type.equals(TypeEnum.warrior)) {
            this.ma = this.ma + radio * cardType.getValue() * endowment.getValue() * 4;
            this.mr = this.mr + radio * cardType.getValue() * endowment.getValue() * 2;
            this.lr = this.mr + radio * cardType.getValue() * endowment.getValue() * 2;
            this.speed = this.speed + radio * (cardType.getValue() + endowment.getValue());
            this.blood = this.blood + radio * cardType.getValue() * endowment.getValue() * 10;
        } else if (this.type.equals(TypeEnum.master)) {
            this.la = this.ma + radio * cardType.getValue() * endowment.getValue() * 4;
            this.mr = this.mr + radio * cardType.getValue() * endowment.getValue();
            this.lr = this.mr + radio * cardType.getValue() * endowment.getValue();
            this.speed = this.speed + radio * Math.round((cardType.getValue() + endowment.getValue()) / 2);
            this.blood = this.blood + radio * cardType.getValue() * endowment.getValue() * 6;
        }
    }

    @Override
    public Hero attack(Hero targetHero) {
        return null;
    }

    public static void main(String[] args) {
        //int a= 1 +3/5;
        System.out.println(Math.pow(1.2, 0));
    }
}
