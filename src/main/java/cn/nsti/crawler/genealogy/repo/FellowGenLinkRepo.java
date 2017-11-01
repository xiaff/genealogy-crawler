package cn.nsti.crawler.genealogy.repo;

import cn.nsti.crawler.genealogy.entity.FellowGenealogyLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FellowGenLinkRepo extends JpaRepository<FellowGenealogyLink, Long> {

    Long countByName(String name);
}
