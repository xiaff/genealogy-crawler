package cn.nsti.crawler.genealogy.processor;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class PersonPageProcessor implements PageProcessor {

    // http://www.genealogy.ams.org/id.php?id=101043
    @Override
    public void process(Page page) {
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);
        Elements infoLine = document.select("#paddingWrapper > div:nth-child(6)");
        String infoLineStr = infoLine.text();
        System.out.println(infoLineStr);
        String schoolInfo = infoLine.select("span>span").text();
        System.out.println(schoolInfo);
        String[] degreeAndYear = infoLineStr.split(schoolInfo);
        String degree = degreeAndYear[0].trim();
        int year = Integer.valueOf(degreeAndYear[1].trim());

        page.putField("infoLine", infoLineStr.trim());
        page.putField("school",schoolInfo);
        page.putField("degree", degree);
        page.putField("year", year);

        Elements thesisTitle = document.select("#thesisTitle");
        if (CollectionUtils.isNotEmpty(thesisTitle)) {
            page.putField("thesisTitle", thesisTitle.text());
        }
        Elements subjectLine = document.select("#paddingWrapper > div:nth-child(8)");
        if (CollectionUtils.isNotEmpty(subjectLine)) {
            String subject = subjectLine.text();
            page.putField("subject", subject.substring(subject.indexOf(":") + 1).trim());
        }
        Elements pElements = document.select("#paddingWrapper > p");
        for (Element pElement : pElements) {
            if (pElement.text().startsWith("Advisor")) {
                page.putField("advisor", pElement.text().replace("Advisor\\d+: ", ""));
                String advisorLink = pElement.select("a").attr("href");
                page.putField("advisorLink", "http://www.genealogy.ams.org/" + advisorLink);
            }
        }
    }

    @Override
    public Site getSite() {
        return new Site().setCharset("utf-8").setSleepTime(100);
    }

    public static void main(String[] args) {
        Spider.create(new PersonPageProcessor())
                .addUrl("http://www.genealogy.ams.org/id.php?id=101043")
                .addUrl("http://www.genealogy.ams.org/id.php?id=87388")
                .addPipeline(new ConsolePipeline())
                .run();
    }
}
