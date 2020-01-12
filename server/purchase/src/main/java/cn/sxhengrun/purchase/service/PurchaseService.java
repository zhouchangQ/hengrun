package cn.sxhengrun.purchase.service;

import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.entity.PurchaseAlbum;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.repository.OffsetBasedPageRequest;
import cn.sxhengrun.purchase.repository.PurchaseAlbumRepository;
import cn.sxhengrun.purchase.repository.PurchaseRepository;
import cn.sxhengrun.purchase.vo.PurchaseVO;
import cn.sxhengrun.purchase.vo.util.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.eulerframework.web.core.base.request.QueryRequest.OrderMode;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseAlbumRepository purchaseAlbumRepository;

    @Autowired
    private ImageRemoteService imageRemoteService;

    public List<PurchaseVO> getPurchases(String userId, String[] types, OrderMode order, long offset, int limit) {
        Specification<Purchase> spec = (Specification<Purchase>) (root, query, criteriaBuilder) -> {
            List<Predicate> p = new ArrayList<>();

            p.add(criteriaBuilder.equal(root.get("deleted"), false));

            if (!ArrayUtils.isEmpty(types)) {
                In<String> in = criteriaBuilder.in(root.get("type"));

                for (String type : types) {
                    in.value(type);
                }

                p.add(in);
            }

            return criteriaBuilder.and(p.toArray(new Predicate[]{}));
        };

        Page<Purchase> page = this.purchaseRepository.findAll(spec, new OffsetBasedPageRequest(offset, limit,
                Sort.by(OrderMode.DESC.equals(order) ? Order.desc("publishAt") : Order.asc("publishAt"))));
        return page.getContent()
                .stream()
                .map(purchase -> {
                    List<PurchaseAlbum> purchaseAlbums = this.purchaseAlbumRepository.findAllByPurchaseId(purchase.getId());
                    return ConvertUtils.toVO(purchase, purchaseAlbums, this.imageRemoteService);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addOrUpdatePurchase(String userId, PurchaseVO purchaseVO) {
        Purchase purchase = new Purchase();
        List<PurchaseAlbum> purchaseAlbums = new ArrayList<>();
        ConvertUtils.toEntity(purchaseVO, purchase, purchaseAlbums);

        purchase.setTitle(purchaseVO.getTitle());
        purchase.setTel(purchaseVO.getTel());
        purchase.setType(purchaseVO.getType());
        purchase.setDetails(purchaseVO.getDetails());

        Date now = new Date();

        if (purchase.getId() == null) {
            purchase.setPublishAt(now);
            purchase.setPublishBy(userId);
            purchase.setCreateAt(now);
        } else {
            Purchase exitsPurchase = this.purchaseRepository.findById(purchase.getId()).orElse(null);

            if (exitsPurchase == null || Boolean.TRUE.equals(exitsPurchase.getDeleted())) {
                throw new IllegalArgumentException("要修改的采购信息不存在");
            }

            purchase.setPublishAt(exitsPurchase.getPublishAt());
            purchase.setPublishBy(exitsPurchase.getPublishBy());
            purchase.setCreateAt(exitsPurchase.getCreateAt());
        }

        purchase.setModifyAt(now);
        purchase.setDeleted(false);
        this.purchaseRepository.save(purchase);

        this.savePurchaseAlbum(purchase.getId(), purchaseAlbums);
        return purchase.getId();
    }

    @Transactional
    public void trashPurchase(String userId, long id) {
        Date now = new Date();
        this.purchaseAlbumRepository.trashByPurchaseId(id, now);
        this.purchaseRepository.trashById(id, now);
    }

    private void savePurchaseAlbum(long purchaseId, List<PurchaseAlbum> purchaseAlbums) {
        this.purchaseAlbumRepository.deleteAllByPurchaseId(purchaseId);
        if (CollectionUtils.isEmpty(purchaseAlbums)) {
            return;
        }
        Date now = new Date();
        for (PurchaseAlbum purchaseAlbum : purchaseAlbums) {
            purchaseAlbum.setPurchaseId(purchaseId);
            purchaseAlbum.setCreateAt(now);
            purchaseAlbum.setModifyAt(now);
            purchaseAlbum.setDeleted(false);
        }
        this.purchaseAlbumRepository.saveAll(purchaseAlbums);
    }
}
