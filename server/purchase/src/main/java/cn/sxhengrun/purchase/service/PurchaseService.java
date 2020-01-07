package cn.sxhengrun.purchase.service;

import cn.sxhengrun.purchase.Increment;
import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.entity.PurchaseAlbum;
import cn.sxhengrun.purchase.repository.PurchaseAlbumRepository;
import cn.sxhengrun.purchase.repository.PurchaseRepository;
import cn.sxhengrun.purchase.vo.AlbumVO;
import cn.sxhengrun.purchase.vo.PurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseAlbumRepository purchaseAlbumRepository;

    public List<PurchaseVO> getPurchases() {
        return Optional.ofNullable(this.purchaseRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .filter(purchase -> purchase.getDeleted() == null || Boolean.FALSE.equals(purchase.getDeleted()))
                .map(purchase -> {
                    PurchaseVO purchaseVO = new PurchaseVO();
                    purchaseVO.setId(purchase.getId());
                    purchaseVO.setTitle(purchase.getTitle());
                    purchaseVO.setTel(purchase.getTel());
                    purchaseVO.setType(purchase.getType());
                    purchaseVO.setDetails(purchase.getDetails());
                    purchaseVO.setPublishAt(purchase.getPublishAt());
                    purchaseVO.setPublishBy(purchase.getPublishBy());
                    purchaseVO.setCompleted(purchase.getCompleted() == null ? false : purchase.getCompleted());

                    List<PurchaseAlbum> purchaseAlbums = this.purchaseAlbumRepository.findAllByPurchaseId(purchase.getId());

                    purchaseVO.setAlbum(Optional
                            .ofNullable(purchaseAlbums)
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(purchaseAlbum -> {
                                AlbumVO albumVO = new AlbumVO();
                                albumVO.setImageId(purchaseAlbum.getImageId());
                                return albumVO;
                            })
                            .collect(Collectors.toList()));

                    return purchaseVO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addPurchase(String userId, PurchaseVO purchaseVO) {
        Purchase purchase = new Purchase();

        purchase.setTitle(purchaseVO.getTitle());
        purchase.setTel(purchaseVO.getTel());
        purchase.setType(purchaseVO.getType());
        purchase.setDetails(purchaseVO.getDetails());

        Date now = new Date();
        purchase.setPublishAt(now);
        purchase.setPublishBy(userId);
        purchase.setCreateAt(now);
        purchase.setModifyAt(now);
        purchase.setDeleted(false);
        purchase.setCompleted(purchaseVO.isCompleted());

        this.purchaseRepository.save(purchase);

        this.savePurchaseAlbum(purchase.getId(), purchaseVO.getAlbum());

        return purchase.getId();
    }

    @Transactional
    public Long updatePurchase(String userId, PurchaseVO purchaseVO) {
        Purchase purchase = this.purchaseRepository.findById(purchaseVO.getId()).orElse(null);

        if (purchase == null || purchase.getDeleted()) {
            throw new IllegalArgumentException("指定的采购ID不存在");
        }

        purchase.setTitle(purchaseVO.getTitle());
        purchase.setTel(purchaseVO.getTel());
        purchase.setType(purchaseVO.getType());
        purchase.setDetails(purchaseVO.getDetails());

        Date now = new Date();
        purchase.setModifyAt(now);
        purchase.setDeleted(false);
        purchase.setCompleted(purchaseVO.isCompleted());

        this.purchaseRepository.save(purchase);

        this.purchaseAlbumRepository.deleteAllByPurchaseId(purchase.getId());

        this.savePurchaseAlbum(purchase.getId(), purchaseVO.getAlbum());

        return purchase.getId();
    }

    private void savePurchaseAlbum(long purchaseId, List<AlbumVO> albumVOS) {
        Increment increment = Increment.newInstance();
        Date now = new Date();
        List<PurchaseAlbum> purchaseAlbums = Optional.ofNullable(albumVOS)
                .orElse(Collections.emptyList())
                .stream()
                .filter(albumVO -> albumVO != null && StringUtils.hasText(albumVO.getImageId()))
                .map(albumVO -> {
                    PurchaseAlbum purchaseAlbum = new PurchaseAlbum();
                    purchaseAlbum.setImageId(albumVO.getImageId());
                    purchaseAlbum.setPurchaseId(purchaseId);
                    purchaseAlbum.setOrderIndex(increment.getAndIncrement());
                    purchaseAlbum.setCreateAt(now);
                    purchaseAlbum.setModifyAt(now);
                    purchaseAlbum.setDeleted(false);
                    return purchaseAlbum;
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(purchaseAlbums)) {
            this.purchaseAlbumRepository.saveAll(purchaseAlbums);
        }
    }

    @Transactional
    public void trashPurchase(long id) {
        this.purchaseAlbumRepository.trashByPurchaseId(id);
        this.purchaseRepository.trashById(id);
    }
}
