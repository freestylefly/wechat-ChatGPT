package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/08 20:02:51
 * @description 描述信息
 * @menu
 */
@Data
public class FriendCircleSnsVO implements Serializable {
    private String id;
    /**
     * 发朋友圈时间
     */
    private Long createTime;

    /**
     * 好友的wcId
     */
    private String userName;

    /**
     * 好友的微信昵称
     */
    private String nickName;
}