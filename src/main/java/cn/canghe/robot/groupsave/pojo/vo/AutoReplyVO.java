package cn.canghe.robot.groupsave.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/10/09 16:24:34
 * @description 描述信息
 * @menu
 */
@Data
public class AutoReplyVO implements Serializable {

    private String key;
    private String value;
    /**
     * 自定义回复类型 0-文本 1-群邀请
     */
    private Integer type;
}