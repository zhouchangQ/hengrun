package org.eulerframework.uc.hengrun.web.service;

import org.eulerframework.uc.hengrun.web.entity.HengRunUserProfileEntity;
import org.eulerframework.uc.hengrun.web.repository.HengRunUserProfileEntityRepository;
import org.eulerframework.uc.hengrun.web.repository.HengRunUserRepository;
import org.eulerframework.uc.hengrun.web.vo.SalerVO;
import org.eulerframework.web.module.authentication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalerService {

    @Autowired
    private HengRunUserProfileEntityRepository hengRunUserProfileEntityRepository;
    @Autowired
    private HengRunUserRepository hengRunUserRepository;

    public List<SalerVO> findSalers(String[] scopes, long offset, int limit) {
        Specification<HengRunUserProfileEntity> spec = (Specification<HengRunUserProfileEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> p = new ArrayList<>();

            if (scopes != null && scopes.length > 0) {
                In<String> in = criteriaBuilder.in(root.get("scope"));

                for (String scope : scopes) {
                    in.value(scope);
                }

                p.add(in);
            }

            return criteriaBuilder.and(p.toArray(new Predicate[]{}));
        };

        Page<HengRunUserProfileEntity> page = this.hengRunUserProfileEntityRepository.findAll(spec, new OffsetBasedPageRequest(offset, limit));
        return page.getContent()
                .stream()
                .map(hengRunUserProfileEntity -> {
                    SalerVO salerVO = new SalerVO();
                    salerVO.setBerth(hengRunUserProfileEntity.getBerth());
                    salerVO.setCompanyName(hengRunUserProfileEntity.getCompanyName());
                    salerVO.setFullName(hengRunUserProfileEntity.getFullName());
                    salerVO.setNickName(hengRunUserProfileEntity.getNickName());
                    salerVO.setScope(hengRunUserProfileEntity.getScope());

                    User user = this.hengRunUserRepository.findById(hengRunUserProfileEntity.getUserId()).orElse(null);

                    if(user != null) {
                        salerVO.setMobile(user.getMobile());
                    }

                    return salerVO;
                })
                .collect(Collectors.toList());
    }
}
