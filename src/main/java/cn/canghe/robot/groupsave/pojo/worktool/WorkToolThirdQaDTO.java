package cn.canghe.robot.groupsave.pojo.worktool;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/24 16:38:42
 * @description workTool QA回调接口入参
 * @menu
 */
@Data
@Accessors(chain = true)
public class WorkToolThirdQaDTO implements Serializable {
    /**
     * 问题文本
     */
    @NotBlank(message = "spoken不能为控")
    private String spoken;

    /**
     * 原始问题文本
     */
    @NotBlank(message = "rawSpoken不能为控")
    private String rawSpoken;

    /**
     * 提问者名称
     */
    @NotBlank(message = "receivedName不能为控")
    private String receivedName;


    /**
     * QA所在群名（群聊）
     */
    @NotBlank(message = "groupName不能为控")
    private String groupName;

    /**
     * QA所在群备注名（群聊）
     */
    @NotBlank(message = "groupRemark不能为控")
    private String groupRemark;

    /**
     * QA所在房间类型 1=外部群 2=外部联系人 3=内部群 4=内部联系人
     */
    @NotBlank(message = "roomType不能为控")
    private String roomType;

    /**
     * 是否@机器人（群聊）
     */
    @NotBlank(message = "atMe不能为控")
    private boolean atMe;

    /**
     * 消息类型 0=未知 1=文本 2=图片 5=视频 7=小程序 8=链接 9=文件
     */
    @NotBlank(message = "textType不能为控")
    private Integer textType;

}