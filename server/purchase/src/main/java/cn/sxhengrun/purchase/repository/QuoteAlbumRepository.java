package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.QuoteAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuoteAlbumRepository extends JpaRepository<QuoteAlbum, Long> {
    List<QuoteAlbum> findAllByQuoteId(long quoteId);

    @Modifying()
    @Query("update QuoteAlbum set modifyAt = :timestamp, deleted = true where quoteId = :quoteId")
    void trashByQuoteId(long quoteId, Date timestamp);

    @Modifying()
    @Query("delete from QuoteAlbum where quoteId = :quoteId")
    void deleteAllByQuoteId(long quoteId);
}
