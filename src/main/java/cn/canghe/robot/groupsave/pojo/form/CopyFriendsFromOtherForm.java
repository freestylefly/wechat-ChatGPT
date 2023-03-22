package cn.canghe.robot.groupsave.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author bin
 * @date 2020/09/17 20:45:53
 * @description 描述信息
 * @menu
 */
@Data
public class CopyFriendsFromOtherForm {
    /**
     * 传入客户的wcid
     */
    @NotBlank(message = "userWcId不能为控")
    private String userWcId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别 0-女 1-男
     */
    private Integer sex;

    /**
     * 标签名
     */
    private List<String> labelName;

    /**
     * 添加好友申请字段
     */
    private String verify;
}