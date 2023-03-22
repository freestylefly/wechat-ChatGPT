package cn.canghe.robot.groupsave.generator;


import cn.canghe.robot.groupsave.common.util.JWTUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bin
 * @date 2020/07/09 14:39:45
 * @description 盛昌token
 * @menu
 */
public class CreateToken {

    public static void main(String[] args) {
        long timeStamp = System.currentTimeMillis()/1000;
        String acsKey = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SmhaMlZ1ZEVsa0lqb2lNVEF3TURBMU9TSXNJbU52Y25CSlpDSTZJbmQzTmpZM09URmpPVEEwTWpWa1ltRXdOU0lzSW1WNGNDSTZNVFU1TlRRNE5qVTJOWDAuYUM4THJST3Bub3loV1M3amZmRkV5cmswaDZDZlNydnBBOTZVb1RsUWUxWQ==";
        Map<String, Object> map = new HashMap<>(2);
        map.put("acsKey", acsKey);
        map.put("timeStamp", timeStamp);

        String token = JWTUtils.createJWT(map);
        System.out.println(token);
        String staticToken = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SmhZM05MWlhraU9pSmFXR3hMVFVkV1dWRlhiRkJoVlhCTVZtcEdVbUZWZUVSVGJXaHBVakpPY0ZReWJFdFRWbFkyVTFSR1QyRlZielZNYlZZMVUyMW9ZVTFzV2pGYVJWWnpZVEJzY1dJeWJFNVdSVVl6VkZWU1FrMVZPVlJUV0U1S1lsVTFNbGt5TlVOVGJIQkVVMVJhU21KdFVYcFViWEJhVFRBNVZWSnRjRkJXUlVWM1ZGZHdWMkV4YkhSU1dHUlBWVEJzZWxOWE1WZE9SMDVFVTFSYVRsWkdWVEZVYkZKU1RrVTFjVlpVU2s5WFJFRjFXVlZOTkZSSVNsTlVNMEoxWWpOc2IxWXhUVE5oYlZwdFVtdFdOV050YzNkaFJGcEVXbXhPZVdSdVFrSlBWRnBXWWpGU2MxVlhWWGhYVVQwOUlpd2lkR2x0WlZOMFlXMXdJam94TlRrM09UZzJORGMwTENKbGVIQWlPakUxT1RreE9UWXdOelY5LmdLNm1kTllZX3Z0cUcxSmp6b0pQTXh2UkJoc18zcW41TlJpREZzaDRhUjA=";

    }

}