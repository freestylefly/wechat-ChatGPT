package cn.canghe.robot.groupsave.controller;

import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.service.FriendCircleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bin
 * @date 2020/09/03 14:39:51
 * @description 朋友圈相关控制器
 * @menu
 */
@Slf4j
@RestController
@RequestMapping("/friendCircle")
public class FriendCircleController {
    @Autowired
    private FriendCircleService friendCircleService;

    /**
     * 给某一个好友的所有朋友圈点赞
     *
     * @param wcId
     * @return
     */
    @PostMapping("snsPraiseAllByWcid")
    public Result snsPraiseAllByWcid(@RequestParam String wcId) {
        log.info("snsPraiseAllByWcid_wcId:{}", wcId);
        Boolean flag = friendCircleService.snsPraiseAllByWcid(wcId);
        if (flag) {
            return Result.ok("点赞好友朋友圈成功");
        }
        return Result.ok("点赞好友朋友圈失败");
    }
}