package com.pamc.api.util;

import com.pamc.api.setting.Setting;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：拼音转换类
 **/
public class PinyinUtil {
    private static final Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    /**
     * 汉语转拼音
     * @param src
     * @return
     */
    public static String hanyuToPinyin(String src) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        String pinyin = null;
        try {
            pinyin = PinyinHelper.toHanYuPinyinString(src, format,"",true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return makeGoodPinyin(pinyin);
    }

    /**
     * 对pinyin4J增强处理
     * pinyin4j有些汉字无法识别，故手动增强
     * @param str
     * @return
     */
    public static String makeGoodPinyin(String str){
        str = str.replaceAll("㧐","song3");
        str = str.replaceAll("䓇","xi4");
        str = str.replaceAll("䨓","lei2");
        str = str.replaceAll("䠥","bie2");
        str = str.replaceAll("𧿁","yue4");
        str = str.replaceAll("𢈪","wu1");
        str = str.replaceAll("㕲","hua2");
        str = str.replaceAll("㷀","qiong2");
        if (!str.matches(Setting.charAndNumPatter)) {
           logger.warn("还有字符未转换成拼音【{}】",str);
        }
      return str;
    }
}
