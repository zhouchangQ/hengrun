package org.eulerframework.uc.hengrun.web.controller;

import org.eulerframework.common.util.Assert;
import org.eulerframework.common.util.StringUtils;
import org.eulerframework.uc.hengrun.web.entity.HengRunUserProfileEntity;
import org.eulerframework.uc.hengrun.web.repository.HengRunUserProfileEntityRepository;
import org.eulerframework.uc.hengrun.web.repository.HengRunUserRepository;
import org.eulerframework.uc.hengrun.web.vo.PurchaserVO;
import org.eulerframework.web.core.annotation.ApiEndpoint;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.core.base.response.ErrorResponse;
import org.eulerframework.web.core.exception.web.SystemWebError;
import org.eulerframework.web.core.exception.web.WebException;
import org.eulerframework.web.module.authentication.context.UserContext;
import org.eulerframework.web.module.authentication.entity.EulerUserEntity;
import org.eulerframework.web.module.authentication.entity.Group;
import org.eulerframework.web.module.authentication.entity.User;
import org.eulerframework.web.module.authentication.exception.UserNotFoundException;
import org.eulerframework.web.module.authentication.principal.EulerUserDetails;
import org.eulerframework.web.module.authentication.repository.GroupRepository;
import org.eulerframework.web.module.authentication.repository.UserRepository;
import org.eulerframework.web.module.authentication.service.EulerUserEntityService;
import org.eulerframework.web.module.authentication.service.UserRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApiEndpoint
@RequestMapping("purchaser")
public class PurchaserManageApi extends ApiSupportWebController {

    @Autowired
    private HengRunUserRepository hengRunUserRepository;
    @Autowired
    private EulerUserEntityService eulerUserEntityService;
    @Autowired
    private UserRegistService userRegistService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupRepository groupRepository;

    @Autowired
    private HengRunUserProfileEntityRepository hengRunUserProfileEntityRepository;

    @GetMapping
    public List<PurchaserVO> getPurchasers() {
        this.checkPermission();

        return Optional.ofNullable(this.hengRunUserRepository.findPurchasers())
                .orElse(Collections.emptyList())
                .stream()
                .map(col -> {
                    PurchaserVO purchaserVO = new PurchaserVO();
                    purchaserVO.setUserId(col[0]);
                    purchaserVO.setMobile(col[1]);
                    purchaserVO.setFullName(col[2]);
                    return purchaserVO;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addPurchasers(@RequestBody PurchaserVO purchaserVO) {
        this.checkPermission();

        Assert.notNull(purchaserVO, "用户信息为空");
        Assert.hasText(purchaserVO.getMobile(), "手机号必填");
        Assert.hasText(purchaserVO.getFullName(), "姓名必填");

        EulerUserEntity eulerUserEntity = null;
        try {
            eulerUserEntity = this.eulerUserEntityService.loadUserByMobile(purchaserVO.getMobile());
        } catch (UserNotFoundException e) {
            //DO_NOTHING
        }

        if (eulerUserEntity == null) {
            String password = StringUtils.randomString(16);
            Map<String, Object> profile = new HashMap<>();
            profile.put("fullName", purchaserVO.getFullName());
            eulerUserEntity = this.userRegistService.signUp(null, null, purchaserVO.getMobile(), password, profile);
        } else {
            //已注册的情况更新姓名
            HengRunUserProfileEntity hengRunUserProfileEntity = this.hengRunUserProfileEntityRepository.findByUserId(eulerUserEntity.getUserId());
            if(hengRunUserProfileEntity == null) {
                //没填写过用户信息的新建一个
                hengRunUserProfileEntity = new HengRunUserProfileEntity();
                hengRunUserProfileEntity.setUserId(eulerUserEntity.getUserId());
            }
            hengRunUserProfileEntity.setFullName(purchaserVO.getFullName());
            this.hengRunUserProfileEntityRepository.save(hengRunUserProfileEntity);
        }

        User currentUserEntity = this.userRepository.findUserById(eulerUserEntity.getUserId());
        Set<Group> groups = currentUserEntity.getGroups();
        Group purchaserGroup = this.groupRepository.findGroupByCode("g_purchaser");
        groups.add(purchaserGroup);
        this.userRepository.save(currentUserEntity);
    }

    @DeleteMapping("{userId}")
    public void delPurchasers(@PathVariable String userId) {
        this.checkPermission();
        User currentUserEntity = this.userRepository.findUserById(userId);
        if (currentUserEntity == null) {
            return;
        }

        Set<Group> groups = Optional.ofNullable(currentUserEntity.getGroups())
                .orElse(Collections.emptySet())
                .stream()
                .filter(group -> !"g_purchaser".equals(group.getCode()))
                .collect(Collectors.toSet());
        currentUserEntity.setGroups(groups);
        this.userRepository.save(currentUserEntity);
    }

    private void checkPermission() {
        EulerUserDetails eulerUserDetails = UserContext.getCurrentUser();
        if (Optional.ofNullable(eulerUserDetails.getAuthorities())
                .orElse(Collections.emptyList())
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .noneMatch("ADMIN"::equals)) {
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
