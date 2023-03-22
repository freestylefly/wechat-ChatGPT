package cn.canghe.robot.groupsave.pojo.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author bin
 * @date 2020/08/28 16:54:34
 * @description 描述信息
 * @menu
 */
@Data
@Accessors(chain = true)
public class AdminLoginForm {
    @NotBlank(message = "userName不能为空")
    private String userName;

    @NotBlank(message = "password不能为空")
    private String password;
}