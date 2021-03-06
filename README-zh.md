![logo](http://webmagic.io/images/logo.jpeg)


[![Build Status](https://travis-ci.org/code4craft/webmagic.png?branch=master)](https://travis-ci.org/code4craft/webmagic)


官方网站[http://webmagic.io/](http://webmagic.io/)

>webmagic是一个开源的Java垂直爬虫框架，目标是简化爬虫的开发流程，让开发者专注于逻辑功能的开发。webmagic的核心非常简单，但是覆盖爬虫的整个流程，也是很好的学习爬虫开发的材料。


webmagic的主要特色：

* 完全模块化的设计，强大的可扩展性。
* 核心简单但是涵盖爬虫的全部流程，灵活而强大，也是学习爬虫入门的好材料。
* 提供丰富的抽取页面API。
* 无配置，但是可通过POJO+注解形式实现一个爬虫。
* 支持多线程。
* 支持分布式。
* 支持爬取js动态渲染的页面。
* 无框架依赖，可以灵活的嵌入到项目中去。

webmagic的架构和设计参考了以下两个项目，感谢以下两个项目的作者：

python爬虫 **scrapy** [https://github.com/scrapy/scrapy](https://github.com/scrapy/scrapy)

Java爬虫 **Spiderman** [http://git.oschina.net/l-weiwei/spiderman](http://git.oschina.net/l-weiwei/spiderman)

webmagic的github地址：[https://github.com/code4craft/webmagic](https://github.com/code4craft/webmagic)。

## 快速开始

### 使用maven

webmagic使用maven管理依赖，在项目中添加对应的依赖即可使用webmagic：

```xml
<dependency>
    <groupId>com.itao</groupId>
    <artifactId>webmagic-core</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.itao</groupId>
    <artifactId>webmagic-extension</artifactId>
    <version>1.0.0</version>
</dependency>
```
        
WebMagic 使用log4j-slf4j-impl作为slf4j的实现.如果你自己定制了slf4j的实现，请在项目中去掉此依赖。

```xml
<exclusions>
    <exclusion>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-slf4j-impl</artifactId>
    </exclusion>
</exclusions>
```

#### 项目结构
	
webmagic主要包括两个包：

* **webmagic-core**
	
	webmagic核心部分，只包含爬虫基本模块和基本抽取器。webmagic-core的目标是成为网页爬虫的一个教科书般的实现。
	
* **webmagic-extension**
	
	webmagic的扩展模块，提供一些更方便的编写爬虫的工具。包括注解格式定义爬虫、JSON、分布式等支持。
	
webmagic还包含两个可用的扩展包，因为这两个包都依赖了比较重量级的工具，所以从主要包中抽离出来，这些包需要下载源码后自己编译：：
在项目中，你可以根据需要依赖不同的包。

### 不使用maven

在项目的**lib**目录下，有依赖的所有jar包，直接在IDE里import即可。

### 第一个爬虫

#### 定制PageProcessor

PageProcessor是webmagic-core的一部分，定制一个PageProcessor即可实现自己的爬虫逻辑。以下是抓取osc博客的一段代码：

```java
public class OschinaBlogPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("my.oschina.net");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1").toString());
        page.putField("content", page.getHtml().$("div.content").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider.create(new OschinaBlogPageProcesser()).addUrl("http://my.oschina.net/flashsword/blog")
             .addPipeline(new ConsolePipeline()).run();
    }
}
```


这里通过page.addTargetRequests()方法来增加要抓取的URL，并通过page.putField()来保存抽取结果。page.getHtml().xpath()则是按照某个规则对结果进行抽取，这里抽取支持链式调用。调用结束后，toString()表示转化为单个String，all()则转化为一个String列表。

Spider是爬虫的入口类。Pipeline是结果输出和持久化的接口，这里ConsolePipeline表示结果输出到控制台。

执行这个main方法，即可在控制台看到抓取结果。webmagic默认有3秒抓取间隔，请耐心等待。

#### 使用注解

webmagic-extension包括了注解方式编写爬虫的方法，只需基于一个POJO增加注解即可完成一个爬虫。以下仍然是抓取oschina博客的一段代码，功能与OschinaBlogPageProcesser完全相同：

```java
@TargetUrl("http://my.oschina.net/flashsword/blog/\\d+")
public class OschinaBlog {

    @ExtractBy("//title")
    private String title;

    @ExtractBy(value = "div.BlogContent",type = ExtractBy.Type.Css)
    private String content;

    @ExtractBy(value = "//div[@class='BlogTags']/a/text()", multi = true)
    private List<String> tags;

    public static void main(String[] args) {
        OOSpider.create(
        	Site.me(),
			new ConsolePageModelPipeline(), OschinaBlog.class).addUrl("http://my.oschina.net/flashsword/blog").run();
    }
}
```

这个例子定义了一个Model类，Model类的字段'title'、'content'、'tags'均为要抽取的属性。这个类在Pipeline里是可以复用的。

### 详细文档

见[http://webmagic.io/docs/](http://webmagic.io/docs/)。