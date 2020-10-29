package com.example.upc.service;

import com.example.upc.dataobject.InspectRetificationNotice;

/**
 * @author zcc
 * @date 2019/8/30 21:31
 */
public interface InspectRetificationNoticeService {
    InspectRetificationNotice getByParentId(int id);
    void insert(InspectRetificationNotice inspectRetificationNotice);
    void update(InspectRetificationNotice inspectRetificationNotice);
    void delete(int id);
}
