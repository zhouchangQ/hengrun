package cn.sxhengrun.purchase.remote;

import cn.sxhengrun.purchase.entity.HengRunUserProfileEntity;
import cn.sxhengrun.purchase.remote.vo.HengRunUserProfileVO;
import cn.sxhengrun.purchase.repository.HengRunUserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class HengRunUserProfileRemoteService {
    @Autowired
    private HengRunUserProfileRepository hengRunUserProfileRepository;

    public HengRunUserProfileVO findHengRunUserProfile(String userId) {
        Assert.hasText(userId, "userId is required");
        HengRunUserProfileEntity hengRunUserProfileEntity = this.hengRunUserProfileRepository.findHengRunUserProfileEntityByUserId(userId);

        if(hengRunUserProfileEntity == null) {
            return null;
        }

        HengRunUserProfileVO hengRunUserProfileVO = new HengRunUserProfileVO();
        hengRunUserProfileVO.setCompanyName(hengRunUserProfileEntity.getCompanyName());
        hengRunUserProfileVO.setScope(hengRunUserProfileEntity.getScope());

        return hengRunUserProfileVO;
    }
}
