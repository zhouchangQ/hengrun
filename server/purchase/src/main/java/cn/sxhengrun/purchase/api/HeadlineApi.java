package cn.sxhengrun.purchase.api;

import cn.sxhengrun.purchase.service.HeadlineService;
import cn.sxhengrun.purchase.vo.HeadlineVO;
import org.eulerframework.cloud.security.EulerCloudUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("headline")
public class HeadlineApi {

    @Autowired
    private HeadlineService headlineService;

    @GetMapping
    public HeadlineVO getHeadline() {
        HeadlineVO headlineVO = this.headlineService.getHeadLine(EulerCloudUserContext.getCurrentUserId());
        if (headlineVO != null && headlineVO.isActive()) {
            return headlineVO;
        } else {
            HeadlineVO emptyHeadlineVO = new HeadlineVO();
            emptyHeadlineVO.setActive(false);
            return emptyHeadlineVO;
        }
    }

    @GetMapping("edit")
    public HeadlineVO getHeadlineForEdit() {
        EulerCloudUserContext.requiredAuthorities("PURCHASER");
        return this.headlineService.getHeadLine(EulerCloudUserContext.getCurrentUserId());
    }

    @PostMapping("edit")
    public Long postHeadlineForEdit(@RequestBody HeadlineVO headlineVO) {
        EulerCloudUserContext.requiredAuthorities("PURCHASER");
        return this.headlineService.updateHeadline(EulerCloudUserContext.getCurrentUserId(), headlineVO);
    }
}
