package cn.canghe.robot.groupsave.pojo.dto;

import lombok.Data;

/**
 * @author bin
 * @date 2020/08/24 16:38:42
 * @description 描述信息
 * @menu
 */
@Data
public class IPadLoginDTO {
    /**
     * 登陆方式，传2即可
     */
    private Integer type;


    /**
     * 代理IP通道：1:广州 2: 南京  3:杭州  4:上海 5:深圳（建议开发者随意选择一个登录通道即可）
     */
    private String proxy;
}