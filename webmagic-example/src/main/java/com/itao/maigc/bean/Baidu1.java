package com.itao.maigc.bean;

import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.zongheng.com/rank.html")
@Data
public class Baidu1 implements AfterExtractor {

    @ExtractBy("//title/text()")
    private String total;


    @Override
    public void afterProcess(Page page) {
        String mes = (String)page.getRequest().getExtra("mes");
        page.getTargetRequests().forEach(System.out::println);
    }
}
