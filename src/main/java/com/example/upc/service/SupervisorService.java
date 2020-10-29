package com.example.upc.service;

import com.example.upc.common.BusinessException;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SupervisorParam;
import com.example.upc.dataobject.SysSupervisor;

public interface SupervisorService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SysSupervisor sysSupervisor);
    void delete(int sId);
    void update(SupervisorParam supervisorParam);
    SysSupervisor selectAllByTelephone(String telephone) throws BusinessException;
}
