package com.pamc.api.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pamc.api.entity.PoetryHead;
import com.pamc.api.entity.PoetryLine;
import com.pamc.api.mapper.PoetryHeadMapper;
import com.pamc.api.mapper.PoetryLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：
 **/
@Service
@Transactional(readOnly = true)
public class PoetryHeadService extends ServiceImpl<PoetryHeadMapper, PoetryHead> {
    @Autowired
    private PoetryHeadMapper poetryHeadMapper;

    /**
     * 保存诗头
     * @param poetryHead
     * @return
     */
    @Transactional(readOnly = false)
    public PoetryHead savePoetryHead(PoetryHead poetryHead){
        poetryHeadMapper.insert(poetryHead);
        return poetryHead;
    }

}
