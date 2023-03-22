package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信用户群信息表
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_user_chatroom")
public class WxUserChatroom implements Serializable {

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
     * 群名称
     */
    private String nickName;

    /**
     * 群主微信号
     */
    private String chatRoomOwner;

    /**
     * 大头像
     */
    private String bigHeadImgUrl;

    /**
     * 小头像
     */
    private String smallHeadImgUrl;

    /**
     * 群v1
     */
    private String v1;


    /**
     * 群成员数
     */
    private Integer memberCount;

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
