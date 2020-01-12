package cn.sxhengrun.purchase.api;

import cn.sxhengrun.purchase.service.SaleService;
import cn.sxhengrun.purchase.vo.SaleVO;
import org.eulerframework.cloud.security.EulerCloudUserContext;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.core.base.request.QueryRequest.OrderMode;
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
@RequestMapping("sale")
public class SaleApi extends ApiSupportWebController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<SaleVO> getSale(
            @RequestParam(required = false) String[] types,
            @RequestParam(required = false, defaultValue = "DESC") OrderMode order,
            @RequestParam long offset,
            @RequestParam int limit) {
        return this.saleService.getSales(EulerCloudUserContext.getCurrentUserId(), types, null, order, offset, limit);
    }

    @GetMapping("mine")
    public List<SaleVO> getMySale(
            @RequestParam(required = false) String[] types,
            @RequestParam(required = false, defaultValue = "DESC") OrderMode order,
            @RequestParam long offset,
            @RequestParam int limit) {
        return this.saleService.getSales(EulerCloudUserContext.getCurrentUserId(), types, EulerCloudUserContext.getCurrentUserId(), order, offset, limit);
    }

    @PostMapping
    public long saveSale(@RequestBody SaleVO sale) {
        return this.saleService.addOrUpdateSale(EulerCloudUserContext.getCurrentUserId(), sale);
    }

    @DeleteMapping
    public void trashSale(@RequestParam long id) {
        this.saleService.trashSale(EulerCloudUserContext.getCurrentUserId(), id);
    }
}
