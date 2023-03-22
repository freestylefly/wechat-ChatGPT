package cn.canghe.robot.groupsave.controller;


import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.service.WxUserChatroomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 微信用户群信息表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/wx-user-chatroom")
public class WxUserChatroomController {
    @Resource
    private WxUserChatroomService wxUserChatroomService;

    /**
     * 手动刷新群列表
     *
     * @return
     */
    @PostMapping("/refreshChatroom")
    public Result refreshChatroom() {
        Boolean flag = wxUserChatroomService.refreshChatroom(AdminSessionContext.getWId(), AdminSessionContext.getWcId());
        String returnData = "刷新群列表";
        if (!flag) {
            returnData = "刷新群列表失败，请联系管理员";
        }
        return Result.ok(returnData);
    }

}

