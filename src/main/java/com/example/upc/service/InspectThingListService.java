package com.example.upc.service;

import com.example.upc.dataobject.InspectThingList;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 21:41
 */
public interface InspectThingListService {
    List<InspectThingList> getListByParentId(int id);
    void batchInsert(List<InspectThingList> inspectThingListList,Integer parentId);
    void batchDelete(int id);
}
