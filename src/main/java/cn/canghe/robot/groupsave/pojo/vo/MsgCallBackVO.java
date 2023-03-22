package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/29 16:06:08
 * @description 描述信息
 * @menu
 */
@Data
public class MsgCallBackVO implements Serializable {
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息来源
     */
    private String fromUser;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 消息newid
     */
    private Long newMsgId;

    /**
     * 是否自己发送给自己的消息
     */
    private Boolean self;

    /**
     * 消息发送时间戳
     */
    private Long timestamp;

    /**
     * 消息接收方
     */
    private String toUser;

    /**
     * 登录实例id
     */
    private String wId;

}