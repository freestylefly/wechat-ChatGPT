package cn.canghe.robot.groupsave.service.handler.msgsend;

import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author bin
 * @date 2020/09/29 13:38:00
 * @description 消息发送
 * @menu
 */
@Component
@Slf4j
public abstract class AbstractMsgSendHandler {

    public final Boolean msgSend(SendMsgDTO dto) {
        return this.handler(dto);
    }

    protected abstract Boolean handler(SendMsgDTO dto);
}