package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.common.util.AdminSessionContext;
import cn.canghe.robot.groupsave.dao.mapper.WxUserFriendsMapper;
import cn.canghe.robot.groupsave.pojo.entity.WxUserFriends;
import cn.canghe.robot.groupsave.pojo.entity.WxUserLabel;
import cn.canghe.robot.groupsave.pojo.form.CopyFriendsFromOtherForm;
import cn.canghe.robot.groupsave.pojo.vo.FriendDetailVO;
import cn.canghe.robot.groupsave.pojo.vo.ZombieFansVO;
import cn.canghe.robot.groupsave.pojo.vo.ZombieVO;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.WxUserFriendsService;
import cn.canghe.robot.groupsave.service.WxUserLabelService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 微信用户好友表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-08-25
 */
@Service
@Slf4j
public class WxUserFriendsServiceImpl extends ServiceImpl<WxUserFriendsMapper, WxUserFriends> implements WxUserFriendsService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private WxUserLabelService wxUserLabelService;

    @Override
    public Boolean initUserFriends(String wId, String wcId) {
        log.info("initUserFriends>>>>>>>>>>>>>");
        //1、刷新好友列表
        Map<String, Object> paramMap1 = new HashMap<>(4);
        paramMap1.put("wId", wId);
        commonService.commonSendPost(paramMap1, UrlConstant.WK_INITIALIZEFRIENDS_URL);

        //2、获取好友列表
        JSONObject json1 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETFRIENDS_URL);
        JSONArray jsonArray1 = json1.getJSONArray("data");
        List<String> friendsList = JSONObject.parseArray(jsonArray1.toJSONString(), String.class);

        //3、获取当前客户已入库的所有好友列表
        List<String> alreadyFriendsWcIdList = new ArrayList<>();
        List<WxUserFriends> wxUserFriendsList = this.lambdaQuery().select(WxUserFriends::getWcId).eq(WxUserFriends::getUserWcid, wcId).list();
        if (!CollectionUtils.isEmpty(wxUserFriendsList)) {
            wxUserFriendsList.forEach(w -> {
                alreadyFriendsWcIdList.add(w.getWcId() + wcId);
            });
        }

        //4、仅仅保存未入库的好友
        List<String> friendWcIdList = friendsList.stream().filter(f -> !alreadyFriendsWcIdList.contains(f + wcId)).collect(Collectors.toList());


        //5、遍历好友列表获取好友详情,并放入详情列表
        List<WxUserFriends> saveFriend = new ArrayList<>();
        friendWcIdList.forEach(friend -> {
            //获取好友详情
            paramMap1.put("wcId", friend);
            JSONObject json2 = commonService.commonSendPost(paramMap1, UrlConstant.WK_GETCONTACT_URL);
            JSONArray jsonArray2 = json2.getJSONArray("data");
            List<FriendDetailVO> friendList = JSONObject.parseArray(jsonArray2.toJSONString(), FriendDetailVO.class);
            FriendDetailVO friendDetailVO = friendList.get(0);
            friendDetailVO.setUserWcid(wcId);
            friendDetailVO.setWcId(friend);
            WxUserFriends wxUserFriends = new WxUserFriends();
            BeanUtils.copyProperties(friendDetailVO, wxUserFriends);
            saveFriend.add(wxUserFriends);
        });
        return this.saveBatch(saveFriend);
    }

    @Override
    public List<ZombieFansVO> checkZombieFans() {
        log.info("checkZombieFans>>>>>>>>>>");
        String wId = AdminSessionContext.getWId();
        String userWcId = AdminSessionContext.getWcId();
        //僵尸粉列表
        List<ZombieFansVO> list = new ArrayList<>();

        /*
            循环分页查询好友列表，每次只查询20条数据
         */
        //定义起始id的值
        int start = 0;
        while (true) {
            //1、遍历我保存的所有好友信息【仅取id和wcId字段】
            List<WxUserFriends> userFriendsList = this.lambdaQuery()
                    .ge(WxUserFriends::getId, start)
                    .eq(WxUserFriends::getUserWcid, userWcId)
                    .orderByAsc(WxUserFriends::getId)
                    .last("limit 20")
                    .list();
            if (CollectionUtils.isEmpty(userFriendsList)) {
                break;
            }
            //2、取出所有好友的wcId，并成字符串形式
            StringBuffer sb = new StringBuffer();
            Map<String, Object> friendMap = new HashMap<>(16);
            userFriendsList.forEach(friend -> {
                String wcId = friend.getWcId();
                sb.append(wcId + ",");
                friendMap.put(userWcId + wcId, friend);
            });
            String s = sb.toString();
            String s1 = s.substring(0, s.length() - 1);
            log.info("checkZombieFans_s1:{}", s1);

            //3、检测僵尸粉
            Map<String, Object> param = new HashMap<>(16);
            param.put("wId", wId);
            param.put("wcId", s1);
            JSONObject json = commonService.commonSendPost(param, UrlConstant.WK_CHECK_ZOMBIE_URL);
            List<ZombieVO> zombieVOList = JSONObject.parseArray(json.getString("data"), ZombieVO.class);
            if (CollectionUtils.isEmpty(zombieVOList)) {
                log.info("无法查询到我的好友列表");
                return null;
            }
            for (int i = 0; i < zombieVOList.size(); i++) {
                ZombieVO zombieVO = zombieVOList.get(i);
                if (zombieVO.getStatus() == 0) {
                    continue;
                }
                String zombieWcId = zombieVO.getUserName();
                //查询他的基本信息
                WxUserFriends wxUserFriends = (WxUserFriends) friendMap.get(userWcId + zombieWcId);
                ZombieFansVO zombieFansVO = new ZombieFansVO();
                BeanUtils.copyProperties(wxUserFriends, zombieFansVO);
                list.add(zombieFansVO);
            }

            //为start重新赋值
            start = userFriendsList.get(userFriendsList.size() - 1).getId() + 1;
        }

        log.info("僵尸粉信息如下：{}", JSONObject.toJSONString(list));
        return list;
    }

    @Override
    public Boolean copyFriendsFromOther(CopyFriendsFromOtherForm form) {
        log.info("copyFriendsFromOther_form:{}", JSONObject.toJSONString(form));
        boolean flag = true;
        String wId = AdminSessionContext.getWId();
        String userWcid = form.getUserWcId();
        List<String> labelNameList = form.getLabelName();
        if (userWcid.equals(AdminSessionContext.getWcId())) {
            log.error("不能重复添加好友");
            return false;
        }
        /*
            1、根据条件获取需要拷贝的好友的微信号
         */
        //标签id列表
        List<Integer> labelIdList = new ArrayList<>();
        //如果有根据标签筛选
        if (!CollectionUtils.isEmpty(labelNameList)) {
            labelNameList.forEach(labelName -> {
                //根据标签名找到标签id
                WxUserLabel userLabel = wxUserLabelService.lambdaQuery()
                        .select(WxUserLabel::getLabelId)
                        .eq(WxUserLabel::getUserWcid, userWcid)
                        .eq(WxUserLabel::getLabelName, labelName)
                        .last("limit 1")
                        .one();
                if (userLabel != null) {
                    labelIdList.add(userLabel.getLabelId());
                }
            });
        }
        List<WxUserFriends> list = this.lambdaQuery()
                .select(WxUserFriends::getAliasName)
                .eq(WxUserFriends::getUserWcid, userWcid)
                .eq(StringUtils.isNotEmpty(form.getNickName()), WxUserFriends::getNickName, form.getNickName())
                .eq(Objects.nonNull(form.getSex()), WxUserFriends::getSex, form.getSex())
                .ne(WxUserFriends::getAliasName, "")
                .like(StringUtils.isNotEmpty(form.getRemark()), WxUserFriends::getRemark, form.getRemark())
                .in(!CollectionUtils.isEmpty(labelNameList), WxUserFriends::getLabelList, labelIdList)
                .list();
        //总的好友的微信号AliasNam
        List<String> allAliasNames = list.stream().map(WxUserFriends::getAliasName).collect(Collectors.toList());
        log.info("所有要添加的微信号为：{}", JSONObject.toJSONString(allAliasNames));

        /*
            2、遍历所有微信号去调用搜索好友接口
         */

        Map<String, Object> param1 = new HashMap<>(4);
        param1.put("wId", wId);
        Map<String, Object> param2 = new HashMap<>(4);
        param2.put("wId", wId);
        for (int i = 0; i < allAliasNames.size(); i++) {
            String aliasName = allAliasNames.get(i);
            param1.put("wcId", aliasName);
            //如果已经是我的好友，不再重复添加
            Integer count = this.lambdaQuery()
                    .eq(WxUserFriends::getUserWcid, AdminSessionContext.getWcId())
                    .eq(WxUserFriends::getAliasName, aliasName)
                    .count();
            if (count > 0) {
                log.info("该人已经是你的好友，无需重复添加");
                continue;
            }

            try {
                //2.1调用搜索好友接口,拿到v1和v2的值
                JSONObject json = commonService.commonSendPost(param1, UrlConstant.WK_SEARCHUSER_URL);
                JSONObject json2 = json.getJSONObject("data");
                String v1 = json2.getString("v1");
                String v2 = json2.getString("v2");
            /*
                3、调用添加好友接口
             */
                String verify = StringUtils.isEmpty(form.getVerify()) ? "您好，我是朋友介绍的" : form.getVerify();
                param2.put("v1", v1);
                param2.put("v2", v2);
                param2.put("type", 3);
                param2.put("verify", verify);
                commonService.commonSendPost(param2, UrlConstant.WK_ADDUSER_URL);
                //为防止封号，3分钟调用一次1000 * 60 * 3
                Thread.sleep(1000 * 60 * 3);
            } catch (Exception e) {
                flag = false;
                log.error("{}添加好友失败,失败原因是：{}", userWcid, e);
            }

        }
        //刷新好友列表
        Boolean flag1 = this.initUserFriends(AdminSessionContext.getWId(), AdminSessionContext.getWcId());
        if (flag1) {
            log.info("添加好友成功，并入库好友信息成功");
        }
        return flag;
    }


    @Override
    public Boolean refreshFriendList(String wId, String wcId) {
        //先删除库里面我的所有好友信息
        this.lambdaUpdate().eq(WxUserFriends::getUserWcid, wcId).remove();
        //再重新初始化入库
        return this.initUserFriends(wId, wcId);
    }
}
