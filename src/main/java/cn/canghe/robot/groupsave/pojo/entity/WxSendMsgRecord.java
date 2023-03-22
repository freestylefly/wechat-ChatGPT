package cn.canghe.robot.groupsave.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息发送记录表
 * </p>
 *
 * @author cang he
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_wx_send_msg_record")
public class WxSendMsgRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发送方微信实例ID
     */
    @TableField("wId")
    private String wId;

    /**
     * 发送方微信号，对应user表的WcId
     */
    private String fromuser;

    /**
     * 接收方微信号
     */
    private String touser;

    /**
     * 消息发送类型，默认为0发送文本消息：0-文本，1-文件，2-图片，3-视频，4-语音，5-链接，6-名片，7-Emoji，8-小程序
     */
    private Integer type;

    /**
     * 消息发送内容
     */
    private String content;

    /**
     * 消息发送来源，默认0自动发送 0-自动发送 1-手动发送 2-欢迎语
     */
    private Integer origin;

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
