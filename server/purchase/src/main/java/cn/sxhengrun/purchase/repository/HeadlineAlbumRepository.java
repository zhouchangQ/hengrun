package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.HeadlineAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HeadlineAlbumRepository extends JpaRepository<HeadlineAlbum, Long> {
    List<HeadlineAlbum> findAllByHeadlineId(long headlineId);

    @Modifying()
    @Query("update HeadlineAlbum set modifyAt = :timestamp, deleted = true where headlineId = :headlineId")
    void trashByHeadlineId(long headlineId, Date timestamp);

    @Modifying()
    @Query("delete from HeadlineAlbum where headlineId = :headlineId")
    void deleteByHeadlineId(long headlineId);
}
