package cn.canghe.robot.groupsave.job;

import cn.canghe.robot.groupsave.common.aop.DistributedLock;
import cn.canghe.robot.groupsave.pojo.entity.WxAdmin;
import cn.canghe.robot.groupsave.pojo.entity.WxSendMsgRecord;
import cn.canghe.robot.groupsave.pojo.entity.WxUser;
import cn.canghe.robot.groupsave.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bin
 * @date 2020/09/02 14:52:13
 * @description 公共的定时任务
 * @menu
 */
@Component
@Slf4j
public class CommonJob {
    @Autowired
    private WxSendMsgRecordService wxSendMsgRecordService;
    @Autowired
    private WxAdminService wxAdminService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxUserFriendsService wxUserFriendsService;
    @Autowired
    private FriendCircleService friendCircleService;
    @Autowired
    private WxUserLabelService wxUserLabelService;


    /**
     * 凌晨2点
     * 定时将所有欢迎语的发送记录清除
     * 过一天后又可以再发送一次欢迎语
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @DistributedLock(taskName = "删除欢迎语消息发送记录", lockName = "DeleteWelcomeSendRecord")
//    @Scheduled(cron = "0 0/2 * * * ?")//每2分钟执行一次，测试
    public void deleteWelcomeSendRecord() {
        log.info("deleteWelcomeSendRecord>>>>>>>>>>>>>");
        wxSendMsgRecordService.lambdaUpdate().eq(WxSendMsgRecord::getOrigin, 2).remove();
    }

    /**
     * 凌晨3点
     * 定时将我的所有好友类表拉取并保存入库【拉取我的好友列表】【刷新】
     * 定时拉取我的标签列表以及标签内的好友【刷新】
     */
    @Scheduled(cron = "0 0 3 * * ?")
//    @DistributedLock(taskName = "拉取我的好友列表", lockName = "RefreshFriendList")
//    @Scheduled(cron = "0 0/2 * * * ?")//每2分钟执行一次，测试
    public void refreshFriendList() {
        log.info("refreshFriendList>>>>>>>>>>>>>");
        //遍历管理员表拿到wcId
        List<WxAdmin> adminList = wxAdminService.lambdaQuery().select(WxAdmin::getWcId).ne(WxAdmin::getWcId, "").list();
        List<String> wcIdList = adminList.stream().map(WxAdmin::getWcId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(wcIdList)) {
            return;
        }
        wcIdList.forEach(wcId -> {
            //根据wcId查询wId
            WxUser wxUser = wxUserService.lambdaQuery().select(WxUser::getWId).eq(WxUser::getWcId, wcId).last("limit 1").one();
            if (wxUser == null) {
                return;
            }
            String wId = wxUser.getWId();
            //拉取好友列表
            Boolean flag1 = wxUserFriendsService.refreshFriendList(wId, wcId);
            if (flag1) {
                log.info("拉取好友列表成功");
            }
            //拉取便签列表以及标签内的好友
            Boolean flag2 = wxUserLabelService.refreshUserLabelList(wId, wcId);
            if (flag2) {
                log.info("拉取标签列表成功");
            }
        });
    }

    /**
     * 凌晨0点开始执行
     * 定时给我的朋友圈点赞
     * 只给当天的朋友圈点赞
     * 将@Scheduled注释打开就可以使用该功能，默认关闭
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    @DistributedLock(taskName = "定时给我的朋友圈点赞", lockName = "snsPraiseMyCircle")
//    @Scheduled(cron = "0 0/2 * * * ?")//每2分钟执行一次，测试
    public void snsPraiseMyCircle() {
        log.info("snsPraiseMyCircle>>>>>>>>>>");
        //遍历管理员表拿到wcId
        List<WxAdmin> adminList = wxAdminService.lambdaQuery().select(WxAdmin::getWcId).ne(WxAdmin::getWcId, "").list();
        List<String> wcIdList = adminList.stream().map(WxAdmin::getWcId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(wcIdList)) {
            return;
        }
        wcIdList.forEach(wcId -> {
            //根据wcId查询wId
            WxUser wxUser = wxUserService.lambdaQuery().select(WxUser::getWId).eq(WxUser::getWcId, wcId).last("limit 1").one();
            if (wxUser == null || StringUtils.isEmpty(wxUser.getWId())) {
                return;
            }
            String wId = wxUser.getWId();
            friendCircleService.snsPraiseMyCircle(wcId, wId);
        });
    }
}