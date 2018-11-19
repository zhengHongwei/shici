package com.pamc.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

/**
 * @author zhenghongwei943
 * @date 2018/11/13
 * @description：上下文工具类
 **/
public class ApplicationUtil {

    //@Value("#{beanInject.another}")
    //private String rootPath;
    /**
     * 获取根路径（windows 系统路径是以"/"开头的）
     */
    public static final String contentPath = ApplicationUtil.class.getClassLoader().getResource("static").getPath();

}
