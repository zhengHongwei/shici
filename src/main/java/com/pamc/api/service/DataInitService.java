package com.pamc.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pamc.api.client.ApiClient;
import com.pamc.api.entity.HtmlSource;
import com.pamc.api.entity.PoetryHead;
import com.pamc.api.entity.PoetryLine;
import com.pamc.api.setting.Setting;
import com.pamc.api.util.PinyinUtil;
import com.pamc.api.util.RedisUtil;
import javafx.scene.chart.Chart;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：初始化诗词数据
 **/
@Service
public class DataInitService {
    private static final Logger logger = LoggerFactory.getLogger(DataInitService.class);

    @Autowired
    private PoetryHeadService poetryService;
    @Autowired
    private PoetryLineService poetryLineService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HtmlSourceService htmlSourceService;


    /**
     * 将爬取的页面数据持久化到数据库
     *
     * @param client    客户端
     * @param baseUrl   url前缀
     * @param api       url后缀
     * @param firstFlag 是否是第一次执行爬虫，如果是第一次则会先清空存储的页面表
     */
    public void crawlData(ApiClient client, String baseUrl, String api, boolean firstFlag) {
        if (firstFlag) {
            htmlSourceService.deleteAll();
        }
        String html = client.excute(null, api);
        HtmlSource htmlSource = new HtmlSource(html, baseUrl + api);
        htmlSourceService.saveHtmlSource(htmlSource);
        Document root = Jsoup.parse(html);
        //爬取下一页
        String nextHref = root.select("#FromPage .pagesright .amore").first().attr("href");
        if (StringUtils.isNotBlank(nextHref)) {
            crawlData(client, baseUrl, nextHref, false);
        }
    }


    /**
     * 初始化唐诗三百首数据
     *
     * @param currentPageNum
     */
    public void inintHead(int currentPageNum) {
        Page<HtmlSource> page = htmlSourceService.pageOfHtmlSource(currentPageNum, 10);
        for (HtmlSource htmlSource : page.getRecords()) {
            String rs = htmlSource.getHtml();
            Document root = Jsoup.parse(rs);
            Elements elements = root.select(".left .sons .cont");
            List<PoetryHead> listHead = new ArrayList<PoetryHead>();
            for (Element element : elements) {
                //取出P标签下的内容 第一个P标签内容为诗的名字，第二个P标签中包含朝代，作者
                Elements pTagElements = element.getElementsByTag("p");
                String title = pTagElements.first().text();
                Elements aTagElements = pTagElements.get(1).getElementsByTag("a");
                String dynasty = aTagElements.first().text();
                String author = aTagElements.get(1).text();
                String fullContent = element.select(".contson").first().text();
                logger.info("title:{} author:{}", title, author);
                String key = RedisUtil.buildKey(title, "(" + author + ")");
                if (!redisTemplate.opsForSet().isMember("shici", key) && !Setting.notDownPoetrySet.contains(title+"."+author)) {
                    PoetryHead head = new PoetryHead(author, title, "诗", htmlSource.getHref(), dynasty);
                    poetryService.savePoetryHead(head);
                    inintLine(fullContent, head.getId());
                    redisTemplate.opsForSet().add("shici", key);
                } else {
                    logger.warn("《{}》 作者{} 已存在", title, author);
                }
            }
        }

        //爬取下一页
        if (currentPageNum <= page.getPages()) {
            currentPageNum++;
            inintHead(currentPageNum);
        }
    }

