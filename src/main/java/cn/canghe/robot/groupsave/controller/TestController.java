package cn.canghe.robot.groupsave.controller;

import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.pojo.dto.ReturnTestDTO;
import cn.canghe.robot.groupsave.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 测试表 前端控制器
 * </p>
 *
 * @author cang he
 * @since 2020-08-21
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;


    @PostMapping("/doTest")
    public Result doTest(@RequestBody ReturnTestDTO dto) {
        return Result.ok(testService.test(dto));
    }

}

