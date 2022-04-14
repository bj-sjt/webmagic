package com.itao.maigc.bean;

import lombok.Data;
import com.itao.webmagic.Page;
import com.itao.webmagic.model.AfterExtractor;
import com.itao.webmagic.model.annotation.ExtractBy;
import com.itao.webmagic.model.annotation.TargetUrl;

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
