package cn.canghe.robot.groupsave.service.handler.msgcallback;

import com.alibaba.fastjson.JSONObject;

/**
 * @author bin
 * @date 2020/09/29 14:37:17
 * @description 消息回调
 * @menu
 */
public abstract class AbstractMsgCallBackHandler {
    public final Boolean process(JSONObject json) {
        return this.handler(json);
    }

    /**
     * 处理相应逻辑
     *
     * @param json
     * @return
     */
    protected abstract Boolean handler(JSONObject json);
}