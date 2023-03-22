package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.entity.WxUserChatroom;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信用户群信息表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
public interface WxUserChatroomService extends IService<WxUserChatroom> {

    /**
     * 初始化所有群
     *
     * @param wId
     * @param wcId
     * @return
     */
    Boolean initUserChatRooms(String wId, String wcId);

    /**
     * 邀请群成员
     *
     * @param wId
     * @param chatRoomId
     * @param userList
     * @return
     */
    Boolean inviteChatRoomMember(String wId,String chatRoomId,String userList);

    /**
     * 手动刷新群列表
     *
     * @param wId
     * @param wcId
     * @return
     */
    Boolean refreshChatroom(String wId, String wcId);
}
