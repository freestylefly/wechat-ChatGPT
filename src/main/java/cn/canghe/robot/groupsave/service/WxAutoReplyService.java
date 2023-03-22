package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.entity.WxAutoReply;
import cn.canghe.robot.groupsave.pojo.vo.AutoReplyVO;
import cn.canghe.robot.groupsave.pojo.vo.MsgCallBackVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息自定义回复表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-10-09
 */
public interface WxAutoReplyService extends IService<WxAutoReply> {

    /**
     * 自定义回复
     *
     * @param msgCallBackVO
     * @param userWcId
     * @param key
     * @return
     */
    String autoReply(MsgCallBackVO msgCallBackVO,String userWcId, String key);

    /**
     * 设置自定义回复关键词
     *
     * @param autoReplyVO
     * @return
     */
    Boolean setAutoReply(AutoReplyVO autoReplyVO);
}
