package com.itao.maigc.bean;

import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@Data
@TargetUrl("http://book.zongheng.com/store.html")
public class Baidu implements AfterExtractor {

    @ExtractBy("//title/text()")
    private String mes;

    @Override
    public void afterProcess(Page page) {
        page.getRequest().putExtra("mes", mes);
        page.getTargetRequests().forEach(System.out::println);
    }
}
