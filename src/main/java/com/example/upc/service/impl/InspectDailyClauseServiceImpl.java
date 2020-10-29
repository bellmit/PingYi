package com.example.upc.service.impl;

import com.example.upc.controller.param.InspectDailyClauseParam;
import com.example.upc.dao.InspectClauseConfMapper;
import com.example.upc.dao.InspectDailyCluseMapper;
import com.example.upc.dao.InspectDailyFoodMapper;
import com.example.upc.dataobject.InspectDailyCluse;
import com.example.upc.service.InspectDailyClauseService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zcc
 * @date 2019/5/18 18:59
 */
@Service
public class InspectDailyClauseServiceImpl implements InspectDailyClauseService {
    @Autowired
    private InspectClauseConfMapper inspectClauseConfMapper;
    @Autowired
    private InspectDailyCluseMapper inspectDailyCluseMapper;
    @Override
    public List<InspectDailyClauseParam> getList(int id,int industryId) {
        return inspectClauseConfMapper.getList(id,industryId);
    }

    @Override
    public List<InspectDailyClauseParam> getEmptyList(int industryId) {
        return inspectClauseConfMapper.getEmptyList(industryId);
    }

    @Override
    @Transactional
    public void changeDailyClauseList(List<InspectDailyClauseParam> inspectDailyClauseParamList, int dailyId) {
        inspectDailyCluseMapper.deleteByDailyId(dailyId);

        if (CollectionUtils.isEmpty(inspectDailyClauseParamList)) {
            return;
        }
        List<InspectDailyCluse> inspectDailyCluseList = Lists.newArrayList();
        for (InspectDailyClauseParam inspectDailyClauseParam : inspectDailyClauseParamList) {
            InspectDailyCluse inspectDailyCluse = new InspectDailyCluse();
            inspectDailyCluse.setInspectDailyFood(dailyId);
            inspectDailyCluse.setInspectClauseConf(inspectDailyClauseParam.getId());
            inspectDailyCluse.setResult(inspectDailyClauseParam.getResult()==null?0:inspectDailyClauseParam.getResult());
            inspectDailyCluse.setRemark(inspectDailyClauseParam.getResultRemark()==null?"":inspectDailyClauseParam.getResultRemark());
            inspectDailyCluse.setDocument(inspectDailyClauseParam.getDocument()==null?"":inspectDailyClauseParam.getDocument());
            inspectDailyCluse.setOperator("操作人");
            inspectDailyCluse.setOperateIp("124.124.124");
            inspectDailyCluse.setOperateTime(new Date());
            inspectDailyCluseList.add(inspectDailyCluse);
        }
        inspectDailyCluseMapper.batchInsert(inspectDailyCluseList);
    }
}
