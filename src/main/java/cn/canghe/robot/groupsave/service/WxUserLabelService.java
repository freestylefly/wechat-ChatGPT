package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.entity.WxUserLabel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信用户标签表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-09-17
 */
public interface WxUserLabelService extends IService<WxUserLabel> {

    /**
     * 初始化标签列表
     *
     * @param wId
     * @param userWcId 用户的wcId
     * @return
     */
    Boolean initUserLabelList(String wId,String userWcId);

    /**
     * 手动刷新标签列表
     *
     * @param wId
     * @param userWcId
     * @return
     */
    Boolean refreshUserLabelList(String wId,String userWcId);
}
