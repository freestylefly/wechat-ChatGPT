package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户标签表
 * </p>
 *
 * @author cang he
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_user_label")
public class WxUserLabel implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的微信号
     */
    @TableField("user_wcId")
    private String userWcid;

    /**
     * 标签id
     */
    @TableField("labelId")
    private Integer labelId;

    /**
     * 标签名
     */
    @TableField("labelName")
    private String labelName;

    /**
     * 标签内好友wcid
     */
    @TableField("userName")
    private String userName;

    /**
     * 标签内好友昵称
     */
    @TableField("nickName")
    private String nickName;

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
