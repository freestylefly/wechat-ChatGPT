package cn.canghe.robot.groupsave.service.handler.msgcallback;

import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.service.CommonService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bin
 * @date 2020/10/20 12:10:19
 * @description 新好友申请
 * @menu
 */
@Component
@Slf4j
public class NewFriendMsgCallBackHandler extends AbstractMsgCallBackHandler {
    @Resource
    private CommonService commonService;

    @Override
    protected Boolean handler(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        String wId = data.getString("wId");
        String v1 = data.getString("v1");
        String v2 = data.getString("v2");
        Integer type = data.getInteger("scene");
        Map<String, Object> param = new HashMap<>();
        param.put("wId", wId);
        param.put("v1", v1);
        param.put("v2", v2);
        param.put("type", type);
        commonService.commonSendPost(param, UrlConstant.WK_ACCEPTUSER_URL);
        return Boolean.TRUE;
    }
}