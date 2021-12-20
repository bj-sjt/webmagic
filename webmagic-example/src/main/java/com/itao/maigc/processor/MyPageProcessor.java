package com.itao.maigc.processor;

import com.itao.maigc.bean.Baidu3;
import com.itao.maigc.pipeline.MysqlPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class MyPageProcessor implements PageProcessor {

    private static final String URL_STORE = "http://book.zongheng.com/store.html";
    private static final String URL_RANK = "http://www.zongheng.com/rank.html";

    @Override
    public void process(Page page) {
        Request request = new Request(URL_STORE, "processStore", new MysqlPipeline());
        page.addTargetRequest(request);
        page.setSkip(true);
    }

    public void processStore(Page page) {
        String title = page.getHtml().xpath("//title/text()").get();
        Request request = new Request(URL_RANK, "processRank");
        request.putExtra("title", title);
        page.addTargetRequest(request);
        page.setSkip(true);
    }

    public void processRank(Page page) {
        String total = page.getHtml().xpath("//title/text()").get();
        String title = (String) page.getRequest().getExtra("title");
        page.putField("baidu", new Baidu3(total, title));
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("zongheng.com").setSleepTime(1000);
    }
}
