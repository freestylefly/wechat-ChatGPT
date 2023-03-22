package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.dao.mapper.WxUserChatroomMapper;
import cn.canghe.robot.groupsave.pojo.entity.WxUserChatroom;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.WxUserChatroomMemberInfoService;
import cn.canghe.robot.groupsave.service.WxUserChatroomService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信用户群信息表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@Service
@Slf4j
public class WxUserChatroomServiceImpl extends ServiceImpl<WxUserChatroomMapper, WxUserChatroom> implements WxUserChatroomService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WxUserChatroomMemberInfoService wxUserChatroomMemberInfoService;


    @Override
    public Boolean initUserChatRooms(String wId, String wcId) {
        //1、刷新群列表
        Map<String, Object> paramMap1 = new HashMap<>(4);
        paramMap1.put("wId", wId);
        commonService.commonSendPost(paramMap1, UrlConstant.WK_INITCHATROOM_URL);

        //2、获取群列表
        JSONObject json1 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETCHATROOMS_URL);
        JSONArray jsonArray1 = json1.getJSONArray("data");
        List<String> chatRoomsList = JSONObject.parseArray(jsonArray1.toJSONString(), String.class);

        //3、遍历群列表，每个群放mq执行
        chatRoomsList.forEach(chatRoom -> {
            Map<String, String> param = new HashMap<>(4);
            param.put("wId", wId);
            param.put("wcId", wcId);
            param.put("chatRoom", chatRoom);
            rabbitTemplate.convertAndSend(RabbitMqQueues.WX_INIT_CHATROOM, param);
            log.info("initUserChatRooms_sendMq_success");
        });
        return true;
    }

    @Override
    public Boolean inviteChatRoomMember(String wId, String chatRoomId, String userList) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("wId", wId);
        param.put("chatRoomId", chatRoomId);
        param.put("userList", userList);

        try {
            commonService.commonSendPost(param, UrlConstant.WK_INVITECHATROOMMEMBER_URL);
        } catch (Exception e) {
            log.error("群聊邀请失败");
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean refreshChatroom(String wId, String wcId) {
        //先将数据全部刷新清除一遍
        this.lambdaUpdate().eq(WxUserChatroom::getUserWcid, wcId).remove();
        Boolean aBoolean = initUserChatRooms(wId, wcId);
        return aBoolean;
    }
}
