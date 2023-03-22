package cn.canghe.robot.groupsave.common.enums;


import cn.canghe.robot.groupsave.common.response.IResult;

/**
 * @author bin
 * @date 2020/07/21 14:53:18
 * @description 异常枚举
 * @menu
 */
public enum ExceptionEnum implements IResult {
    LIMIT_ACCOUNT(1001,"当前用户登录微信号上限已满"),
    LOGIN_ERROR(1002,"登录异常"),
    SECOND_LOGIN(1003,"二次登录失败，请重新扫码登录"),
    SEND_ERROR(1004,"发送请求，无返回数据"),
    ADMIN_FAIL(1005,"账号或者密码错误"),
    //token缺失
    TOKEN_MISS(3001,"token缺失"),
    //参数缺失
    PARAM_MISS(4001, "参数缺失"),
    PARAM_ERROR(4002, "参数错误"),
    RESULT_ERROR(4003, "返回结果异常"),
    BEAN_NULL(4004, "对象为null");

    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}