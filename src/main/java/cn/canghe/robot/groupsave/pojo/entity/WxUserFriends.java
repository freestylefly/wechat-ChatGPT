package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户好友表
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_user_friends")
public class WxUserFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的微信号
     */
    @TableField("user_wcId")
    private String userWcid;

    /**
     * 好友的微信号wcId
     */
    @TableField("wcId")
    private String wcId;

    /**
     * 微信号
     */
    @TableField("userName")
    private String userName;

    /**
     * 昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 微信号【用户自己设置的真实微信号】
     */
    @TableField("aliasName")
    private String aliasName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 大头像
     */
    @TableField("bigHead")
    private String bigHead;

    /**
     * 标签列表
     */
    @TableField("labelList")
    private String labelList;

    /**
     * 是否需要支持机器人自动聊天，默认为0-不需要 1-需要
     */
    private Integer robert;

    /**
     * 好友的的wxId，都是以v1开头的一串数值
     */
    private String v1;

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
