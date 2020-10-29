package com.example.upc.service;

import com.example.upc.controller.param.InspectPlaceFinancialListParam;
import com.example.upc.dataobject.InspectPlaceFinancialList;

/**
 * @author zcc
 * @date 2019/8/30 21:34
 */
public interface InspectPlaceFinancialListService {
    InspectPlaceFinancialListParam getByParentId(int id);
    void insert(InspectPlaceFinancialListParam inspectPlaceFinancialListParam);
    void update(InspectPlaceFinancialListParam inspectPlaceFinancialListParam);
    void delete(int id);
}
