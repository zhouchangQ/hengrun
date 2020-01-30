package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    long countQuotesByPurchaseId(long purchaseId);
    List<Quote> findAllByQuoteByAndPurchaseId(String quoteBy, long purchaseId);
    List<Quote> findAllByPurchaseId(long purchaseId);
}
