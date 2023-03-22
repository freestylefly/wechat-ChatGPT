package cn.canghe.robot.groupsave.service.handler.msgcallback;
/**
 * @author canghe
 * @date 2020/09/29 14:40:29
 * @description 描述信息
 */

import cn.canghe.robot.groupsave.common.exception.BusinessException;
import lombok.Getter;

/**
 * @author: bin
 * @description:
 * @date: 2020/9/29
 */
@Getter
public enum MsgCallBackEnum {
    //新好友申请
    NEWFRIEND_MSG_CALL_BACK(0, "newFriendMsgCallBackHandler"),
    //添加好友成功
    ADDFRIENDSUCCESS_MSG_CALL_BACK(16, "addfriendSuccessMsgCallBackHandler"),
    //私聊文本消息
    PRIVATECHATTEXT_MSG_CALL_BACK(5, "privateChatTextMsgCallBackHandler"),
    //私聊图片消息
    PRIVATECHATIMAGE_MSG_CALL_BACK(6, "privateChatImageMsgCallBackHandler"),
    //私聊视频消息
    privateChatVIDEO_MSG_CALL_BACK(7, "privateChatVideoMsgCallBackHandler"),
    //私聊语音消息
    PRIVATECHATVOICE_MSG_CALL_BACK(8, "privateChatVoiceMsgCallBackHandler");

    private Integer type;
    private String beanName;

    MsgCallBackEnum(Integer type, String beanName) {
        this.type = type;
        this.beanName = beanName;
    }

    public static String getBeanName(Integer type) throws BusinessException {
        for (MsgCallBackEnum msgCallBackStrategyEnum : MsgCallBackEnum.values()) {
            if (msgCallBackStrategyEnum.type.equals(type)) {
                return msgCallBackStrategyEnum.getBeanName();
            }
        }
//        throw new BusinessException(ResultEnum.ERROR.getCode(), "消息回调类型有误");
        return null;
    }

}