package cn.canghe.robot.groupsave.service.handler.msgsend;

import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.pojo.entity.WxSendMsgRecord;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.WxSendMsgRecordService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bin
 * @date 2020/09/29 13:55:32
 * @description 发送文本消息
 * @menu
 */
@Component
@Slf4j
public class TextMsgSendHandler extends AbstractMsgSendHandler {
    @Resource
    private CommonService commonService;
    @Resource
    private WxSendMsgRecordService wxSendMsgRecordService;

    @Override
    protected Boolean handler(SendMsgDTO dto) {
        log.info("sendTextMsg_dto:{}", JSON.toJSONString(dto));
        String fromUser = dto.getFromUser();
        String touser = dto.getWcId();
        Integer origin = dto.getOrigin();

        Map<String, Object> param = new HashMap<>(4);
        param.put("wId", dto.getWId());
        param.put("wcId", dto.getWcId());
        param.put("content", dto.getContent());
        commonService.commonSendPost(param, UrlConstant.WK_SEND_TEXT_URL);
        //保存消息记录表
        WxSendMsgRecord wxSendMsgRecord = new WxSendMsgRecord();
        wxSendMsgRecord.setFromuser(fromUser);
        wxSendMsgRecord.setTouser(touser);
        wxSendMsgRecord.setContent(dto.getContent());
        wxSendMsgRecord.setOrigin(origin);
        wxSendMsgRecord.setType(dto.getType());
        wxSendMsgRecord.setWId(dto.getWId());
        wxSendMsgRecordService.save(wxSendMsgRecord);
        return true;
    }
}