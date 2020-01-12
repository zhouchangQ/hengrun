package cn.sxhengrun.purchase.vo.util;

import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.entity.PurchaseAlbum;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.remote.vo.ImageInfo;
import cn.sxhengrun.purchase.vo.PhotoVO;
import cn.sxhengrun.purchase.vo.PurchaseVO;
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
}
