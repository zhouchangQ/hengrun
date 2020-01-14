package cn.sxhengrun.purchase.service;

import cn.sxhengrun.purchase.entity.Headline;
import cn.sxhengrun.purchase.entity.HeadlineAlbum;
import cn.sxhengrun.purchase.remote.ImageRemoteService;
import cn.sxhengrun.purchase.repository.HeadlineAlbumRepository;
import cn.sxhengrun.purchase.repository.HeadlineRepository;
import cn.sxhengrun.purchase.vo.HeadlineVO;
import cn.sxhengrun.purchase.vo.util.ConvertUtils;
import org.eulerframework.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HeadlineService {

    @Autowired
    private HeadlineRepository headlineRepository;
    @Autowired
    private HeadlineAlbumRepository headlineAlbumRepository;

    @Autowired
    private ImageRemoteService imageRemoteService;

    public HeadlineVO getHeadLine(String userId) {
        Assert.hasText(userId, "userId is required");

        List<Headline> headlines = this.headlineRepository.findAll();

        if (headlines.isEmpty()) {
            return null;
        }

        List<HeadlineAlbum> headlineAlbums = this.headlineAlbumRepository.findAll();

        return ConvertUtils.toVO(headlines.get(0), headlineAlbums, this.imageRemoteService);
    }

    @Transactional
    public Long updateHeadline(String userId, HeadlineVO headlineVO) {
        Assert.hasText(userId, "userId is required");

        Headline headline = new Headline();
        List<HeadlineAlbum> headlineAlbums = new ArrayList<>();
        ConvertUtils.toEntity(headlineVO, headline, headlineAlbums);

        Date now = new Date();

        this.headlineRepository.deleteAll();
        headline.setId(null);
        headline.setPublishBy(userId);
        headline.setPublishAt(now);
        headline.setCreateAt(now);
        headline.setModifyAt(now);
        headline.setDeleted(false);
        this.headlineRepository.save(headline);

        this.saveHeadlineAlbum(headline.getId(), headlineAlbums);
        return headline.getId();
    }

    private void saveHeadlineAlbum(long headlineId, List<HeadlineAlbum> headlineAlbums) {
        this.headlineAlbumRepository.deleteAll();

        if (CollectionUtils.isEmpty(headlineAlbums)) {
            return;
        }
        Date now = new Date();
        for (HeadlineAlbum headlineAlbum : headlineAlbums) {
            headlineAlbum.setHeadlineId(headlineId);
            headlineAlbum.setCreateAt(now);
            headlineAlbum.setModifyAt(now);
            headlineAlbum.setDeleted(false);
        }
        this.headlineAlbumRepository.saveAll(headlineAlbums);
    }

}
