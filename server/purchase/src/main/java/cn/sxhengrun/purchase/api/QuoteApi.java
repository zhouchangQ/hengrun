package cn.sxhengrun.purchase.api;

import cn.sxhengrun.purchase.service.QuoteService;
import cn.sxhengrun.purchase.vo.QuoteVO;
import org.eulerframework.cloud.security.EulerCloudUserContext;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("quote")
public class QuoteApi extends ApiSupportWebController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public List<QuoteVO> quote(@RequestParam long purchaseId) {
        return this.quoteService.getQuotes(EulerCloudUserContext.getCurrentUserId(), purchaseId);
    }

    @PostMapping
    public void quote(@RequestParam long purchaseId, @RequestBody QuoteVO quoteVO) {
        this.quoteService.quoteForPurchase(EulerCloudUserContext.getCurrentUserId(), purchaseId, quoteVO);
    }
}
