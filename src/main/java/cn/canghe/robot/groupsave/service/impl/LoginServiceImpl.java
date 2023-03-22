package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.RedisConstant;
import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.common.enums.ExceptionEnum;
import cn.canghe.robot.groupsave.common.exception.BusinessException;
import cn.canghe.robot.groupsave.common.properties.CommonProperties;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.common.util.HttpUtil;
import cn.canghe.robot.groupsave.common.util.JWTUtils;
import cn.canghe.robot.groupsave.pojo.bo.AdminInfo;
import cn.canghe.robot.groupsave.pojo.dto.IPadLoginDTO;
import cn.canghe.robot.groupsave.pojo.dto.LoginWkDTO;
import cn.canghe.robot.groupsave.pojo.dto.WxLoginDTO;
import cn.canghe.robot.groupsave.pojo.entity.WxAdmin;
import cn.canghe.robot.groupsave.pojo.entity.WxUser;
import cn.canghe.robot.groupsave.pojo.form.AdminLoginForm;
import cn.canghe.robot.groupsave.pojo.vo.AdminLoginVO;
import cn.canghe.robot.groupsave.pojo.vo.IPadLoginVO;
import cn.canghe.robot.groupsave.pojo.vo.LoginWkVO;
import cn.canghe.robot.groupsave.pojo.vo.WxLoginVO;
import cn.canghe.robot.groupsave.service.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author bin
 * @date 2020/08/24 15:39:31
 * @description 描述信息
 * @menu
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxUserFriendsService wxUserFriendsService;
    @Autowired
    private WxUserChatroomService wxUserChatroomService;
    @Autowired
    private WxAdminService wxAdminService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private WxUserLabelService wxUserLabelService;

    @Override
    public AdminLoginVO adminLogin(AdminLoginForm form) {
        log.info("adminLogin_form:{}", form);
        //1、校验
        WxAdmin admin = wxAdminService.lambdaQuery()
                .eq(WxAdmin::getUserName, form.getUserName())
                .last("limit 1")
                .one();
        Optional.ofNullable(admin).orElseThrow(() -> new BusinessException(ExceptionEnum.ADMIN_FAIL));
        //2、校验密码
        if (!admin.getPassword().equals(form.getPassword())) {
            throw new BusinessException(ExceptionEnum.ADMIN_FAIL);
        }
        //3、生成token
        String token = createToken(admin);
        updateToken(admin.getId(), token);
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setAdminUUID(admin.getUuid());
        adminLoginVO.setToken(token);
        //4、放在上下文中
        saveContext(admin);
        return adminLoginVO;
    }

    private void saveContext(WxAdmin admin) {
        AdminInfo adminInfo = new AdminInfo();
        BeanUtils.copyProperties(admin, adminInfo);
        AdminSessionContext.setContext(adminInfo);
    }

    private void updateToken(Integer adminId, String token) {
        wxAdminService.lambdaUpdate().eq(WxAdmin::getId, adminId)
                .set(WxAdmin::getToken, token)
                .update();
    }

    /**
     * 生成token
     *
     * @param admin
     * @return
     */
    private String createToken(WxAdmin admin) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", admin.getId());
        map.put("adminUuid", admin.getUuid());
        String wcId = admin.getWcId();
        if (StringUtils.isNotEmpty(wcId)) {
            map.put("wcId", wcId);
        }

        String token = JWTUtils.createJWT(map);
        return token;
    }

    @Override
    public LoginWkVO loginWk(LoginWkDTO dto) {
        log.info("loginWk_dto:{}", dto);
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("account", dto.getAccount());
        paramMap.put("password", dto.getPassword());

        String returnData = HttpUtil.sendPost(UrlConstant.WK_LOGIN_URL, JSONObject.toJSONString(paramMap));
        log.info("loginWk_data:{}", returnData);

        JSONObject json = JSONObject.parseObject(returnData);
        String code = json.getString("code");
        String message = json.getString("message");
        JSONObject data = json.getJSONObject("data");
        if (!"1000".equals(code) || !"成功".equals(message) || data == null) {
            log.error("loginWk_error");
            throw new BusinessException(ExceptionEnum.LOGIN_ERROR);
        }
        String authorization = data.getString("Authorization");
        String callbackUrl = data.getString("callbackUrl");
        LoginWkVO loginWkVO = new LoginWkVO();
        loginWkVO.setAuthorization(authorization);
        loginWkVO.setCallbackUrl(callbackUrl);
        log.info("loginWk_loginWkVO:{}", JSONObject.toJSONString(loginWkVO));

        //将凭证放在Redis中，24小时后过期
        redisTemplate.opsForValue().set(RedisConstant.AUTHORIZATION + ": " + dto.getAccount(), authorization, 24, TimeUnit.HOURS);


        //设置消息回调地址
        Map<String, Object> paramMap2 = new HashMap<>(16);
        paramMap2.put("httpUrl", commonProperties.getHttpUrl());
        HttpUtil.sendPostOfAuth(UrlConstant.WK_SET_CALLBACK_URL, JSONObject.toJSONString(paramMap2), authorization);
        return loginWkVO;
    }

    @Override
    public String getAuthorization() {
        LoginWkDTO dto = new LoginWkDTO();
        String account = commonProperties.getAccount();
        dto.setAccount(account);
        dto.setPassword(commonProperties.getPassword());
        String authorization = redisTemplate.opsForValue().get(RedisConstant.AUTHORIZATION + ": " + account);
        if (StringUtils.isBlank(authorization)) {
            LoginWkVO loginWkVO = this.loginWk(dto);
            authorization = loginWkVO.getAuthorization();
            if (StringUtils.isNotBlank(authorization)) {
                //将凭证放在Redis中，24小时后过期
                redisTemplate.opsForValue().set(RedisConstant.AUTHORIZATION + ": " + account, authorization, 24, TimeUnit.HOURS);
            }
        }
        return authorization;
    }

    @Override
    public IPadLoginVO iPadLogin(IPadLoginDTO dto) {
        log.info("iPadLogin_dto:{}", dto);
        //从上下文中取出wcId
        String wcId = AdminSessionContext.getWcId();
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("type", 2);
        if (StringUtils.isNotEmpty(dto.getProxy())) {
            paramMap.put("proxy", 5);
        }
        if (StringUtils.isNotEmpty(wcId)) {
            paramMap.put("wcId", wcId);
        }

        JSONObject json = commonService.commonSendPost(paramMap, UrlConstant.WK_IPADE_URL);
        JSONObject data = json.getJSONObject("data");
        String wId = data.getString("wId");
        String qrCodeUrl = data.getString("qrCodeUrl");
        IPadLoginVO iPadLoginVO = new IPadLoginVO();
        iPadLoginVO.setWId(wId);
        iPadLoginVO.setQrCodeUrl(qrCodeUrl);
        log.info("iPadLogin_iPadLoginVO:{}", JSONObject.toJSONString(iPadLoginVO));
        return iPadLoginVO;
    }

    @Override
    public WxLoginVO wxLogin(WxLoginDTO dto) {
        log.info("wxLogin_dto:{}", dto);
        String wcId = AdminSessionContext.getWcId();
        String wId = dto.getWId();
        String authorization = getAuthorization();
        //1、登录微信
        WxLoginVO iPadLoginInfo = getIPadLoginInfo(wId, wcId, authorization);
        WxUser wxUser = new WxUser();
        BeanUtils.copyProperties(iPadLoginInfo, wxUser);
        //设置自动回复类型，这里定制化，只回复对应的群邀请信息,所以只开启自定义回复
        wxUser.setRobert(2);
        Integer count = wxUserService.lambdaQuery().eq(WxUser::getWcId, iPadLoginInfo.getWcId()).count();
        //2、保存或者更新用户表信息
        if (StringUtils.isNotBlank(wId) && StringUtils.isBlank(wcId) && count == 0) {
            //首次登录,保存入库
            wxUserService.save(wxUser);
        } else if (StringUtils.isNotBlank(iPadLoginInfo.getWcId())) {
            //二次登录，更新库里面信息
            wxUserService.lambdaUpdate().eq(WxUser::getWcId, iPadLoginInfo.getWcId()).update(wxUser);
        }
        //3、初始化好友列表
        try {
            wxUserFriendsService.initUserFriends(iPadLoginInfo.getWId(), iPadLoginInfo.getWcId());
        } catch (Exception e) {
            log.error("用户:{}登录初始化好友列表异常", wcId);
        }
        //4、初始化群列表
        try {
            wxUserChatroomService.initUserChatRooms(iPadLoginInfo.getWId(), iPadLoginInfo.getWcId());
        } catch (Exception e) {
            log.error("用户:{}登录初始化群列表异常", wcId);
        }
        //5、初始化标签列表，保存所有标签和标签内的好友信息
        try {
            wxUserLabelService.initUserLabelList(iPadLoginInfo.getWId(), iPadLoginInfo.getWcId());
        } catch (Exception e) {
            log.error("用户:{}登录初始化标签异常", wcId);
        }

        return iPadLoginInfo;
    }


    private WxLoginVO getIPadLoginInfo(String wId, String wcId, String authorization) {
        Map<String, Object> paramMap = new HashMap<>(4);
        String returnData = "";
        //首次登录
        if (StringUtils.isNotBlank(wId) && StringUtils.isBlank(wcId)) {
            paramMap.put("wId", wId);
            returnData = HttpUtil.sendPostOfAuth(UrlConstant.WK_GET_IPADLOGININFO_URL, JSONObject.toJSONString(paramMap), authorization);
        } else if (StringUtils.isNotBlank(wcId)) {
            //二次登录
            paramMap.put("wcId", wcId);
            paramMap.put("type", 2);
            returnData = HttpUtil.sendPostOfAuth(UrlConstant.WK_SECONDLOGIN_URL, JSONObject.toJSONString(paramMap), authorization);
        }
        log.info("getIPadLoginInfo_returnData:{}", returnData);

        JSONObject json = JSONObject.parseObject(returnData);
        JSONObject data = json.getJSONObject("data");
        wcId = data.getString("wcId");
        String wAccount = data.getString("wAccount");
        wId = data.getString("wId");
        String nickName = data.getString("nickName");
        Integer sex = data.getInteger("sex");
        String headUrl = data.getString("headUrl");
        Integer status = data.getInteger("status");
        if (StringUtils.isAnyBlank(wcId, wId, nickName, headUrl)) {
            log.error("getIPadLoginInfo_error2");
            throw new BusinessException(ExceptionEnum.SECOND_LOGIN);
        }
        WxLoginVO wxLoginVO = new WxLoginVO();
        wxLoginVO.setWcId(wcId);
        wxLoginVO.setWAccount(wAccount);
        wxLoginVO.setWId(wId);
        wxLoginVO.setNickName(nickName);
        wxLoginVO.setSex(sex);
        wxLoginVO.setHeadUrl(headUrl);
        wxLoginVO.setStatus(status);
        //更新admin表的wcId
        wxAdminService.lambdaUpdate().eq(WxAdmin::getUuid, AdminSessionContext.get().getUuid())
                .set(WxAdmin::getWcId, wcId)
                .update();
        return wxLoginVO;
    }

    @Override
    public Boolean logout(Boolean isDeleteAdminWcId) {
        String wId = AdminSessionContext.getWId();
        String wcId = AdminSessionContext.getWcId();
        log.info("logout_wId:{},wcId:{}", wId, wcId);
        //1、用户表im_wx_user的wid置为空字符串
        wxUserService.lambdaUpdate().set(WxUser::getWId, "").eq(WxUser::getWcId, wcId).update();
        //如果想清空管理员表中的wcid，适用场景是长时间不登录导致二次登录无效
        if (Objects.nonNull(isDeleteAdminWcId) && isDeleteAdminWcId) {
            wxAdminService.lambdaUpdate().set(WxAdmin::getWcId, "").eq(WxAdmin::getUuid, AdminSessionContext.get().getUuid()).update();
        }

        //2、退出微信登录
        Map<String, Object> param = new HashMap<>(4);
        param.put("wId", wId);
        commonService.commonSendPost(param, UrlConstant.WK_LOGOUT_URL);

        //3、清空上下文
        AdminSessionContext.clearContext();

        return true;
    }
}