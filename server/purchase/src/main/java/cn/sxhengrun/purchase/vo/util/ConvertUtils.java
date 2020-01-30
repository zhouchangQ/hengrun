package cn.sxhengrun.purchase.vo.util;

import cn.sxhengrun.purchase.entity.Headline;
import cn.sxhengrun.purchase.entity.HeadlineAlbum;
import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.entity.PurchaseAlbum;
import cn.sxhengrun.purchase.entity.Quote;
import cn.sxhengrun.purchase.entity.QuoteAlbum;
import cn.sxhengrun.purchase.entity.Sale;
import cn.sxhengrun.purchase.entity.SaleAlbum;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.remote.vo.ImageInfo;
import cn.sxhengrun.purchase.vo.HeadlineVO;
import cn.sxhengrun.purchase.vo.PhotoVO;
import cn.sxhengrun.purchase.vo.PurchaseVO;
import cn.sxhengrun.purchase.vo.QuoteVO;
import cn.sxhengrun.purchase.vo.SaleVO;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class ConvertUtils {
    public static PurchaseVO toVO(final Purchase purchase, final List<PurchaseAlbum> purchaseAlbums, final ImageRemoteService imageRemoteService) {
        PurchaseVO purchaseVO = new PurchaseVO();
        purchaseVO.setId(String.valueOf(purchase.getId()));
        purchaseVO.setTitle(purchase.getTitle());
        purchaseVO.setTel(purchase.getTel());
        purchaseVO.setType(purchase.getType());
        purchaseVO.setDetails(purchase.getDetails());
        purchaseVO.setCompleted(purchase.getCompleted() == null ? false : purchase.getCompleted());
        purchaseVO.setPublishBy(purchase.getPublishBy());
        purchaseVO.setPublishAt(purchase.getPublishAt());
        purchaseVO.setPhotos(Optional.ofNullable(purchaseAlbums)
                .orElse(Collections.emptyList())
                .stream()
                .filter(purchaseAlbum -> purchaseAlbum.getOrderIndex() != null)
                .filter(purchaseAlbum -> Boolean.FALSE.equals(purchaseAlbum.getDeleted()))
                .sorted(comparing(PurchaseAlbum::getOrderIndex))
                .map(purchaseAlbum -> {
                    ImageInfo imageInfo = imageRemoteService.findImageInfo(purchaseAlbum.getImageId());
                    if (imageInfo == null) {
                        return null;
                    }

                    PhotoVO photoVO = new PhotoVO();
                    photoVO.setImageId(imageInfo.getId());
                    photoVO.setShowOrder(purchaseAlbum.getOrderIndex());
                    photoVO.setThumbUrl(imageInfo.getThumbUrl());
                    photoVO.setUrl(imageInfo.getOriginUrl());
                    photoVO.setUserId(imageInfo.getUploadedBy());
                    return photoVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return purchaseVO;
    }

    public static void toEntity(final PurchaseVO purchaseVO, final Purchase purchase, final List<PurchaseAlbum> purchaseAlbums) {
        Assert.notNull(purchase, "purchase is null");
        Assert.notNull(purchaseAlbums, "purchaseAlbums is null");
        Assert.isTrue(purchaseAlbums.isEmpty(), "purchaseAlbums is not empty");

        purchase.setId(purchaseVO.getId() == null ? null : Long.parseLong(purchaseVO.getId()));
        purchase.setTitle(purchaseVO.getTitle());
        purchase.setTel(purchaseVO.getTel());
        purchase.setType(purchaseVO.getType());
        purchase.setDetails(purchaseVO.getDetails());
        purchase.setCompleted(purchaseVO.isCompleted());

        if (!CollectionUtils.isEmpty(purchaseVO.getPhotos())) {
            int index = 0;
            for (PhotoVO photoVO : purchaseVO.getPhotos()) {
                PurchaseAlbum purchaseAlbum = new PurchaseAlbum();
                purchaseAlbum.setImageId(photoVO.getImageId());
                purchaseAlbum.setOrderIndex(index++);
                purchaseAlbums.add(purchaseAlbum);
            }
        }
    }

    public static SaleVO toVO(final Sale sale, final List<SaleAlbum> saleAlbums, final ImageRemoteService imageRemoteService) {
        SaleVO saleVO = new SaleVO();
        saleVO.setId(String.valueOf(sale.getId()));
        saleVO.setTitle(sale.getTitle());
        saleVO.setTel(sale.getTel());
        //TODO saleVO.setType(sale.getType());
        saleVO.setDetails(sale.getDetails());
        saleVO.setPublishBy(sale.getPublishBy());
        saleVO.setPublishAt(sale.getPublishAt());
        saleVO.setPhotos(Optional.ofNullable(saleAlbums)
                .orElse(Collections.emptyList())
                .stream()
                .filter(saleAlbum -> saleAlbum.getOrderIndex() != null)
                .filter(saleAlbum -> Boolean.FALSE.equals(saleAlbum.getDeleted()))
                .sorted(comparing(SaleAlbum::getOrderIndex))
                .map(saleAlbum -> {
                    ImageInfo imageInfo = imageRemoteService.findImageInfo(saleAlbum.getImageId());
                    if (imageInfo == null) {
                        return null;
                    }

                    PhotoVO photoVO = new PhotoVO();
                    photoVO.setImageId(imageInfo.getId());
                    photoVO.setShowOrder(saleAlbum.getOrderIndex());
                    photoVO.setThumbUrl(imageInfo.getThumbUrl());
                    photoVO.setUrl(imageInfo.getOriginUrl());
                    photoVO.setUserId(imageInfo.getUploadedBy());
                    return photoVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return saleVO;
    }

    public static void toEntity(final SaleVO saleVO, final Sale sale, final List<SaleAlbum> saleAlbums) {
        Assert.notNull(sale, "sale is null");
        Assert.notNull(saleAlbums, "saleAlbums is null");
        Assert.isTrue(saleAlbums.isEmpty(), "saleAlbums is not empty");

        sale.setId(saleVO.getId() == null ? null : Long.parseLong(saleVO.getId()));
        sale.setTitle(saleVO.getTitle());
        sale.setTel(saleVO.getTel());
        sale.setDetails(saleVO.getDetails());

        if (!CollectionUtils.isEmpty(saleVO.getPhotos())) {
            int index = 0;
            for (PhotoVO photoVO : saleVO.getPhotos()) {
                SaleAlbum saleAlbum = new SaleAlbum();
                saleAlbum.setImageId(photoVO.getImageId());
                saleAlbum.setOrderIndex(index++);
                saleAlbums.add(saleAlbum);
            }
        }
    }


    public static QuoteVO toVO(final Quote quote, final List<QuoteAlbum> quoteAlbums, final ImageRemoteService imageRemoteService) {
        QuoteVO quoteVO = new QuoteVO();
        quoteVO.setId(String.valueOf(quote.getId()));
        quoteVO.setTel(quote.getTel());
        quoteVO.setDetails(quote.getDetails());
        quoteVO.setQuoteBy(quote.getQuoteBy());
        quoteVO.setQuoteAt(quote.getQuoteAt());
        quoteVO.setPhotos(Optional.ofNullable(quoteAlbums)
                .orElse(Collections.emptyList())
                .stream()
                .filter(quoteAlbum -> quoteAlbum.getOrderIndex() != null)
                .filter(quoteAlbum -> Boolean.FALSE.equals(quoteAlbum.getDeleted()))
                .sorted(comparing(QuoteAlbum::getOrderIndex))
                .map(quoteAlbum -> {
                    ImageInfo imageInfo = imageRemoteService.findImageInfo(quoteAlbum.getImageId());
                    if (imageInfo == null) {
                        return null;
                    }

                    PhotoVO photoVO = new PhotoVO();
                    photoVO.setImageId(imageInfo.getId());
                    photoVO.setShowOrder(quoteAlbum.getOrderIndex());
                    photoVO.setThumbUrl(imageInfo.getThumbUrl());
                    photoVO.setUrl(imageInfo.getOriginUrl());
                    photoVO.setUserId(imageInfo.getUploadedBy());
                    return photoVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return quoteVO;
    }

    public static void toEntity(final QuoteVO quoteVO, final Quote quote, final List<QuoteAlbum> quoteAlbums) {
        Assert.notNull(quote, "quote is null");
        Assert.notNull(quoteAlbums, "quoteAlbums is null");
        Assert.isTrue(quoteAlbums.isEmpty(), "quoteAlbums is not empty");

        quote.setId(quoteVO.getId() == null ? null : Long.parseLong(quoteVO.getId()));
        quote.setTel(quoteVO.getTel());
        quote.setDetails(quoteVO.getDetails());

        if (!CollectionUtils.isEmpty(quoteVO.getPhotos())) {
            int index = 0;
            for (PhotoVO photoVO : quoteVO.getPhotos()) {
                QuoteAlbum quoteAlbum = new QuoteAlbum();
                quoteAlbum.setImageId(photoVO.getImageId());
                quoteAlbum.setOrderIndex(index++);
                quoteAlbums.add(quoteAlbum);
            }
        }
    }

    public static HeadlineVO toVO(final Headline headline, final List<HeadlineAlbum> headlineAlbums, final ImageRemoteService imageRemoteService) {
        HeadlineVO headlineVO = new HeadlineVO();
        headlineVO.setId(String.valueOf(headline.getId()));
        headlineVO.setTitle(headline.getTitle());
        headlineVO.setDetails(headline.getDetails());
        headlineVO.setActive(headline.getActive() == null ? false : headline.getActive());
        headlineVO.setPublishBy(headline.getPublishBy());
        headlineVO.setPublishAt(headline.getPublishAt());
        headlineVO.setPhotos(Optional.ofNullable(headlineAlbums)
                .orElse(Collections.emptyList())
                .stream()
                .filter(headlineAlbum -> headlineAlbum.getOrderIndex() != null)
                .filter(headlineAlbum -> Boolean.FALSE.equals(headlineAlbum.getDeleted()))
                .sorted(comparing(HeadlineAlbum::getOrderIndex))
                .map(headlineAlbum -> {
                    ImageInfo imageInfo = imageRemoteService.findImageInfo(headlineAlbum.getImageId());
                    if (imageInfo == null) {
                        return null;
                    }

                    PhotoVO photoVO = new PhotoVO();
                    photoVO.setImageId(imageInfo.getId());
                    photoVO.setShowOrder(headlineAlbum.getOrderIndex());
                    photoVO.setThumbUrl(imageInfo.getThumbUrl());
                    photoVO.setUrl(imageInfo.getOriginUrl());
                    photoVO.setUserId(imageInfo.getUploadedBy());
                    return photoVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return headlineVO;
    }

    public static void toEntity(final HeadlineVO headlineVO, final Headline headline, final List<HeadlineAlbum> headlineAlbums) {
        Assert.notNull(headline, "headline is null");
        Assert.notNull(headlineAlbums, "headlineAlbums is null");
        Assert.isTrue(headlineAlbums.isEmpty(), "headlineAlbums is not empty");

        headline.setId(headlineVO.getId() == null ? null : Long.parseLong(headlineVO.getId()));
        headline.setTitle(headlineVO.getTitle());
        headline.setDetails(headlineVO.getDetails());
        headline.setActive(headlineVO.isActive());

        if (!CollectionUtils.isEmpty(headlineVO.getPhotos())) {
            int index = 0;
            for (PhotoVO photoVO : headlineVO.getPhotos()) {
                HeadlineAlbum headlineAlbum = new HeadlineAlbum();
                headlineAlbum.setImageId(photoVO.getImageId());
                headlineAlbum.setOrderIndex(index++);
                headlineAlbums.add(headlineAlbum);
            }
        }
    }
}
