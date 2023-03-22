package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.dto.ReturnTestDTO;
import cn.canghe.robot.groupsave.pojo.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 测试表 服务类
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
public interface TestService extends IService<Test> {
    Boolean test(ReturnTestDTO dto);
}
