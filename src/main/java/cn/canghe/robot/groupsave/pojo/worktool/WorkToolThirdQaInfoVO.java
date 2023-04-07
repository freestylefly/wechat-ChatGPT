package cn.canghe.robot.groupsave.pojo.worktool;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/28 16:53:18
 * @description workTool QA回调接口出参  回答结果集合
 * @menu
 */
@Data
public class WorkToolThirdQaInfoVO implements Serializable {
    /**
     * 回答文本(您期望的回复内容) \n可换行 空字符串则不回复
     */
    private String text;


}