package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.PurchaseAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseAlbumRepository extends JpaRepository<PurchaseAlbum, Long> {
    List<PurchaseAlbum> findAllByPurchaseId(long purchaseId);

    @Modifying()
    @Query("update PurchaseAlbum set modifyAt = :timestamp, deleted = true where purchaseId = :purchaseId")
    void trashByPurchaseId(long purchaseId, Date timestamp);

    @Modifying()
    @Query("delete from PurchaseAlbum where purchaseId = :purchaseId")
    void deleteByPurchaseId(long purchaseId);
}
