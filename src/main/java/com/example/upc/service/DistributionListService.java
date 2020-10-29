package com.example.upc.service;

import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.dataobject.DistributionList;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface DistributionListService {
    void insert(DistributionList distributionList, SysUser sysUser);
    void update(DistributionList distributionList, SysUser sysUser);
    void delete(int id);
    List<DistributionList> getByEnterpriseId (InspectionSearchParam inspectionSearchParam, int id);
}
