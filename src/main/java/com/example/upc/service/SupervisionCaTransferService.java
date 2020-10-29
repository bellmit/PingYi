package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionCaTransfer;

/**
 * @author zcc
 * @date 2019/6/26 17:11
 */
public interface SupervisionCaTransferService {
    PageResult<SupervisionCaTransfer> getPage (PageQuery pageQuery);
    PageResult<SupervisionCaTransfer> getPageByCaId (PageQuery pageQuery,Integer id);
    void insert(SupervisionCaTransfer supervisionCaTransfer);
    void delete(int id);
    void update(SupervisionCaTransfer supervisionCaTransfer);
}
