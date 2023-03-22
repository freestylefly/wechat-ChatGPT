package cn.canghe.robot.groupsave.common.interceptor;


import cn.canghe.robot.groupsave.common.enums.TokenResultEnum;
import cn.canghe.robot.groupsave.common.exception.BusinessException;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.common.util.JWTUtils;
import cn.canghe.robot.groupsave.pojo.bo.AdminInfo;
import cn.canghe.robot.groupsave.pojo.entity.WxAdmin;
import cn.canghe.robot.groupsave.pojo.entity.WxUser;
import cn.canghe.robot.groupsave.service.WxAdminService;
import cn.canghe.robot.groupsave.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author bin
 * @date 2020/07/09 14:34:08
 * @description 权限过滤
 * @menu
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private WxAdminService wxAdminService;
    @Autowired
    private WxUserService wxUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法的请求, 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        TokenNeedless tokenNeedless = method.getMethodAnnotation(TokenNeedless.class);
        if (null != tokenNeedless) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            log.error("preHandle_fail_token_isnull");
            throw new BusinessException(TokenResultEnum.MISS_TOEKN);
        }
//
        Map jwtData = JWTUtils.getJWTData(token);
        if (jwtData == null ) {
            log.error("preHandle_fail");
            throw new BusinessException(TokenResultEnum.UNVALID_TOEKN);
        }
        String adminUuid = (String) jwtData.get("adminUuid");
        Optional.ofNullable(adminUuid).orElseThrow(() -> new BusinessException(TokenResultEnum.MISS_ADMIN_UUID));
        WxAdmin admin = wxAdminService.lambdaQuery().eq(WxAdmin::getUuid, adminUuid).last("limit 1").one();
        Optional.ofNullable(admin).orElseThrow(() -> new BusinessException(TokenResultEnum.MISS_ADMIN));
        String wcId = admin.getWcId();
        String wId = "";
        WxUser wxUser = wxUserService.lambdaQuery().select(WxUser::getWId).eq(WxUser::getWcId, wcId).last("limit 1").one();
        if (StringUtils.isNotEmpty(wcId) && wxUser != null) {
            wId = wxUser.getWId();
        }
        AdminInfo adminInfo = new AdminInfo();
        BeanUtils.copyProperties(admin,adminInfo);
        adminInfo.setToken(token);
        adminInfo.setWId(wId);
        //保存上下文
        AdminSessionContext.setContext(adminInfo);
        return true;
    }

}