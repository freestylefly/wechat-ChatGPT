package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/09/11 17:40:09
 * @description 描述信息
 * @menu
 */
@Data
public class ZombieVO implements Serializable {
    private String userName;
    /**
     * 1-僵尸粉 0-普通好友
     */
    private Integer status;
}