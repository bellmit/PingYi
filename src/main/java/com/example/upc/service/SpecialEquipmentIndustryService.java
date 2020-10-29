package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SpecialEquipmentIndustry;

public interface SpecialEquipmentIndustryService {
    PageResult getPage (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam);
    void insert(SpecialEquipmentIndustry specialEquipmentIndustry);
    void delete(int id);
    void update(SpecialEquipmentIndustry specialEquipmentIndustry);
}
