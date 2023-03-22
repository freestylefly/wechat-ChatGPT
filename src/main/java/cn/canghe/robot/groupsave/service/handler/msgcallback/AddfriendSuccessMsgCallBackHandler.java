package cn.canghe.robot.groupsave.service.handler.msgcallback;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.pojo.entity.WxSendMsgRecord;
import cn.canghe.robot.groupsave.service.WxSendMsgRecordService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author bin
 * @date 2020/09/29 14:55:41
 * @description 添加好友成功回调
 * @menu
 */
@Component
@Slf4j
public class AddfriendSuccessMsgCallBackHandler extends AbstractMsgCallBackHandler{
    @Resource
    private WxSendMsgRecordService wxSendMsgRecordService;
    @Resource
    private AmqpTemplate rabbitTemplate;

    @Override
    protected Boolean handler(JSONObject json) {
        JSONObject data = json.getJSONObject("data");
        String fromUser = json.getString("wcId");
        String wId = data.getString("wId");
        String wcId = data.getString("userName");
        //发送欢迎语
        SendMsgDTO sendMsgDTO = new SendMsgDTO();
        sendMsgDTO.setWcId(wcId);
        sendMsgDTO.setWId(wId);
        sendMsgDTO.setContent("很高兴认识你。。。");
        sendMsgDTO.setFromUser(fromUser);
        sendMsgDTO.setOrigin(2);
        sendMsgDTO.setType(0);
        //如果已经发送过一次欢迎语了就不再发送
        Integer count = wxSendMsgRecordService.lambdaQuery()
                .eq(WxSendMsgRecord::getFromuser, fromUser)
                .eq(WxSendMsgRecord::getTouser, wcId)
                .eq(WxSendMsgRecord::getOrigin, 2)
                .count();
        if (count > 0) {
            log.error("已经发送过欢迎语了");
            return false;
        }
        rabbitTemplate.convertAndSend(RabbitMqQueues.WX_SEND_MSG,sendMsgDTO);
        log.info("sendWelcomeCode_send_mq_success");
        return true;
    }
}