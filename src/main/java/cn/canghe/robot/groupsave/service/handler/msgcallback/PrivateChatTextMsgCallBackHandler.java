package cn.canghe.robot.groupsave.service.handler.msgcallback;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.common.util.RobertUtils;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.pojo.entity.WxUser;
import cn.canghe.robot.groupsave.pojo.vo.MsgCallBackVO;
import cn.canghe.robot.groupsave.service.WxAutoReplyService;
import cn.canghe.robot.groupsave.service.WxUserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author bin
 * @date 2020/09/29 15:46:56
 * @description 私聊文本消息
 * @menu
 */
@Component
public class PrivateChatTextMsgCallBackHandler extends AbstractMsgCallBackHandler {
    @Resource
    private RobertUtils robertUtils;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private WxUserService wxUserService;
    @Resource
    private WxAutoReplyService wxAutoReplyService;

    @Override
    protected Boolean handler(JSONObject json) {
        //获取微信号
        String userWcId = json.getString("wcId");
        WxUser wxUser = wxUserService.lambdaQuery()
                .select(WxUser::getRobert)
                .eq(WxUser::getWcId, userWcId)
                .last("limit 1")
                .one();
        if (wxUser == null) {
            return Boolean.FALSE;
        }
        //拿到消息内容
        MsgCallBackVO msgCallBackVO = JSONObject.parseObject(json.getString("data"), MsgCallBackVO.class);
        if (msgCallBackVO == null) {
            return Boolean.FALSE;
        }
        //接收到的消息内容
        String content = msgCallBackVO.getContent();
        int robert = wxUser.getRobert();
        if (robert == 1) {
            //调用机器人获得自动回复内容
            String reply = robertUtils.sendMsgToRobert(content);
            autoSendMsg(msgCallBackVO, reply);
            return Boolean.TRUE;
        } else if (robert == 2) {
            //自定义回复
            String reply = wxAutoReplyService.autoReply(msgCallBackVO, userWcId, content);
            if (StringUtils.isNotEmpty(reply)) {
                autoSendMsg(msgCallBackVO, reply);
            }
            return Boolean.TRUE;
        }
        return Boolean.TRUE;
    }


    private void autoSendMsg(MsgCallBackVO msgCallBackVO, String reply) {
        //调用消息发送，自动回复
        SendMsgDTO sendMsgDTO = new SendMsgDTO();
        sendMsgDTO.setWId(msgCallBackVO.getWId());
        sendMsgDTO.setWcId(msgCallBackVO.getFromUser());
        sendMsgDTO.setFromUser(msgCallBackVO.getToUser());
        sendMsgDTO.setContent(reply);
        sendMsgDTO.setType(0);
        sendMsgDTO.setOrigin(0);
        rabbitTemplate.convertAndSend(RabbitMqQueues.WX_SEND_MSG, sendMsgDTO);
    }
}