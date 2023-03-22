package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/25 14:11:59
 * @description 好友详情
 * @menu
 */
@Data
public class FriendDetailVO implements Serializable {
    /**
     * 用户的微信号
     */
    private String userWcid;

    /**
     * 好友的微信号wcId
     */
    private String wcId;

    /**
     * 微信号
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 微信号【用户自己设置的真实微信号】
     */
    private String aliasName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 大头像
     */
    private String bigHead;

    /**
     * 标签列表
     */
    private String labelList;

    /**
     * 好友的的wxId，都是以v1开头的一串数值
     */
    private String v1;
}