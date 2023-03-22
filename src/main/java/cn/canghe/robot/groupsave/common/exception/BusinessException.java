package cn.canghe.robot.groupsave.common.exception;

/**
 * @author bin
 * @date 2020/08/21 12:40:38
 * @description 自定义异常
 * @menu
 */

import cn.canghe.robot.groupsave.common.response.IResult;
import cn.canghe.robot.groupsave.common.response.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(BusinessException.class);
    private Integer code;
    private String msg;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    protected BusinessException() {
        this(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage());
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }

    public BusinessException(IResult result) {
        super(result.getMessage());
        this.code = result.getCode();
        this.msg = result.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
