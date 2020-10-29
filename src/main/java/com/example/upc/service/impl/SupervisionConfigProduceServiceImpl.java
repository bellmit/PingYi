package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionConfigProduceMapper;
import com.example.upc.dataobject.SupervisionConfigProduce;
import com.example.upc.service.SupervisionConfigProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:23
 */
@Service
public class SupervisionConfigProduceServiceImpl implements SupervisionConfigProduceService {
    @Autowired
    private SupervisionConfigProduceMapper supervisionConfigProduceMapper;
    @Override
    public PageResult<SupervisionConfigProduce> getPage(PageQuery pageQuery) {
        int count= supervisionConfigProduceMapper.countList();
        if (count > 0) {
            List<SupervisionConfigProduce> supervisionConfigProduceList = supervisionConfigProduceMapper.getPage(pageQuery);
            PageResult<SupervisionConfigProduce> pageResult = new PageResult<>();
            pageResult.setData(supervisionConfigProduceList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionConfigProduce> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionConfigProduce supervisionConfigProduce) {
        supervisionConfigProduce.setOperateIp("124.214.124");
        supervisionConfigProduce.setOperateTime(new Date());
        supervisionConfigProduce.setOperator("操作人");
          supervisionConfigProduceMapper.insertSelective(supervisionConfigProduce);
    }

    @Override
    @Transactional
    public void update(SupervisionConfigProduce supervisionConfigProduce) {
        SupervisionConfigProduce before = supervisionConfigProduceMapper.selectByPrimaryKey(supervisionConfigProduce.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新配置不存在");
        }
        supervisionConfigProduce.setOperateIp("124.214.124");
        supervisionConfigProduce.setOperateTime(new Date());
        supervisionConfigProduce.setOperator("操作人");
        supervisionConfigProduceMapper.updateByPrimaryKeySelective(supervisionConfigProduce);
    }

    @Override
    public void delete(int id) {
        SupervisionConfigProduce before = supervisionConfigProduceMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigProduceMapper.deleteByPrimaryKey(id);
    }
}
