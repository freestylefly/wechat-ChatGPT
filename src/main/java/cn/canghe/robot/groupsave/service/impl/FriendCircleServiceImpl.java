package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.RabbitMqQueues;
import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.common.properties.CommonProperties;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.common.util.DateUtils;
import cn.canghe.robot.groupsave.pojo.dto.SendMsgDTO;
import cn.canghe.robot.groupsave.pojo.vo.FriendCircleSnsVO;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.FriendCircleService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author bin
 * @date 2020/09/03 14:46:04
 * @description 朋友圈点赞相关
 * @menu
 */
@Slf4j
@Service
public class FriendCircleServiceImpl implements FriendCircleService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Boolean snsPraiseAllByWcid(String wcId) {
        log.info("snsPraiseAllByWcid_wcId:{}", wcId);
        //定义全局参数
        String firstPageMd5 = "";
        List<FriendCircleSnsVO> friendCircleSnsVOList = null;
        String maxId = "0";
        boolean flag = true;

        String wId = AdminSessionContext.getWId();

        //1、获取该好友的朋友圈
        Map<String, Object> param1 = new HashMap<>(16);
        param1.put("wId", wId);
        param1.put("wcId", wcId);
        param1.put("firstPageMd5", firstPageMd5);

        //统计此次点赞了几条朋友圈
        int count = 0;
        while (flag) {
            param1.put("maxId", maxId);

            JSONObject json = commonService.commonSendPost(param1, UrlConstant.WK_GETFRIENDCIRCLE_URL);
            JSONObject data = json.getJSONObject("data");
//        firstPageMd5 = data.getString("firstPageMd5");
            friendCircleSnsVOList = JSONObject.parseArray(data.getString("sns"), FriendCircleSnsVO.class);
            if (CollectionUtils.isNotEmpty(friendCircleSnsVOList)) {
                for (int i = 0; i < friendCircleSnsVOList.size(); i++) {
                    //朋友圈id
                    String friendCircleId = friendCircleSnsVOList.get(i).getId();
                    //2、给每一条朋友圈点赞
                    Map<String, Object> param2 = new HashMap<>(16);
                    param2.put("wId", wId);
                    param2.put("id", friendCircleId);
                    //30秒点一个赞
//                    try {
//                        Thread.sleep(1*60*1000);
//                    } catch (InterruptedException e) {
//                        log.error("线程休眠失败：" + e.getMessage());
//                    }
                    try {
                        commonService.commonSendPost(param2, UrlConstant.WK_SNS_PRAISE_URL);
                    } catch (Exception e) {
                        log.error("点赞执行结束");
                    }
                    //取maxId
                    if (i == (friendCircleSnsVOList.size() - 1)) {
                        maxId = friendCircleSnsVOList.get(i).getId();
                    }
                    count++;
                }
            } else {
                flag = false;
            }
        }
        log.info("此次点赞了{}条朋友圈", count);
        return true;
    }

    @Override
    public Boolean snsPraiseMyCircle(String wcId, String wId) {
        //定义全局参数
        String firstPageMd5 = "";
        List<FriendCircleSnsVO> friendCircleSnsVOList = null;
        String maxId = "0";
        boolean flag = true;


        //1、获取该好友的朋友圈
        Map<String, Object> param1 = new HashMap<>(16);
        param1.put("wId", wId);
        param1.put("firstPageMd5", firstPageMd5);

        //统计此次点赞了几条朋友圈
        int counts = 0;

        //循环调用
        while (flag) {
            param1.put("maxId", maxId);

            JSONObject json = null;
            try {
                json = commonService.commonSendPost(param1, UrlConstant.WK_GETCIRCLE_URL);
            } catch (Exception e) {
                log.info("获取朋友圈失败");
            }
            JSONObject data = json.getJSONObject("data");
//        firstPageMd5 = data.getString("firstPageMd5");
            friendCircleSnsVOList = JSONObject.parseArray(data.getString("sns"), FriendCircleSnsVO.class);
            if (CollectionUtils.isNotEmpty(friendCircleSnsVOList)) {
                //当前时间
                Date nowDate = new Date();
                for (int i = 0; i < friendCircleSnsVOList.size(); i++) {
                    FriendCircleSnsVO friendCircleSnsVO = friendCircleSnsVOList.get(i);
                    //2、如果朋友圈发布时间超过3天，直接跳过不点赞和发送消息
                    Date creatDate = new Date(friendCircleSnsVO.getCreateTime() * 1000);
                    int count = DateUtils.getDatePoor(nowDate, creatDate);
                    //取maxId【只取3天以内的数据】
                    if (i == (friendCircleSnsVOList.size() - 1)) {
                        FriendCircleSnsVO friendCircleSnsVO1 = friendCircleSnsVOList.get(i);
                        Date creatDate1 = new Date(friendCircleSnsVO1.getCreateTime() * 1000);
                        int count1 = DateUtils.getDatePoor(nowDate, creatDate1);
                        if (count1 <= 24*3) {
                            maxId = friendCircleSnsVOList.get(i).getId();
                        }
                        flag = false;
                    }
                    if (count > 24*3) {
                        log.info("朋友圈发布时间大于3天，暂不点赞");
                        continue;
                    }
                    //3、监控某个特别关心人物是否在当天发送朋友圈，如果有立马通知到我，防止错过关键消息，并且给好友发一条慰问消息
                    //3.1从朋友圈返回值中取出好友的wcId
                    String userName = friendCircleSnsVO.getUserName();
                    //3.2从配置中取的特别关心好友
                    String especialfriend = commonProperties.getEspecialfriend();
                    String[] friend = especialfriend.split(",");
                    List<String> especialfriendList = Arrays.asList(friend);
                    if (especialfriendList.contains(userName)) {
                        //3.3发送通知
                        SendMsgDTO sendMsgDTO = new SendMsgDTO();
                        sendMsgDTO.setWId(wId);
                        sendMsgDTO.setWcId(userName);
                        sendMsgDTO.setFromUser(wcId);
                        sendMsgDTO.setContent("看了你发的朋友圈，挺有感触的，真的！");
                        sendMsgDTO.setType(0);
                        sendMsgDTO.setOrigin(0);
                        rabbitTemplate.convertAndSend(RabbitMqQueues.WX_SEND_MSG, sendMsgDTO);
                    }


                    //朋友圈id
                    String friendCircleId = friendCircleSnsVO.getId();

                    //4、给每一条朋友圈点赞
                    Map<String, Object> param2 = new HashMap<>(16);
                    param2.put("wId", wId);
                    param2.put("id", friendCircleId);
                    //3秒点一个赞
                    try {
                        Thread.sleep(1 * 3 * 1000);
                    } catch (InterruptedException e) {
                        log.error("线程休眠失败：" + e.getMessage());
                    }
                    try {
                        commonService.commonSendPost(param2, UrlConstant.WK_SNS_PRAISE_URL);
                    } catch (Exception e) {
                        log.error("点赞执行结束");
                    }
                    counts++;
                    //防止无限循环
                    if (counts > 200) {
                        flag = false;
                    }
                }
            } else {
                flag = false;
            }
        }
        log.info("此次点赞了{}条朋友圈", counts);
        return true;
    }
}