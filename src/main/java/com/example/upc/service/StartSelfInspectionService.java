package com.example.upc.service;

import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.controller.searchParam.StartSelfInspectionPublicSearchParam;
import com.example.upc.dataobject.InspectionList;
import com.example.upc.dataobject.InspectionPosition;
import com.example.upc.dataobject.StartSelfInspection;
import com.example.upc.dataobject.SysUser;

import java.text.ParseException;
import java.util.List;

public interface StartSelfInspectionService {
    void insert(InspectionList inspectionList, SysUser sysUser);
    void update(InspectionList inspectionList, SysUser sysUser);
    void delete(int id);
    List<StartSelfInspection> getByEnterpriseId (InspectionSearchParam inspectionSearchParam, int id);
    List<InspectionPosition> getInspectionPositionByDate (InspectionSearchParam inspectionSearchParam, int id);
    List<StartSelfInspection> getInspectionByPosition(int positionId) throws ParseException;

    List<StartSelfInspectionPublicSearchParam> getInspectionByPositionPublic(InspectionSearchParam inspectionSearchParam, Integer infoId) throws ParseException;
}
