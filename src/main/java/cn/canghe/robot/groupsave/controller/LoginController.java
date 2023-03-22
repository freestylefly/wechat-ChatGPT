package cn.canghe.robot.groupsave.controller;

import cn.canghe.robot.groupsave.common.interceptor.TokenNeedless;
import cn.canghe.robot.groupsave.common.response.Result;
import cn.canghe.robot.groupsave.pojo.dto.IPadLoginDTO;
import cn.canghe.robot.groupsave.pojo.dto.LoginWkDTO;
import cn.canghe.robot.groupsave.pojo.dto.WxLoginDTO;
import cn.canghe.robot.groupsave.pojo.form.AdminLoginForm;
import cn.canghe.robot.groupsave.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author bin
 * @date 2020/08/24 15:25:08
 * @description 登录相关控制层
 * @menu
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 管理员登录本服务
     *
     * @param form
     * @return
     */
    @TokenNeedless
    @PostMapping("/adminLogin")
    public Result adminLogin(@RequestBody @Valid AdminLoginForm form) {
        log.info("adminLogin_form:{}", form);
        return Result.ok(loginService.adminLogin(form));
    }

    /**
     * 第三方登录
     *
     * @param dto
     * @return
     */
    @PostMapping("/loginWk")
    public Result loginWk(@RequestBody LoginWkDTO dto) {
        return Result.ok(loginService.loginWk(dto));
    }

    /**
     * 获取微信二维码
     * 调用本接口得到二维码图片地址
     * 调用执行登录接口（第三步）（此时第三步的接口不会立即返回，是根据用户是否扫码返回数据，最长等待150S）
     * 开发者将本接口返回的二维码让用户去扫码
     * 扫码结束后，方才的执行登录接口会返回登录成功或者登录失败
     * 注意：请求所有接口需在header包裹Authorization(必须)
     *
     * @param dto
     * @return
     */
    @PostMapping("/iPadLogin")
    public Result iPadLogin(@RequestBody IPadLoginDTO dto) {
        return Result.ok(loginService.iPadLogin(dto));
    }


    /**
     * 执行微信登录
     * 此步骤成功后手机上会显示mac登录成功，才可以收发消息及调用其它接口！
     *
     * @param dto
     * @return
     */
    @PostMapping("/wxLogin")
    public Result wxLogin(@RequestBody WxLoginDTO dto) {
        return Result.ok(loginService.wxLogin(dto));
    }

    /**
     * 退出微信登录
     *
     * @return
     */
    @PostMapping("/logout")
    public Result logout(@RequestParam(required = false) Boolean isDeleteAdminWcId) {
        return Result.ok(loginService.logout(isDeleteAdminWcId));
    }

}