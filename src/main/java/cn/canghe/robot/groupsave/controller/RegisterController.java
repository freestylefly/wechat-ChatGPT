package cn.canghe.robot.groupsave.controller;

import cn.canghe.robot.groupsave.common.interceptor.TokenNeedless;
import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.common.util.Uuid;
import cn.canghe.robot.groupsave.pojo.entity.WxAdmin;
import cn.canghe.robot.groupsave.pojo.form.RegisterForm;
import cn.canghe.robot.groupsave.service.WxAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bin
 * @date 2020/08/30 00:35:03
 * @description 注册管理员
 * @menu
 */
@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private WxAdminService wxAdminService;

    @TokenNeedless
    @PostMapping("/registeAdmin")
    public Result registeAdmin(@RequestBody RegisterForm form) {
        Integer count = wxAdminService.lambdaQuery().eq(WxAdmin::getUserName, form.getUserName()).count();
        if (count > 0) {
            return Result.error("账号已存在");
        }
        WxAdmin wxAdmin = new WxAdmin();
        BeanUtils.copyProperties(form,wxAdmin);
        String adminUuid = Uuid.generateShortUuid();
        wxAdmin.setUuid(adminUuid);
        return Result.ok(wxAdminService.save(wxAdmin));
    }
}