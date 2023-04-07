package cn.canghe.robot.groupsave.servicetest;

import cn.canghe.robot.groupsave.common.properties.CommonProperties;
import cn.canghe.robot.groupsave.common.util.DateUtils;
import cn.canghe.robot.groupsave.common.util.RobertUtils;
import cn.canghe.robot.groupsave.pojo.worktool.SendRawMsgEntity;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolSendRawMsgDTO;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaDTO;
import cn.canghe.robot.groupsave.pojo.entity.WxUserFriends;
import cn.canghe.robot.groupsave.pojo.worktool.WorkToolThirdQaVO;
import cn.canghe.robot.groupsave.service.LoginService;
import cn.canghe.robot.groupsave.service.TestService;
import cn.canghe.robot.groupsave.service.worktool.WorkToolService;
import cn.canghe.robot.groupsave.service.WxUserFriendsService;
import org.assertj.core.util.Lists;
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

    @Autowired
    private WorkToolService workToolService;

    @Test
    public void testWorkToolQa() {
        WorkToolThirdQaDTO dto = new WorkToolThirdQaDTO();
        dto.setSpoken("你好啊");
        dto.setRawSpoken("@me 你好啊");
        dto.setReceivedName("苍何");
        dto.setGroupName("测试群1");
        dto.setGroupRemark("测试群1备注名");
        dto.setRoomType("1");
        dto.setAtMe(true);
        dto.setTextType(1);
        WorkToolThirdQaVO workToolThirdQaVO = workToolService.qaConverse(dto);
        System.out.println(workToolThirdQaVO.getInfo());
    }

    @Test
    public void testWorkToolSendQawMsg() {
        WorkToolSendRawMsgDTO dto = new WorkToolSendRawMsgDTO();
        dto.setSocketType(2);
        ArrayList<SendRawMsgEntity> entities = new ArrayList<>();
        SendRawMsgEntity entity = new SendRawMsgEntity();
        entity.setType(203);
        entity.setTitleList(Lists.newArrayList("苍何"));
        entity.setReceivedContent("你好啊");
        entity.setAtList(Lists.newArrayList("@所有人"));
        entities.add(entity);
        dto.setList(entities);
        String response = workToolService.sendRawMessage(dto);
        System.out.println(response);
    }

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