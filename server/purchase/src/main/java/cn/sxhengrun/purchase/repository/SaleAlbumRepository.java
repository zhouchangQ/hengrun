package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.SaleAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleAlbumRepository extends JpaRepository<SaleAlbum, Long> {
    List<SaleAlbum> findAllBySaleId(long saleId);

    @Modifying()
    @Query("update SaleAlbum set modifyAt = :timestamp, deleted = true where saleId = :saleId")
    void trashBySaleId(long saleId, Date timestamp);

    @Modifying()
    @Query("delete from SaleAlbum where saleId = :saleId")
    void deleteBySaleId(long saleId);
}
