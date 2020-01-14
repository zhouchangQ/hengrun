package org.eulerframework.uc.hengrun.web.repository;

import org.eulerframework.uc.hengrun.web.entity.HengRunUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HengRunUserProfileEntityRepository extends JpaRepository<HengRunUserProfileEntity, String>, JpaSpecificationExecutor<HengRunUserProfileEntity> {
    HengRunUserProfileEntity findByUserId(String userId);
}
