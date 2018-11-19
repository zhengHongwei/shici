package com.pamc.api.setting;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pamc.api.dto.BackImagePointDto;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

/**
 * @author zhenghongwei943
 * @date 2018/11/6
 * @description：
 **/
public interface Setting {

    static final String NAME_TOO_LONG = "请输入2到4个汉字!";
    /**
     * 输入名字的最大长度
     */
    static final int MAX_NAME_LENGTH = 4;

    /**
     * 字母和数字正则
     */
    static String charAndNumPatter = "[a-zA-Z0-9]+";
    /**
     * 不下载到数据库的诗
     */
    static final Set<String> notDownPoetrySet = Sets.newHashSet("王子约双钩竹歌.李昱",
            "相和歌辞。野田黄雀行.贯休"
            , "水调歌头.朱祖谋");

    /**
     * static下诗的背景图所在目录
     */
    static final String POETRY_BACKIMAG_PATH = "static/img/poetry/";
    /**
     * 背景图对应的诗词起点坐标
     * 顺序与 POETRY_BACKIMAG_PATH目录下的图片名称一直，比如{1162, 208}对应/static/poetry/1.jpg
     */
    static final List<BackImagePointDto> picDefaulPoint = Lists.newArrayList(
            new BackImagePointDto("1.jpg",211,160),
            new BackImagePointDto("2.jpg",611,65),
            new BackImagePointDto("3.jpg",505,31),
            new BackImagePointDto("5.jpg",580,85),
            new BackImagePointDto("8.jpg",153,528),
            new BackImagePointDto("10.jpg",524,170),
            new BackImagePointDto("12.jpg",343,448),
            new BackImagePointDto("13.jpg",333,27),
            new BackImagePointDto("17.jpg",180,30),
            new BackImagePointDto("19.jpg",188,30),
            new BackImagePointDto("20.jpg",486,80),
            new BackImagePointDto("21.jpg",456,53),
            new BackImagePointDto("22.jpg",191,39),
            new BackImagePointDto("23.jpg",583,64),
            new BackImagePointDto("24.jpg",188,417),
            new BackImagePointDto("26.jpg",138,41),
            new BackImagePointDto("27.jpg",399,156),
            new BackImagePointDto("28.jpg",522,88),
            new BackImagePointDto("31.jpg",233,33),
            new BackImagePointDto("32.jpg",251,30),
            new BackImagePointDto("33.jpg",523,137),
            new BackImagePointDto("34.jpg",810,31),
            new BackImagePointDto("35.jpg",520,282),
            new BackImagePointDto("36.jpg",263,120),
            new BackImagePointDto("37.jpg",206,33),
            new BackImagePointDto("38.jpg",657,27),
            new BackImagePointDto("39.jpg",663,30),
            new BackImagePointDto("40.jpg",134,28),
            new BackImagePointDto("41.jpg",300,326),
            new BackImagePointDto("42.jpg",324,34),
            new BackImagePointDto("43.jpg",616,70),
            new BackImagePointDto("44.jpg",330,377)
    );

    /**
     * 背景图片总数
     */
    static final int totalBackImageNum = picDefaulPoint.size();
}
