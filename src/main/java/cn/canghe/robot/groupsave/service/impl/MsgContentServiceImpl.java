package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.dao.mapper.MsgContentMapper;
import cn.canghe.robot.groupsave.pojo.dto.MsgContentDTO;
import cn.canghe.robot.groupsave.pojo.entity.MsgContent;
import cn.canghe.robot.groupsave.service.MsgContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息保存表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
@Service
public class MsgContentServiceImpl extends ServiceImpl<MsgContentMapper, MsgContent> implements MsgContentService {

    @Override
    public Boolean save(MsgContentDTO dto) {
        String fromUserName = dto.getFromUserName();
        String toUserName = dto.getToUserName();
        String content = dto.getContent();
        MsgContent msgContent = new MsgContent();
        msgContent.setFromUserName(fromUserName);
        msgContent.setToUserName(toUserName);
        msgContent.setContent(content);
        return this.save(msgContent);
    }
}
