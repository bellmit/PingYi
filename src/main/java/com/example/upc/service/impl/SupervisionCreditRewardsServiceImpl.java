package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionCreditRewardsMapper;
import com.example.upc.dataobject.SupervisionCreditRewards;
import com.example.upc.service.SupervisionCreditRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:29
 */
@Service
public class SupervisionCreditRewardsServiceImpl implements SupervisionCreditRewardsService {
    @Autowired
    private SupervisionCreditRewardsMapper supervisionCreditRewardsMapper;
    @Override
    public PageResult<SupervisionCreditRewards> getPage(PageQuery pageQuery) {
        int count= supervisionCreditRewardsMapper.countList();
        if (count > 0) {
            List<SupervisionCreditRewards> supervisionCreditRewardsList = supervisionCreditRewardsMapper.getPage(pageQuery);
            PageResult<SupervisionCreditRewards> pageResult = new PageResult<>();
            pageResult.setData(supervisionCreditRewardsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCreditRewards> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionCreditRewards supervisionCreditRewards) {
        supervisionCreditRewards.setOperateIp("124.214.124");
        supervisionCreditRewards.setOperateTime(new Date());
        supervisionCreditRewards.setOperator("操作人");
          supervisionCreditRewardsMapper.insertSelective(supervisionCreditRewards);
    }

    @Override
    @Transactional
    public void update(SupervisionCreditRewards supervisionCreditRewards) {
        SupervisionCreditRewards before = supervisionCreditRewardsMapper.selectByPrimaryKey(supervisionCreditRewards.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新配置不存在");
        }
        supervisionCreditRewards.setOperateIp("124.214.124");
        supervisionCreditRewards.setOperateTime(new Date());
        supervisionCreditRewards.setOperator("操作人");
        supervisionCreditRewardsMapper.updateByPrimaryKeySelective(supervisionCreditRewards);
    }

    @Override
    public void delete(int id) {
        SupervisionCreditRewards before = supervisionCreditRewardsMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionCreditRewardsMapper.deleteByPrimaryKey(id);
    }

}
