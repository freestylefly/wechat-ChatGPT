package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户群成员信息表
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_user_chatroom_member_info")
public class WxUserChatroomMemberInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的微信号
     */
    @TableField("user_wcId")
    private String userWcid;

    /**
     * 群号
     */
    private String chatRoomId;

    /**
     * 成员微信号
     */
    @TableField("userName")
    private String userName;

    /**
     * 成员名称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 个性签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 性别0-女 1-男
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 微信号【用户自己设置的真实微信号】
     */
    @TableField("aliasName")
    private String aliasName;

    /**
     * 标签列表
     */
    @TableField("labelList")
    private String labelList;



    /**
     * 大头像
     */
    @TableField("bigHead")
    private String bigHead;

    /**
     * 小头像
     */
    @TableField("smallHead")
    private String smallHead;

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
