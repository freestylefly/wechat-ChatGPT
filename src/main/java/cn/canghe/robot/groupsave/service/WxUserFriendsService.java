package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.entity.WxUserFriends;
import cn.canghe.robot.groupsave.pojo.form.CopyFriendsFromOtherForm;
import cn.canghe.robot.groupsave.pojo.vo.ZombieFansVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 微信用户好友表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
public interface WxUserFriendsService extends IService<WxUserFriends> {

    /**
     * 初始化好友
     *
     * @param wId
     * @param wcId
     * @return
     */
    Boolean initUserFriends(String wId, String wcId);


    /**
     * 检测僵尸粉
     * 给到通知告诉自己这些人已经把我删除了
     *
     * @return
     */
    List<ZombieFansVO> checkZombieFans();


    /**
     * 批量添加好友
     * 小号添加大号的好友
     * 支持按照备注、昵称、性别、 标签名【标签名：多个用英文逗号隔开】进行筛选添加
     *
     * @param form
     * @return
     */
    Boolean copyFriendsFromOther(CopyFriendsFromOtherForm form);


    /**
     * 手动刷新好友列表
     *
     * @param wId
     * @param wcId
     * @return
     */
    Boolean refreshFriendList(String wId, String wcId);

}
