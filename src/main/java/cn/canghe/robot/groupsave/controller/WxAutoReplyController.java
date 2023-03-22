package cn.canghe.robot.groupsave.controller;


import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.pojo.vo.AutoReplyVO;
import cn.canghe.robot.groupsave.service.WxAutoReplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 消息自定义回复表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/wx-auto-reply")
public class WxAutoReplyController {
    @Resource
    private WxAutoReplyService wxAutoReplyService;


    /**
     * 设置自定义回复关键词
     *
     * @param autoReplyVO
     * @return
     */
    @PostMapping("setAutoReply")
    public Result setAutoReply(@RequestBody AutoReplyVO autoReplyVO) {
        return Result.ok(wxAutoReplyService.setAutoReply(autoReplyVO));
    }
}

