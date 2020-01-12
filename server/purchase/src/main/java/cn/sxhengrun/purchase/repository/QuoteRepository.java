package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Quote;
import cn.sxhengrun.purchase.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByQuoteBy(String quoteBy);
}
