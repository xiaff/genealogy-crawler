package cn.nsti.crawler.genealogy.repo;

import cn.nsti.crawler.genealogy.entity.FellowName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FellowNameRepo extends JpaRepository<FellowName, Long> {
}
