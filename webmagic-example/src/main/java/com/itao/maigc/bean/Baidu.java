package com.itao.maigc.bean;

import lombok.Data;
import com.itao.webmagic.Page;
import com.itao.webmagic.model.AfterExtractor;
import com.itao.webmagic.model.annotation.ExtractBy;
import com.itao.webmagic.model.annotation.TargetUrl;

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
