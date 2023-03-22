package cn.canghe.robot.groupsave.controller;

import cn.canghe.robot.groupsave.common.interceptor.TokenNeedless;
import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.service.WxAdminService;
import cn.canghe.robot.groupsave.service.handler.msgcallback.AbstractMsgCallBackHandler;
import cn.canghe.robot.groupsave.service.handler.msgcallback.MsgCallBackEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author bin
 * @date 2020/09/01 17:51:07
 * @description 消息回调
 * @menu
 */
@Slf4j
@RestController
@RequestMapping("/callBack")
public class CallBackController {
    @Resource
    private WxAdminService wxAdminService;
    @Resource
    private Map<String, AbstractMsgCallBackHandler> abstractMsgCallBackHandlerMap;

    /**
     * 消息接收服务地址
     *
     * @param msg
     * @return
     */
    @TokenNeedless
    @PostMapping("/getMsgCallBack")
    public Result getMsgCallBack(@RequestBody String msg) {
        JSONObject json = JSONObject.parseObject(msg);
        //消息类型

        Integer messageType = json.getInteger("messageType");
        //消息体
        JSONObject data = json.getJSONObject("data");

        //过滤消息类型为13的消息
        if (messageType == 13) {
            log.info("非法消息，暂不接收");
            return null;
        }
        log.info("getMsgCallBack:{}", msg);
        String beanName = MsgCallBackEnum.getBeanName(messageType);
        AbstractMsgCallBackHandler abstractMsgCallBackHandler = abstractMsgCallBackHandlerMap.get(beanName);
        if (abstractMsgCallBackHandler == null) {
            return Result.ok();
        }
        Boolean process = abstractMsgCallBackHandler.process(json);
        log.info("getMsgCallBack_process:{}", process);

        return Result.ok();
    }
}