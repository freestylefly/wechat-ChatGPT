package cn.canghe.robot.groupsave.common.mq;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.common.interceptor.OpenAILogger;
import cn.canghe.robot.groupsave.openai.ChatCompletion;
import cn.canghe.robot.groupsave.openai.ChatCompletionResponse;
import cn.canghe.robot.groupsave.openai.Message;
import cn.canghe.robot.groupsave.openai.OpenAiClient;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.pojo.worktool.SendRawMsgEntity;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolSendRawMsgDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaDTO;
import cn.canghe.robot.groupsave.service.JobService;
import cn.canghe.robot.groupsave.service.handler.msgsend.AbstractMsgSendHandler;
import cn.canghe.robot.groupsave.service.handler.msgsend.MsgSendEnum;
import cn.canghe.robot.groupsave.service.worktool.WorkToolService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource
    private WorkToolService workToolService;

    private OpenAiClient openAiClient;


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

    /**
     * worktool指令消息发送监听器
     *
     * @param dto
     */
    @RabbitListener(queues = RabbitMqQueues.WORK_TOOL_SEND_RAW_MSG)
    @RabbitHandler
    public void workToolSendRawMsg(WorkToolThirdQaDTO dto) {
        log.info("RabbitMqConsumer_workToolSendRawMsg_dto:{}", dto);
        // chatgpt接口
        // chatgpt 前置条件
        chatGptBefore();
        //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content(dto.getSpoken()).build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);

        WorkToolSendRawMsgDTO workToolSendRawMsgDTO = new WorkToolSendRawMsgDTO();
        workToolSendRawMsgDTO.setSocketType(2);
        ArrayList<SendRawMsgEntity> sendRawMsgEntities = Lists.newArrayList();
        SendRawMsgEntity sendRawMsgEntity = new SendRawMsgEntity();
        sendRawMsgEntity.setType(203);
        sendRawMsgEntity.setTitleList(Lists.newArrayList(dto.getReceivedName()));
        //将chatgpt回复内容填充到消息体中
        chatCompletionResponse.getChoices().forEach(e -> {
            sendRawMsgEntity.setReceivedContent(e.getMessage().getContent());
        });
        sendRawMsgEntities.add(sendRawMsgEntity);
        workToolSendRawMsgDTO.setList(sendRawMsgEntities);
        String rawMessage = workToolService.sendRawMessage(workToolSendRawMsgDTO);
        log.info("消息发送成功");
    }

    private void chatGptBefore() {
        //可以为null
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        openAiClient = OpenAiClient.builder()
                .apiKey("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")
                .connectTimeout(50)
                .writeTimeout(50)
                .readTimeout(50)
                .interceptor(Arrays.asList(httpLoggingInterceptor))
                .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build();
    }


}