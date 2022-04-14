package com.itao.maigc;

import com.itao.maigc.processor.MyPageProcessor;
import com.itao.webmagic.Spider;

public class Launcher {

    public static void main(String[] args) {
        Spider.create(new MyPageProcessor())
                .addUrl("http://www.zongheng.com")
                .thread(4)
                .start();

        /*OOSpider.create(Site.me())
                .addPageModel(Arrays.asList(new MysqlPageModelPipeline<>(), new MysqlPageModelPipeline1<>()), Baidu.class)
                .addPageModel(Arrays.asList(new MysqlPageModelPipeline<>(), new MysqlPageModelPipeline1<>()), Baidu.class)
                .addUrl("http://www.zongheng.com")
                .run();*/
    }
}