    public void inintLine(String fullContent, Long headId) {
        //取出诗中的括号注解，以?？,.字符结尾的替换为，
        fullContent = fullContent.replaceAll("([?？,.！!；;：:]+)", "，")
                .replaceAll("([(（]+)(.+)([)）]+)", "")
                .replaceAll("([\"'“”《》「」【】、—{}□¤]+)", "")
                .replaceAll(Setting.charAndNumPatter,"").replaceAll("曰：", "")
                .replaceAll("——程高通行本", "").replaceAll("——甲戌本", "")
                .replaceAll("——周汝昌校本", "").replaceAll("gf々", "木雙")
                .replaceAll("々","").replaceAll("dU", "弁页")
                .replaceAll("𢬵","拼").replaceAll("𧿁","𧿁")
        .replaceAll("𢈪","坞").replaceAll("𨁏","扑")
        .replaceAll("𪄀𪃑","磐髦").replaceAll("mH々","尊尊")
        .replaceAll("","㧐").replaceAll("𣑽","梵")
        .replaceAll("𣒰","栴");
        String[] lines = fullContent.split("。");
        int seq = 0;
        for (int i = 0; i < lines.length; i++) {
            String[] minLines = lines[i].split("，");
            for (int j = 0; j < minLines.length; j++) {
                String lineContent = StringUtils.deleteWhitespace(minLines[j]);
                if (StringUtils.isNotBlank(lineContent)) {
                    //去掉特殊字符如《凤求凰》【其一：】到【其二：】中间的内容
                    lineContent = lineContent.contains("其二：") ? StringUtils.substringAfter(lineContent, "其二：") : lineContent;
                    seq++;
                    String pinyinContent = PinyinUtil.hanyuToPinyin(lineContent);
                    String firstCharPinYinAndTone = PinyinUtil.hanyuToPinyin(String.valueOf(lineContent.charAt(0)));
                    logger.info("中文【{}】 拼音【{}】", lineContent, pinyinContent);
                    Integer firstCharPinYinTone = Integer.parseInt(firstCharPinYinAndTone.substring(firstCharPinYinAndTone.length() - 1));
                    PoetryLine poetryLine = new PoetryLine(headId, seq, lineContent, pinyinContent, lineContent.length(),
                            firstCharPinYinAndTone.substring(0,firstCharPinYinAndTone.length()-1), firstCharPinYinTone);
                    poetryLineService.savePoetryLine(poetryLine);
                }
            }
        }
    }

