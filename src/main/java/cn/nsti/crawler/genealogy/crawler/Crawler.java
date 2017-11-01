package cn.nsti.crawler.genealogy.crawler;

import cn.nsti.crawler.genealogy.entity.FellowName;
import cn.nsti.crawler.genealogy.pipeline.FellowGenLinkPipeline;
import cn.nsti.crawler.genealogy.processor.SearchPageProcessor;
import cn.nsti.crawler.genealogy.repo.FellowNameRepo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Crawler {
    @Resource
    FellowNameRepo fellowNameRepo;

    @Resource
    private FellowGenLinkPipeline pipeline;

    private Request buildRequest(String name) {
        Request request = new Request("http://www.genealogy.ams.org/quickSearch.php");
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("Submit", "Search");
        params.put("searchTerms", name);
        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
        return request;
    }

    public void run() {
        List<FellowName> fellowNameList = fellowNameRepo.findAll();

        Spider spider = Spider.create(new SearchPageProcessor());
        for (FellowName fellowName : fellowNameList) {
            System.out.println(fellowName);
            String name = fellowName.getName();
            Request request = buildRequest(name);
            spider.addRequest(request);
        }

        spider.addPipeline(pipeline).addPipeline(new ConsolePipeline()).thread(3).run();
    }
}
