package org.eulerframework.uc.hengrun.web.service;

import org.eulerframework.uc.hengrun.web.entity.HengRunUserProfileEntity;
import org.eulerframework.uc.hengrun.web.repository.HengRunUserProfileEntityRepository;
import org.eulerframework.common.util.JavaObjectUtils;
import org.eulerframework.web.module.authentication.service.EulerUserExtraDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HengRunUserExtraDataProcessor implements EulerUserExtraDataProcessor {
    @Autowired
    private HengRunUserProfileEntityRepository hengRunUserProfileEntityRepository;

    public boolean process(String userId, Map<String, Object> extraData) {
        try {
            HengRunUserProfileEntity hengRunUserProfileEntity = JavaObjectUtils.readMapAsObject(extraData, HengRunUserProfileEntity.class);
            hengRunUserProfileEntity.setUserId(userId);
            this.hengRunUserProfileEntityRepository.save(hengRunUserProfileEntity);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Map<String, Object> loadExtraData(String userId) {
        HengRunUserProfileEntity hengRunUserProfileEntity = this.hengRunUserProfileEntityRepository.findById(userId).orElse(null);
        if(hengRunUserProfileEntity == null) {
            return null;
        }

        try {
            return JavaObjectUtils.writeObjectToMap(hengRunUserProfileEntity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
