package cn.canghe.robot.groupsave.controller;


import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.service.WxUserLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信用户标签表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/wx-user-label")
public class WxUserLabelController {
    @Autowired
    private WxUserLabelService wxUserLabelService;

    /**
     * 手动刷新好友列表
     *
     * @return
     */
    @PostMapping("/refreshUserLabelList")
    public Result refreshFriendList() {
        Boolean flag = wxUserLabelService.refreshUserLabelList(AdminSessionContext.getWId(), AdminSessionContext.getWcId());
        String returnSth = "刷新标签列表成功";
        if (!flag) {
            returnSth = "刷新标签列表失败，请联系管理员";
        }
        return Result.ok(returnSth);
    }
}

