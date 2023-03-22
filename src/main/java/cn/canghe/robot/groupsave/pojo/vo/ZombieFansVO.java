package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/11 17:09:37
 * @description 描述信息
 * @menu
 */
@Data
public class ZombieFansVO implements Serializable {
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