package com.itao.maigc.pipeline;

import com.itao.maigc.bean.Baidu3;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
public class MysqlPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("{}", (Baidu3)resultItems.get("baidu"));
    }
}
