package cn.canghe.robot.groupsave.common.config;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

/**
 * @author bin
 * @date 2020/07/10 16:25:35
 * @description RabbitConfig
 * @menu
 */
@Configuration
public class RabbitConfig {

    @Bean(RabbitMqQueues.WX_INIT_CHATROOM)
    public Queue initChatroom() {
        return new Queue(RabbitMqQueues.WX_INIT_CHATROOM);
    }

    @Bean(RabbitMqQueues.WX_SEND_MSG)
    public Queue sendMsg() {
        return new Queue(RabbitMqQueues.WX_SEND_MSG);
    }

    @Bean(RabbitMqQueues.WORK_TOOL_SEND_RAW_MSG)
    public Queue workToolSendRawMsg() {
        return new Queue(RabbitMqQueues.WORK_TOOL_SEND_RAW_MSG);
    }

}