package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionConfigLicence;

import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 16:38
 */
public interface SupervisionConfigLicenceService {
    PageResult<SupervisionConfigLicence> getPage (PageQuery pageQuery);
    void insert(SupervisionConfigLicence supervisionConfigLicence);
    void delete(int id);
    void update(SupervisionConfigLicence supervisionConfigLicence);
    List<SupervisionConfigLicence> getByPermiss(int id);
}
