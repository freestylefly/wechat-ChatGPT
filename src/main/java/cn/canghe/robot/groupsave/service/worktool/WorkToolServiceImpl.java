package cn.canghe.robot.groupsave.service.worktool;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.common.util.HttpUtil;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolSendRawMsgDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaInfoVO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * WorkTool 服务实现类
 */
@Slf4j
@Service
public class WorkToolServiceImpl implements WorkToolService {

    private static final String ROBOTID = "7de9ffb98b6f4a1eac2bba44e025a211";
    private static final String SENDRAWMESSAGEURL = "https://worktool.asrtts.cn/wework/sendRawMessage?robotId=" + ROBOTID;


    @Resource
    private AmqpTemplate rabbitTemplate;


    @Override
    public WorkToolThirdQaVO qaConverse(WorkToolThirdQaDTO dto) {
        // 问题文本
        String spoken = dto.getSpoken();
        if (StringUtils.isBlank(spoken)) {
            return null;
        }
        // 定义返回值
        WorkToolThirdQaVO vo = new WorkToolThirdQaVO();
        WorkToolThirdQaInfoVO info = new WorkToolThirdQaInfoVO();
        vo.setType(5000);
        info.setText("");
        vo.setInfo(info);
        // 移步将消息入mq，移步发送指令消息
        rabbitTemplate.convertAndSend(RabbitMqQueues.WORK_TOOL_SEND_RAW_MSG,dto);
        return vo;
    }


    @Override
    public String sendRawMessage(WorkToolSendRawMsgDTO dto) {
        log.info("WorkToolService_sendRawMessage_开始发送指令消息，消息内容为：{}", JSONObject.toJSON(dto));
        return HttpUtil.sendPost(SENDRAWMESSAGEURL, JSONObject.toJSONString(dto));
    }


}
