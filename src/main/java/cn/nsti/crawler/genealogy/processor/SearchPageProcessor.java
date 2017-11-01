package cn.nsti.crawler.genealogy.processor;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http://www.genealogy.ams.org/quickSearch.php
 */
public class SearchPageProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        String searchTerm = getSearchTermFromRequestBody(page.getRequest().getRequestBody());
        if (searchTerm == null) {
            return;
        }

        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);
        Elements lines = document.select("#paddingWrapper > table > tbody > tr");
        for (Element line : lines) {
            Elements nameLink = line.select("td:nth-child(1) > a");
            String name = nameLink.text();
            String org = line.select("td:nth-child(2)").text();
            if (searchTerm.equals(name)) {
                System.out.println(name + ": " + org);
                page.putField("link", nameLink.attr("href"));
                page.putField("name", name);
                page.putField("org", org);
            }
        }
        page.putField("result", "none");
    }

    @Override
    public Site getSite() {
        return new Site().setCharset("utf-8");
    }


    private String getSearchTermFromRequestBody(HttpRequestBody requestBody) {
        byte[] requestBodyBytes = requestBody.getBody();
        String requestBodyStr = new String(requestBodyBytes);
        List<NameValuePair> params = URLEncodedUtils.parse(requestBodyStr, Charset.forName("utf-8"));
        for (NameValuePair param : params) {
            if (param.getName().equals("searchTerms")) {
                return param.getValue();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Request request = new Request("http://www.genealogy.ams.org/quickSearch.php");
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("Submit", "Search");
        params.put("searchTerms", "Ng, Andrew");
        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
        Spider.create(new SearchPageProcessor())
                .addRequest(request)
                .addPipeline(new ConsolePipeline())
                .start();
    }


}
