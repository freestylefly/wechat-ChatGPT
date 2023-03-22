package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/24 15:36:02
 * @description 描述信息
 * @menu
 */
@Data
public class LoginWkVO implements Serializable {
    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 接口调用凭证
     */
    private String authorization;
}