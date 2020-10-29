package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SpecialEquipmentTypeOne;
import com.example.upc.dataobject.SpecialEquipmentTypeThree;
import com.example.upc.dataobject.SpecialEquipmentTypeTwo;

public interface SpecialEquipmentTypeService {
    //类别一的一套
    PageResult getPageOne (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam);
    void insertOne(SpecialEquipmentTypeOne specialEquipmentTypeOne);
    void deleteOne(int id);
    void updateOne(SpecialEquipmentTypeOne specialEquipmentTypeOne);
    //类别二的一套
    PageResult getPageTwo (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam);
    PageResult getPageTwoByParent (PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam);
    void insertTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo);
    void deleteTwo(int id);
    void updateTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo);
    //类别三的一套
    PageResult getPageThree (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam);
    PageResult getPageThreeByParent (PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam);
    void insertThree(SpecialEquipmentTypeThree specialEquipmentTypeThree);
    void deleteThree(int id);
    void updateThree(SpecialEquipmentTypeThree specialEquipmentTypeThree);
}
