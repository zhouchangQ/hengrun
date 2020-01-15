package org.eulerframework.uc.hengrun.web.controller;

import org.eulerframework.uc.hengrun.web.service.SalerService;
import org.eulerframework.uc.hengrun.web.vo.SalerVO;
import org.eulerframework.web.core.annotation.ApiEndpoint;
import org.eulerframework.web.core.base.response.ErrorResponse;
import org.eulerframework.web.core.exception.web.SystemWebError;
import org.eulerframework.web.core.exception.web.WebException;
import org.eulerframework.web.module.authentication.context.UserContext;
import org.eulerframework.web.module.authentication.principal.EulerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        this.checkPermission();
        return this.salerService.findSalers(scopes, offset, limit);
    }

    private void checkPermission() {
        EulerUserDetails eulerUserDetails = UserContext.getCurrentUser();
        if (Optional.ofNullable(eulerUserDetails.getAuthorities())
                .orElse(Collections.emptyList())
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .noneMatch("PURCHASER"::equals)) {
            throw new AccessDeniedException("access denied");
        }
    }

    /**
     * 用于在程序发生{@link AccessDeniedException}异常时统一返回错误信息
     *
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(new WebException(e.getMessage(), SystemWebError.ACCESS_DENIED, e));
    }
}
