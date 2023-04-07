package cn.canghe.robot.groupsave.common.response;

/**
 * @author bin
 * @date 2020/08/21 13:24:28
 * @description 返回结果集 workTool
 * @menu
 */
import java.io.Serializable;

public class WorkToolResult<T> implements Serializable {
    private static final long serialVersionUID = 8708569195046968619L;
    private Integer code;
    private String message;
    private T data;

    private WorkToolResult() {
        this.setCode(WorkToolResultEnum.OK.getCode());
        this.setMessage(WorkToolResultEnum.OK.getMessage());
    }

    public static <T> WorkToolResult<T> ok() {
        return new WorkToolResult();
    }

    public static <T> WorkToolResult<T> ok(T data) {
        WorkToolResult<T> result = new WorkToolResult();
        result.setData(data);
        return result;
    }

    public static <T> WorkToolResult<T> error() {
        WorkToolResult<T> result = new WorkToolResult();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMessage(ResultEnum.ERROR.getMessage());
        return result;
    }

    public static <T> WorkToolResult<T> error(String msg) {
        WorkToolResult<T> result = new WorkToolResult();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMessage(msg);
        return result;
    }

    public static <T> WorkToolResult<T> error(Integer code, String msg) {
        WorkToolResult<T> result = new WorkToolResult();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
