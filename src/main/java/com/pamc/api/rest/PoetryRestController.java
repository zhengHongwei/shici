package com.pamc.api.rest;

import com.alibaba.fastjson.JSON;
import com.pamc.api.service.DataInitService;
import com.pamc.api.setting.Setting;
import com.pamc.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author zhenghongwei943
 * @date 2018/11/1
 * @description：自动生成诗API
 **/
@RestController
@RequestMapping("/api/v1/poetrye")
public class PoetryRestController {
    @Autowired
    private DataInitService dataInitService;

    /**
     * 提供用户名，根据名字生成一首藏头诗
     * @param name
     * @return
     */
    @RequestMapping(value = "make")
    public void makePoetry(@RequestParam(value = "name",required = true)String name,
                             HttpServletResponse response) throws Exception{
        OutputStream stream = response.getOutputStream();
        if(!PatterUtil.isChinaseName(name)){
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            BaseResp baseResp = BaseResult.paramError(Setting.NAME_TOO_LONG);
            stream.write(JSON.toJSONString(baseResp).getBytes());
        }else{
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            String [] poetryLineArray = dataInitService.writePoetry(name);
            stream.write(ImageUtil.drawPoetry(poetryLineArray,name));
        }
        stream.flush();
        stream.close();
    }
}