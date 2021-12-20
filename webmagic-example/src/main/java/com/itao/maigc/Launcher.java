package com.itao.maigc;

import com.itao.maigc.bean.Baidu;
import com.itao.maigc.bean.Baidu1;
import com.itao.maigc.bean.Baidu3;
import com.itao.maigc.pipeline.MysqlPageModelPipeline;
import com.itao.maigc.pipeline.MysqlPageModelPipeline1;
import com.itao.maigc.processor.MyPageProcessor;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        /*Spider.create(new MyPageProcessor())
                .addUrl("http://www.zongheng.com")
                .thread(4)
                .start();*/

        OOSpider.create(Site.me())
                .addPageModel(new MysqlPageModelPipeline<>(), Baidu.class)
                .addUrl("http://www.zongheng.com")
                .run();
    }
}
