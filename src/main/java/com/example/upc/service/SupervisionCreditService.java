package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionCredit;

/**
 * @author zcc
 * @date 2019/6/26 16:40
 */
public interface SupervisionCreditService {
    PageResult<SupervisionCredit> getPage (PageQuery pageQuery);
    void insert(SupervisionCredit supervisionCredit);
    void delete(int id);
    void update(SupervisionCredit supervisionCredit);
}
