package cn.canghe.robot.groupsave.common.response;

/**
 * @author bin
 * @date 2020/08/21 13:24:28
 * @description 返回结果集
 * @menu
 */
import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8708569195046968619L;
    private Integer code;
    private String msg;
    private T data;

    private Result() {
        this.setCode(ResultEnum.OK.getCode());
        this.setMsg(ResultEnum.OK.getMessage());
    }

    public static <T> Result<T> ok() {
        return new Result();
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        Result<T> result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(ResultEnum.ERROR.getMessage());
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
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

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
