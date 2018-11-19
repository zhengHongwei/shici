package com.pamc.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.pamc.api.entity.HtmlSource;
import com.pamc.api.mapper.HtmlSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhenghongwei943
 * @date 2018/11/8
 * @description：
 **/
@Service
@Transactional(readOnly = true)
public class HtmlSourceService {

    @Autowired
    private HtmlSourceMapper htmlSourceMapper;

    /**
     * 保存爬取的内容
     * @param htmlSource
     * @return
     */
    @Transactional(readOnly = false)
    public HtmlSource saveHtmlSource(HtmlSource htmlSource) {
        htmlSourceMapper.insert(htmlSource);
        return htmlSource;
    }

    /**
     * 删除所有的爬取内容
     * @return
     */
    @Transactional(readOnly = false)
    public Integer deleteAll(){
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new HtmlSource());
        return htmlSourceMapper.delete(ew);
    }

    /**
     * 查询所有的爬取内容
     * @return
     */
    public List<HtmlSource> findAll(){
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new HtmlSource());
        return htmlSourceMapper.selectList(ew);
    }

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    public Page<HtmlSource> pageOfHtmlSource(int current,int size){
        Page<HtmlSource> page = new Page<HtmlSource>(current,size);
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new HtmlSource());
        ew.orderBy("id",true);
        return page.setRecords(htmlSourceMapper.selectPage(page,ew));
    }
}
