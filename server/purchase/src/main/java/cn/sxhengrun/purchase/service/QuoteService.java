package cn.sxhengrun.purchase.service;

import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.entity.Quote;
import cn.sxhengrun.purchase.entity.QuoteAlbum;
import cn.sxhengrun.purchase.remote.HengRunUserProfileRemoteService;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.repository.PurchaseRepository;
import cn.sxhengrun.purchase.repository.QuoteAlbumRepository;
import cn.sxhengrun.purchase.repository.QuoteRepository;
import cn.sxhengrun.purchase.vo.QuoteVO;
import cn.sxhengrun.purchase.vo.util.ConvertUtils;
import org.eulerframework.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private QuoteAlbumRepository quoteAlbumRepository;

    @Autowired
    private ImageRemoteService imageRemoteService;
    @Autowired
    private HengRunUserProfileRemoteService hengRunUserProfileRemoteService;

    public List<QuoteVO> getQuotes(String userId, long purchaseId) {
        Assert.hasText(userId, "userId is required");

        List<Quote> quotes = this.quoteRepository.findAllByPurchaseId(purchaseId);
        return quotes.stream()
                .sorted(Comparator.comparing(Quote::getQuoteAt))
                .map(quote -> {
                    List<QuoteAlbum> quoteAlbums = this.quoteAlbumRepository.findAllByQuoteId(quote.getId());
                    return ConvertUtils.toVO(quote, quoteAlbums, this.imageRemoteService, this.hengRunUserProfileRemoteService);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void quoteForPurchase(String userId, long purchaseId, QuoteVO quoteVO) {
        Assert.hasText(userId, "userId is required");

        Purchase exitsPurchase = this.purchaseRepository.findById(purchaseId).orElse(null);

        if (exitsPurchase == null || Boolean.TRUE.equals(exitsPurchase.getDeleted())) {
            throw new IllegalArgumentException("采购信息不存在");
        }

        Date now = new Date();

        Quote quote = new Quote();
        List<QuoteAlbum> quoteAlbums = new ArrayList<>();
        ConvertUtils.toEntity(quoteVO, quote, quoteAlbums);

        quote.setPurchaseId(purchaseId);
        quote.setQuoteAt(now);
        quote.setQuoteBy(userId);
        quote.setCreateAt(now);
        quote.setModifyAt(now);
        quote.setDeleted(false);


        List<Quote> exitsQuotes = this.quoteRepository.findAllByQuoteByAndPurchaseId(userId, purchaseId);
        if (!CollectionUtils.isEmpty(exitsQuotes)) {
            for (Quote exitsQuote : exitsQuotes) {
                this.quoteAlbumRepository.deleteByQuoteId(exitsQuote.getId());
                this.quoteRepository.delete(exitsQuote);
            }
        }

        this.quoteRepository.save(quote);

        this.saveQuoteAlbum(quote.getId(), quoteAlbums);
    }

    private void saveQuoteAlbum(Long quoteId, List<QuoteAlbum> quoteAlbums) {
        this.quoteAlbumRepository.deleteByQuoteId(quoteId);
        if (CollectionUtils.isEmpty(quoteAlbums)) {
            return;
        }
        Date now = new Date();
        for (QuoteAlbum quoteAlbum : quoteAlbums) {
            quoteAlbum.setQuoteId(quoteId);
            quoteAlbum.setCreateAt(now);
            quoteAlbum.setModifyAt(now);
            quoteAlbum.setDeleted(false);
        }
        this.quoteAlbumRepository.saveAll(quoteAlbums);
    }
}
