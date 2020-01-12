package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

    @Modifying()
    @Query("update Purchase set modifyAt = :timestamp, deleted = true where id = :id")
    void trashById(long id, Date timestamp);
}
