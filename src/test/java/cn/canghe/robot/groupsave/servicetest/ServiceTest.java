package cn.canghe.robot.groupsave.servicetest;

import cn.canghe.robot.groupsave.common.properties.CommonProperties;
import cn.canghe.robot.groupsave.common.util.DateUtils;
import cn.canghe.robot.groupsave.common.util.RobertUtils;
import cn.canghe.robot.groupsave.pojo.entity.WxUserFriends;
import cn.canghe.robot.groupsave.service.LoginService;
import cn.canghe.robot.groupsave.service.TestService;
import cn.canghe.robot.groupsave.service.WxUserFriendsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author bin
 * @date 2020/08/21 13:16:12
 * @description 单元测试
 * @menu
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ServiceTest {
    @Autowired
    private TestService testService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private WxUserFriendsService wxUserFriendsService;
    @Resource
    private RobertUtils robertUtils;

    @Test
    public void testMysql() {
//        Boolean test = testService.test();
//        System.out.println("test:" + test);
    }

    @Test
    public void testLoginWk() {
        String authorization = loginService.getAuthorization();
        System.out.println("authorization:" + authorization);
    }

    @Test
    public void testTime() {
        long creatTime = 1599570798;

        Date date = new Date();
        long time = creatTime*1000;
        Date creatData = new Date(time);

        int datePoor = DateUtils.getDatePoor(date, creatData);
        //如果大于24小时
        if (datePoor >24) {
            System.out.println(datePoor);
        }
    }

    @Test
    public void testProperties() {
        String a = "";
        String especialfriend = commonProperties.getEspecialfriend();
        String[] friend = especialfriend.split(",");
        List<String> list = Arrays.asList(friend);
        boolean contains = list.contains(a);
    }

    @Test
    public void testIn() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        List<WxUserFriends> wlist = wxUserFriendsService.lambdaQuery()
                .eq(WxUserFriends::getUserWcid, "")
                .in(WxUserFriends::getLabelList, list)
                .list();

    }

    @Test
    public void testRobert() {
        String s = robertUtils.sendMsgToRobert("你好啊");
        System.out.println(s);
    }


}