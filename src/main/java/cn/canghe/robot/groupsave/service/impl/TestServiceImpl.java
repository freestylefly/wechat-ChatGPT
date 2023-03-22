package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.dao.mapper.TestMapper;
import cn.canghe.robot.groupsave.pojo.dto.ReturnTestDTO;
import cn.canghe.robot.groupsave.pojo.entity.Test;
import cn.canghe.robot.groupsave.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
@Slf4j
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Override
    public Boolean test(ReturnTestDTO dto) {
        String corpId = dto.getSayHello();
        String agentId = "1111";
        String userId = "2222";
        Test test = new Test();
        test.setCorpId(corpId);
        test.setAgentId(agentId);
        test.setUserId(userId);
        return this.save(test);
    }
}
