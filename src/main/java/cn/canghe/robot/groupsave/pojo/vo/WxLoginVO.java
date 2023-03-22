package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/24 17:38:16
 * @description 描述信息
 * @menu
 */
@Data
public class WxLoginVO implements Serializable {
    /**
     * 手机上显示的微信号
     */
    private String wAccount;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态:0已失效、1等待扫码、2扫码待确定、3扫码登录成功
     */
    private Integer status;

    /**
     * 是否权限最高：1、默认-2、是
     */
    private Integer type;

    /**
     * 头像url
     */
    private String headUrl;

    /**
     * 微信实例ID
     */
    private String wId;

    /**
     * 微信号【唯一】
     */
    private String wcId;




}