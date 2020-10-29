package com.example.upc.service;

import com.example.upc.dataobject.InspectAssistBook;

/**
 * @author zcc
 * @date 2019/8/30 21:45
 */
public interface InspectAssistBookService {
    InspectAssistBook getByParentId(int id);
    void insert(InspectAssistBook inspectAssistBook);
    void update(InspectAssistBook inspectAssistBook);
    void delete(int id);
}
