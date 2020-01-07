package cn.sxhengrun.purchase.api;

import cn.sxhengrun.purchase.entity.Purchase;
import cn.sxhengrun.purchase.service.PurchaseService;
import cn.sxhengrun.purchase.vo.PurchaseVO;
import org.eulerframework.cloud.security.EulerCloudUserContext;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("purchase")
public class PurchaseApi extends ApiSupportWebController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping
    public List<PurchaseVO> getPurchase() {
        return this.purchaseService.getPurchases();
    }

    @PostMapping
    public long savePurchase(@RequestBody PurchaseVO purchase) {
        if(purchase.getId() == null) {
            return this.purchaseService.addPurchase(EulerCloudUserContext.getCurrentUserId(), purchase);
        } else {
            return this.purchaseService.updatePurchase(EulerCloudUserContext.getCurrentUserId(), purchase);
        }
    }

    @DeleteMapping
    public void trashPurchase(@RequestParam long id) {
        this.purchaseService.trashPurchase(id);
    }
}
