package cn.canghe.robot.groupsave.common.response;

/**
 * @author bin
 * @date 2020/08/21 12:44:27
 * @description 返回结果
 * @menu
 */

public enum ResultEnum implements IResult {
    OK(200, "success"),
    FAIL(1000, "fail"),
    ALERT(1001, "need login"),
    PARAMETER_CHECK_ERR(3001, "参数校验失败"),
    BAD_REQUEST(4000, "bad_request"),
    NOT_FOUND(4004, "not_found"),
    METHOD_NOT_ALLOWED(4005, "method_not_allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE(4006, "media_type_not_acceptable"),
    TOO_MANY_REQUEST(4007, "too_many_request"),
    ERROR(5000, "系统异常"),
    SERVICE_NOT_FOUND(5004, "service_not_found");

    private int code;
    private String message;

    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultEnum getResultEnum(int code) {
        ResultEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultEnum type = var1[var3];
            if (type.getCode() == code) {
                return type;
            }
        }

        return ERROR;
    }

    public static ResultEnum getResultEnum(String message) {
        ResultEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultEnum type = var1[var3];
            if (type.getMessage().equals(message)) {
                return type;
            }
        }

        return ERROR;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
