package cn.canghe.robot.groupsave.pojo.worktool;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

/**
 * WorkTool 发送指令消息接口入参
 */
@Data
@Accessors(chain = true)
public class WorkToolSendRawMsgDTO {

    /**
     * 通讯类型 固定值=2
     */
    @NotBlank(message = "socketType不能为控")
    private Integer socketType;


    /**
     * 指令消息发送内容
     */
    private ArrayList<SendRawMsgEntity> list;
}
