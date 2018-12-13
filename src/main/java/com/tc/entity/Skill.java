package com.tc.entity;

/**
 * @author zhenghongwei943
 * @date 2018/12/4
 * @description：技能
 **/
public class Skill {
    private Long id;
    private String name;//技能名称
    private Integer level = 1;//技能等级
    private Integer heroMinLevel = 1;//对应的英雄等级
    private String skillDescription;//当前级技能描述
    private Integer nextHeroMinLevel;//下一级英雄等级
    private String nextSkillDescription;//下一级技能描述
    private Integer delayMesc = 0;//延迟释放毫秒数
    private Integer releaseMesc = 0;//技能蓄力时间
    private Integer [] targetArray;//攻击或者受益对象数组
    private Integer lastMesc = 0;//技能持续总时间
    private Integer partNumber = 1;//技能段数
    private Integer partIntervalMesc = 0;//段数间隔


}
