package cn.sxhengrun.purchase.service;

import cn.sxhengrun.purchase.entity.Sale;
import cn.sxhengrun.purchase.entity.SaleAlbum;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.repository.SaleAlbumRepository;
import cn.sxhengrun.purchase.repository.SaleRepository;
import cn.sxhengrun.purchase.repository.domain.OffsetBasedPageRequest;
import cn.sxhengrun.purchase.vo.SaleVO;
import cn.sxhengrun.purchase.vo.util.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.eulerframework.common.util.Assert;
import org.eulerframework.web.core.base.request.QueryRequest.OrderMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleAlbumRepository saleAlbumRepository;

    @Autowired
    private ImageRemoteService imageRemoteService;

    public List<SaleVO> getSales(String userId, String[] types, String publishBy, OrderMode order, long offset, int limit) {
        Assert.hasText(userId, "userId is required");

        Specification<Sale> spec = (Specification<Sale>) (root, query, criteriaBuilder) -> {
            List<Predicate> p = new ArrayList<>();

            p.add(criteriaBuilder.equal(root.get("deleted"), false));

            if(StringUtils.hasText(publishBy)) {
                p.add(criteriaBuilder.equal(root.get("publishBy"), publishBy));
            }

            if (!ArrayUtils.isEmpty(types)) {
                In<String> in = criteriaBuilder.in(root.get("type"));

                for (String type : types) {
                    in.value(type);
                }

                p.add(in);
            }

            return criteriaBuilder.and(p.toArray(new Predicate[0]));
        };

        Page<Sale> page = this.saleRepository.findAll(spec, new OffsetBasedPageRequest(offset, limit,
                Sort.by(OrderMode.DESC.equals(order) ? Order.desc("publishAt") : Order.asc("publishAt"))));
        return page.getContent()
                .stream()
                .map(sale -> {
                    List<SaleAlbum> saleAlbums = this.saleAlbumRepository.findAllBySaleId(sale.getId());
                    return ConvertUtils.toVO(sale, saleAlbums, this.imageRemoteService);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addOrUpdateSale(String userId, SaleVO saleVO) {
        Assert.hasText(userId, "userId is required");

        Sale sale = new Sale();
        List<SaleAlbum> saleAlbums = new ArrayList<>();
        ConvertUtils.toEntity(saleVO, sale, saleAlbums);

        Date now = new Date();

        if (sale.getId() == null) {
            sale.setPublishAt(now);
            sale.setPublishBy(userId);
            sale.setCreateAt(now);
        } else {
            Sale exitsSale = this.saleRepository.findById(sale.getId()).orElse(null);

            if (exitsSale == null || Boolean.TRUE.equals(exitsSale.getDeleted()) || !userId.equals(exitsSale.getPublishBy())) {
                throw new IllegalArgumentException("要修改的甩卖信息不存在");
            }

            sale.setPublishAt(exitsSale.getPublishAt());
            sale.setPublishBy(exitsSale.getPublishBy());
            sale.setCreateAt(exitsSale.getCreateAt());
        }

        sale.setModifyAt(now);
        sale.setDeleted(false);
        this.saleRepository.save(sale);

        this.saveSaleAlbum(sale.getId(), saleAlbums);
        return sale.getId();
    }

    @Transactional
    public void trashSale(String userId, long id) {
        Assert.hasText(userId, "userId is required");

        Date now = new Date();
        Sale exitsSale = this.saleRepository.findById(id).orElse(null);

        if (exitsSale == null || Boolean.TRUE.equals(exitsSale.getDeleted()) || !userId.equals(exitsSale.getPublishBy())) {
            throw new IllegalArgumentException("要删除的甩卖信息不存在");
        }

        this.saleAlbumRepository.trashBySaleId(id, now);
        this.saleRepository.trashById(id, now);
    }

    private void saveSaleAlbum(long saleId, List<SaleAlbum> saleAlbums) {
        this.saleAlbumRepository.deleteBySaleId(saleId);
        if (CollectionUtils.isEmpty(saleAlbums)) {
            return;
        }
        Date now = new Date();
        for (SaleAlbum saleAlbum : saleAlbums) {
            saleAlbum.setSaleId(saleId);
            saleAlbum.setCreateAt(now);
            saleAlbum.setModifyAt(now);
            saleAlbum.setDeleted(false);
        }
        this.saleAlbumRepository.saveAll(saleAlbums);
    }
}
