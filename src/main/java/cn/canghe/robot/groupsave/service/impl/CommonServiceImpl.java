package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.enums.ExceptionEnum;
import cn.canghe.robot.groupsave.common.exception.BusinessException;
import cn.canghe.robot.groupsave.common.util.HttpUtil;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.LoginService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author bin
 * @date 2020/08/25 11:22:21
 * @description 描述信息
 * @menu
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private LoginService loginService;

    @Override
    public JSONObject commonSendPost(Map<String, Object> paramMap, String url) {
        String authorization = loginService.getAuthorization();
        log.info("commonSendPost_paramMap:{},url:{}", JSONObject.toJSONString(paramMap), url);
        String returnData = HttpUtil.sendPostOfAuth(url, JSONObject.toJSONString(paramMap), authorization);
        log.info("commonSendPost_returnData:{}", returnData);

        JSONObject json = JSONObject.parseObject(returnData);
        if (json == null) {
            log.info("commonSendPost_error");
            throw new BusinessException(ExceptionEnum.SEND_ERROR);
        }
        String code = json.getString("code");
        if (!"1000".equals(code)) {
            log.info("commonSendPost_error");
            throw new BusinessException(ExceptionEnum.SEND_ERROR);
        }
        return json;
    }

}