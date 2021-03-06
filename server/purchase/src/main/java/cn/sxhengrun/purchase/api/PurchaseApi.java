package cn.sxhengrun.purchase.api;

import cn.sxhengrun.purchase.service.PurchaseService;
import cn.sxhengrun.purchase.vo.PurchaseVO;
import org.eulerframework.cloud.security.EulerAccessDeniedException;
import org.eulerframework.cloud.security.EulerCloudUserContext;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.core.base.request.QueryRequest.OrderMode;
import org.eulerframework.web.core.base.response.ErrorResponse;
import org.eulerframework.web.core.exception.web.SystemWebError;
import org.eulerframework.web.core.exception.web.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("purchase")
public class PurchaseApi extends ApiSupportWebController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public List<PurchaseVO> getPurchase(
            @RequestParam(required = false) String[] types,
            @RequestParam(required = false, defaultValue = "DESC") OrderMode order,
            @RequestParam long offset,
            @RequestParam int limit) {
        //EulerCloudUserContext.requiredAuthorities("PURCHASER");
        return this.purchaseService.getPurchases(EulerCloudUserContext.getCurrentUserId(), types, order, offset, limit);
    }

    @PostMapping
    public long savePurchase(@RequestBody PurchaseVO purchase) {
        EulerCloudUserContext.requiredAuthorities("PURCHASER");
        return this.purchaseService.addOrUpdatePurchase(EulerCloudUserContext.getCurrentUserId(), purchase);
    }

    @DeleteMapping
    public void trashPurchase(@RequestParam long id) {
        EulerCloudUserContext.requiredAuthorities("PURCHASER");
        this.purchaseService.trashPurchase(EulerCloudUserContext.getCurrentUserId(), id);
    }

    @PostMapping("completePurchase")
    public void completePurchase(@RequestParam long id) {
        EulerCloudUserContext.requiredAuthorities("PURCHASER");
        this.purchaseService.completePurchase(EulerCloudUserContext.getCurrentUserId(), id);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({EulerAccessDeniedException.class})
    public Object methodEulerAccessDeniedException(EulerAccessDeniedException e) {
        return new ErrorResponse(new WebException(e.getMessage(), SystemWebError.ACCESS_DENIED, e));
    }
}
