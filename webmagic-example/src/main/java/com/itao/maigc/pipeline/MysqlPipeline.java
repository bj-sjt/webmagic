package com.itao.maigc.pipeline;

import com.itao.maigc.bean.Baidu3;
import lombok.extern.slf4j.Slf4j;
import com.itao.webmagic.ResultItems;
import com.itao.webmagic.Task;
import com.itao.webmagic.pipeline.Pipeline;

@Slf4j
public class MysqlPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("{}", (Baidu3)resultItems.get("baidu"));
    }
}
