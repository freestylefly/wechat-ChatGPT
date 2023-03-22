package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.dao.mapper.WxAutoReplyMapper;
import cn.canghe.robot.groupsave.pojo.entity.WxAutoReply;
import cn.canghe.robot.groupsave.pojo.entity.WxUserChatroom;
import cn.canghe.robot.groupsave.pojo.vo.AutoReplyVO;
import cn.canghe.robot.groupsave.pojo.vo.MsgCallBackVO;
import cn.canghe.robot.groupsave.service.WxAutoReplyService;
import cn.canghe.robot.groupsave.service.WxUserChatroomService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 消息自定义回复表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-10-09
 */
@Service
@Slf4j
public class WxAutoReplyServiceImpl extends ServiceImpl<WxAutoReplyMapper, WxAutoReply> implements WxAutoReplyService {
    @Resource
    private WxUserChatroomService wxUserChatroomService;

    @Override
    public String autoReply(MsgCallBackVO msgCallBackVO, String userWcId, String key) {
        WxAutoReply wxAutoReply = this.lambdaQuery()
                .eq(WxAutoReply::getUserWcid, userWcId)
                .eq(WxAutoReply::getKey, key)
                .last("limit 1")
                .one();
        if (wxAutoReply == null) {
            return null;
        }
        //得到回复内容
        String value = wxAutoReply.getValue();
        int type = wxAutoReply.getType();
        if (type == 0) {
            //文本回复
            return value;
        } else if (type == 1) {
            //群聊邀请
            //根据value查询群id
            WxUserChatroom wxUserChatroom = wxUserChatroomService.lambdaQuery()
                    .eq(WxUserChatroom::getUserWcid, userWcId)
                    .eq(WxUserChatroom::getNickName, value)
                    .last("limit 1")
                    .one();
            if (wxUserChatroom == null) {
                return null;
            }
            String chatRoomId = wxUserChatroom.getChatRoomId();
            Boolean aBoolean = wxUserChatroomService.inviteChatRoomMember(msgCallBackVO.getWId(), chatRoomId, msgCallBackVO.getFromUser());
            if (aBoolean) {
                log.info("群邀请已发送，等待入群");
            }
        }
        return null;
    }

    @Override
    public Boolean setAutoReply(AutoReplyVO autoReplyVO) {
        log.info("setAutoReply_autoReplyVO:{}", JSON.toJSON(autoReplyVO));
        WxAutoReply wxAutoReply = new WxAutoReply();
        BeanUtils.copyProperties(autoReplyVO,wxAutoReply);
        wxAutoReply.setUserWcid(AdminSessionContext.getWcId());
        return this.save(wxAutoReply);
    }
}
