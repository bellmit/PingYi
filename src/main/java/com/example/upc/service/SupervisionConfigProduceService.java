package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionConfigProduce;

/**
 * @author zcc
 * @date 2019/6/26 16:38
 */
public interface SupervisionConfigProduceService {
    PageResult<SupervisionConfigProduce> getPage (PageQuery pageQuery);
    void insert(SupervisionConfigProduce supervisionConfigProduce);
    void delete(int id);
    void update(SupervisionConfigProduce supervisionConfigProduce);
}
