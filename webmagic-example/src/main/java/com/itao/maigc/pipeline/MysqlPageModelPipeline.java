package com.itao.maigc.pipeline;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

@Slf4j
public class MysqlPageModelPipeline<T> implements PageModelPipeline<T> {
    @Override
    public void process(T t, Task task) {
        log.info("MysqlPageModelPipeline: {}", t);
    }
}
