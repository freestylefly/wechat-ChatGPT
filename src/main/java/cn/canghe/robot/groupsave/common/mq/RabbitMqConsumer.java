package cn.canghe.robot.groupsave.common.mq;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.service.JobService;
import cn.canghe.robot.groupsave.service.handler.msgsend.AbstractMsgSendHandler;
import cn.canghe.robot.groupsave.service.handler.msgsend.MsgSendEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author bin
 * @date 2020/07/10 16:28:07
 * @description RabbitMq监听
 * @menu
 */
@Component
@EnableRabbit
@Slf4j
public class RabbitMqConsumer {
    @Resource
    private JobService jobService;
    @Resource
    private Map<String, AbstractMsgSendHandler> abstractMsgSendHandlerMap;


    /**
     * 监听初始化群列表
     * 将该群的详情信息和群成员信息保存入库
     *
     * @param param
     */
    @RabbitListener(queues = RabbitMqQueues.WX_INIT_CHATROOM)
    @RabbitHandler
    public void initChatroom(Map<String, String> param) {
        log.info("RabbitMqConsumer_initChatroom_param:" + param);
        jobService.initUserChatRoom(param);
    }


    /**
     * 消息发送监听器
     *
     * @param dto
     */
    @RabbitListener(queues = RabbitMqQueues.WX_SEND_MSG)
    @RabbitHandler
    public void sendMsg(SendMsgDTO dto) {
        log.info("RabbitMqConsumer_sendMsg_dto:" + dto);
        //默认为文本类型
        if (Objects.nonNull(dto.getType())) {
            dto.setType(MsgSendEnum.TEXT_MSG_SEND.getType());
        }
        String beanName = MsgSendEnum.getBeanName(dto.getType());
        Boolean aBoolean = abstractMsgSendHandlerMap.get(beanName).msgSend(dto);
        if (aBoolean) {
            log.info("消息发送成功");
        }
    }


}