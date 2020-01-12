package cn.sxhengrun.purchase.remote;

import cn.sxhengrun.purchase.remote.vo.ImageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "euler-cloud-image", fallback = ImageRemoteService.ImageRemoteServiceFallback.class)
public interface ImageRemoteService {

    @GetMapping("image/{imageInfoId}")
    ImageInfo findImageInfo(@PathVariable("imageInfoId") String imageInfoId);

    @Component
    class ImageRemoteServiceFallback implements ImageRemoteService {
        @Override
        public ImageInfo findImageInfo(String imageInfoId) {
            return null;
        }
    }
}
