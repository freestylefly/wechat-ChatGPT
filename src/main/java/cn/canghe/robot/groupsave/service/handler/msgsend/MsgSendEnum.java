package cn.canghe.robot.groupsave.service.handler.msgsend;

import cn.canghe.robot.groupsave.common.exception.BusinessException;
import cn.canghe.robot.groupsave.common.response.ResultEnum;
import lombok.Getter;

/**
 * @author canghe
 * @date 2020/09/29 13:58:27
 * @description 消息发送类型枚举
 */
@Getter
public enum MsgSendEnum {
    //文本消息
    TEXT_MSG_SEND(0, "textMsgSendHandler"),
    //文件消息
    FILE_MSG_SEND(1, "fileMsgSendHandler"),
    //图片消息
    IMAGE_MSG_SEND(1, "imageMsgSendHandler"),
    //视频消息
    VIDEO_MSG_SEND(1, "videoMsgSendHandler"),
    //语音消息
    VOICE_MSG_SEND(1, "voiceMsgSendHandler"),
    //链接消息
    LINK_MSG_SEND(1, "linkMsgSendHandler"),
    //名片消息
    CARD_MSG_SEND(1, "cardMsgSendHandler"),
    //Emoji消息
    EMOJI_MSG_SEND(1, "emojiMsgSendHandler"),
    //小程序消息
    APP_MSG_SEND(1, "appMsgSendHandler");

    private Integer type;
    private String beanName;

    MsgSendEnum(Integer type, String beanName) {
        this.type = type;
        this.beanName = beanName;
    }

    public static String getBeanName(Integer type) throws BusinessException {
        for (MsgSendEnum msgSendStrategyEnum : MsgSendEnum.values()) {
            if (msgSendStrategyEnum.type.equals(type)) {
                return msgSendStrategyEnum.getBeanName();
            }
        }
        throw new BusinessException(ResultEnum.ERROR.getCode(), "消息发送类型有误");
    }
}