package cn.canghe.robot.groupsave.service;

import cn.canghe.robot.groupsave.pojo.dto.IPadLoginDTO;
import cn.canghe.robot.groupsave.pojo.dto.LoginWkDTO;
import cn.canghe.robot.groupsave.pojo.dto.WxLoginDTO;
import cn.canghe.robot.groupsave.pojo.form.AdminLoginForm;
import cn.canghe.robot.groupsave.pojo.vo.AdminLoginVO;
import cn.canghe.robot.groupsave.pojo.vo.IPadLoginVO;
import cn.canghe.robot.groupsave.pojo.vo.LoginWkVO;
import cn.canghe.robot.groupsave.pojo.vo.WxLoginVO;

/**
 * @author canghe
 * @date 2020/08/24 15:34:51
 * @description 描述信息
 */

public interface LoginService {

    /**
     * 管理员登录本服务
     *
     * @param form
     * @return
     */
    AdminLoginVO adminLogin(AdminLoginForm form);

    /**
     * 登录平台
     *
     * @param dto
     * @return
     */
    LoginWkVO loginWk(LoginWkDTO dto);

    /**
     * 获取wk接口调用凭证
     *
     * @return
     */
    String getAuthorization();

    /**
     * 获取微信二维码
     *
     * @param dto
     * @return
     */
    IPadLoginVO iPadLogin(IPadLoginDTO dto);

    /**
     * 执行微信登录
     * 包含首次登录和二次登录
     *
     * @param dto
     * @return
     */
    WxLoginVO wxLogin(WxLoginDTO dto);

    /**
     * 退出微信登录
     * 是否删除管理员的wcId
     *
     * @param isDeleteAdminWcId
     * @return
     */
    Boolean logout(Boolean isDeleteAdminWcId);

}