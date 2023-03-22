package cn.canghe.robot.groupsave.service.impl;

import cn.canghe.robot.groupsave.common.constant.UrlConstant;
import cn.canghe.robot.groupsave.dao.mapper.WxUserLabelMapper;
import cn.canghe.robot.groupsave.pojo.entity.WxUserLabel;
import cn.canghe.robot.groupsave.pojo.vo.LabelInfoVO;
import cn.canghe.robot.groupsave.service.CommonService;
import cn.canghe.robot.groupsave.service.WxUserLabelService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信用户标签表 服务实现类
 * </p>
 *
 * @author cang he
 * @since 2020-09-17
 */
@Service
@Slf4j
public class WxUserLabelServiceImpl extends ServiceImpl<WxUserLabelMapper, WxUserLabel> implements WxUserLabelService {
    @Autowired
    private CommonService commonService;

    @Override
    public Boolean initUserLabelList(String wId, String userWcId) {
        log.info("初始化标签列表开始>>>>>>>>,wid:{},userWcId:{}", wId, userWcId);
        //1、获取用户标签列表
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("wId", wId);
        JSONObject json1 = commonService.commonSendPost(paramMap, UrlConstant.WK_GETCONTACT_LABELLIST_URL);
        List<LabelInfoVO> labelInfoVOList = JSONObject.parseArray(json1.getString("data"), LabelInfoVO.class);
        List<LabelInfoVO> allLabelInfoVOs = new ArrayList<>();
        labelInfoVOList.forEach(labelInfoVO -> {
            Integer labelId = labelInfoVO.getLabelId();
            //2、根据标签id获取标签内好友列表
            paramMap.put("labelId", labelId);
            JSONObject json2 = commonService.commonSendPost(paramMap, UrlConstant.WK_GETLABEL_CONTACTS_URL);
            List<LabelInfoVO> labelInfoVOs = JSONObject.parseArray(json2.getString("data"), LabelInfoVO.class);
            labelInfoVOs.forEach(labelInfo -> {
                labelInfo.setLabelId(labelId);
                labelInfo.setLabelName(labelInfoVO.getLabelName());
                labelInfo.setUserWcid(userWcId);
            });
            allLabelInfoVOs.addAll(labelInfoVOs);
        });
        //2、查询已入库数据
        List<WxUserLabel> alreadyWxLabelList = this.lambdaQuery()
                .select(WxUserLabel::getUserWcid, WxUserLabel::getLabelId, WxUserLabel::getLabelName, WxUserLabel::getUserName, WxUserLabel::getNickName)
                .eq(WxUserLabel::getUserWcid, userWcId)
                .list();


        //3、入库数据
        List<WxUserLabel> list = new ArrayList<>();
        allLabelInfoVOs.forEach(allLabelInfoVO -> {
            WxUserLabel wxUserLabel = new WxUserLabel();
            BeanUtils.copyProperties(allLabelInfoVO, wxUserLabel);
            if (!alreadyWxLabelList.contains(wxUserLabel)) {
                list.add(wxUserLabel);
            }

        });
        return this.saveBatch(list);
    }

    @Override
    public Boolean refreshUserLabelList(String wId, String userWcId) {
        //先删除该用户所有标签数据
        this.lambdaUpdate().eq(WxUserLabel::getUserWcid,userWcId).remove();
        //2、重新初始化
        return this.initUserLabelList(wId,userWcId);
    }
}
