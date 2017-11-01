package cn.nsti.crawler.genealogy.crawler;

import cn.nsti.crawler.genealogy.pipeline.FellowGenLinkPipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerTest {
    @Autowired
    private Crawler crawler;

    @Test
    public void run() throws Exception {
        crawler.run();
    }

}