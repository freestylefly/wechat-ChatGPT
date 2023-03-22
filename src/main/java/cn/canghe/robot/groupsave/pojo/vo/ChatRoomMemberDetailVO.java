package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/25 16:22:40
 * @description 描述信息
 * @menu
 */
@Data
public class ChatRoomMemberDetailVO implements Serializable {

    /**
     * 用户的微信号
     */
    private String userWcid;

    /**
     * 群号
     */
    private String chatRoomId;

    /**
     * 成员微信号
     */
    private String userName;

    /**
     * 成员名称
     */
    private String nickName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 性别0-女 1-男
     */
    private Integer sex;

    /**
     * 微信号【用户自己设置的真实微信号】
     */
    private String aliasName;

    /**
     * 标签列表
     */
    private String labelList;


    /**
     * 大头像
     */
    private String bigHead;

    /**
     * 小头像
     */
    private String smallHead;

}