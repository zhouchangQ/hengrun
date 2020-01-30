package cn.sxhengrun.purchase.repository;

import cn.sxhengrun.purchase.entity.HengRunUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HengRunUserProfileRepository extends JpaRepository<HengRunUserProfileEntity, String> {
    HengRunUserProfileEntity findHengRunUserProfileEntityByUserId(String userId);
}
