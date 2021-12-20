package us.codecraft.webmagic.model;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The extension to Pipeline for page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
class ModelPipeline<T> implements Pipeline {

    private final Map<Class<T>, List<PageModelPipeline<T>>> pageModelPipelines = new ConcurrentHashMap<>();

    public ModelPipeline() {
    }

    public void put(Class<T> clazz, List<PageModelPipeline<T>> pipeline) {
        pageModelPipelines.put(clazz, pipeline);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<Class<T>, List<PageModelPipeline<T>>> classPageModelPipelineEntry : pageModelPipelines.entrySet()) {
            List<PageModelPipeline<T>> pipelines = classPageModelPipelineEntry.getValue();
            Object o = resultItems.get(classPageModelPipelineEntry.getKey().getCanonicalName());
            if (o != null) {
                ExtractBy annotation = classPageModelPipelineEntry.getKey().getAnnotation(ExtractBy.class);
                if (annotation == null || !annotation.multi()) {
                    for (PageModelPipeline<T> pipeline : pipelines) {
                        pipeline.process((T)o, task);
                    }
                } else {
                    List<T> list = (List<T>) o;
                    for (T o1 : list) {
                        for (PageModelPipeline<T> pipeline : pipelines) {
                            pipeline.process(o1, task);
                        }
                    }
                }
            }
        }
    }
}
