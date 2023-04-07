package cn.canghe.robot.groupsave.service.worktool;

import cn.canghe.robot.groupsave.pojo.worktool.WorkToolSendRawMsgDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaVO;

/**
 * WorkTool 服务类
 */
public interface WorkToolService {

    /**
     * Qa回调接口
     * @param dto
     * @return
     */
    WorkToolThirdQaVO qaConverse(WorkToolThirdQaDTO dto);

    /**
     * 发送指令消息
     * @param dto
     * @return
     */
    String sendRawMessage(WorkToolSendRawMsgDTO dto);
}
