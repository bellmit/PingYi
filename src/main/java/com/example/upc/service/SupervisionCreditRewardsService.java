package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionCreditRewards;

/**
 * @author zcc
 * @date 2019/6/26 17:12
 */
public interface SupervisionCreditRewardsService {
    PageResult<SupervisionCreditRewards> getPage (PageQuery pageQuery);
    void insert(SupervisionCreditRewards supervisionCreditRewards);
    void delete(int id);
    void update(SupervisionCreditRewards supervisionCreditRewards);
}
