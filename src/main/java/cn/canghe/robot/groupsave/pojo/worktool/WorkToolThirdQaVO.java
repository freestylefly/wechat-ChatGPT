package cn.canghe.robot.groupsave.pojo.worktool;

import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaInfoVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/28 16:53:18
 * @description workTool QA回调接口出参
 * @menu
 */
@Data
public class WorkToolThirdQaVO implements Serializable {
    /**
     * 5000 回答类型为文本
     */
    private Integer type;
    /**
     * 回答结果集合
     */
    private WorkToolThirdQaInfoVO info;

}