package cn.canghe.robot.groupsave.common.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author bin
 * @date 2020/08/24 16:12:39
 * @description 描述信息
 * @menu
 */
@Data
@Configuration
public class CommonProperties {

    /**
     * wk用户名和密码
     */
    @Value("${wk.account}")
    private String account;
    @Value("${wk.password}")
    private String password;

    /**
     * 回调地址
     */
    @Value("${wk.httpUrl}")
    private String httpUrl;


    /**
     * 特别关心的好友
     */
    @Value("${friendcircle.especialfriend}")
    private String especialfriend;



}