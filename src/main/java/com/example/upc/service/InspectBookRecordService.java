package com.example.upc.service;

import com.example.upc.dataobject.InspectBookRecord;

/**
 * @author zcc
 * @date 2019/8/30 21:17
 */
public interface InspectBookRecordService {
    InspectBookRecord getByParentId(int id);
    void insert(InspectBookRecord inspectBookRecord);
    void update(InspectBookRecord inspectBookRecord);
    void delete(int id);
}
