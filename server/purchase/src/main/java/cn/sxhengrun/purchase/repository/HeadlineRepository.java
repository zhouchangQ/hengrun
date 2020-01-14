package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Headline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadlineRepository extends JpaRepository<Headline, Long>{

}
