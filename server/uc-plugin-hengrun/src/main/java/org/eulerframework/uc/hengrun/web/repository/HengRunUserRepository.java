package org.eulerframework.uc.hengrun.web.repository;

import org.eulerframework.uc.hengrun.web.vo.PurchaserVO;
import org.eulerframework.web.module.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Converter;
import java.util.List;

/**
 * @author cFrost
 *
 */
@Repository
public interface HengRunUserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(value = "select u.id as userId, " +
            "       u.mobile as mobile, " +
            "       hup.full_name as fullName " +
            "from sys_user u " +
            "inner join sys_user_group ug on ug.user_id = u.id " +
            "inner join sys_group g on g.id = ug.group_id " +
            "inner join sys_group_authority ga on g.id = ga.group_id " +
            "left join hengrun_user_profile hup on u.id = hup.user_id " +
            "where ga.authority = 'PURCHASER'", nativeQuery = true)
    List<String[]> findPurchasers();
}
