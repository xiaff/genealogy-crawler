package cn.nsti.crawler.genealogy.pipeline;

import cn.nsti.crawler.genealogy.entity.FellowGenealogyLink;
import cn.nsti.crawler.genealogy.repo.FellowGenLinkRepo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

@Component
public class FellowGenLinkPipeline implements Pipeline {
    @Resource
    private FellowGenLinkRepo fellowGenLinkRepo;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String name = resultItems.get("name");
        String org = resultItems.get("org");
        String link = resultItems.get("link");
        if (fellowGenLinkRepo.countByName(name) > 0) {
            return;
        }
        FellowGenealogyLink fellowGenealogyLink = new FellowGenealogyLink(name, org, link);
        fellowGenLinkRepo.save(fellowGenealogyLink);
    }
}
