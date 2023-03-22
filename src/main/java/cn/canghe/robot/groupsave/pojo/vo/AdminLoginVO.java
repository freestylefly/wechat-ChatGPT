package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/28 16:53:18
 * @description 描述信息
 * @menu
 */
@Data
public class AdminLoginVO implements Serializable {
    private String token;
    private String adminUUID;
}