package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.dto.MsgContentDTO;
import cn.canghe.robot.groupsave.pojo.entity.MsgContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息保存表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
public interface MsgContentService extends IService<MsgContent> {
    Boolean save(MsgContentDTO dto);
}
