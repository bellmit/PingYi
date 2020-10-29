package com.example.upc.service;

import com.example.upc.controller.param.MorningCheckOutputParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionCaMorningCheck;

import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 16:09
 */
public interface SupervisionCaMorningCheckService {
    PageResult<SupervisionCaMorningCheck> getPage (PageQuery pageQuery);
    PageResult<SupervisionCaMorningCheck> getPageByCaId (PageQuery pageQuery,Integer id);
    void insert(SupervisionCaMorningCheck supervisionCaMorningCheck);
    void delete(int id);
    void update(SupervisionCaMorningCheck supervisionCaMorningCheck);
    List<MorningCheckOutputParam> output(String start, String end);
}
