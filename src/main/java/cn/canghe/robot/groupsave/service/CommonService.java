package cn.canghe.robot.groupsave.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author bin
 * @date 2020/08/25 11:21:57
 * @description 公共方法
 * @menu
 */
public interface CommonService {

    /**
     * 公共发送post请求方法
     *
     * @param paramMap
     * @param url
     * @return
     */
    JSONObject commonSendPost(Map<String, Object> paramMap, String url);

}