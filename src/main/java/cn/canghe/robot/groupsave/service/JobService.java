package cn.canghe.robot.groupsave.service;

import java.util.Map;

/**
 * @author bin
 * @date 2020/08/26 16:42:50
 * @description 描述信息
 * @menu
 */
public interface JobService {
    /**
     * 初始化单个群
     *
     * @param param
     * @return
     */
    Boolean initUserChatRoom(Map<String, String> param);
}