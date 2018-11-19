import com.baomidou.mybatisplus.plugins.Page;
import com.pamc.api.Application;
import com.pamc.api.client.ApiClient;
import com.pamc.api.entity.PoetryHead;
import com.pamc.api.entity.PoetryLine;
import com.pamc.api.service.DataInitService;
import com.pamc.api.service.HtmlSourceService;
import com.pamc.api.util.PinyinUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDemo {

    private static ApiClient client;
    private static String baseUrl;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private HtmlSourceService htmlSourceService;

    static {
        baseUrl = "https://www.gushiwen.org/";
        client = new ApiClient(baseUrl, null);
    }

    /**
     * 将诗词网的页面数据整体保存到数据库
     */
    @Test
    public void paserHtml(){
        dataInitService.crawlData(client, baseUrl,"shiwen/default_4A111111111111A1.aspx",false);
    }

    /**
     * 解析数据库中的页面数据到诗词表头表和行表中
     */
    @Test
    public void initData() {
        dataInitService.inintHead(1);
    }

    /**
     * 使用姓名写一首藏头诗
     */
    @Test
    public void makePoetry() {
        dataInitService.writePoetry("郑宏伟");
    }

    @Test
    public void testPage(){
       Page page = htmlSourceService.pageOfHtmlSource(1,10);
       System.out.println(page.getRecords().size());
    }
}