    /**
     * 根据名字写一首藏头诗
     *
     * @param name 名字
     * @return
     */
    public String[] writePoetry(String name) {
        name = StringUtils.deleteWhitespace(name);
        char[] chars = name.toCharArray();
        //存储行字数
        Set<Integer> lineSizesSet = Sets.newHashSet();
        //存储满足条件的诗
        List<List<PoetryLine>> initDataList = Lists.newArrayList();
        String[] resultFirstChar = new String[name.length()];
        List<PoetryLine> equalNameList = null;
        //名字中的字能匹配到的诗数
        int hasLines = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                equalNameList = poetryLineService.selectByFirstCharPinPinAndTone(String.valueOf(chars[i]), null);
            } else {
                Integer[] sizes = lineSizesSet.toArray(new Integer[0]);
                equalNameList = poetryLineService.selectByFirstCharPinPinAndTone(String.valueOf(chars[i]), sizes);
            }
            if (equalNameList != null && !equalNameList.isEmpty()) {
                initDataList.add(equalNameList);
                //清空列表
                lineSizesSet.clear();
                for (PoetryLine line : equalNameList) {
                    if (!lineSizesSet.contains(line.getSize())) {
                        lineSizesSet.add(line.getSize());
                    }
                }
                resultFirstChar[i] = String.valueOf(chars[i]);
                hasLines++;
            } else {
                resultFirstChar[i] = null;
            }
        }
        List<Map<Integer, List<PoetryLine>>> roundList = handleResultData(initDataList, lineSizesSet.toArray(new Integer[0]));
        String[] poetryLines = producePoetry(roundList, lineSizesSet.toArray(new Integer[0]), resultFirstChar, hasLines,0);
        return poetryLines;
    }

    /**
     * 处理数据整理成满足条件的诗
     * 诗的每一行要求字数相同
     * @param initDataList
     * @param lineSizesSet
     * @return
     */
    public List<Map<Integer, List<PoetryLine>>> handleResultData(List<List<PoetryLine>> initDataList, Integer[] lineSizesSet) {
        List<Map<Integer, List<PoetryLine>>> rs = Lists.newArrayList();
        //保存第一行的结果行长度，第二行之后的行长度必须在第一行的长度之中
        List<Integer> lastlineContainSizesSet = Arrays.asList(lineSizesSet);
        if (initDataList != null && !initDataList.isEmpty()) {
            for (int i = 0; i < initDataList.size(); i++) {
                Map<Integer, List<PoetryLine>> map = Maps.newConcurrentMap();
                //前一行取出的结果包含的行长度
                for (PoetryLine line : initDataList.get(i)) {
                    if (lastlineContainSizesSet.contains(line.getSize())) {
                        if (map.containsKey(line.getSize())) {
                            List<PoetryLine> lp = map.get(line.getSize());
                            lp.add(line);
                            map.put(line.getSize(), lp);
                        } else {
                            List<PoetryLine> lp = Lists.newArrayList();
                            lp.add(line);
                            map.put(line.getSize(), lp);
                        }
                    }
                }
                rs.add(map);
            }
        }
        return rs;
    }

    /**
     * 从满足的诗中随机产生
     *如果出现补行时，可能出现补的那句长度与其它行出现长度不同的情况
     * 这个时候系统将进行重新随机生成，为了保存不出现死循环的，所以将自动随机生成的次数限制为1000
     * @param data
     * @param lineSizesSet
     * @param loopNum
     * @return
     */
    public String[] producePoetry(List<Map<Integer, List<PoetryLine>>> data, Integer[] lineSizesSet, String[] resultFirstChar, int hasLines ,int loopNum) {
        String[] rs = new String[4];
        Random rand = new Random();
        Integer randSize = lineSizesSet[rand.nextInt(lineSizesSet.length)];
        List<PoetryLine> listLines = Lists.newArrayList();
        int k = 0;
        for (int i = 0; i < resultFirstChar.length; i++) {
            String nameChar = resultFirstChar[i];
            if (StringUtils.isNotBlank(nameChar)) {
                Map<Integer, List<PoetryLine>> map = data.get(k);
                List<PoetryLine> lines = map.get(randSize);
                PoetryLine randLine = lines.get(rand.nextInt(lines.size()));
                randLine.setContent(nameChar + randLine.getContent().substring(1));
                k++;
                switch (hasLines) {
                    case 1: {
                        Integer[] seqArray = null;
                        if (randLine.getSize() <= 4) {
                            seqArray = new Integer[]{1, 2, 3, 4};
                        } else {
                            if (randLine.getSeq() % 2 == 0) {
                                seqArray = new Integer[]{randLine.getSeq().intValue() - 3, randLine.getSeq().intValue() - 2,
                                        randLine.getSeq().intValue() - 1, randLine.getSeq().intValue()};
                            } else {
                                seqArray = new Integer[]{randLine.getSeq().intValue() - 2, randLine.getSeq().intValue() - 1,
                                        randLine.getSeq().intValue(), randLine.getSeq().intValue() + 1};
                            }
                        }
                        listLines = poetryLineService.selectByHeadIdAndSeq(randLine.getHeadId(), seqArray);
                        break;
                    }
                    case 2: {
                        PoetryLine supLine = null;
                        if (randLine.getSeq() % 2 == 0) {
                            supLine = poetryLineService.findById(randLine.getId() - 1);
                            listLines.add(supLine);
                            listLines.add(randLine);
                        } else {
                            supLine = poetryLineService.findById(randLine.getId() + 1);
                            listLines.add(randLine);
                            listLines.add(supLine);
                        }
                        break;
                    }
                    case 3: {
                        if (i < 2) {
                            listLines.add(randLine);
                        } else {
                            PoetryLine supLine = null;
                            if (randLine.getSeq() % 2 == 0) {
                                supLine = poetryLineService.findById(randLine.getId() - 1);
                                listLines.add(supLine);
                                listLines.add(randLine);
                            } else {
                                supLine = poetryLineService.findById(randLine.getId() + 1);
                                listLines.add(randLine);
                                listLines.add(supLine);
                            }
                        }
                        break;
                    }
                    case 4: {
                        listLines.add(randLine);
                        break;
                    }
                    default: {
                        listLines.add(randLine);
                    }
                }
            }
        }
        for (int i = 0; i < listLines.size(); i++) {
            rs[i] = listLines.get(i).getContent();
        }
        //如果四句诗出现字数不等则从新组合，重新组合的次数不超过1000次
        if( !((rs[0].length() == rs[1].length()) && rs[0].length() == rs[2].length() &&  rs[0].length() == rs[3].length())){
            if(loopNum < 1000){
                logger.warn("开始重新组合当前次数：{}",loopNum);
                loopNum++;
                rs = producePoetry( data, lineSizesSet,  resultFirstChar,  hasLines , loopNum) ;
            }
        }
        return rs;
    }

}