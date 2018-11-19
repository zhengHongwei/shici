package com.pamc.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pamc.api.entity.PoetryHead;
import com.pamc.api.entity.PoetryLine;
import com.pamc.api.mapper.PoetryHeadMapper;
import com.pamc.api.mapper.PoetryLineMapper;
import com.pamc.api.util.PinyinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：
 **/
@Service
@Transactional(readOnly = true)
public class PoetryLineService extends ServiceImpl<PoetryLineMapper, PoetryLine> {
    @Autowired
    private PoetryLineMapper poetryLineMapper;

    /**
     * 保存诗行
     *
     * @param poetryLine
     * @return
     */
    @Transactional(readOnly = false)
    public PoetryLine savePoetryLine(PoetryLine poetryLine) {
        poetryLineMapper.insert(poetryLine);
        return poetryLine;
    }

    /**
     * 根据内容查询
     * 首个字一样
     *
     * @param content
     * @param size
     * @return
     */
    public List<PoetryLine> selectByContent(String content, Integer[] size) {
        EntityWrapper ew = new EntityWrapper<PoetryLine>();
        ew.setEntity(new PoetryLine());
        ew.where("content like{0}", content + "%");
        if (size != null) {
            ew.in("size", size);
        }
        return poetryLineMapper.selectList(ew);
    }

    /**
     * 根据首字母的发音查询
     * 发音规则，优先取同音同声调的，如果没有取同音该声调的平仄音（比如3声取4声，4声取3声，1声取2声，2声取1声），
     *
     * @param firstChar
     * @param size
     * @return
     */
    public List<PoetryLine> selectByFirstCharPinPinAndTone(String firstChar, Integer[] size) {
        String firstCharPinYinAndTone  = PinyinUtil.hanyuToPinyin(firstChar);
        String firstCharPinYin = firstCharPinYinAndTone.substring(0,firstCharPinYinAndTone.length()-1);
        int tone = Integer.parseInt(firstCharPinYinAndTone.substring(firstCharPinYinAndTone.length() - 1));
        List<PoetryLine> rs = Lists.newArrayList();
        EntityWrapper ew = new EntityWrapper<PoetryLine>();
        ew.setEntity(new PoetryLine());
        ew.where("first_char_pin_yin ={0}", firstCharPinYin);
        ew.eq("first_char_pin_yin_tone", tone);
        if (size != null) {
            ew.in("size", size);
        }
        rs = poetryLineMapper.selectList(ew);
        if (rs.isEmpty()) {
            EntityWrapper entityWrapper = new EntityWrapper<PoetryLine>();
            entityWrapper.setEntity(new PoetryLine());
            entityWrapper.where("first_char_pin_yin ={0}", firstCharPinYin);
            entityWrapper.eq("first_char_pin_yin_tone", tone % 2 == 0 ? tone - 1 : tone + 1);
            if (size != null) {
                entityWrapper.in("size", size);
            }
            rs = poetryLineMapper.selectList(entityWrapper);
            if (rs.isEmpty()) {
                EntityWrapper noTonewrapper = new EntityWrapper<PoetryLine>();
                noTonewrapper.setEntity(new PoetryLine());
                noTonewrapper.where("first_char_pin_yin ={0}", firstCharPinYin);
                if (size != null) {
                    noTonewrapper.in("size", size);
                }
                rs = poetryLineMapper.selectList(noTonewrapper);
            }
        }
        return rs;
    }

    /**
     * 按首拼音匹配首个字拼音
     *
     * @param content
     * @param size
     * @param equal   根据equal取首个字的拼音是相等还是类似
     * @return
     */
    public List<PoetryLine> selectByFirstCharPinyin(String content, Integer[] size, boolean equal) {
        EntityWrapper ew = new EntityWrapper<PoetryLine>();
        ew.setEntity(new PoetryLine());
        if (equal) {
            ew.where("first_char_pin_yin = {0}", content);
        } else {
            ew.where("first_char_pin_yin like {0}", content + "%");
        }
        if (size != null) {
            ew.in("size", size);
        }
        return poetryLineMapper.selectList(ew);
    }

    public List<PoetryLine> selectByHeadIdAndSeq(Long headId, Integer[] seqArray) {
        EntityWrapper ew = new EntityWrapper<PoetryLine>();
        ew.setEntity(new PoetryLine());
        ew.where("head_id = {0}", headId);
        ew.in("seq", seqArray);
        ew.orderBy("seq");
        return poetryLineMapper.selectList(ew);
    }

    /**
     * 根据主键查找
     *
     * @param id
     * @return
     */
    public PoetryLine findById(Long id) {
        return poetryLineMapper.selectById(id);
    }
}
