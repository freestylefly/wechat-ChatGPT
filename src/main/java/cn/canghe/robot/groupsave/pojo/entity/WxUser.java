package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户表
 * </p>
 *
 * @author cang he
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_user")
public class WxUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信号【唯一】
     */
    @TableField("wcId")
    private String wcId;

    /**
     * 微信实例ID
     */
    @TableField("wId")
    private String wId;

    /**
     * 昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 头像url
     */
    @TableField("headUrl")
    private String headUrl;

    /**
     * 手机上显示的微信号
     */
    @TableField("wAccount")
    private String wAccount;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态:0已失效、1等待扫码、2扫码待确定、3扫码登录成功
     */
    private Integer status;

    /**
     * 是否权限最高：1、默认-2、是
     */
    private Integer type;

    /**
     * 是否开启自动回复，默认为0 0-不开启，1-开启机器人回复，2-开启自定义回复
     */
    private Integer robert;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 创建人编码
     */
    private Integer createdBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    /**
     * 最后更新人ID
     */
    private Integer updatedBy;

    /**
     * 是否删除（0：未删除 1：已删除）
     */
    private Integer isDeleted;


}
