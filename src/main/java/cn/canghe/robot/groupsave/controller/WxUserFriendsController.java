package cn.canghe.robot.groupsave.controller;


import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.pojo.form.CopyFriendsFromOtherForm;
import cn.canghe.robot.groupsave.service.WxUserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 微信用户好友表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/wx-user-friends")
public class WxUserFriendsController {
    @Autowired
    private WxUserFriendsService wxUserFriendsService;

    /**
     * 检测僵尸粉
     * 给到通知告诉自己这些人已经把我删除了
     *
     * @return
     */
    @PostMapping("/checkZombieFans")
    public Result checkZombieFans() {
        return Result.ok(wxUserFriendsService.checkZombieFans());
    }


    /**
     * 批量添加好友
     * 小号添加大号的好友
     * 支持按照备注、昵称、性别、 标签名【标签名：多个用英文逗号隔开】进行筛选添加
     *
     * @param form
     * @return
     */
    @PostMapping("/copyFriendsFromOther")
    public Result copyFriendsFromOther(@RequestBody @Valid CopyFriendsFromOtherForm form) {
        String returnData = "批量添加好友成功，好友申请已发送";
        Boolean flag = wxUserFriendsService.copyFriendsFromOther(form);
        if (!flag) {
            returnData = "批量添加好友失败，请联系管理员";
        }
        return Result.ok(returnData);
    }

    /**
     * 手动刷新好友列表
     *
     * @return
     */
    @PostMapping("/refreshFriendList")
    public Result refreshFriendList() {
        Boolean flag = wxUserFriendsService.refreshFriendList(AdminSessionContext.getWId(), AdminSessionContext.getWcId());
        String returnData = "刷新好友列表成功";
        if (!flag) {
            returnData = "刷新好友列表失败，请联系管理员";
        }
        return Result.ok(returnData);
    }


}

