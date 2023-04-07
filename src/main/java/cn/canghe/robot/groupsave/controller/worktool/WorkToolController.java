package cn.canghe.robot.groupsave.controller.worktool;

import cn.canghe.robot.groupsave.common.interceptor.TokenNeedless;
import cn.canghe.robot.groupsave.common.response.WorkToolResult;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaVO;
import cn.canghe.robot.groupsave.service.worktool.WorkToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bin
 * @date 2020/08/30 00:35:03
 * @description 注册管理员
 * @menu
 */
@Slf4j
@RestController
@RequestMapping("/worktool")
public class WorkToolController {

    @Autowired
    private WorkToolService workToolService;

    @TokenNeedless
    @PostMapping("/qa")
    public WorkToolResult<WorkToolThirdQaVO> qa(@RequestBody WorkToolThirdQaDTO dto) {
        log.info("worktool qa start >>>>>>,spoken = {}", dto.getSpoken());
        return WorkToolResult.ok(workToolService.qaConverse(dto));
    }
}