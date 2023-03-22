package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/24 16:41:29
 * @description 描述信息
 * @menu
 */
@Data
public class IPadLoginVO implements Serializable {
    /**
     * 登录微信实例
     */
    private String wId;

    /**
     * 扫码登录地址
     */
    private String qrCodeUrl;
}