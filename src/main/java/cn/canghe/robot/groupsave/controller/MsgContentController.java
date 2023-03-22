package cn.canghe.robot.groupsave.controller;


import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.pojo.dto.MsgContentDTO;
import cn.canghe.robot.groupsave.service.MsgContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息保存表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
@RestController
@RequestMapping("/msg-content")
@Slf4j
public class MsgContentController {
    @Autowired
    private MsgContentService msgContentService;

    /**
     * 保存@我的群消息【itchat】
     *
     * @param dto
     * @return
     */
    @PostMapping("/doSave")
    public Result doTest(@RequestBody MsgContentDTO dto) {
        return Result.ok(msgContentService.save(dto));
    }

    /**
     * 消息接收服务地址
     *
     * @param msg
     * @return
     */
    @PostMapping("/saveByWk")
    public Result saveByWk(@RequestBody String msg) {
        log.info("saveByWk_msg:{}", msg);
        return Result.ok();
    }

}

