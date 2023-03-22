package cn.canghe.robot.groupsave.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/02 12:24:54
 * @description 描述信息
 * @menu
 */
@Data
public class SendMsgDTO implements Serializable {
    private String wId;
    /**
     * toUser,消息接收方
     */
    private String wcId;
    /**
     * 消息发送方
     */
    private String fromUser;
    private String content;
    /**
     * 消息发送类型，默认为0发送文本消息：0-文本，1-文件，2-图片，3-视频，4-语音，5-链接，6-名片，7-Emoji，8-小程序
     */
    private Integer type;

    /**
     * 消息发送来源，默认0自动发送 0-自动发送 1-手动发送 2-欢迎语
     */
    private Integer origin;
}