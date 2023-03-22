package cn.canghe.robot.groupsave.common.enums;


import cn.canghe.robot.groupsave.common.response.IResult;

/**
 * @author: bin
 * @description:
 * @date: 2020/7/9
 */
public enum TokenResultEnum implements IResult {
    MISS_TOEKN(7001, "缺失TOKEN"),
    UNVALID_TOEKN(7002, "无效TOKEN"),
    UNVALID_ADMIN(7003, "无效用户"),
    EXPIRED_TOKEN(7004, "access_token expired"),
    MISS_ADMIN_UUID(7005, "miss_admin_uuid"),
    MISS_ADMIN(7006, "查询不到管理严信息");
    private Integer code;
    private String message;

    TokenResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}