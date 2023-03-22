package cn.canghe.robot.groupsave.service;

/**
 * @author bin
 * @date 2020/09/03 14:44:17
 * @description 描述信息
 * @menu
 */
public interface FriendCircleService {

    /**
     * 给某一个好友的所有朋友圈点赞
     *
     * @param wcId
     * @return
     */
    Boolean snsPraiseAllByWcid(String wcId);

    /**
     * 定时给我的朋友圈点赞
     * @param wId 微信登录实例
     * @param wcId 我的wcId
     * @return
     */
    Boolean snsPraiseMyCircle(String wcId, String wId);
}