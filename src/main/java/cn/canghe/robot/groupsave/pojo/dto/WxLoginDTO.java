package cn.canghe.robot.groupsave.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author bin
 * @date 2020/08/24 17:38:16
 * @description 描述信息
 * @menu
 */
@Data
public class WxLoginDTO implements Serializable {

    @JsonProperty(value = "wId")
    private String wId;



}