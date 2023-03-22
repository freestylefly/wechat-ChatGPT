package cn.canghe.robot.groupsave.pojo.bo;

import lombok.Data;

/**
 * @author bin
 * @date 2020/08/28 18:21:49
 * @description 描述信息
 * @menu
 */
@Data
public class AdminInfo {

    /**
     * 微信登录实例id
     */
    private String wId;

    /**
     * 微信号
     */
    private String wcId;

    /**
     * 管理员uuid【唯一】
     */
    private String uuid;

    /**
     * 管理员账号
     */
    private String userName;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * token
     */
    private String token;
}