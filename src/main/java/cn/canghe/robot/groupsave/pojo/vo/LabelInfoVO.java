package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/17 22:17:43
 * @description 描述信息
 * @menu
 */
@Data
public class LabelInfoVO implements Serializable {

    /**
     * 用户的微信号
     */
    private String userWcid;

    /**
     * 标签名
     */
    private String labelName;

    /**
     * 标签id
     */
    private Integer labelId;

    /**
     * 标签内好友wcid
     */
    private String userName;

    /**
     * 标签内好友昵称
     */
    private String nickName;
}