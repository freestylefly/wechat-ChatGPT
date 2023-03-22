package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/25 16:01:31
 * @description 群详情
 * @menu
 */
@Data
public class ChatRoomDetailVO implements Serializable {
    /**
     * 用户的微信号
     */
    private String userWcid;

    /**
     * 群号
     */
    private String chatRoomId;

    /**
     * 群名称
     */
    private String nickName;

    /**
     * 群主微信号
     */
    private String chatRoomOwner;

    /**
     * 大头像
     */
    private String bigHeadImgUrl;

    /**
     * 小头像
     */
    private String smallHeadImgUrl;

    /**
     * 群v1
     */
    private String v1;


    /**
     * 群成员数
     */
    private Integer memberCount;
}