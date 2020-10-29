package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionConfigCategoryMapper;
import com.example.upc.dataobject.SupervisionConfigCategory;
import com.example.upc.service.SupervisionConfigCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:22
 */
@Service
public class SupervisionConfigCategoryServiceImpl implements SupervisionConfigCategoryService {
    @Autowired
    private SupervisionConfigCategoryMapper supervisionConfigCategoryMapper;
    @Override
    public PageResult<SupervisionConfigCategory> getPage(PageQuery pageQuery) {
        int count= supervisionConfigCategoryMapper.countList();
        if (count > 0) {
            List<SupervisionConfigCategory> supervisionConfigCategoryList = supervisionConfigCategoryMapper.getPage(pageQuery);
            PageResult<SupervisionConfigCategory> pageResult = new PageResult<>();
            pageResult.setData(supervisionConfigCategoryList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionConfigCategory> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionConfigCategory supervisionConfigCategory) {
        supervisionConfigCategory.setOperateIp("124.214.124");
        supervisionConfigCategory.setOperateTime(new Date());
        supervisionConfigCategory.setOperator("操作人");
         supervisionConfigCategoryMapper.insertSelective(supervisionConfigCategory);
    }

    @Override
    @Transactional
    public void update(SupervisionConfigCategory supervisionConfigCategory) {
        SupervisionConfigCategory before = supervisionConfigCategoryMapper.selectByPrimaryKey(supervisionConfigCategory.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigCategory.setOperateIp("124.214.124");
        supervisionConfigCategory.setOperateTime(new Date());
        supervisionConfigCategory.setOperator("操作人");
        supervisionConfigCategoryMapper.updateByPrimaryKeySelective(supervisionConfigCategory);
    }

    @Override
    public List<SupervisionConfigCategory> getByPermiss(int id) {
        return supervisionConfigCategoryMapper.getByIndustry(id);
    }

    @Override
    public void delete(int id) {
        SupervisionConfigCategory before = supervisionConfigCategoryMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigCategoryMapper.deleteByPrimaryKey(id);
    }

}
