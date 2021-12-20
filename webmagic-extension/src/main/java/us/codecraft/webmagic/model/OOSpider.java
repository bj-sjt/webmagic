package us.codecraft.webmagic.model;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * The spider for page model extractor.<br>
 * In webmagic, we call a POJO containing extract result as "page model". <br>
 * You can customize a crawler by write a page model with annotations. <br>
 * Such as:
 * <pre>
 * {@literal @}TargetUrl("http://my.oschina.net/flashsword/blog/\\d+")
 *  public class OschinaBlog{
 *
 *      {@literal @}ExtractBy("//title")
 *      private String title;
 *
 *      {@literal @}ExtractBy(value = "div.BlogContent",type = ExtractBy.Type.Css)
 *      private String content;
 *
 *      {@literal @}ExtractBy(value = "//div[@class='BlogTags']/a/text()", multi = true)
 *      private List&lt;String&gt; tags;
 * }
 * </pre>
 * And start the spider by:
 * <pre>
 *   OOSpider.create(Site.me().addStartUrl("http://my.oschina.net/flashsword/blog")
 *        ,new JsonFilePageModelPipeline(), OschinaBlog.class).run();
 * }
 * </pre>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class OOSpider extends Spider {

    private ModelPageProcessor modelPageProcessor;

    public OOSpider(Site site) {
        super(ModelPageProcessor.create(site));
    }

    public static OOSpider create(Site site) {
        return new OOSpider(site);
    }

    public <T> OOSpider addPageModel(List<PageModelPipeline<T>> pageModelPipeline, Class<T> pageModel) {
        if(modelPageProcessor == null) {
            modelPageProcessor = (ModelPageProcessor) this.pageProcessor;
        }
        modelPageProcessor.addPageModel(pageModel);
        ModelPipeline<T> modelPipeline = new ModelPipeline<>();
        super.addPipeline(modelPipeline);
        modelPipeline.put(pageModel, pageModelPipeline);
        return this;
    }


    public OOSpider setIsExtractLinks(boolean isExtractLinks) {
        modelPageProcessor.setExtractLinks(isExtractLinks);
        return this;
    }

}
