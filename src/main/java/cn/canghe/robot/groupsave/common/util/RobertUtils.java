package cn.canghe.robot.groupsave.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bin
 * @date 2020/09/29 16:36:24
 * @description 描述信息
 * @menu
 */
@Configuration
@Slf4j
public class RobertUtils {
    @Value("${tulingrobert.apiKey}")
    private  String apiKey;
    @Value("${tulingrobert.userId}")
    private  String userId;
    @Value("${tulingrobert.openurl}")
    private  String openurl;

    public  String sendMsgToRobert(String content) {
        log.info("sendMsgToRobert_content:{}", content);
        Map<String, Object> param = new HashMap<>();
        param.put("reqType", 0);
        Map<String, Object> inputText = Maps.newHashMap();
        Map<String, Object> text = Maps.newHashMap();
        text.put("text", content);
        inputText.put("inputText", text);
        param.put("perception", inputText);
        Map<String, Object> userInfo = Maps.newHashMap();
        userInfo.put("apiKey", apiKey);
        userInfo.put("userId", userId);
        param.put("userInfo", userInfo);
        log.info("sendMsgToRobert_param:{}", JSON.toJSONString(param));
        String returnData = HttpUtil.sendPost(openurl, JSON.toJSONString(param));
        log.info("sendMsgToRobert_returnData:{}", JSON.toJSONString(returnData));
        JSONObject json = JSONObject.parseObject(returnData);
        JSONObject results = (JSONObject) json.getJSONArray("results").get(0);
        String replay = results.getJSONObject("values").getString("text");
        log.info("sendMsgToRobert_replay:{}", replay);
        return replay;
    }
}