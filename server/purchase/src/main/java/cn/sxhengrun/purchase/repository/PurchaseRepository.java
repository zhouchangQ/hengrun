package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Modifying()
    @Query("update Purchase set modifyAt = current_timestamp, deleted = true where id = :id")
    void trashById(long id);

    @Modifying
    @Query("delete from Purchase where id = :id")
    void deletePurchase(long id);
}
