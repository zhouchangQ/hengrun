package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

    @Modifying()
    @Query("update Sale set modifyAt = :timestamp, deleted = true where id = :id")
    void trashById(long id, Date timestamp);
}
