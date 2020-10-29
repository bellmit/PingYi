package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionConfigBigClassMapper;
import com.example.upc.dataobject.SupervisionConfigBigClass;
import com.example.upc.service.SupervisionConfigBigClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:20
 */
@Service
public class SupervisionConfigBigClassServiceImpl implements SupervisionConfigBigClassService {
    @Autowired
    private SupervisionConfigBigClassMapper supervisionConfigBigClassMapper;
    @Override
    public PageResult<SupervisionConfigBigClass> getPage(PageQuery pageQuery) {
        int count= supervisionConfigBigClassMapper.countList();
        if (count > 0) {
            List<SupervisionConfigBigClass> supervisionConfigBigClassList = supervisionConfigBigClassMapper.getPage(pageQuery);
            PageResult<SupervisionConfigBigClass> pageResult = new PageResult<>();
            pageResult.setData(supervisionConfigBigClassList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionConfigBigClass> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<SupervisionConfigBigClass> getList() {
        return supervisionConfigBigClassMapper.getList();
    }

    @Override
    @Transactional
    public void insert(SupervisionConfigBigClass supervisionConfigBigClass) {
        supervisionConfigBigClass.setOperateIp("124.214.124");
        supervisionConfigBigClass.setOperateTime(new Date());
        supervisionConfigBigClass.setOperator("操作人");
        supervisionConfigBigClassMapper.insertSelective(supervisionConfigBigClass);
    }

    @Override
    @Transactional
    public void update(SupervisionConfigBigClass supervisionConfigBigClass) {
        SupervisionConfigBigClass before = supervisionConfigBigClassMapper.selectByPrimaryKey(supervisionConfigBigClass.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新配置不存在");
        }
        supervisionConfigBigClass.setOperateIp("124.214.124");
        supervisionConfigBigClass.setOperateTime(new Date());
        supervisionConfigBigClass.setOperator("操作人");
        supervisionConfigBigClassMapper.updateByPrimaryKeySelective(supervisionConfigBigClass);
    }

    @Override
    public void delete(int id) {
        SupervisionConfigBigClass before = supervisionConfigBigClassMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除配置不存在");
        }
        supervisionConfigBigClassMapper.deleteByPrimaryKey(id);
    }

}
