package com.example.upc.service.impl;

import com.example.upc.dao.InspectThingListMapper;
import com.example.upc.dataobject.InspectThingList;
import com.example.upc.service.InspectThingListService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 22:10
 */
@Service
public class InspectThingListServiceImpl implements InspectThingListService {
    @Autowired
    private InspectThingListMapper inspectThingListMapper;
    @Override
    public List<InspectThingList> getListByParentId(int id) {
        return inspectThingListMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void batchInsert(List<InspectThingList> inspectThingListList,Integer parentId) {
        inspectThingListMapper.deleteByParentId(parentId);
        if (CollectionUtils.isEmpty(inspectThingListList)) {
            return;
        }
        for(InspectThingList inspectThingList : inspectThingListList){
            inspectThingList.setNameAddress(inspectThingList.getNameAddress()==null?"":inspectThingList.getNameAddress());
            inspectThingList.setSatndardTypeAdress(inspectThingList.getSatndardTypeAdress()==null?"":inspectThingList.getSatndardTypeAdress());
            inspectThingList.setUnit(inspectThingList.getUnit()==null?"":inspectThingList.getUnit());
            inspectThingList.setNumber(inspectThingList.getNumber()==null?0:inspectThingList.getNumber());
            inspectThingList.setRemark(inspectThingList.getRemark()==null?"":inspectThingList.getRemark());
            inspectThingList.setParentId(parentId);
            inspectThingList.setOperator("操作人");
            inspectThingList.setOperateIp("124.124.124");
            inspectThingList.setOperateTime(new Date());
        }
        inspectThingListMapper.batchInsert(inspectThingListList);
    }

    @Override
    @Transactional
    public void batchDelete(int id) {
        inspectThingListMapper.deleteByParentId(id);
    }
}
