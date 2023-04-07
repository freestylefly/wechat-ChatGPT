package cn.canghe.robot.groupsave.pojo.worktool;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 指令消息发送内容
 */
@Data
public class SendRawMsgEntity {
    /**
     * 消息类型 固定值=203
     */
    @NotBlank(message = "type不能为控")
    private Integer type;

    /**
     * 昵称或群名
     */
    @NotBlank(message = "titleList不能为控")
    private List<String> titleList;

    /**
     * 发送文本内容 (\n换行)
     */
    @NotBlank(message = "receivedContent不能为控")
    private String receivedContent;

    /**
     * at的人(at所有人用"@所有人")
     */
    private List<String> atList;
}

