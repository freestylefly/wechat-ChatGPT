package cn.canghe.robot.groupsave.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/21 17:51:08
 * @description 描述信息
 * @menu
 */
@Data
public class MsgContentDTO implements Serializable {

    private String fromUserName;
    private String toUserName;
    private String content;

}