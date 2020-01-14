package org.eulerframework.uc.hengrun.web.controller;

import org.eulerframework.uc.hengrun.web.service.SalerService;
import org.eulerframework.uc.hengrun.web.vo.SalerVO;
import org.eulerframework.web.core.annotation.ApiEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiEndpoint
@RequestMapping("saler")
public class SalerApi {

    @Autowired
    private SalerService salerService;

    @GetMapping
    public List<SalerVO> getSalers(
            @RequestParam(required = false) String[] scopes,
            @RequestParam long offset,
            @RequestParam int limit
    ) {
        return this.salerService.findSalers(scopes, offset, limit);
    }
}
