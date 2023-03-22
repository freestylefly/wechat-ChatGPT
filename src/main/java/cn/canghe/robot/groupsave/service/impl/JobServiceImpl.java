package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.pojo.entity.WxUserChatroom;
import cn.canghe.robot.groupsave.pojo.vo.ChatRoomDetailVO;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.JobService;
import cn.canghe.robot.groupsave.service.WxUserChatroomMemberInfoService;
import cn.canghe.robot.groupsave.service.WxUserChatroomService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bin
 * @date 2020/08/26 16:43:30
 * @description 描述信息
 * @menu
 */
@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private WxUserChatroomMemberInfoService wxUserChatroomMemberInfoService;
    @Autowired
    private WxUserChatroomService wxUserChatroomService;

    @Override
    public Boolean initUserChatRoom(Map<String, String> param) {
        log.info("initUserChatRoom_param:{}", param);
        String wId = param.get("wId");
        String wcId = param.get("wcId");
        String chatRoom = param.get("chatRoom");

        //1、获取群详情并入库
        Map<String, Object> paramMap1 = new HashMap<>(4);
        paramMap1.put("wId", wId);
        paramMap1.put("chatRoomId", chatRoom);
        JSONObject json1 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETCHATROOMINFO_URL);
        JSONArray jsonArray1 = json1.getJSONArray("data");
        List<ChatRoomDetailVO> chatRoomList = JSONObject.parseArray(jsonArray1.toJSONString(), ChatRoomDetailVO.class);
        ChatRoomDetailVO chatRoomDetailVO = chatRoomList.get(0);
        chatRoomDetailVO.setUserWcid(wcId);
        log.info("initUserChatRoom_chatRoomDetailVO:{}", chatRoomDetailVO);
        if (wxUserChatroomService.lambdaQuery().eq(WxUserChatroom::getUserWcid, wcId).eq(WxUserChatroom::getChatRoomId, chatRoom).count() == 0) {
            WxUserChatroom wxUserChatroom = new WxUserChatroom();
            BeanUtils.copyProperties(chatRoomDetailVO, wxUserChatroom);
            wxUserChatroomService.save(wxUserChatroom);
        }

//        //2、获取群成员列表
//        JSONObject json2 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETCHATROOMMEMBER_URL);
//        JSONArray jsonArray2 = json2.getJSONArray("data");
//        List<ChatRoomMemberDetailVO> chatRoomMemberList = JSONObject.parseArray(jsonArray2.toJSONString(), ChatRoomMemberDetailVO.class);
//        log.info("initUserChatRoom_chatRoomMemberList:{}", chatRoomMemberList);
//
//        //3、遍历群成员列表获取群成员详情
//        List<ChatRoomMemberDetailVO> chatRoomMemberInfoList = new ArrayList<>();
//        for (int i = 0; i < chatRoomMemberList.size(); i++) {
//            ChatRoomMemberDetailVO menber = chatRoomMemberList.get(i);
//            //群成员微信号
//            String userName = menber.getUserName();
//            paramMap1.put("userList", userName);
//            //获取群成员详情列表
//            JSONObject json3 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETCHATROOMMEMBERINFO_URL);
//            JSONArray jsonArray3 = json3.getJSONArray("data");
//            List<ChatRoomMemberDetailVO> chatRoomMemberInfo = JSONObject.parseArray(jsonArray3.toJSONString(), ChatRoomMemberDetailVO.class);
//            chatRoomMemberInfoList.addAll(chatRoomMemberInfo);
//        }
//        log.info("群成员详情列表信息：{}", JSONObject.toJSONString(chatRoomMemberInfoList));
//
//
//        //3、查出已经入库的该用户的该群的群成员
//        List<WxUserChatroomMemberInfo> alreadyChatMenberList = wxUserChatroomMemberInfoService.lambdaQuery()
//                .select(WxUserChatroomMemberInfo::getUserName)
//                .eq(WxUserChatroomMemberInfo::getUserWcid, wcId)
//                .eq(WxUserChatroomMemberInfo::getChatRoomId, chatRoom)
//                .list();
//        List<String> alreadyChatUserNameList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(alreadyChatMenberList)) {
//            alreadyChatMenberList.forEach(a -> {
//                alreadyChatUserNameList.add(a.getUserName() + chatRoom);
//            });
//        }
//        //4、批量入库
//        List<WxUserChatroomMemberInfo> alreadyChatMenberList2 = new ArrayList<>();
//        chatRoomMemberInfoList.forEach(chatRoomMemberDetailVO -> {
//            chatRoomMemberDetailVO.setUserWcid(wcId);
//            chatRoomMemberDetailVO.setChatRoomId(chatRoom);
//            WxUserChatroomMemberInfo wxUserChatroomMemberInfo = new WxUserChatroomMemberInfo();
//            BeanUtils.copyProperties(chatRoomMemberDetailVO, wxUserChatroomMemberInfo);
//            alreadyChatMenberList2.add(wxUserChatroomMemberInfo);
//        });
//        List<WxUserChatroomMemberInfo> saveList = alreadyChatMenberList2.stream().filter(a -> !alreadyChatUserNameList.contains(a.getUserName() + chatRoom)).collect(Collectors.toList());
//        boolean saveBatch = wxUserChatroomMemberInfoService.saveBatch(saveList);
        return true;
    }
}